package com.bmc.flow.modules.database.dto.records;

import com.bmc.flow.modules.database.dto.base.BaseRecordDto;
import io.quarkus.hibernate.reactive.panache.common.ProjectedFieldName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.bmc.flow.modules.utilities.DataUtils.getImage;

@Getter
@Setter
@RegisterForReflection
public class ProjectDto extends BaseRecordDto {

  @NotNull
  private UUID accountId;
  private UUID projectLead;

  public ProjectDto(final UUID id, final String name, String description, final String coverImage, final LocalDateTime createdAt,
                    @ProjectedFieldName("createdBy.id") final UUID createdBy,
                    @ProjectedFieldName("account.id") final UUID accountId,
                    @ProjectedFieldName("projectLead.id") final UUID projectLead) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.coverImage = getImage(coverImage, "PROJECT");
    this.createdAt = createdAt;
    this.createdBy = createdBy;
    this.accountId = accountId;
    this.projectLead = projectLead;
  }

}
