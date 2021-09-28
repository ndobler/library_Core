package com.santander.games.challenges.quarkus.dao;


import javax.persistence.*;


import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Table(name = Book.TABLE_NAME)
@Entity
public class Book {
    public static final String TABLE_NAME= "BOOK";

    @Id
    //@GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    public String name;

    public Integer publicationYear;
}


