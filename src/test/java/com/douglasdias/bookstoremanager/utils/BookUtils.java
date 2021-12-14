package com.douglasdias.bookstoremanager.utils;

import com.douglasdias.bookstoremanager.dto.AuthorDTO;
import com.douglasdias.bookstoremanager.dto.BookDTO;
import com.douglasdias.bookstoremanager.entity.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.javafaker.Faker;

import static com.douglasdias.bookstoremanager.utils.AuthorUtils.createFakeAuthor;
import static com.douglasdias.bookstoremanager.utils.AuthorUtils.createFakeAuthorDTO;

public class BookUtils {
    private static final Faker faker = Faker.instance();

    public static BookDTO createFakeBookDTO(){
        return BookDTO.builder()
                .id(faker.number().randomNumber())
                .name(faker.book().title())
                .pages(faker.number().numberBetween(0,200))
                .chapters(faker.number().numberBetween(1,20))
                .isbn("0-596-52068-9")
                .publisherName(faker.book().publisher())
                .author(createFakeAuthorDTO())
                .build();
    }

    private static Book createFakeBook(){
        return Book.builder()
                .id(faker.number().randomNumber())
                .name(faker.book().title())
                .pages(faker.number().numberBetween(0,200))
                .chapters(faker.number().numberBetween(1,20))
                .isbn("0-596-52068-9")
                .publisherName(faker.book().publisher())
                .author(createFakeAuthor())
                .build();
    }

    public static String asJsonString(BookDTO bookDTO) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(bookDTO);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
