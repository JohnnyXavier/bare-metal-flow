package com.bmc.flow.modules.database.entities.resourcing;

import com.bmc.flow.modules.database.entities.base.BaseEntity;
import com.bmc.flow.modules.database.entities.records.ProjectEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * this class represents the schedule entry table and it's relations.
 */
@Entity
@Table(name = "schedule_entry")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class ScheduleEntryEntity extends BaseEntity {

    private LocalDate scheduledFrom;

    private LocalDate scheduledTo;

    private Short scheduledHours;

    @Column(columnDefinition = "boolean default false")
    private Boolean isUnbounded; //when someone is scheduled from now to eternity

    @ManyToOne
    private ScheduleEntity schedule;

    @ManyToOne
    private ProjectEntity project;

}
