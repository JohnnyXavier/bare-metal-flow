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
public class AccountDto extends BaseRecordDto {


  public AccountDto(final UUID id, final String name, final String description, final String coverImage, final LocalDateTime createdAt,
                    @ProjectedFieldName("createdBy.id") final UUID createdBy) {
    this.id = id;
    this.name = name;
    this.coverImage = getImage(coverImage, "ACCOUNT");
    this.description = description;
    this.createdBy = createdBy;
    this.createdAt = createdAt;
  }

}
