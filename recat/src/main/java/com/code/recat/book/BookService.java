package com.code.recat.book;

import org.springframework.data.domain.Page;


public interface BookService {

    Page<Book> findAllBooks(int pageNumber, int pageSize);
}
