package com.bmc.flow.modules.database.entities;

import com.bmc.flow.modules.database.entities.base.BaseEntity;
import com.bmc.flow.modules.database.entities.catalogs.DepartmentEntity;
import com.bmc.flow.modules.database.entities.catalogs.SeniorityEntity;
import com.bmc.flow.modules.database.entities.records.*;
import com.bmc.flow.modules.database.entities.records.retro.RetroActionEntity;
import com.bmc.flow.modules.database.entities.records.retro.RetrospectiveEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;

/**
 * this class represents the user table and it's relations.
 */
@Entity
// required for Postgres as 'user' is reserved
@Table(name = "users")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class UserEntity extends BaseEntity {

    private String password;

    @NotNull
    private String callSign;

    @Column(unique = true)
    @Email
    private String email;

    private String avatar;

    @Column(columnDefinition = "boolean default true")
    private boolean isActive;

    @ManyToOne(fetch = LAZY)
    private SeniorityEntity seniority;

    @ManyToOne(fetch = LAZY)
    private DepartmentEntity department;

    //  @OneToOne(mappedBy = "user", cascade = ALL, fetch = LAZY)
//  private ScheduleEntity           userSchedule;
    @OneToMany(mappedBy = "projectLead", cascade = ALL, fetch = LAZY)
    private Set<ProjectEntity> projectLead;

    @OneToMany(mappedBy = "createdBy", fetch = LAZY)
    private Set<UserEntity> createdUsers = new HashSet<>();

    @OneToMany(mappedBy = "createdBy", fetch = LAZY)
    private Set<CommentEntity> comments = new HashSet<>();

    @ManyToMany(mappedBy = "users", fetch = LAZY)
    private Set<AccountEntity> accounts = new HashSet<>();

    @ManyToMany(mappedBy = "users", fetch = LAZY)
    private Set<ProjectEntity> projects = new HashSet<>();

    @ManyToMany(mappedBy = "users", fetch = LAZY)
    private Set<BoardEntity> boards = new HashSet<>();

    @ManyToMany(mappedBy = "assignees", fetch = LAZY)
    private Set<CardEntity> assignedCards = new HashSet<>();

    @ManyToMany(mappedBy = "watchers", fetch = LAZY)
    private Set<CardEntity> watchingCards = new HashSet<>();

    @ManyToMany(mappedBy = "assignedTo", fetch = LAZY)
    private Set<TaskEntity> tasks = new HashSet<>();

    @ManyToMany(mappedBy = "assignedTo", fetch = LAZY)
    private Set<RetroActionEntity> retroActions = new HashSet<>();

    @ManyToMany(mappedBy = "attendingUsers", fetch = LAZY)
    private Set<RetrospectiveEntity> retroBoards = new HashSet<>();

    @ManyToMany(mappedBy = "missingUsers", fetch = LAZY)
    private Set<RetrospectiveEntity> missedRetroBoards = new HashSet<>();
}
