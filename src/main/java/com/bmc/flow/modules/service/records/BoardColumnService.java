package com.bmc.flow.modules.service.records;

import com.bmc.flow.modules.database.dto.records.BoardColumnDto;
import com.bmc.flow.modules.database.entities.UserEntity;
import com.bmc.flow.modules.database.entities.catalogs.BoardColumnEntity;
import com.bmc.flow.modules.database.entities.catalogs.StatusEntity;
import com.bmc.flow.modules.database.entities.records.BoardEntity;
import com.bmc.flow.modules.database.repositories.records.BoardColumnRepository;
import com.bmc.flow.modules.resources.base.Pageable;
import com.bmc.flow.modules.service.base.BasicPersistenceService;
import com.bmc.flow.modules.service.base.PageResult;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import org.hibernate.reactive.mutiny.Mutiny;

import java.util.UUID;

import static java.util.UUID.randomUUID;

@ApplicationScoped
public class BoardColumnService extends BasicPersistenceService<BoardColumnDto, BoardColumnEntity> {

  private final BoardColumnRepository repository;

  private final Mutiny.SessionFactory sf;

  public BoardColumnService(final BoardColumnRepository repository, Mutiny.SessionFactory sf) {
    super(repository, BoardColumnDto.class);
    this.repository = repository;
    this.sf         = sf;
  }

  public Uni<PageResult<BoardColumnDto>> findAllByBoardIdPaged(final UUID boardId, final Pageable pageable) {
    return findAllPaged(repository.find("board.id", pageable.getSort(), boardId), "all-board-cols-by-board-id", pageable.getPage());
  }

  @WithTransaction
  public Uni<BoardColumnDto> create(@Valid final BoardColumnDto boardColumnDto) {
    UserEntity boardColumnCreator = new UserEntity();
    boardColumnCreator.setId(boardColumnDto.getCreatedBy());

    BoardEntity board = new BoardEntity();
    board.setId(boardColumnDto.getBoardId());

    StatusEntity status = new StatusEntity();
    status.setId(boardColumnDto.getStatusId());

    BoardColumnEntity boardColumn = new BoardColumnEntity();
    boardColumn.setId(randomUUID());
    boardColumn.setBoard(board);
    boardColumn.setStatus(status);
    boardColumn.setCreatedBy(boardColumnCreator);

    return repository.persist(boardColumn)
        .replaceWith(findById(boardColumn.getId()));
  }

  @Override
  protected void updateField(final BoardColumnEntity toUpdate, final String key, final String value) {
    switch (key) {
      case "name" -> toUpdate.setName(value);
      case "status" -> setStatus(toUpdate, UUID.fromString(value));
      default -> throw new IllegalStateException("Unexpected value: " + key);
    }
  }

  private void setStatus(final BoardColumnEntity toUpdate, final UUID uuid) {
    StatusEntity status = new StatusEntity();
    status.setId(uuid);
    toUpdate.setStatus(status);
  }
}
