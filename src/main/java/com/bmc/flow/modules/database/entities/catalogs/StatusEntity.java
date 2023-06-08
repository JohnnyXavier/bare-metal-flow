package com.bmc.flow.modules.database.entities.catalogs;

import com.bmc.flow.modules.database.entities.base.BaseCatalogEntity;
import com.bmc.flow.modules.database.entities.records.CardEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * this class represents the card status table and it's relations.
 * <p>
 * will probably migrate to a general status instead of the specialized card status it is now.
 */
@Entity
@Table(name = "card_status")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class StatusEntity extends BaseCatalogEntity {

    @OneToMany(mappedBy = "cardStatus")
    private Set<CardEntity> cards = new HashSet<>();

    @OneToMany(mappedBy = "status")
    private Set<BoardColumnEntity> boardColumns = new HashSet<>();

}
