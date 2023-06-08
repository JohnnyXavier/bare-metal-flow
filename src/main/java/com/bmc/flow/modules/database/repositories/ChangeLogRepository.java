package com.bmc.flow.modules.database.repositories;

import com.bmc.flow.modules.database.entities.ChangelogEntity;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

/**
 * this class is the data access for changelog data.
 */
@ApplicationScoped
public class ChangeLogRepository implements PanacheRepositoryBase<ChangelogEntity, UUID> {

}
