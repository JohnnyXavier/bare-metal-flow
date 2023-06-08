package com.bmc.flow.modules.database.entities.catalogs;

import com.bmc.flow.modules.database.entities.base.BaseCatalogEntity;
import com.bmc.flow.modules.database.entities.records.AccountEntity;
import com.bmc.flow.modules.database.entities.records.BoardEntity;
import com.bmc.flow.modules.database.entities.records.CardLabelEntity;
import com.bmc.flow.modules.database.entities.records.ProjectEntity;
import com.bmc.flow.modules.database.entities.records.retro.RetroActionEntity;
import com.bmc.flow.modules.database.entities.records.retro.RetrospectiveEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

/**
 * this class represents the label table and it's relations.
 */
@Entity
@Table(name = "label")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@NaturalIdCache
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LabelEntity extends BaseCatalogEntity {

    //TODO: choose a better DB dataType for hex. Candidate is bytea(hex format) for postgresql
    // https://www.postgresql.org/docs/current/datatype-binary.html#id-1.5.7.12.9
    // ...
    // @Column(columnDefinition = "varchar", length = 4)
    private String colorHex;

    @NaturalId
    private String name;

    @OneToMany(mappedBy = "label", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CardLabelEntity> cards    = new HashSet<>();

    @ManyToMany(mappedBy = "labels")
    private Set<AccountEntity>   accounts = new HashSet<>();

    @ManyToMany(mappedBy = "labels")
    private Set<BoardEntity>   boards   = new HashSet<>();

    @ManyToMany(mappedBy = "labels")
    private Set<ProjectEntity> projects = new HashSet<>();

    @ManyToMany(mappedBy = "labels")
    private Set<RetrospectiveEntity> retroBoards = new HashSet<>();

    @ManyToMany(mappedBy = "labels")
    private Set<RetroActionEntity> retroActions = new HashSet<>();

}
