package com.bmc.flow.modules.service.catalogs;

import com.bmc.flow.modules.database.dto.catalogs.DepartmentDto;
import com.bmc.flow.modules.database.entities.catalogs.DepartmentEntity;
import com.bmc.flow.modules.database.repositories.catalogs.DepartmentRepository;
import com.bmc.flow.modules.service.base.BasicPersistenceService;
import com.bmc.flow.modules.service.utils.CreationUtils;
import io.quarkus.cache.CacheResult;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;

@ApplicationScoped
public class DepartmentService extends BasicPersistenceService<DepartmentDto, DepartmentEntity> {

  private final DepartmentRepository departmentRepo;

  public DepartmentService(final DepartmentRepository departmentRepo) {
    super(departmentRepo, DepartmentDto.class);
    this.departmentRepo = departmentRepo;
  }

  @WithTransaction
  public Uni<DepartmentDto> create(@Valid final DepartmentDto departmentDto) {
    DepartmentEntity newDepartment = new DepartmentEntity();
    CreationUtils.createBaseCatalogEntity(newDepartment, departmentDto);

    return departmentRepo.persist(newDepartment)
                         .replaceWith(findById(newDepartment.getId()));
  }

  @CacheResult(cacheName = "department-by-name")
  public Uni<DepartmentEntity> findEntityByName(final String name) {
    return departmentRepo.findEntityByName(name);
  }

  @Override
  protected void updateField(final DepartmentEntity toUpdate, final String key, final String value) {
    switch (key) {
      case "name" -> toUpdate.setName(value);
      case "description" -> toUpdate.setDescription(value);

      default -> throw new IllegalStateException("Unexpected value: " + key);
    }
  }
}
