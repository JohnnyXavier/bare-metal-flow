package com.bmc.flow.modules.database.entities.catalogs;

import com.bmc.flow.modules.database.entities.UserEntity;
import com.bmc.flow.modules.database.entities.base.BaseCatalogEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "department")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class DepartmentEntity extends BaseCatalogEntity {

  @OneToMany(mappedBy = "department", cascade = ALL)
  private Set<UserEntity> users = new HashSet<>();

}
