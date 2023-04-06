package com.bmc.flow.modules.service;

import com.bmc.flow.modules.database.dto.UserDto;
import com.bmc.flow.modules.database.entities.UserEntity;
import com.bmc.flow.modules.database.entities.catalogs.SeniorityEntity;
import com.bmc.flow.modules.database.entities.records.*;
import com.bmc.flow.modules.database.entities.resourcing.ScheduleEntity;
import com.bmc.flow.modules.database.entities.resourcing.ShrinkageEntity;
import com.bmc.flow.modules.database.repositories.UserRepository;
import com.bmc.flow.modules.database.repositories.records.*;
import com.bmc.flow.modules.database.repositories.resourcing.ScheduleRepository;
import com.bmc.flow.modules.database.repositories.resourcing.ShrinkageRepository;
import com.bmc.flow.modules.resources.base.Pageable;
import com.bmc.flow.modules.service.base.BasicPersistenceService;
import com.bmc.flow.modules.service.base.PageResult;
import com.bmc.flow.modules.service.catalogs.BoardTypeService;
import com.bmc.flow.modules.service.catalogs.DepartmentService;
import com.bmc.flow.modules.service.catalogs.LabelService;
import com.bmc.flow.modules.service.catalogs.SeniorityService;
import com.bmc.flow.modules.utilities.SecurityUtils;
import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.util.UUID.randomUUID;

@ApplicationScoped
public class UserService extends BasicPersistenceService<UserDto, UserEntity> {
  private final UserRepository      userRepo;
  private final AccountRepository   accountRepo;
  private final ProjectRepository   projectRepo;
  private final BoardRepository     boardRepo;
  private final CardRepository      cardRepo;
  private final ScheduleRepository  scheduleRepo;
  private final CommentRepository   commentRepo;
  private final BoardTypeService    boardTypeService;
  private final LabelService        labelService;
  private final ShrinkageRepository shrinkageRepo;
  private final SeniorityService    seniorityService;
  private final DepartmentService   departmentService;
  private final SecurityUtils       secUtils;

  public UserService(final UserRepository userRepo, final AccountRepository accountRepo,
                     final ProjectRepository projectRepo,
                     final BoardRepository boardRepo, final CardRepository cardRepo,
                     final ScheduleRepository scheduleRepo,
                     final CommentRepository commentRepo, final LabelService labelService,
                     final BoardTypeService boardTypeService,
                     final ShrinkageRepository shrinkageRepo, SeniorityService seniorityService,
                     DepartmentService departmentService, final SecurityUtils secUtils) {
    super(userRepo, UserDto.class);
    this.userRepo          = userRepo;
    this.accountRepo       = accountRepo;
    this.projectRepo       = projectRepo;
    this.boardRepo         = boardRepo;
    this.cardRepo          = cardRepo;
    this.scheduleRepo      = scheduleRepo;
    this.commentRepo       = commentRepo;
    this.boardTypeService  = boardTypeService;
    this.labelService      = labelService;
    this.shrinkageRepo     = shrinkageRepo;
    this.seniorityService  = seniorityService;
    this.departmentService = departmentService;
    this.secUtils          = secUtils;
  }

  public Uni<PageResult<UserDto>> findAllInCollectionId(final String collectionName, final UUID collectionId,
                                                        final Pageable pageable) {
    return findAllPaged(userRepo.findAllByCollectionId(collectionName, collectionId, pageable.getSort()),
        "-find-all-users-in-" + collectionName,
        pageable.getPage());
  }

  @ReactiveTransactional
  @Override
  public Uni<UserDto> create(@Valid final UserDto userDto) {

    UserEntity newUser = new UserEntity();
    newUser.setId(randomUUID());
    newUser.setEmail(userDto.getEmail().toLowerCase());
    newUser.setPassword("demo");
    newUser.setCallSign(userDto.getCallSign());
    newUser.setAvatar("https://robohash.org/" + newUser.getId() + "?set=set2");
    newUser.setActive(true);

    ScheduleEntity userSchedule = new ScheduleEntity();
    userSchedule.setId(randomUUID());
    userSchedule.setUser(newUser);
    userSchedule.setHoursADay((short) 8);
    userSchedule.setCreatedBy(newUser);

    AccountEntity firstAccount = new AccountEntity();
    firstAccount.setId(randomUUID());
    firstAccount.setDescription("This is your personal account, you can use it to group multiple projects.");
    firstAccount.setName(newUser.getCallSign() + "'s Personal account");
    firstAccount.setCreatedBy(newUser);
    firstAccount.setCoverImage("https://robohash.org/" + firstAccount.getId());
    firstAccount.setUsers(Set.of(newUser));

    ProjectEntity firstProject = new ProjectEntity();
    firstProject.setId(randomUUID());
    firstProject.setDescription("This is your personal project");
    firstProject.setName(newUser.getCallSign() + "'s personal project");
    firstProject.setAccount(firstAccount);
    firstProject.setCoverImage("https://robohash.org/" + firstProject.getId());
    firstProject.setUsers(Set.of(newUser));
    firstProject.setProjectLead(newUser);
    firstProject.setCreatedBy(newUser);

    BoardEntity kanbanBoard = new BoardEntity();
    kanbanBoard.setId(randomUUID());
    kanbanBoard.setDescription("This is your personal Board");
    kanbanBoard.setName(newUser.getCallSign() + "'s personal board");
    kanbanBoard.setProject(firstProject);
    kanbanBoard.setUsers(Set.of(newUser));
    kanbanBoard.setCreatedBy(newUser);

    CardEntity newCard = new CardEntity();
    newCard.setId(randomUUID());
    newCard.setDescription("This is a default card description");
    newCard.setName(newUser.getCallSign() + "'s default card");
    newCard.setAssignees(Set.of(newUser));
    newCard.setWatchers(Set.of(newUser));
    newCard.setBoard(kanbanBoard);
    newCard.setDueDate(LocalDateTime.of(2025, 10, 10, 10, 10, 10, 10));
    newCard.setCreatedBy(newUser);

    CommentEntity newComment = new CommentEntity();
    newComment.setId(randomUUID());
    newComment.setComment("this is a default comment, it can be formatted with markdown");
    newComment.setCard(newCard);
    newComment.setCreatedBy(newUser);

    Set<ShrinkageEntity> shrinkages = userSchedule.getShrinkages();

    return userRepo
        .persist(newUser)
        // persist entities
        .call(() -> accountRepo.persist(firstAccount))
        .call(() -> projectRepo.persist(firstProject))
        .call(() -> boardRepo.persist(kanbanBoard))
        .call(() -> cardRepo.persist(newCard))
        .call(() -> commentRepo.persist(newComment))
        .call(() -> scheduleRepo.persist(userSchedule))

        //update user with attached entities
        .invoke(() -> newUser.setCreatedBy(newUser))
        .invoke(() -> newUser.setUserSchedule(userSchedule))

        // update entities with existing system data
        .chain(() -> seniorityService.findEntityByName("architect").invoke(newUser::setSeniority))
        .chain(() -> departmentService.findEntityByName("engineering").invoke(newUser::setDepartment))
        .chain(() -> boardTypeService.findEntityByName("kanban").invoke(kanbanBoard::setBoardType))
        .chain(() -> shrinkageRepo.findEntityByName("coffee-break-15-min").invoke(shrinkages::add))
        .chain(() -> shrinkageRepo.findEntityByName("personal-break-10-min").invoke(shrinkages::add))
        .chain(() -> shrinkageRepo.findEntityByName("agile-standUp-10-min").invoke(shrinkages::add))
        .chain(() -> labelService.findEntityByName("personal").invoke(label -> {
          firstAccount.setLabels(Set.of(label));
          firstProject.setLabels(Set.of(label));
          kanbanBoard.setLabels(Set.of(label));
          newCard.setLabels(Set.of(label));
        }))
        .replaceWith(findById(newUser.getId()));
  }

  @ReactiveTransactional
  @Override
  public Uni<Boolean> deleteById(final UUID userId) {
    return userRepo.find("id =?1 and isActive=?2", userId, true)
                   .singleResult().onFailure().recoverWithNull()
                   .onItem().ifNotNull().transform(userToDeactivate -> {
          secUtils.obfuscateUser(userToDeactivate);
          userToDeactivate.setActive(false);
          return TRUE;
        })
                   .onItem().ifNull().continueWith(FALSE);
  }

  public void updateField(final UserEntity toUpdate, final String key, final String value) {
    switch (key) {
      case "email" -> toUpdate.setEmail(value.toLowerCase());
      case "callSign" -> toUpdate.setCallSign(value);
      case "avatar" -> toUpdate.setAvatar(value);
      case "seniority" -> Optional.ofNullable(value)
                                  .ifPresent(seniorityId -> {
                                    SeniorityEntity seniority = new SeniorityEntity();
                                    seniority.setId(UUID.fromString(seniorityId));
                                    toUpdate.setSeniority(seniority);
                                  });

      default -> throw new IllegalStateException("Unexpected value: " + key);
    }

  }

}
