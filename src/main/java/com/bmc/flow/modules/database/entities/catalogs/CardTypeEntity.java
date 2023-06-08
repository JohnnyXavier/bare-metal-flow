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

import static jakarta.persistence.CascadeType.ALL;

/**
 * this class represents the card type table and it's relations.
 */
@Entity
@Table(name = "card_type")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class CardTypeEntity extends BaseCatalogEntity {

    @OneToMany(mappedBy = "cardType", cascade = ALL)
    private Set<CardEntity> cards = new HashSet<>();
}
