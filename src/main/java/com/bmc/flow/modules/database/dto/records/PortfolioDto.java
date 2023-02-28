package com.bmc.flow.modules.database.dto.records;

import com.bmc.flow.modules.database.dto.base.BaseRecordDto;
import io.quarkus.hibernate.reactive.panache.common.ProjectedFieldName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.bmc.flow.modules.utilities.DataUtils.getImage;

@Getter
@Setter
@RegisterForReflection
public class PortfolioDto extends BaseRecordDto {

  private Boolean isDefault;

  public PortfolioDto(final UUID id, final String name, final String description, final String coverImage, final Boolean isDefault,
                      final LocalDateTime createdAt,
                      @ProjectedFieldName("createdBy.id") final UUID createdBy) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.coverImage = getImage(coverImage, "PORTFOLIO");
    this.createdAt = createdAt;
    this.createdBy = createdBy;
    this.isDefault = isDefault;
  }

}
