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
 * this class represents the card difficulty table and it's relations.
 */
@Entity
@Table(name = "card_difficulty")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class CardDifficultyEntity extends BaseCatalogEntity {

    private Short level;

    @OneToMany(mappedBy = "cardDifficulty", cascade = ALL)
    private Set<CardEntity> cards = new HashSet<>();

}
