package com.bmc.flow.modules.service;

import com.bmc.flow.modules.database.dto.UserDto;
import com.bmc.flow.modules.database.entities.UserEntity;
import com.bmc.flow.modules.database.entities.catalogs.SeniorityEntity;
import com.bmc.flow.modules.database.entities.records.*;
import com.bmc.flow.modules.database.repositories.UserRepository;
import com.bmc.flow.modules.database.repositories.records.*;
import com.bmc.flow.modules.resources.base.Pageable;
import com.bmc.flow.modules.service.base.BasicPersistenceService;
import com.bmc.flow.modules.service.base.PageResult;
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

  private final UserRepository userRepo;

  private final AccountRepository accountRepo;

  private final PortfolioRepository portfolioRepo;

  private final ProjectRepository projectRepo;

  private final BoardRepository boardRepo;

  private final CardRepository cardRepo;

  private final CommentRepository commentRepo;

  private final SecurityUtils secUtils;

  public UserService(final UserRepository userRepo, final AccountRepository accountRepo, final PortfolioRepository portfolioRepo,
                     final ProjectRepository projectRepo, final BoardRepository boardRepo, final CardRepository cardRepo,
                     final CommentRepository commentRepo, final SecurityUtils secUtils) {
    super(userRepo, UserDto.class);
    this.userRepo = userRepo;
    this.accountRepo = accountRepo;
    this.portfolioRepo = portfolioRepo;
    this.projectRepo = projectRepo;
    this.boardRepo = boardRepo;
    this.cardRepo = cardRepo;
    this.commentRepo = commentRepo;
    this.secUtils = secUtils;
  }


  public Uni<PageResult<UserDto>> findAllByProjectId(final UUID projectId, final Pageable pageable) {
    return findAllPaged(userRepo.findAllByProjectId(projectId, pageable.getSort()), pageable.getPage());
  }

  public Uni<PageResult<UserDto>> findAllByBoardId(final UUID boardId, final Pageable pageable) {
    return findAllPaged(userRepo.findAllByBoardId(boardId, pageable.getSort()), pageable.getPage());
  }

  @ReactiveTransactional
  @Override
  public Uni<UserDto> create(@Valid final UserDto userDto) {

    UserEntity newUser = new UserEntity();
    newUser.setId(randomUUID());
    newUser.setFirstName(userDto.getFirstName());
    newUser.setLastName(userDto.getLastName());
    newUser.setEmail(userDto.getEmail());
    newUser.setCallSign(userDto.getCallSign());
    newUser.setActive(true);
    newUser.setCreatedBy(newUser.getCreatedBy());

    PortfolioEntity newPortfolio = new PortfolioEntity();
    newPortfolio.setId(randomUUID());
    newPortfolio.setDescription("default portfolio's description");
    newPortfolio.setName(newUser.getFirstName() + "'s default portfolio");
    newPortfolio.setCreatedBy(newUser);
    newPortfolio.setIsDefault(TRUE);

    AccountEntity newAccount = new AccountEntity();
    newAccount.setId(randomUUID());
    newAccount.setDescription("default account's description");
    newAccount.setName(newUser.getFirstName() + "'s default account");
    newAccount.setPortfolio(newPortfolio);
    newAccount.setCreatedBy(newUser);

    ProjectEntity newProject = new ProjectEntity();
    newProject.setId(randomUUID());
    newProject.setDescription("default project's description");
    newProject.setName(newUser.getFirstName() + "'s default project");
    newProject.setAccount(newAccount);
    newProject.getUsers().add(newUser);
    newProject.setCreatedBy(newUser);

    BoardEntity board = new BoardEntity();
    board.setId(randomUUID());
    board.setDescription("default Board's description");
    board.setName(newUser.getFirstName() + "'s default board");
    board.setProject(newProject);
    board.getUsers().add(newUser);
    board.setCreatedBy(newUser);

    CardEntity newCard = new CardEntity();
    newCard.setId(randomUUID());
    newCard.setDescription("default card's description");
    newCard.setName(newUser.getFirstName() + "'s default card");
    newCard.setAssignees(Set.of(newUser));
    newCard.setWatchers(Set.of(newUser));
    newCard.setBoard(board);
    newCard.setDueDate(LocalDateTime.of(2025, 10, 10, 10, 10, 10, 10));
    newCard.setCreatedBy(newUser);

    CommentEntity newComment = new CommentEntity();
    newComment.setId(randomUUID());
    newComment.setComment("this is a default comment, it can be formatted with markdown");
    newComment.setCard(newCard);
    newComment.setCreatedBy(newUser);

    return userRepo.persist(newUser)
                   .call(() -> accountRepo.persist(newAccount))
                   .call(() -> portfolioRepo.persist(newPortfolio))
                   .call(() -> projectRepo.persist(newProject))
                   .call(() -> boardRepo.persist(board))
                   .call(() -> cardRepo.persist(newCard))
                   .call(() -> commentRepo.persist(newComment))
                   .invoke(() -> newUser.setCreatedBy(newUser))
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
      case "firstName" -> toUpdate.setFirstName(value);
      case "lastName" -> toUpdate.setLastName(value);
      case "email" -> toUpdate.setEmail(value);
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
