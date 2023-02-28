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
@Table(name = "portfolio")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class PortfolioEntity extends BaseRecordEntity {

  // FIXME: maybe add users to portfolios to limit which user can be proposed to be added accounts / projects / etc...
  //  the above will make stats on accounts and lower segmentations clearer and queries less "joiny" and convoluted
  @OneToMany(mappedBy = "portfolio", cascade = ALL)
  private Set<AccountEntity> accounts = new HashSet<>();

  @Column(columnDefinition = "boolean default false")
  private Boolean isDefault;

  @ManyToMany(cascade = {PERSIST, MERGE})
  @JoinTable(name = "portfolio_labels", joinColumns = @JoinColumn(name = "portfolio_id"), inverseJoinColumns = @JoinColumn(name = "label_id"))
  private Set<LabelEntity> labels = new HashSet<>();
}
