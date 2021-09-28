package com.santander.games.challenges.quarkus.mapper;

import com.santander.games.challenges.quarkus.resources.BookDto;
import com.santander.games.challenges.quarkus.dao.Book;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;

/**
 * Mapper for dto to entity
 */
@ApplicationScoped
public class BookMapper {

    /**
     * From front to data
     * @param book BookDto
     * @return Book Entity
     */
    public Book dtoToEntity(BookDto book){
        Book be = new Book();
        be.setId(book.getId());
        be.setName(book.getName());
        be.setPublicationYear(book.getPublicationYear());
        return be;
    }

    /**
     * from dat to front
     * @param  book Book
     * @return BookDto
     */
    public BookDto entityToDto(Book book){
        BookDto bookDto =new BookDto();
        bookDto.setId(book.getId());
        bookDto.setName(book.getName());
        bookDto.setPublicationYear(book.publicationYear);
        return bookDto;
    }


    /**
     * Map list from dat to front
     * @param bookEntities List of Entities
     * @return List of Dtos
     */
    public List<BookDto> entitiesToDtos(List<Book> bookEntities) {
        return bookEntities.stream().map(x -> entityToDto(x)).collect(Collectors.toList());
    }
}
