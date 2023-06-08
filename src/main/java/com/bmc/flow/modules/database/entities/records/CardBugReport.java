package com.bmc.flow.modules.database.entities.records;

import com.bmc.flow.modules.database.entities.base.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * this class represents the bug report table and it's relations.
 */
@Entity
@Table(name = "bug_report")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class CardBugReport extends BaseEntity {

    @ManyToOne
    private CardEntity card;

    @Column(columnDefinition = "text")
    private String description;

    @Column(columnDefinition = "boolean default false")
    private Boolean isSolved;

}
