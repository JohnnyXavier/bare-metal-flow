package com.bmc.flow.modules.service.statistics;

import com.bmc.flow.modules.database.dto.statistics.StatsCountDto;
import com.bmc.flow.modules.database.repositories.records.AccountRepository;
import com.bmc.flow.modules.service.UserService;
import com.bmc.flow.modules.service.base.BasicPersistenceService;
import com.bmc.flow.modules.service.base.BasicStatsService;
import com.bmc.flow.modules.service.records.AccountService;
import com.bmc.flow.modules.service.records.CardService;
import com.bmc.flow.modules.service.records.ProjectService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * this class is a data access service for statistics.
 * <p>
 * this is a placeholder for a proper stats service in a future.
 */
@ApplicationScoped
public class StatisticsService extends BasicStatsService {

    private final AccountService accountService;

    private final AccountRepository accountRepo;

    private final ProjectService projectService;

    private final CardService cardService;

    private final UserService userService;

    private final Map<String, BasicPersistenceService> atAtGlanceMap = new HashMap<>();

    public StatisticsService(final AccountService accountService, final AccountRepository accountRepo, final ProjectService projectService,
                             final CardService cardService, final UserService userService) {
        this.accountService = accountService;
        this.accountRepo    = accountRepo;
        this.projectService = projectService;
        this.cardService    = cardService;
        this.userService    = userService;

        atAtGlanceMap.put("Accounts", accountService);
        atAtGlanceMap.put("Projects", projectService);
        atAtGlanceMap.put("Cards", cardService);
        atAtGlanceMap.put("Users", userService);
    }

    public Uni<Set<StatsCountDto>> getAtAGlanceStats(final UUID userId) {
        final StatsCountDto accountsCount               = new StatsCountDto();
        final StatsCountDto projectsCount               = new StatsCountDto();
        final StatsCountDto cardsCountOnUserAccounts    = new StatsCountDto();
        final StatsCountDto activeUserCountUserAccounts = new StatsCountDto();

        final Set<StatsCountDto> atAGlanceStats = Set
            .of(accountsCount, projectsCount, cardsCountOnUserAccounts, activeUserCountUserAccounts);

        return Uni.createFrom().item(atAGlanceStats);
    }
}
