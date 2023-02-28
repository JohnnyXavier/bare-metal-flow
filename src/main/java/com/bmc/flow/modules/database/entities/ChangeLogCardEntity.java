package com.bmc.flow.modules.database.entities;

import com.bmc.flow.modules.database.entities.base.BaseEntity;
import com.bmc.flow.modules.database.entities.records.CardEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "card_history")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class ChangeLogCardEntity extends BaseEntity {

  /*
   * for ChangeLogs:
   * use the "createdAt" as changedAt field
   * use the "createdBy" as a changedBy field
   */

  private String changedField;

  private String changeFrom;

  private String changeTo;

  @ManyToOne
  private CardEntity card;

}
