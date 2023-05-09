package com.bmc.flow.modules.database.entities.catalogs;

public enum ChangeAction {
  ADDED(0),
  ATTACHED(1),
  CREATED(2),
  DELETED(3),
  REMOVED(4),
  UPDATED(5);


  ChangeAction(int actionId) {

  }
}
