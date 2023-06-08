package com.bmc.flow.modules.database.entities.catalogs;

/**
 * this enum contains the possible change actions a changelog supports.
 * <p>
 * we may have created a catalog entity for this to avoid having to update the enum in case we add more change actions but change actions
 * are off any possible manipulation from any external client, so enum it is.
 */
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
