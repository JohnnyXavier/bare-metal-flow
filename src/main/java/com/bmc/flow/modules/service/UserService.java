package com.bmc.flow.modules.service;

import com.bmc.flow.modules.database.dto.UserDto;
import com.bmc.flow.modules.database.dto.UserRegistrationDto;
import com.bmc.flow.modules.database.entities.UserEntity;
import com.bmc.flow.modules.database.entities.catalogs.BoardColumnEntity;
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
import com.bmc.flow.modules.service.catalogs.*;
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
  private final UserRepository        userRepo;
  private final AccountRepository     accountRepo;
  private final ProjectRepository     projectRepo;
  private final BoardRepository       boardRepo;
  private final BoardColumnRepository boardColumnRepo;
  private final CardRepository        cardRepo;
  private final CardStatusService     cardStatusService;
  private final ScheduleRepository    scheduleRepo;
  private final CommentRepository     commentRepo;
  private final BoardTypeService      boardTypeService;
  private final LabelService          labelService;
  private final ShrinkageRepository   shrinkageRepo;
  private final SeniorityService      seniorityService;
  private final DepartmentService     departmentService;
  private final SecurityUtils         secUtils;

  public UserService(final UserRepository userRepo, final AccountRepository accountRepo,
                     final ProjectRepository projectRepo,
                     final BoardRepository boardRepo, BoardColumnRepository boardColumnRepo, final CardRepository cardRepo,
                     CardStatusService cardStatusService, final ScheduleRepository scheduleRepo,
                     final CommentRepository commentRepo, final LabelService labelService,
                     final BoardTypeService boardTypeService,
                     final ShrinkageRepository shrinkageRepo, SeniorityService seniorityService,
                     DepartmentService departmentService, final SecurityUtils secUtils) {
    super(userRepo, UserDto.class);
    this.userRepo          = userRepo;
    this.accountRepo       = accountRepo;
    this.projectRepo       = projectRepo;
    this.boardRepo         = boardRepo;
    this.boardColumnRepo   = boardColumnRepo;
    this.cardRepo          = cardRepo;
    this.cardStatusService = cardStatusService;
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

  @Override
  public Uni<UserDto> create(UserDto fromDto) {
    //TODO: this is a placeholder for creating a user after registration, maybe not required
    return null;
  }

  @ReactiveTransactional
  public Uni<UserDto> register(@Valid final UserRegistrationDto userRegistrationDto) {

    UserEntity newUser = new UserEntity();
    newUser.setId(randomUUID());
    newUser.setEmail(userRegistrationDto.getEmail().toLowerCase());
    newUser.setPassword("demo");
    newUser.setCallSign(userRegistrationDto.getCallSign());
    newUser.setAvatar("https://robohash.org/" + newUser.getId() + "?set=set3");
    newUser.setActive(true);

    ScheduleEntity userSchedule = new ScheduleEntity();
    userSchedule.setId(randomUUID());
    userSchedule.setUser(newUser);
    userSchedule.setHoursADay((short) 8);
    userSchedule.setCreatedBy(newUser);

    AccountEntity firstAccount = new AccountEntity();
    firstAccount.setId(randomUUID());
    firstAccount.setDescription("This is your personal account, you can use it to group multiple projects.");
    firstAccount.setName(newUser.getCallSign() + "'s account");
    firstAccount.setCreatedBy(newUser);
    firstAccount.setCoverImage("https://loremflickr.com/g/800/400/computer");
    firstAccount.setUsers(Set.of(newUser));

    ProjectEntity firstProject = new ProjectEntity();
    firstProject.setId(randomUUID());
    firstProject.setDescription("This is your personal project");
    firstProject.setName(newUser.getCallSign() + "'s project");
    firstProject.setAccount(firstAccount);
    firstProject.setCoverImage("https://loremflickr.com/g/800/400/car");
    firstProject.setUsers(Set.of(newUser));
    firstProject.setProjectLead(newUser);
    firstProject.setCreatedBy(newUser);

    firstAccount.setProjects(Set.of(firstProject));

    BoardEntity kanbanBoard = new BoardEntity();
    kanbanBoard.setId(randomUUID());
    kanbanBoard.setDescription("This is your personal Board");
    kanbanBoard.setCoverImage("https://loremflickr.com/g/800/400/sports");
    kanbanBoard.setName(newUser.getCallSign() + "'s board");
    kanbanBoard.setProject(firstProject);
    kanbanBoard.setAccount(firstAccount);
    kanbanBoard.setUsers(Set.of(newUser));
    kanbanBoard.setCreatedBy(newUser);
    kanbanBoard.setIsFavorite(true);

    BoardColumnEntity boardColumn = new BoardColumnEntity();
    boardColumn.setId(randomUUID());
    boardColumn.setBoard(kanbanBoard);
    boardColumn.setProject(firstProject);
    boardColumn.setAccount(firstAccount);
    boardColumn.setCreatedBy(newUser);

    kanbanBoard.setBoardColumns(Set.of(boardColumn));

    CardEntity newCard = new CardEntity();
    newCard.setId(randomUUID());
    newCard.setDescription("This is a default card description");
    newCard.setName(newUser.getCallSign() + "'s default card");
    newCard.setCoverImage("https://loremflickr.com/g/800/400/game");
    newCard.setAssignees(Set.of(newUser));
    newCard.setWatchers(Set.of(newUser));
    newCard.setBoard(kanbanBoard);
    newCard.setBoardColumn(boardColumn);
    newCard.setDueDate(LocalDateTime.of(2025, 10, 10, 10, 10, 10, 10));
    newCard.setCreatedBy(newUser);


    boardColumn.setCards(Set.of(newCard));

    CommentEntity newComment = new CommentEntity();
    newComment.setId(randomUUID());
    newComment.setComment("this is a default comment, add as many as you like!");
    newComment.setCard(newCard);
    newComment.setCreatedBy(newUser);

    Set<ShrinkageEntity> shrinkages = userSchedule.getShrinkages();

    return userRepo
        .persist(newUser)
        // persist entities
        .call(() -> accountRepo.persist(firstAccount))
        .call(() -> projectRepo.persist(firstProject))
        .call(() -> boardRepo.persist(kanbanBoard))
        .call(() -> boardColumnRepo.persist(boardColumn))
        .call(() -> cardRepo.persist(newCard))
        .call(() -> commentRepo.persist(newComment))
        .call(() -> scheduleRepo.persist(userSchedule))

        //update user with attached entities
        .invoke(() -> newUser.setCreatedBy(newUser))
        //        .invoke(() -> newUser.setUserSchedule(userSchedule))

        // update entities with existing system data
        .chain(() -> seniorityService.findEntityByName("architect").invoke(newUser::setSeniority))
        .chain(() -> departmentService.findEntityByName("engineering").invoke(newUser::setDepartment))
        .chain(() -> boardTypeService.findEntityByName("kanban").invoke(kanbanBoard::setBoardType))
        .chain(() -> cardStatusService.findEntityByName("new").invoke(boardColumn::setStatus).invoke(newCard::setCardStatus))
        .chain(() -> shrinkageRepo.findEntityByName("coffee-break-15-min").invoke(shrinkages::add))
        .chain(() -> shrinkageRepo.findEntityByName("personal-break-10-min").invoke(shrinkages::add))
        .chain(() -> shrinkageRepo.findEntityByName("agile-standUp-10-min").invoke(shrinkages::add))
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
