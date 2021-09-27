package com.santander.games.challenges.quarkus.services;

import com.santander.games.challenges.quarkus.resources.BookDto;
import com.santander.games.challenges.quarkus.mapper.BookMapper;
import com.santander.games.challenges.quarkus.dao.Book;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

/**
 * Our service to register at the database
 */
@ApplicationScoped
public class LibraryService {
    /**
     * Entity Manager
     */
    @Inject
    EntityManager em;

    /**
     * Mapping Component
     */
    @Inject
    BookMapper bookMapper;

    /**
     * Add a new KudoCard
     * @param book
     * @return
     */
    @Transactional
    public BookDto addBook(BookDto book) {
        Book be = bookMapper.dtoToEntity(book);
        em.persist(be);
        book.setId(be.getId());
        return book;
    }

    /**
     * Get the list of books paging (order by id)
     * @param pageNumber Page number
     * @param numberelements (number of elments by page)
     *
     */
    @Transactional
    public List<BookDto> listOfBooks(int pageNumber, int numberelements) {
        Query query = em.createQuery("FROM BOOK order by id");
        query.setFirstResult((pageNumber-1)*numberelements);
        query.setMaxResults(numberelements);
        return bookMapper.entitiesToDtos(query.getResultList());
    }

    /**
     * Get the list of books paging (order by id)
     *
     */
    @Transactional
    public List<BookDto> getAll() {
        Query query = em.createQuery("FROM BOOK order by id");
        return bookMapper.entitiesToDtos(query.getResultList());
    }


    /**
     * DElete book
     * @param book
     */
    @Transactional
    public void deleteBook(BookDto book){
        Query q = em.createQuery("DELETE from kudo where id=?");
        q.setParameter(0,book.getId());
        q.executeUpdate();
    }

    /**
     *  Find by id a book
     * @param id Integer
     * @return BookDto
     */
    public BookDto getBookById(Integer id){
        return bookMapper.entityToDto(Book.findById(id));
    }

    /**
     *  Find by id a book
     * @param name String
     * @return BookDto
     *
     */
    public BookDto getBookByName(String name){
        return bookMapper.entityToDto(Book.findByName(name));
    }

    /**
     * Find books bettewen publish years
     * @param lowerYear Integer
     * @param higherYear Integer
     * @return List of books
     */
    public List<BookDto>  getBookBetweenYears(Integer lowerYear, Integer higherYear){
        String betweenQuery = "publicationYear   BETWEEN ".concat(lowerYear+"")
                .concat(" AND ").concat(higherYear+"");
        if(higherYear == null){
            betweenQuery = "publicationYear > "+ lowerYear;
        }
        Query query = em.createQuery("FROM BOOK " + betweenQuery +
                "order by publicationYear");
        return bookMapper.entitiesToDtos(query.getResultList());
    }

}
