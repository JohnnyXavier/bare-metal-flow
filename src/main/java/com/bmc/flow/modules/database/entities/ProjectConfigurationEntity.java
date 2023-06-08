package com.bmc.flow.modules.database.entities;

import com.bmc.flow.modules.database.entities.base.BaseEntity;
import com.bmc.flow.modules.database.entities.records.ProjectEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * this class represents the project configuration table and it's relations.
 * <p>
 * a project configuration allows to define defaults per projects.
 */
@Entity
@Table(name = "project_configuration")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class ProjectConfigurationEntity extends BaseEntity {

    private Integer hoursADay;

    private UUID cardDifficultyDefault;

    private UUID cardStatusDefault;

    private UUID cardTypeDefault;

    @OneToOne
    private ProjectEntity project;

}
