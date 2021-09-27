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

import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

@Slf4j
@Path("/books")
public class LibraryResource {

    @Inject
    LibraryService libraryService;

    @POST
    public BookDto add(BookDto book) {
        return libraryService.addBook(book);
    }

    @PUT
    public BookDto modify(BookDto book) {
        return null;
    }

    /**
     * List Books paging
     *
     * @param pageNumber Number of pages
     * @param maxNumber  max number
     * @return list of kudos
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
     * @param id INteger
     * @return Book
     */
    @GET
    @Path("/book/{id}")
    public BookDto get(@PathParam("id") Integer id){
            return libraryService.getBookById(id);

    }

    /**
     * Get book by id
     * @param name Stengi
     * @return Book
     */
    @GET
    @Path("/byName/{name}")
    public BookDto getByName(@PathParam("name") String name){
            return libraryService.getBookByName(name);
    }

    /**
     * Get book by id
     * @param name Stengi
     * @return Book
     */
    @GET
    @Path("/byPublicationYearBetween/{lowerYear}/{higherYear}")
    public  List<BookDto> getByPublicationYearBetween(
            @PathParam("lowerYear") Integer lowerYear,
            @PathParam("higherYear") Integer higherYear){
        return libraryService.getBookBetweenYears(lowerYear,higherYear);

    }


    @DELETE
    public void delete(BookDto book) {
       libraryService.deleteBook(book);
    }
}
