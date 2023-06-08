package com.bmc.flow.modules.database.entities.records;

import com.bmc.flow.modules.database.entities.UserEntity;
import com.bmc.flow.modules.database.entities.base.BaseEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.FetchType.LAZY;

/**
 * this class represents the task table and it's relations.
 */
@Entity
@Table(name = "task")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class TaskEntity extends BaseEntity {

    private String name;

    private LocalDateTime dueDate;

    // this gets set when is completed is true
    private LocalDateTime completedDate;

    @Column(columnDefinition = "boolean default false")
    private Boolean isCompleted;

    @ManyToOne(fetch = LAZY)
    private CardEntity card;

    @ManyToMany(cascade = {PERSIST, MERGE}, fetch = LAZY)
    @JoinTable(name = "tasks_users_assigned", joinColumns = @JoinColumn(name = "task_id"),
               inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<UserEntity> assignedTo = new HashSet<>();

}
