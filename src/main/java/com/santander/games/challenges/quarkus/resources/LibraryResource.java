package com.santander.games.challenges.quarkus.resources;


import com.santander.games.challenges.quarkus.services.LibraryService;
import java.net.HttpURLConnection;
import java.util.List;
import javax.inject.Inject;

import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

@Slf4j
@Path("/books")
public class LibraryResource {

    /**
     * Inject Library Service
     */
    @Inject
    LibraryService libraryService;

    /**
     * Add a new book
     * @param book BookDto
     * @return Response
     */
    @POST
    public Response add(BookDto book) {
        BookDto bd =libraryService.addBook(book);
        if(bd == null){
            throw new WebApplicationException(HttpURLConnection.HTTP_BAD_REQUEST);
        }
        return Response.status(Response.Status.CREATED).entity(bd).build() ;
    }

    /**
     * Modify a book
     * @param book BookDto
     * @return BookDto
     */
    @PUT
    public BookDto modify(BookDto book) {
        BookDto bd =libraryService.updateBook(book);
        if(bd == null){
            throw new WebApplicationException(HttpURLConnection.HTTP_BAD_REQUEST);
        }
        return bd;
    }

    /**
     * List Books paging
     *
     * @param pageNumber Number of pages
     * @param maxNumber  max number
     * @return list of books
     */
    @GET
    @Path("/list")
    public List<BookDto> list(
            @DefaultValue("1") @QueryParam("pageNumber") Integer pageNumber,
            @DefaultValue("5") @QueryParam("maxNumber") Integer maxNumber) {
        //Defing parameters defaults
        return libraryService.listOfBooks(pageNumber, maxNumber);
    }

    /**
     *  Get all elements
     * @return List of all books
     */
    @GET
    @Path("/all")
    public List<BookDto> getAll(){
        return libraryService.getAll();
    }

    /**
     * Get book by id
     * @param id String
     * @return Book
     */
    @GET
    @Path("/book/{id}")
    public BookDto get(@PathParam("id") String id){
        try {
            Integer idInt =Integer.parseInt(id);
            return libraryService.getBookById(idInt);
        } catch (NumberFormatException ne) {
            throw new WebApplicationException(HttpURLConnection.HTTP_BAD_REQUEST);
        }

    }

    /**
     * Get book by name
     * @param name String
     * @return Response
     */
    @GET
    @Path("/byName/{name}")
    public Response getByName(@PathParam("name") String name){
        BookDto bookDto =libraryService.getBookByName(name);
        if(bookDto == null){
            return Response.status(Response.Status.NOT_FOUND).entity("Sorry").build();
        }
        return Response.status(Response.Status.OK).entity(bookDto).build();
    }

    /**
     * Get books between publishing dates
     * @param lowerYear String
     * @param higherYear String
     * @return Book
     */
    @GET
    @Path("/byPublicationYearBetween/{lowerYear}/{higherYear}")
    public  List<BookDto> getByPublicationYearBetween(
            @PathParam("lowerYear") String lowerYear,
            @PathParam("higherYear") String higherYear){
        try {
            Integer lowerYearInt =Integer.parseInt(lowerYear);
            Integer higherYearInt =Integer.parseInt(higherYear);
            return libraryService.getBookBetweenYears(lowerYearInt,higherYearInt);
        } catch (NumberFormatException ne) {
            throw new WebApplicationException(HttpURLConnection.HTTP_BAD_REQUEST);
        }


    }


    /**
     * Delete a book from the list
     * @param bookId String
     */
    @DELETE
    @Path("/book/{id}")
    public void delete(@PathParam("id") String  bookId) {
        try {
            Integer idInt =Integer.parseInt(bookId);
            libraryService.deleteBook(idInt);
        } catch (NumberFormatException ne) {
            throw new WebApplicationException(HttpURLConnection.HTTP_BAD_REQUEST);
        }
    }
}
