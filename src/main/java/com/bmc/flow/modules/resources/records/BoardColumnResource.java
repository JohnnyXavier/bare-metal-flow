package com.bmc.flow.modules.resources.records;

import com.bmc.flow.modules.database.dto.records.BoardColumnDto;
import com.bmc.flow.modules.database.entities.catalogs.BoardColumnEntity;
import com.bmc.flow.modules.resources.base.BasicOpsResource;
import com.bmc.flow.modules.resources.utils.ResponseUtils;
import com.bmc.flow.modules.service.records.BoardColumnService;
import io.smallrye.mutiny.Uni;

import javax.persistence.NoResultException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.UUID;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

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
  public Uni<Response> findBoardsByProjectId(final UUID id) {
    return boardColumnService.getAllByBoardId(id)
        .map(boardDtos -> Response.ok(boardDtos).build())
        .onFailure(NoResultException.class).recoverWithItem(Response.status(NOT_FOUND)::build)
        .onFailure().recoverWithItem(ResponseUtils::failToServerError);
  }

}
