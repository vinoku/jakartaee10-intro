package com.pluralsight;

import jakarta.inject.Inject;
import jakarta.validation.constraints.Min;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import java.net.URI;
import java.util.List;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static jakarta.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("/api/books")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class BookResource {

    @Inject
    UriInfo uriInfo;

    @Inject
    BookService bookService;

    @GET
    @Operation(summary = "Returns all the books")
    @APIResponse(responseCode = "200", description = "Books found", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Book.class, type = SchemaType.ARRAY)))
    @APIResponse(responseCode = "204", description = "No books found")
    public Response getBooks() {
        List<Book> books = bookService.findAll();

        if (books.isEmpty())
            return Response.noContent().build();

        return Response.ok(books).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Returns a book given an id")
    @APIResponse(responseCode = "200", description = "Book found", content = @Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = Book.class, required = true)))
    @APIResponse(responseCode = "404", description = "Book not found")
    public Response getBook(@PathParam("id") @Min(1) Long id) {
        Book book = bookService.find(id);
        if (book == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok(book).build();
    }

    @GET
    @Path("/count")
    @Produces(TEXT_PLAIN)
    @Operation(summary = "Returns the number of books")
    @APIResponse(responseCode = "200", description = "Number of books found", content = @Content(mediaType = TEXT_PLAIN, schema = @Schema(implementation = Long.class, required = true)))
    @APIResponse(responseCode = "204", description = "No books found")
    public Response countBooks() {
        Long nbOfBooks = bookService.countAll();
        if (nbOfBooks == 0)
            return Response.noContent().build();

        return Response.ok(nbOfBooks).build();
    }

    @POST
    @Operation(summary = "Creates a book given a JSon Book representation")
    @APIResponse(responseCode = "201", description = "The book is created", content = @Content(schema = @Schema(implementation = URI.class, required = true)))
    public Response createBook(Book book) {
        book = bookService.create(book);
        URI createdURI = uriInfo.getAbsolutePathBuilder().path(Long.toString(book.getId())).build();
        return Response.created(createdURI).entity(book).build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Deletes a book given an id")
    @APIResponse(responseCode = "204", description = "The book is deleted")
    public Response deleteBook(@PathParam("id") @Min(1) Long id) {
        bookService.delete(id);
        return Response.noContent().build();
    }
}
