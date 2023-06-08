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

/**
 * this class represents the attachment table and it's relations.
 */
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
    // sharing attachments among cards might be adding an unnecessary difficulty at the beginning.
    // we could just recreate "re-upload" a given attachment and treat it like a new one changing this to Many-to-One
    private Set<CardEntity> card = new HashSet<>();

}
