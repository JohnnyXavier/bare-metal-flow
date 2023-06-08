package com.bmc.flow.modules.database.entities.base;

import com.bmc.flow.modules.database.entities.UserEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

import static jakarta.persistence.FetchType.LAZY;


@MappedSuperclass
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class BaseEntity {

  /**
   * going with uuid for a few reasons:
   * - using auto incrementing will be a problem if batching / looping is required in a future
   * - using a sequence strategy, thou it will generate incremental values and efficient indexes, the values will be "shared" among
   * all tables. Generating a strategy for each entity to have consecutive increments on each table defeats this inheritance.
   * <p>
   * using PostgreSQL, and it's <a href="https://www.postgresql.org/docs/current/datatype-uuid.html,">built in UUID type</a>,
   * makes for a fantastic speed/reusability balance, ang given that the IDs are generated by
   * the app, the db is free and won't care for spending any cycles on uuid generation.
   * <p>
   * about the possibility to use hbn uuid2 GenericGenerator.
   * <p>
   * tested caveat on that approach:
   * we will break the functional flow of reactive approach as on transactional method that will require a .replaceWith(),
   * flushing to DB, even manually will not happen until the end and the id won't be created before needed.
   * <p>
   * we can decouple creation from transformation and make a later call to the required op but for the moment, manually generating the uuid
   * when needed, makes more sense as everything is contained by the transaction and an err on potential transformation will be caught.
   */
  @Id
  @EqualsAndHashCode.Include
  private UUID id;

  @ManyToOne(fetch = LAZY)
  private UserEntity createdBy;

  @CreationTimestamp
  @Column(updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;

}
