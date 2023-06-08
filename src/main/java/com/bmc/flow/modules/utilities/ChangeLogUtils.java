package com.bmc.flow.modules.utilities;

import com.bmc.flow.modules.database.entities.ChangelogEntity;
import com.bmc.flow.modules.database.entities.UserEntity;
import com.bmc.flow.modules.database.entities.records.CardEntity;

import java.util.ArrayList;
import java.util.List;

import static java.util.UUID.randomUUID;

/**
 * this class contains utilities to generate changelogs. It is not a utility class per se.
 * <p>
 * I used to handle changelogs very differently than how it is now, and this class became redundant... I cannot get myself to delete it yet
 * as I may come back to my original idea.
 */
public class ChangeLogUtils {

    private final List<ChangelogEntity> changelogEntityList = new ArrayList<>();

    /**
     * @param changedField
     * @param changedFrom
     * @param changedTo
     * @param changedBy
     * @param card
     *
     * @return
     */
    public ChangeLogUtils createEntryList(final String changedField, final String changedFrom, final String changedTo,
                                          final UserEntity changedBy,
                                          final CardEntity card) {
        ChangelogEntity newChangelog = new ChangelogEntity();

        newChangelog.setId(randomUUID());
        newChangelog.setField(changedField);
        newChangelog.setChangeFrom(changedFrom);
        newChangelog.setChangeTo(changedTo);
        newChangelog.setCard(card);
        newChangelog.setCreatedBy(changedBy);

        changelogEntityList.add(newChangelog);

        return this;
    }

    public List<ChangelogEntity> buildEntryList() {
        return this.changelogEntityList;
    }

}
