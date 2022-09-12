package com.sulbin.junittest.repository;

import com.sulbin.junittest.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Long> {
}
