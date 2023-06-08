package com.bmc.flow.modules.service.catalogs;

import com.bmc.flow.modules.database.dto.catalogs.BoardTypeDto;
import com.bmc.flow.modules.database.entities.catalogs.BoardTypeEntity;
import com.bmc.flow.modules.database.repositories.catalogs.BoardTypeRepository;
import com.bmc.flow.modules.service.base.BasicPersistenceService;
import com.bmc.flow.modules.service.utils.CreationUtils;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;

import static com.bmc.flow.modules.service.reflection.MethodNames.SET_DESCRIPTION;
import static com.bmc.flow.modules.service.reflection.MethodNames.SET_NAME;

/**
 * this class is a data access service for board data.
 */
@ApplicationScoped
public class BoardTypeService extends BasicPersistenceService<BoardTypeDto, BoardTypeEntity> {

    private final BoardTypeRepository boardTypeRepo;

    public BoardTypeService(final BoardTypeRepository boardTypeRepo) {
        super(boardTypeRepo, BoardTypeDto.class);
        this.boardTypeRepo = boardTypeRepo;
    }

    @WithTransaction
    public Uni<BoardTypeDto> create(@Valid final BoardTypeDto boardTypeDto) {
        BoardTypeEntity newBoardType = new BoardTypeEntity();
        CreationUtils.populateBaseCatalogEntity(newBoardType, boardTypeDto);

        return boardTypeRepo.persist(newBoardType)
                            .replaceWith(findById(newBoardType.getId()));
    }


    public Uni<BoardTypeEntity> findEntityByName(final String name) {
        return boardTypeRepo.findEntityByName(name);
    }


    @Override
    @WithTransaction
    protected Uni<Void> update(final BoardTypeEntity toUpdate, final String key, final String value) {
        return updateInPlace(toUpdate, key, value);
    }

    protected Uni<Void> updateInPlace(final BoardTypeEntity toUpdate, final String key, final String value) {
        return switch (key) {
            case "name" -> updateInPlace(toUpdate, SET_NAME, value);
            case "description" -> updateInPlace(toUpdate, SET_DESCRIPTION, value);

            default -> throw new IllegalStateException("Unexpected value: " + key);
        };
    }

}
