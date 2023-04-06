package com.bmc.flow.modules.service.records;

import com.bmc.flow.modules.database.dto.records.BoardColumnDto;
import com.bmc.flow.modules.database.entities.UserEntity;
import com.bmc.flow.modules.database.entities.catalogs.BoardColumnEntity;
import com.bmc.flow.modules.database.entities.catalogs.StatusEntity;
import com.bmc.flow.modules.database.entities.records.BoardEntity;
import com.bmc.flow.modules.database.repositories.records.BoardColumnRepository;
import com.bmc.flow.modules.service.base.BasicPersistenceService;
import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static java.util.UUID.randomUUID;

@ApplicationScoped
public class BoardColumnService extends BasicPersistenceService<BoardColumnDto, BoardColumnEntity> {

  private final BoardColumnRepository repository;

  public BoardColumnService(final BoardColumnRepository repository) {
    super(repository, BoardColumnDto.class);
    this.repository = repository;
  }

  public Uni<List<BoardColumnDto>> getAllByBoardId(final UUID boardId) {
    return repository.find("board.id", boardId).project(BoardColumnDto.class).list();
  }

  @ReactiveTransactional
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
      default -> throw new IllegalStateException("Unexpected value: " + key);
    }
  }
}
