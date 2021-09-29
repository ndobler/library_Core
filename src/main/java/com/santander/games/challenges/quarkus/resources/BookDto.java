package com.santander.games.challenges.quarkus.resources;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/**
 * DTO Book object
 */
public class BookDto {

    private Integer id;

    public String name;

    public Integer publicationYear;

}