package com.bmc.flow.modules.database.entities.resourcing;

import com.bmc.flow.modules.database.entities.base.BaseCatalogEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * this class represents the shrinkage table and it's relations.
 */
@Entity
@Table(name = "shrinkage")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class ShrinkageEntity extends BaseCatalogEntity {


    @Column(nullable = true)
    private Short durationInMin;

    @Column(nullable = true)
    private Short percentage;

    @ManyToMany(mappedBy = "shrinkages")
    private Set<ScheduleEntity> schedules;

}
