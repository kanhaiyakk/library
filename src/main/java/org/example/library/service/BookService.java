package org.example.library.service;

import org.example.library.dto.BookDto;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

public interface BookService {

    BookDto createBook(BookDto bookDto);
    BookDto getBookById(Integer id);

    BookDto getBookByName();

    List<BookDto> getAllBooks();



}
