package com.pluralsight;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static jakarta.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("/api/books")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class BookResource {

    @Inject
    private BookService service;

    @Inject
    UriInfo uriInfo;

    @GET
    public Response getBooks() {
        List<Book> books = service.findAll();

        if (books.isEmpty()) {
            return Response.noContent().build();
        }

        return Response.ok(books).build();
    }

    @GET
    @Path("/{id}")
    public Response getBook(@PathParam("id") long id) {
        Book book = service.find(id);
        if (book == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(book).build();
    }

    @GET
    @Path("/count")
    @Produces(TEXT_PLAIN)
    public Response countBooks() {
        Long nbOfBooks = service.count();

        if (nbOfBooks == 0) {
            return Response.noContent().build();
        }

        return Response.ok(nbOfBooks).build();
    }

    @POST
    public Response createBook(Book book) throws URISyntaxException {
        book = service.create(book);
        URI createdURI = uriInfo.getAbsolutePathBuilder().path(Long.toString(book.getId())).build();
        return Response.created(createdURI).entity(book).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") long id) {
        service.delete(id);
        return Response.noContent().build();
    }
}
