package com.bmc.flow.modules.database.entities.records;

import com.bmc.flow.modules.database.entities.base.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "bug_report")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class CardBugReport extends BaseEntity {

  @ManyToOne
  private CardEntity card;

  @Column(columnDefinition = "text")
  private String description;

  @Column(columnDefinition = "boolean default false")
  private Boolean isSolved;



}
