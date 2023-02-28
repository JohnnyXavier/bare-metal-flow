package com.bmc.flow.modules.database.entities.records.retro;

import com.bmc.flow.modules.database.entities.base.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
