package com.bmc.flow.modules.database.entities.records.retro;

import com.bmc.flow.modules.database.entities.base.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "retro_card")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class RetroCardEntity extends BaseEntity {

  @Column(columnDefinition = "text")

  private String comment;

  // This can / will be updated concurrently, careful...
  private short votes;

  @ManyToOne
  private RetrospectiveEntity retroBoard;
}
