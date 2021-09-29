package com.santander.games.challenges.quarkus.services;

import com.santander.games.challenges.quarkus.resources.BookDto;
import com.santander.games.challenges.quarkus.mapper.BookMapper;
import com.santander.games.challenges.quarkus.dao.Book;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.spi.UnhandledException;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;

/**
 * Our service to register at the database
 */
@Slf4j
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
     * Add a new Book
     * @param book
     * @return
     */
    @Transactional
    public BookDto addBook(BookDto book) {
        Book be = bookMapper.dtoToEntity(book);
        BookDto exist = getBookByName(book.getName());
        if(exist != null){
            return exist;
        }
        try {
            em.persist(be);
        } catch (Exception e) {
            log.warn(e.getMessage());
            return null;
        }
        book.setId(be.getId());
        return book;
    }

    /**
     * Update a Book
     * @param book
     * @return
     */
    @Transactional
    public BookDto updateBook(BookDto book) {
        Book be = bookMapper.dtoToEntity(book);
        try{
            em.merge(be);
        } catch (Exception e) {
            log.warn(e.getMessage());
            return null;
        }
        return bookMapper.entityToDto(be);
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
     * @param id
     */
    @Transactional
    public void deleteBook(Integer id){
        Query q = em.createQuery("DELETE from Book where id=?1");
        q.setParameter(1,id);
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
        try {
            return bookMapper.entityToDto((Book)query.getSingleResult());
        } catch (Exception nre){
            log.warn(nre.getMessage());
            return null;
        }
    }

    /**
     * Find books bettewen publish years
     * @param lowerYear Integer
     * @param higherYear Integer
     * @return List of books
     */
    public List<BookDto>  getBookBetweenYears(Integer lowerYear, Integer higherYear){
        Query query = em.createQuery("FROM Book where publicationYear BETWEEN ?1  AND ?2 " +
                " order by publicationYear");
        query.setParameter(1,lowerYear);
        query.setParameter(2,higherYear);


        return bookMapper.entitiesToDtos(query.getResultList());

    }

}
