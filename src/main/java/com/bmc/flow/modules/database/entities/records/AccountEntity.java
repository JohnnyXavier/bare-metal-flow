package com.bmc.flow.modules.database.entities.records;

import com.bmc.flow.modules.database.entities.base.BaseRecordEntity;
import com.bmc.flow.modules.database.entities.catalogs.LabelEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.*;

@Entity
@Table(name = "account")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class AccountEntity extends BaseRecordEntity {

  // FIXME: maybe add users to accounts to limit which user can be proposed to be added pFolios / projects / etc...
  //  the above will make stats on accounts and lower segmentations clearer and queries less "joiny" and convoluted
  @ManyToOne
  private PortfolioEntity portfolio;

  @OneToMany(mappedBy = "account", cascade = ALL)
  private Set<ProjectEntity> projects = new HashSet<>();

  @ManyToMany(cascade = {PERSIST, MERGE})
  @JoinTable(name = "account_labels", joinColumns = @JoinColumn(name = "account_id"), inverseJoinColumns = @JoinColumn(name = "label_id"))
  private Set<LabelEntity> labels = new HashSet<>();

}
