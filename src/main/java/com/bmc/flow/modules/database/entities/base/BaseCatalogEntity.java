package com.bmc.flow.modules.database.entities.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * this class is the basis for catalog entities in the application.
 * <br>
 * catalogs in this app are "enum-like" shared data that is shared by many other entities or "qualifies" entities. i.e. labels, statuses,
 * seniority, etc.
 */
@MappedSuperclass
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public abstract class BaseCatalogEntity extends BaseEntity {

    @Column(unique = true)
    private String  name;
    @Column(columnDefinition = "boolean default false")
    private Boolean isSystem;
    private String  description;

}
