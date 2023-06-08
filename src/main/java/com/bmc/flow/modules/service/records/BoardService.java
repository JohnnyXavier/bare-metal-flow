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
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.extern.jbosslog.JBossLog;
import org.hibernate.reactive.mutiny.Mutiny;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.bmc.flow.modules.service.reflection.MethodNames.*;
import static java.util.UUID.randomUUID;

@ApplicationScoped
@JBossLog
public class BoardService extends BasicPersistenceService<BoardDto, BoardEntity> {

    private final BoardRepository repository;

    private final Mutiny.SessionFactory sf;

    public BoardService(final BoardRepository repository, Mutiny.SessionFactory sf) {
        super(repository, BoardDto.class);
        this.repository = repository;
        this.sf         = sf;
    }

    public Uni<List<BoardDto>> getAllByProjectId(final UUID projectId) {
        return repository.findAllByProjectId(projectId);
    }

    public Uni<PageResult<BoardDto>> findAllByUserIdPaged(UUID userId, final Pageable pageable) {
        return findAllPaged(repository.find("createdBy.id", pageable.getSort(), userId), "-all-boards-by-user",
            pageable.getPage());
    }

    @WithTransaction
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
    @WithTransaction
    protected Uni<Void> update(final BoardEntity toUpdate, final String key, final String value) {
        return switch (key) {
            case "name" -> updateInPlace(toUpdate, SET_NAME, value);
            case "description" -> updateInPlace(toUpdate, SET_DESCRIPTION, value);
            case "coverImage" -> updateInPlace(toUpdate, SET_COVER_IMAGE, value);
            case "isFavorite" -> updateInPlace(toUpdate, SET_IS_FAVORITE, Boolean.valueOf(value));
            case "addMember" -> addMember(toUpdate, value);
            case "removeMember" -> removeMember(toUpdate, value);

            default -> throw new IllegalStateException("Unexpected value: " + key);
        };
    }

    private Uni<Void> addMember(BoardEntity toUpdate, String value) {
        return sf.withTransaction((session, transaction) ->
                     session.createNativeQuery("insert into board_users(board_id, user_id) VALUES  (?1, ?2)")
                            .setParameter(1, toUpdate.getId())
                            .setParameter(2, UUID.fromString(value))
                            .executeUpdate()
                            .chain(() -> session.createNativeQuery("update board set updated_at = ?1 where id = ?2")
                                                .setParameter(1, LocalDateTime.now())
                                                .setParameter(2, toUpdate.getId())
                                                .executeUpdate()))
                 .replaceWithVoid();
    }

    private Uni<Void> removeMember(BoardEntity toUpdate, String value) {
        return sf.withTransaction((session, transaction) ->
                     session.createNativeQuery("delete from board_users where user_id = ?1 and board_id = ?2")
                            .setParameter(1, UUID.fromString(value))
                            .setParameter(2, toUpdate.getId())
                            .executeUpdate()
                            .chain(() -> session.createNativeQuery("update board set updated_at = ?1 where id = ?2")
                                                .setParameter(1, LocalDateTime.now())
                                                .setParameter(2, toUpdate.getId())
                                                .executeUpdate()))
                 .replaceWithVoid();
    }
}
