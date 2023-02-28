package com.bmc.flow.modules.database.entities.base;

import com.bmc.flow.modules.database.entities.UserEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class BaseEntity {

  @Id
  @EqualsAndHashCode.Include
  private UUID id;

  @ManyToOne
  private UserEntity createdBy;

  @CreationTimestamp
  @EqualsAndHashCode.Include
  @Column(updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;

}
