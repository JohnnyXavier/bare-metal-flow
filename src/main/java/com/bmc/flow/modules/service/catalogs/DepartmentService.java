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

import static com.bmc.flow.modules.service.reflection.MethodNames.SET_DESCRIPTION;
import static com.bmc.flow.modules.service.reflection.MethodNames.SET_NAME;

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
  @WithTransaction
  protected Uni<Void> update(final DepartmentEntity toUpdate, final String key, final String value) {
    return switch (key) {
      case "name" -> updateInplace(toUpdate, SET_NAME, value);
      case "description" -> updateInplace(toUpdate, SET_DESCRIPTION, value);

      default -> throw new IllegalStateException("Unexpected value: " + key);
    };
  }
}
