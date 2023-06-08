package com.bmc.flow.modules.database.entities.records;

import com.bmc.flow.modules.database.entities.UserEntity;
import com.bmc.flow.modules.database.entities.base.BaseRecordEntity;
import com.bmc.flow.modules.database.entities.catalogs.BoardColumnEntity;
import com.bmc.flow.modules.database.entities.catalogs.LabelEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.CascadeType.*;

/**
 * this class represents the account table and it's relations.
 */
@Entity
@Table(name = "account")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class AccountEntity extends BaseRecordEntity {

    @OneToMany(mappedBy = "account", cascade = ALL)
    private Set<ProjectEntity> projects = new HashSet<>();

    @OneToMany(mappedBy = "account", cascade = ALL)
    private Set<BoardEntity> boards = new HashSet<>();

    @OneToMany(mappedBy = "account", cascade = ALL)
    private Set<CardEntity> cards = new HashSet<>();

    @OneToMany(mappedBy = "account", cascade = ALL)
    private Set<BoardColumnEntity> boardColumns = new HashSet<>();

    @ManyToMany(cascade = {PERSIST, MERGE})
    @JoinTable(name = "account_labels", joinColumns = @JoinColumn(name = "account_id"), inverseJoinColumns = @JoinColumn(name = "label_id"))
    private Set<LabelEntity> labels = new HashSet<>();

    @ManyToMany(cascade = {PERSIST, MERGE})
    @JoinTable(name = "account_users", joinColumns = @JoinColumn(name = "account_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<UserEntity> users = new HashSet<>();

}
