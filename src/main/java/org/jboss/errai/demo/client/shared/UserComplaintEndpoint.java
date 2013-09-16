package org.jboss.errai.demo.client.shared;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * This JAX-RS resource interface is used on both the client and the server. On
 * the server, it is implemented to expose the described resource methods as
 * HTTP endpoints. On the client, the interface can be used to construct
 * type safe remote method calls without having to worry about implementing the
 * request or serialization logic.
 */
@Path("/usercomplaints")
public interface UserComplaintEndpoint {

  @POST
  @Consumes("application/json")
  public Response create(UserComplaint entity);

  @PUT
  @Path("/{id:[0-9][0-9]*}")
  @Consumes("application/json")
  public Response update(@PathParam("id") Long id, UserComplaint entity);

  @DELETE
  @Path("/{id:[0-9][0-9]*}")
  public Response delete(@PathParam("id") Long id);

  @GET
  @Path("search/{query}")
  @Produces("application/json")
  public List<UserComplaint> search(@PathParam("query") String query);

  @GET
  @Path("/{id:[0-9][0-9]*}")
  @Produces("application/json")
  public UserComplaint get(@PathParam("id") Long id);

  @GET
  @Produces("application/json")
  public List<UserComplaint> findAll(@QueryParam("start") int start, @QueryParam("limit") int limit);
}
