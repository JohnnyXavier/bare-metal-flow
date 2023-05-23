package com.bmc.flow.modules.service.records;

import com.bmc.flow.modules.database.dto.records.CommentDto;
import com.bmc.flow.modules.database.entities.ChangelogEntity;
import com.bmc.flow.modules.database.entities.records.CommentEntity;
import com.bmc.flow.modules.database.repositories.ChangeLogRepository;
import com.bmc.flow.modules.database.repositories.UserRepository;
import com.bmc.flow.modules.database.repositories.records.CardRepository;
import com.bmc.flow.modules.database.repositories.records.CommentRepository;
import com.bmc.flow.modules.resources.base.Pageable;
import com.bmc.flow.modules.service.base.BasicPersistenceService;
import com.bmc.flow.modules.service.base.PageResult;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

import static com.bmc.flow.modules.database.entities.catalogs.ChangeAction.ADDED;
import static java.util.UUID.randomUUID;

@ApplicationScoped
public class CommentService extends BasicPersistenceService<CommentDto, CommentEntity> {

    private final CommentRepository   commentRepo;
    private final CardRepository      cardRepo;
    private final UserRepository      userRepo;
    private final ChangeLogRepository changeLogRepo;

    public CommentService(CommentRepository commentRepo, CardRepository cardRepo, UserRepository userRepo,
                          ChangeLogRepository changeLogRepo) {
        super(commentRepo, CommentDto.class);
        this.commentRepo   = commentRepo;
        this.cardRepo      = cardRepo;
        this.userRepo      = userRepo;
        this.changeLogRepo = changeLogRepo;
    }

    public Uni<PageResult<CommentDto>> findAllByCardIdPaged(final UUID cardId, final Pageable pageable) {
        return findAllPaged(commentRepo.findAllByCardId(cardId, pageable.getSort()), "all-comments-by-card",
            pageable.getPage());
    }

    @Override
    public Uni<CommentDto> create(CommentDto fromDto) {
        CommentEntity comment = new CommentEntity();
        comment.setId(randomUUID());
        comment.setComment(fromDto.getComment());

        ChangelogEntity changelog = new ChangelogEntity();
        changelog.setId(randomUUID());
        changelog.setField("comment");
        changelog.setChangeAction(ADDED);
        changelog.setChangeTo(fromDto.getComment());

        return userRepo.findById(fromDto.getCreatedBy())
            .onItem().ifNotNull()
            .invoke(comment::setCreatedBy).invoke(changelog::setCreatedBy)
            .chain(() -> cardRepo.findById(fromDto.getCardId()).invoke(comment::setCard).invoke(changelog::setCard))
            .call(() -> commentRepo.persist(comment))
            .call(() -> changeLogRepo.persist(changelog))
            .replaceWith(findById(comment.getId()));
    }

    @Override
    @WithTransaction
    protected Uni<Void> update(CommentEntity toUpdate, String key, String value) {
        return Uni.createFrom().voidItem();
    }
}
