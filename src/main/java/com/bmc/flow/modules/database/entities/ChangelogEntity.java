package com.bmc.flow.modules.database.entities;

import com.bmc.flow.modules.database.entities.base.BaseEntity;
import com.bmc.flow.modules.database.entities.catalogs.ChangeAction;
import com.bmc.flow.modules.database.entities.records.CardEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


/**
 * Article about the benefits of a proper reusable Native Query:<br>
 * a changelog that is mostly repeated everywhere is a great candidate for this sql code into the namedNativeQuery
 */
@Entity
@Table(name = "changelog")
@NamedNativeQuery(name = "Changelog.Create",
    query = "insert into changelog(id, created_at, change_from, field, created_by_id, card_id, change_action)" +
        " VALUES (gen_random_uuid(), current_timestamp, ?1, ?2, ?3, ?4, ?5 )")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class ChangelogEntity extends BaseEntity {
  /**
   * for ChangeLogs: use the "createdAt" as changedAt field use the "createdBy" as a changedBy field
   * <br><br>
   * there is a good reason to use Enums for the changeActions but also enums will eat DB space for no reason, and they are just repetitions
   * of small dataSets just like catalogs.<br> Using a catalog instead of an enum will benefit into not repeating the data but will also
   * require us to search the DB prior to use them and to use them consistently we will need another enum mapping the DB's content.<br> With
   * a local cache, that pre-fetch could be done on startup with ease, but for the moment we will stick to regular enums to showcase the use
   * of enums on a DB as there are enough examples of catalogs all over the app.
   * <br><br>
   * Thou using originally postgres I won't use postgres enums as that datatype is not widely supported elsewhere
   */

  private String field;

  private String changeFrom;

  private String changeTo;

  @Enumerated
  @Column(columnDefinition = "smallint")
  private ChangeAction changeAction;

  @ManyToOne
  private CardEntity card;

}
