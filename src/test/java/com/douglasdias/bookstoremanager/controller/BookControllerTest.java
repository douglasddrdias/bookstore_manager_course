package com.douglasdias.bookstoremanager.controller;

import com.douglasdias.bookstoremanager.dto.BookDTO;
import com.douglasdias.bookstoremanager.dto.MessageResponseDTO;
import com.douglasdias.bookstoremanager.service.BookService;
import com.douglasdias.bookstoremanager.utils.BookUtils;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    public static final String BOOK_API_URL_PATH = "/api/v1/books";
    private MockMvc mockMvc;
    @Mock
    private BookService bookService;
    @InjectMocks
    private  BookController bookController;
    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).
                setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver()).
                setViewResolvers((viewName, locale) -> new MappingJackson2JsonView()).
                build();
    }

    @Test
    void testWhenPostisCalledThenABookShouldBeCreted() throws Exception {
        BookDTO bookDTO = BookUtils.createFakeBookDTO();
        MessageResponseDTO expectedMessageReponse = MessageResponseDTO.builder()
                .message("Book created with ID " + bookDTO.getId())
                .build();
        Mockito.when(bookService.create(bookDTO)).thenReturn(expectedMessageReponse);
        mockMvc.perform(MockMvcRequestBuilders.post(BOOK_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(BookUtils.asJsonString(bookDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Is.is(expectedMessageReponse.getMessage())));
    }

    @Test
    void testWhenPOSTWithInvalidISBNIsCalledThenBadRequestBeReturned() throws Exception {
        BookDTO bookDTO = BookUtils.createFakeBookDTO();
        bookDTO.setIsbn("Invalid isbn");
        mockMvc.perform(MockMvcRequestBuilders.post(BOOK_API_URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(BookUtils.asJsonString(bookDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
