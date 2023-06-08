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
 * this class represents the comment table and it's relations.
 */
@Entity
@Table(name = "comment")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class CommentEntity extends BaseEntity {

    @Column(columnDefinition = "text")
    @EqualsAndHashCode.Include
    private String comment;

    @ManyToOne
    private CardEntity card;

}
