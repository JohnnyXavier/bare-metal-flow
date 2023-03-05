package com.bmc.flow.modules.service.records;

import com.bmc.flow.modules.database.dto.records.ProjectDto;
import com.bmc.flow.modules.database.entities.UserEntity;
import com.bmc.flow.modules.database.entities.records.AccountEntity;
import com.bmc.flow.modules.database.entities.records.ProjectEntity;
import com.bmc.flow.modules.database.repositories.records.ProjectRepository;
import com.bmc.flow.modules.resources.base.Pageable;
import com.bmc.flow.modules.service.base.BasicPersistenceService;
import com.bmc.flow.modules.service.base.PageResult;
import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.Valid;
import java.util.UUID;

import static java.util.UUID.randomUUID;

@ApplicationScoped
public class ProjectService extends BasicPersistenceService<ProjectDto, ProjectEntity> {

  private final ProjectRepository projectRepo;

  public ProjectService(final ProjectRepository projectRepo) {
    super(projectRepo, ProjectDto.class);
    this.projectRepo = projectRepo;
  }

  public Uni<PageResult<ProjectDto>> findAllByUserIdPaged(final UUID userId, final Pageable pageable) {
    return super.findAllPaged(projectRepo.findAllCreatedByUserId(userId, pageable.getSort()), "-all-projects-by-user", pageable.getPage());
  }

  public Uni<PageResult<ProjectDto>> findAllByAccountIdPaged(final UUID userId, final Pageable pageable) {
    return super.findAllPaged(projectRepo.findAllByAccountId(userId, pageable.getSort()), "-all-projects-by-account",
                              pageable.getPage());
  }

  @ReactiveTransactional
  public Uni<ProjectDto> create(@Valid final ProjectDto projectDto) {
    UserEntity projectCreator = new UserEntity();
    projectCreator.setId(projectDto.getCreatedBy());

    AccountEntity account = new AccountEntity();
    account.setId(projectDto.getAccountId());

    ProjectEntity newProject = new ProjectEntity();
    newProject.setId(randomUUID());
    newProject.setName(projectDto.getName());
    newProject.setDescription(projectDto.getDescription());
    newProject.setAccount(account);
    newProject.setCreatedBy(projectCreator);

    return projectRepo.persist(newProject)
                      .replaceWith(findById(newProject.getId()));
  }

  @Override
  protected void updateField(final ProjectEntity toUpdate, final String key, final String value) {
    switch (key) {
      case "name" -> toUpdate.setName(value);
      case "description" -> toUpdate.setDescription(value);
      case "coverImage" -> toUpdate.setCoverImage(value);

      default -> throw new IllegalStateException("Unexpected value: " + key);
    }
  }
}
