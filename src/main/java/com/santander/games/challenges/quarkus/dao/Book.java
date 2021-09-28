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
    @SequenceGenerator(name = "book_seq", sequenceName = "book_seq_1",initialValue = 100,   allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_seq")
    private Integer id;

    public String name;

    public Integer publicationYear;
}


