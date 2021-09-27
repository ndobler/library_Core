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
        Query query = em.createQuery("FROM Book order by id");
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
        Query query = em.createQuery("FROM Book order by id");
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
        Query query = em.createQuery("FROM Book where id= ?1");
        query.setParameter(1,id);

        return bookMapper.entityToDto((Book)query.getSingleResult());
    }

    /**
     *  Find by id a book
     * @param name String
     * @return BookDto
     *
     */
    public BookDto getBookByName(String name){
        Query query = em.createQuery("FROM Book where name= ?1");
        query.setParameter(1,name);

        return bookMapper.entityToDto((Book)query.getSingleResult());
    }

    /**
     * Find books bettewen publish years
     * @param lowerYear Integer
     * @param higherYear Integer
     * @return List of books
     */
    public List<BookDto>  getBookBetweenYears(Integer lowerYear, Integer higherYear){
        String betweenQuery = "publicationYear BETWEEN ?1  AND ?1";
        if(higherYear == null){
            betweenQuery = "publicationYear > ?1";
        }
        Query query = em.createQuery("FROM Book " + betweenQuery +
                "order by publicationYear");
        query.setParameter(1,lowerYear);
        query.setParameter(2,higherYear);

        return bookMapper.entitiesToDtos(query.getResultList());
    }

}
