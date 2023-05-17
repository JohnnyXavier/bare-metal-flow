package com.bmc.flow.modules.service;

import com.bmc.flow.modules.database.dto.ChangeLogCardDto;
import com.bmc.flow.modules.database.entities.ChangelogEntity;
import com.bmc.flow.modules.database.repositories.ChangeLogRepository;
import com.bmc.flow.modules.database.repositories.UserRepository;
import com.bmc.flow.modules.resources.base.Pageable;
import com.bmc.flow.modules.service.base.BasicPersistenceService;
import com.bmc.flow.modules.service.base.PageResult;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class ChangeLogService extends BasicPersistenceService<ChangeLogCardDto, ChangelogEntity> {

  private final ChangeLogRepository repository;
  private final UserRepository      userRepository;

  public ChangeLogService(final ChangeLogRepository repository, final UserRepository userRepository) {
    super(repository, ChangeLogCardDto.class);
    this.repository     = repository;
    this.userRepository = userRepository;
  }

  public Uni<PageResult<ChangeLogCardDto>> findAllByCardId(final UUID cardId, final Pageable pageable) {
    return findAllPaged(repository.find("card.id", pageable.getSort(), cardId), "all-changelog-by-card-id", pageable.getPage());
  }

  @Override
  public Uni<ChangeLogCardDto> create(final ChangeLogCardDto fromDto) {
    // there is no creation of a changeLog via a DTO
    return null;
  }

  @Override
  protected void updateField(final ChangelogEntity toUpdate, final String key, final String value) {
    // there is no updating of any field for a changeLog
  }
}