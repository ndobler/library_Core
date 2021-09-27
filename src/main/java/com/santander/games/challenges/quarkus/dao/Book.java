package com.santander.games.challenges.quarkus.dao;


import io.quarkus.hibernate.orm.panache.PanacheEntity;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Book extends PanacheEntity {
    @Id
    private Integer id;

    public String name;

    public Integer publicationYear;

    /**
     * Find book by Id
     * @param id Integer
     * @return
     */
    public static Book findById(Integer id){
        return find("id", id).firstResult();
    }

    /**
     *  Find a book by name
     * @param name String
     * @return Book
     */
    public static Book findByName(String name){
        return find("name", name).firstResult();
    }

}
