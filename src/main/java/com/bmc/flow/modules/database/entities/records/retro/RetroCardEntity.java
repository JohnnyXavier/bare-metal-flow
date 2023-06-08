package com.bmc.flow.modules.database.entities.records.retro;

import com.bmc.flow.modules.database.entities.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * this class represents the retrospective card table and it's relations.
 */
@Entity
@Table(name = "retro_card")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class RetroCardEntity extends BaseEntity {

    @Column(columnDefinition = "text")
    private String comment;

    // This will be updated concurrently and requires atomic additions instead of first one wins as with other data, careful...
    // TODO: check for atomics on reactive environments?
    private Short votes;

    @ManyToOne
    private RetrospectiveEntity retroBoard;
}
