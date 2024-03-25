package org.example.library.controllers;

import org.example.library.entities.Book;
import org.example.library.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DateUpdator {

    @Autowired
    private BookRepository bookRepository;
//    @Scheduled(fixedDelay = 1000) // Run every 5 minutes
   @Scheduled(cron = "0 0 0 * * *") // Run every day at midnight
    public void updateTotalDaysOfIssueBook() {
        List<Book> allBooks = this.bookRepository.findAll();
//        long daysDifference = ChronoUnit.DAYS.between(book.getDateOfIssue(), currentDate);
        List<Book> collect = allBooks.stream().map(book -> {
            long dayDiffernce = ChronoUnit.DAYS.between(book.getDateOfIssue(), LocalDate.now());
            if (book.getDateOfIssue() != null && book.getDateOfSubmission() == null) {
                book.setTotalDaysOfIssueBook((int)dayDiffernce);
                if(dayDiffernce>180){
                    int dayDiffernce1=(int)dayDiffernce;
                    book.setFine(String.valueOf(dayDiffernce1-180));
                }else{
                    book.setFine("NA");
                }
                bookRepository.save(book);
                return book;
            } else {
                book.setTotalDaysOfIssueBook(0);
                bookRepository.save(book);
                return book;
            }

        }).collect(Collectors.toList());

        System.out.println(collect);

    }

}
