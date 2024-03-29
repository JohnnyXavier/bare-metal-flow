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

import static com.bmc.flow.modules.service.reflection.MethodNames.SET_NAME;
import static java.util.UUID.randomUUID;

/**
 * this class is a data access service for board column data.
 */
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
    @WithTransaction
    protected Uni<Void> update(final BoardColumnEntity toUpdate, final String key, final String value) {
        return switch (key) {
            case "name" -> updateInPlace(toUpdate, SET_NAME, value);
            case "status" -> setStatus(toUpdate, UUID.fromString(value));

            default -> throw new IllegalStateException("Unexpected value: " + key);
        };
    }

    private Uni<Void> setStatus(final BoardColumnEntity toUpdate, final UUID uuid) {
        StatusEntity status = new StatusEntity();
        status.setId(uuid);
        toUpdate.setStatus(status);

        return Uni.createFrom().voidItem();
    }
}
