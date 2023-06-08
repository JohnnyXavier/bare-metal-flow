package com.bmc.flow.modules.database.entities;

import com.bmc.flow.modules.database.entities.base.BaseEntity;
import com.bmc.flow.modules.database.entities.records.CardEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "attachment")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class AttachmentEntity extends BaseEntity {

  private String name;

  private String extension;

  private String size;

  private String mimeType;

  private String location;

  @ManyToMany(mappedBy = "attachments")
  private Set<CardEntity> card = new HashSet<>();

}
