package com.bmc.flow.modules.database.entities.catalogs;

import com.bmc.flow.modules.database.entities.UserEntity;
import com.bmc.flow.modules.database.entities.base.BaseCatalogEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.CascadeType.ALL;

/**
 * this class represents the department table and it's relations.
 * <p>
 * this department is in relation to a company's department. ie finance, engineering, etc
 */
@Entity
@Table(name = "department")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class DepartmentEntity extends BaseCatalogEntity {

    @OneToMany(mappedBy = "department", cascade = ALL)
    private Set<UserEntity> users = new HashSet<>();

}
