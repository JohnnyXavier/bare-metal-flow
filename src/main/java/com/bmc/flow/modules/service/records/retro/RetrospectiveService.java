package com.bmc.flow.modules.service.records.retro;

import com.bmc.flow.modules.database.dto.records.retro.RetrospectiveDto;
import com.bmc.flow.modules.database.entities.UserEntity;
import com.bmc.flow.modules.database.entities.records.retro.RetrospectiveEntity;
import com.bmc.flow.modules.database.repositories.records.SprintRepository;
import com.bmc.flow.modules.database.repositories.records.retro.RetrospectiveRepository;
import com.bmc.flow.modules.service.base.BasicPersistenceService;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

import static java.util.UUID.randomUUID;

/**
 * this class is a data access service for retrospective data.
 */
@ApplicationScoped
public class RetrospectiveService extends BasicPersistenceService<RetrospectiveDto, RetrospectiveEntity> {

    private final RetrospectiveRepository repository;

    private final SprintRepository sprintRepository;


    public RetrospectiveService(final RetrospectiveRepository repository, final SprintRepository sprintRepository) {
        super(repository, RetrospectiveDto.class);
        this.repository       = repository;
        this.sprintRepository = sprintRepository;
    }

    public Uni<List<RetrospectiveDto>> findAllByProjectId(final UUID projectId) {
        return repository.findAllByProjectId(projectId);
    }

    public Uni<RetrospectiveDto> findBySprintBoardId(final UUID sprintBoardId) {
        return repository.findBySprintId(sprintBoardId);
    }

    @WithTransaction
    public Uni<RetrospectiveDto> create(@Valid final RetrospectiveDto retrospectiveDto) {
        UserEntity cardCreator = new UserEntity();
        cardCreator.setId(retrospectiveDto.getCreatedBy());

        RetrospectiveEntity newRetroBoard = new RetrospectiveEntity();
        newRetroBoard.setId(randomUUID());
        newRetroBoard.setCreatedBy(cardCreator);

        return sprintRepository.findById(retrospectiveDto.getSprintBoardId())
                               .onItem()
                               .invoke(newRetroBoard::setSprintBoard)
                               .replaceWith(repository.persist(newRetroBoard)
                                                      .replaceWith(findById(newRetroBoard.getId())));
    }

    @Override
    @WithTransaction
    protected Uni<Void> update(final RetrospectiveEntity toUpdate, final String key, final String value) {
        // nothing to update here
        return Uni.createFrom().voidItem();
    }
}
