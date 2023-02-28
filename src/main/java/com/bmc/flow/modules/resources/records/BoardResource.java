package com.bmc.flow.modules.resources.records;

import com.bmc.flow.modules.database.dto.records.BoardDto;
import com.bmc.flow.modules.database.entities.records.BoardEntity;
import com.bmc.flow.modules.resources.base.BasicOpsResource;
import com.bmc.flow.modules.resources.utils.ResponseUtils;
import com.bmc.flow.modules.service.records.BoardService;
import io.smallrye.mutiny.Uni;

import javax.persistence.NoResultException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.UUID;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Path("/v1/board")
@Produces("application/json")
public class BoardResource extends BasicOpsResource<BoardDto, BoardEntity> {

  private final BoardService boardService;

  public BoardResource(final BoardService boardService) {
    super(boardService);
    this.boardService = boardService;
  }

  @GET
  @Path("project/{projectId}")
  public Uni<Response> findBoardsByProjectId(final UUID projectId) {
    return boardService.getAllByProjectId(projectId)
                       .map(boardDtos -> Response.ok(boardDtos).build())
                       .onFailure(NoResultException.class).recoverWithItem(Response.status(NOT_FOUND)::build)
                       .onFailure().recoverWithItem(ResponseUtils::failToServerError);
  }

}
