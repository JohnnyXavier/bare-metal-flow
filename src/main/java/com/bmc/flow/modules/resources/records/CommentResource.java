package com.bmc.flow.modules.resources.records;

import com.bmc.flow.modules.database.dto.records.CommentDto;
import com.bmc.flow.modules.database.entities.records.CommentEntity;
import com.bmc.flow.modules.resources.base.BasicOpsResource;
import com.bmc.flow.modules.resources.base.Pageable;
import com.bmc.flow.modules.resources.utils.ResponseUtils;
import com.bmc.flow.modules.service.records.CommentService;
import io.smallrye.mutiny.Uni;

import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("/v1/comments")
@Produces("application/json")
public class CommentResource extends BasicOpsResource<CommentDto, CommentEntity> {

  private final CommentService commentService;

  public CommentResource(final CommentService commentService) {
    super(commentService);
    this.commentService = commentService;
  }

  @GET
  @Path("card/{id}")
  public Uni<Response> findCommentsByCardId(final UUID id,
                                          @QueryParam(value = "sortBy") @NotNull final String sortBy,
                                          @QueryParam(value = "sortDir") final String sortDir,
                                          @QueryParam(value = "pageIx") final Integer pageIx,
                                          @QueryParam(value = "pageSize") @NotNull final Integer pageSize) {
    return commentService.findAllByCardIdPaged(id, new Pageable(sortBy, sortDir, pageIx, pageSize))
        .map(resultDtos -> Response.ok(resultDtos).build())
        .onFailure().recoverWithItem(ResponseUtils::failToServerError);
  }

}
