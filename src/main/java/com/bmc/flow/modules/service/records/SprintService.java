package com.bmc.flow.modules.service.records;

import com.bmc.flow.modules.database.dto.records.SprintDto;
import com.bmc.flow.modules.database.entities.UserEntity;
import com.bmc.flow.modules.database.entities.records.BoardEntity;
import com.bmc.flow.modules.database.entities.records.ProjectEntity;
import com.bmc.flow.modules.database.entities.records.SprintEntity;
import com.bmc.flow.modules.database.entities.records.retro.RetrospectiveEntity;
import com.bmc.flow.modules.database.repositories.records.SprintRepository;
import com.bmc.flow.modules.resources.base.Pageable;
import com.bmc.flow.modules.service.base.BasicPersistenceService;
import com.bmc.flow.modules.service.base.PageResult;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.bmc.flow.modules.service.reflection.MethodNames.*;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.time.LocalDateTime.parse;
import static java.util.UUID.randomUUID;

@ApplicationScoped
public class SprintService extends BasicPersistenceService<SprintDto, SprintEntity> {

    private final SprintRepository repository;

    public SprintService(final SprintRepository repository) {
        super(repository, SprintDto.class);
        this.repository = repository;
    }


    @WithTransaction
    public Uni<SprintDto> create(@Valid final SprintDto boardDto) {
        UserEntity sprintCreator = new UserEntity();
        sprintCreator.setId(boardDto.getCreatedBy());

        ProjectEntity project = new ProjectEntity();
        project.setId(boardDto.getProjectId());

        BoardEntity board = new BoardEntity();
        board.setId(boardDto.getBoardId());

        SprintEntity newSprint = new SprintEntity();
        newSprint.setId(randomUUID());
        newSprint.setName(boardDto.getName());
        newSprint.setFromDate(boardDto.getFromDate());
        newSprint.setCloseDate(boardDto.getCloseDate());
        newSprint.setGoal(boardDto.getGoal());
        newSprint.setProject(project);
        newSprint.setBoard(board);
        newSprint.setCreatedBy(sprintCreator);

        return repository.persist(newSprint)
            .replaceWith(findById(newSprint.getId()));
    }

    public Uni<Void> startSprint(final UUID sprintId) {
        return repository.findById(sprintId)
            .onItem().ifNotNull().invoke(sprintFromDb -> {
                if (sprintFromDb.getHasStarted() == FALSE) {
                    sprintFromDb.setStartDate(LocalDateTime.now());
                    sprintFromDb.setHasStarted(TRUE);
                }
            }).replaceWith(Uni.createFrom().voidItem());
    }

    public Uni<SprintDto> closeSprint(final UUID sprintId) {
        return repository.findById(sprintId)
            .onItem().ifNotNull().invoke(sprintFromDb -> {
                if (sprintFromDb.getHasStarted() == TRUE && sprintFromDb.getIsClosed() == FALSE) {

                    sprintFromDb.setCloseDate(LocalDateTime.now());
                    sprintFromDb.setIsClosed(TRUE);

                    RetrospectiveEntity newRetroBoard = new RetrospectiveEntity();
                    newRetroBoard.setId(randomUUID());
                    newRetroBoard.setProject(sprintFromDb.getProject());
                    newRetroBoard.setCreatedBy(sprintFromDb.getCreatedBy());

                    sprintFromDb.setRetroBoard(newRetroBoard);
                }
            }).flatMap(sprintEntity -> this.findById(sprintEntity.getId()));
    }

    @Override
    @WithTransaction
    protected Uni<Void> update(final SprintEntity toUpdate, final String key, final String value) {
        return switch (key) {
            case "name" -> updateInPlace(toUpdate, SET_NAME, value);
            case "fromDate" -> updateInPlace(toUpdate, SET_FROM_DATE, parse(value));
            case "startDate" -> updateInPlace(toUpdate, SET_START_DATE, parse(value));
            case "endDate" -> updateInPlace(toUpdate, SET_END_DATE, parse(value));
            case "closeDate" -> updateInPlace(toUpdate, SET_CLOSE_DATE, parse(value));
            case "goal" -> updateInPlace(toUpdate, SET_GOAL, value);

            default -> throw new IllegalStateException("Unexpected value: " + key);
        };
    }

    public Uni<PageResult<SprintDto>> findAllInCollectionId(final String collectionName, final UUID collectionId, final Pageable pageable) {
        return findAllPaged(repository.findAllByCollectionId(collectionName, collectionId, pageable.getSort()),
            "-find-all-sprints-in-" + collectionName,
            pageable.getPage());
    }
}
