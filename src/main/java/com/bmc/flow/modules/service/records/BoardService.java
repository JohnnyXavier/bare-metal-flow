package com.bmc.flow.modules.service.records;

import com.bmc.flow.modules.database.dto.records.BoardDto;
import com.bmc.flow.modules.database.entities.UserEntity;
import com.bmc.flow.modules.database.entities.catalogs.BoardTypeEntity;
import com.bmc.flow.modules.database.entities.records.BoardEntity;
import com.bmc.flow.modules.database.entities.records.ProjectEntity;
import com.bmc.flow.modules.database.repositories.records.BoardRepository;
import com.bmc.flow.modules.resources.base.Pageable;
import com.bmc.flow.modules.service.base.BasicPersistenceService;
import com.bmc.flow.modules.service.base.PageResult;
import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static java.util.UUID.randomUUID;

@ApplicationScoped
public class BoardService extends BasicPersistenceService<BoardDto, BoardEntity> {

  private final BoardRepository repository;

  public BoardService(final BoardRepository repository) {
    super(repository, BoardDto.class);
    this.repository = repository;
  }

  public Uni<List<BoardDto>> getAllByProjectId(final UUID projectId) {
    return repository.findAllByProjectId(projectId);
  }

  public Uni<PageResult<BoardDto>> findAllByUserIdPaged(UUID userId, final Pageable pageable) {
    return findAllPaged(repository.find("createdBy.id", pageable.getSort(), userId), "-all-boards-by-user",
        pageable.getPage());
  }

  @ReactiveTransactional
  public Uni<BoardDto> create(@Valid final BoardDto boardDto) {
    UserEntity boardCreator = new UserEntity();
    boardCreator.setId(boardDto.getCreatedBy());

    ProjectEntity project = new ProjectEntity();
    project.setId(boardDto.getProjectId());

    BoardTypeEntity boardTypeEntity = new BoardTypeEntity();
    boardTypeEntity.setId(boardDto.getBoardTypeId());

    BoardEntity board = new BoardEntity();
    board.setId(randomUUID());
    board.setName(boardDto.getName());
    board.setDescription(boardDto.getDescription());
    board.setBoardType(boardTypeEntity);
    board.setProject(project);
    board.setCreatedBy(boardCreator);

    return repository.persist(board)
                     .replaceWith(findById(board.getId()));
  }

  @Override
  protected void updateField(final BoardEntity toUpdate, final String key, final String value) {
    switch (key) {
      case "name" -> toUpdate.setName(value);
      case "description" -> toUpdate.setDescription(value);
      case "coverImage" -> toUpdate.setCoverImage(value);

      default -> throw new IllegalStateException("Unexpected value: " + key);
    }
  }
}
