package com.bmc.flow.modules.resources.records;

import com.bmc.flow.modules.database.dto.records.BoardColumnDto;
import com.bmc.flow.modules.database.entities.catalogs.BoardColumnEntity;
import com.bmc.flow.modules.resources.base.BasicOpsResource;
import com.bmc.flow.modules.resources.base.Pageable;
import com.bmc.flow.modules.resources.utils.ResponseUtils;
import com.bmc.flow.modules.service.records.BoardColumnService;
import io.smallrye.mutiny.Uni;

import jakarta.persistence.NoResultException;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import java.util.UUID;

import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;

@Path("/v1/boardColumn")
@Produces("application/json")
public class BoardColumnResource extends BasicOpsResource<BoardColumnDto, BoardColumnEntity> {

  private final BoardColumnService boardColumnService;

  public BoardColumnResource(final BoardColumnService boardColumnService) {
    super(boardColumnService);
    this.boardColumnService = boardColumnService;
  }

  @GET
  @Path("board/{id}")
  public Uni<Response> findBoardsByProjectId(final UUID id,
                                             @QueryParam(value = "sortBy") @NotNull final String sortBy,
                                             @QueryParam(value = "sortDir") final String sortDir,
                                             @QueryParam(value = "pageIx") final Integer pageIx,
                                             @QueryParam(value = "pageSize") @NotNull final Integer pageSize) {
    return boardColumnService.findAllByBoardIdPaged(id, new Pageable(sortBy, sortDir, pageIx, pageSize))
        .map(boardDtos -> Response.ok(boardDtos).build())
        .onFailure(NoResultException.class).recoverWithItem(Response.status(NOT_FOUND)::build)
        .onFailure().recoverWithItem(ResponseUtils::failToServerError);
  }

}
