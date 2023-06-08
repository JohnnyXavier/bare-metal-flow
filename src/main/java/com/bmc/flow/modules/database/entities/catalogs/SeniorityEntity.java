package com.bmc.flow.modules.database.entities.catalogs;

import com.bmc.flow.modules.database.entities.UserEntity;
import com.bmc.flow.modules.database.entities.base.BaseCatalogEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.CascadeType.ALL;

/**
 * this class represents the seniority table and it's relations.
 */
@Entity
@Table(name = "seniority")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class SeniorityEntity extends BaseCatalogEntity {

    private Short level;

    @OneToMany(mappedBy = "seniority", cascade = ALL)
    public Set<UserEntity> users = new HashSet<>();

}
