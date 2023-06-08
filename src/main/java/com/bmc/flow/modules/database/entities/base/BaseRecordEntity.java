package com.bmc.flow.modules.database.entities.base;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.MappedSuperclass;

/**
 * this class is the basis for record entities in the application.
 * <br><br>
 * records in this app are the core of what we want to track. i.e. users, cards, accounts, projects, boards, etc.
 */
@MappedSuperclass
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public abstract class BaseRecordEntity extends BaseEntity {

    private String name;
    private String description;
    private String coverImage;

}
