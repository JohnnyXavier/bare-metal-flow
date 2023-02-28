package com.bmc.flow.modules.utilities;

import com.bmc.flow.modules.database.entities.ChangeLogCardEntity;
import com.bmc.flow.modules.database.entities.UserEntity;
import com.bmc.flow.modules.database.entities.records.CardEntity;

import java.util.ArrayList;
import java.util.List;

import static java.util.UUID.randomUUID;

public class ChangeLogUtils {

  private final List<ChangeLogCardEntity> changeLogCardEntityList = new ArrayList<>();

  public ChangeLogUtils createEntryList(final String changedField, final String changedFrom, final String changedTo,
                                        final UserEntity changedBy,
                                        final CardEntity card) {
    ChangeLogCardEntity newChangelog = new ChangeLogCardEntity();

    newChangelog.setId(randomUUID());
    newChangelog.setChangedField(changedField);
    newChangelog.setChangeFrom(changedFrom);
    newChangelog.setChangeTo(changedTo);
    newChangelog.setCard(card);
    newChangelog.setCreatedBy(changedBy);

    changeLogCardEntityList.add(newChangelog);

    return this;
  }

  public List<ChangeLogCardEntity> buildEntryList() {
    return this.changeLogCardEntityList;
  }

}
