package com.bmc.flow.modules.database.entities;

import com.bmc.flow.modules.database.entities.base.BaseEntity;
import com.bmc.flow.modules.database.entities.records.CardEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "attachment")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class AttachmentEntity extends BaseEntity {

  public static final String FIELD_NAME = "attachment";

  private String name;

  private String extension;

  private String size;

  private String mimeType;

  private String location;

  @ManyToMany(mappedBy = "attachments")
  private Set<CardEntity> card = new HashSet<>();

}
