package org.example.library.repositories;

import org.example.library.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Integer> {

    boolean existsByBookId(Integer id);
}
