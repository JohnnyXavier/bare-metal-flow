package com.bmc.flow.modules.service.catalogs;

import com.bmc.flow.modules.database.dto.catalogs.BoardTypeDto;
import com.bmc.flow.modules.database.entities.catalogs.BoardTypeEntity;
import com.bmc.flow.modules.database.repositories.catalogs.BoardTypeRepository;
import com.bmc.flow.modules.service.base.BasicPersistenceService;
import com.bmc.flow.modules.service.utils.CreationUtils;
import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.Valid;

@ApplicationScoped
public class BoardTypeService extends BasicPersistenceService<BoardTypeDto, BoardTypeEntity> {

  private final BoardTypeRepository boardTypeRepo;

  public BoardTypeService(final BoardTypeRepository boardTypeRepo) {
    super(boardTypeRepo, BoardTypeDto.class);
    this.boardTypeRepo = boardTypeRepo;
  }

  @ReactiveTransactional
  public Uni<BoardTypeDto> create(@Valid final BoardTypeDto boardTypeDto) {
    BoardTypeEntity newBoardType = new BoardTypeEntity();
    CreationUtils.createBaseCatalogEntity(newBoardType, boardTypeDto);

    return boardTypeRepo.persist(newBoardType)
                        .replaceWith(findById(newBoardType.getId()));
  }


  public Uni<BoardTypeEntity> findEntityByName(final String name) {
    return boardTypeRepo.findEntityByName(name);
  }


  @Override
  protected void updateField(final BoardTypeEntity toUpdate, final String key, final String value) {
    switch (key) {
      case "name" -> toUpdate.setName(value);
      case "description" -> toUpdate.setDescription(value);

      default -> throw new IllegalStateException("Unexpected value: " + key);
    }
  }
}
