package com.bmc.flow.modules.resources;

import com.bmc.flow.modules.database.dto.UserDto;
import com.bmc.flow.modules.database.dto.UserRegistrationDto;
import com.bmc.flow.modules.database.entities.UserEntity;
import com.bmc.flow.modules.database.repositories.UserRepository;
import com.bmc.flow.modules.resources.base.BasicOpsResource;
import com.bmc.flow.modules.resources.base.Pageable;
import com.bmc.flow.modules.resources.utils.ResponseUtils;
import com.bmc.flow.modules.service.UserService;
import io.smallrye.mutiny.Uni;
import io.vertx.pgclient.PgException;
import jakarta.persistence.NoResultException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Cookie;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
import lombok.extern.jbosslog.JBossLog;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Map;
import java.util.UUID;

import static jakarta.ws.rs.core.Response.Status.*;
import static java.util.Map.ofEntries;

/**
 * this class is the rest resource handling user requests.
 */
@Path("v1/users")
@Produces("application/json")
@JBossLog
public class UserResource extends BasicOpsResource<UserDto, UserEntity> {

    private final UserService userService;

    private final UserRepository userRepo;

    private final Map<String, String> userSupportedCollections = ofEntries(
        new SimpleImmutableEntry<>("project", "projects"),
        new SimpleImmutableEntry<>("account", "accounts"),
        new SimpleImmutableEntry<>("board", "boards"),
        new SimpleImmutableEntry<>("sprint", "sprints"),
        new SimpleImmutableEntry<>("assignedCard", "assignedCards"),
        new SimpleImmutableEntry<>("watchingCard", "watchingCards"),
        new SimpleImmutableEntry<>("department", "departments"),
        new SimpleImmutableEntry<>("retroBoard", "retroBoards")
    );

    public UserResource(final UserService userService, UserRepository userRepo) {
        super(userService);
        this.userService = userService;
        this.userRepo    = userRepo;
    }

    @GET
    @Path("{collection}/{collectionId}")
    public Uni<Response> findAllByCollectionId(final String collection, final UUID collectionId,
                                               @QueryParam(value = "sortBy") @NotNull final String sortBy,
                                               @QueryParam(value = "sortDir") final String sortDir,
                                               @QueryParam(value = "pageIx") final Integer pageIx,
                                               @QueryParam(value = "pageSize") @NotNull final Integer pageSize) {

        String collections = userSupportedCollections.get(collection);
        if (collections == null) {
            return Uni.createFrom().item(Response.ok().status(NOT_FOUND).build());
        } else {
            return userService.findAllInCollectionId(collections, collectionId, new Pageable(sortBy, sortDir, pageIx,
                                  pageSize))
                              .map(userDtos -> Response.ok(userDtos).build());
        }
    }


    @POST
    @Path("register")
    @Consumes("application/json")
    public Uni<Response> register(final UserRegistrationDto fromDto) {
        return userService.register(fromDto)
                          .map(newlyCreatedDto -> {
                              Cookie cookie = new Cookie.Builder("userId")
                                  .value(newlyCreatedDto.getId().toString())
                                  .path("/")
                                  .domain("localhost")
                                  .build();
                              return Response.ok(newlyCreatedDto).status(CREATED).cookie(new NewCookie.Builder(cookie).build()).build();
                          })
                          .onFailure(ConstraintViolationException.class).recoverWithItem(ResponseUtils::violationsToResponse)
                          .onFailure(PgException.class).recoverWithItem(ResponseUtils::processPgException)
                          .onFailure().recoverWithItem(ResponseUtils::failToServerError);
    }


    @Override
    public Uni<Response> create(final UserDto fromDto) {
        return Uni.createFrom().item(Response.status(NOT_FOUND).build());
    }


    /***
     * This is a silly login to play with the front end and have a showcase-able flow
     * ---<br>
     * Security is not yet implemented in the app on purpose as the app functionality itself is the key for the moment
     * <p>
     * this is not safe---<br>
     * this is not secure---<br>
     * do not ever use this in any production environment if you clone this repo do not use the app as is, and implement
     * your own sec or wait until I implement it myself<br
     * <p>
     *
     * @param email user unique readable identifier
     * @param password the pas, plain text, not encoded
     * @return the response with the user id as a cookie
     */
    @POST
    @Path("login")
    @Consumes("application/x-www-form-urlencoded")
    public Uni<Response> moreThanInnocentLoginDoNotUseInProdEver(@FormParam("email") final String email,
                                                                 @FormParam("password") final String password
    ) {
        log.infof("email is: %s", email);
        log.infof("password is: %s", password);

        return userRepo.find("email", email)
                       .singleResult()
                       .map(userEntity -> {
                           if (userEntity.getPassword().equals(password)) {
                               NewCookie setUserIdCookie = new NewCookie.Builder("userId")
                                   .value(userEntity.getId().toString())
                                   .path("/")
                                   .domain("localhost")
                                   .build();

                               return Response.ok().cookie(setUserIdCookie).build();
                           } else {
                               NewCookie removeUserIdCookie = new NewCookie.Builder("userId")
                                   .value(null)
                                   .path("/")
                                   .domain("localhost")
                                   .maxAge(0)
                                   .build();

                               return Response.status(UNAUTHORIZED)
                                              .cookie(removeUserIdCookie).build();
                           }
                       })
                       .onFailure(ConstraintViolationException.class).recoverWithItem(ResponseUtils::violationsToResponse)
                       .onFailure(PgException.class).recoverWithItem(ResponseUtils::processPgException)
                       .onFailure(NoResultException.class).recoverWithItem(Response.ok("user not found").status(NOT_FOUND).build())
                       .onFailure().recoverWithItem(ResponseUtils::failToServerError);
    }

}
