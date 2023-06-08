package com.bmc.flow.modules.database.entities.catalogs;

import com.bmc.flow.modules.database.entities.base.BaseCatalogEntity;
import com.bmc.flow.modules.database.entities.records.BoardEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

import static jakarta.persistence.CascadeType.ALL;

/**
 * this class represents the board type table and it's relations.
 */
@Entity
@Table(name = "board_type")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class BoardTypeEntity extends BaseCatalogEntity {

    @OneToMany(mappedBy = "boardType", cascade = ALL)
    private Set<BoardEntity> board;

}
