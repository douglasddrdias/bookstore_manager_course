package com.douglasdias.bookstoremanager.repository;

import com.douglasdias.bookstoremanager.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository  extends JpaRepository<Book, Long> {
}
