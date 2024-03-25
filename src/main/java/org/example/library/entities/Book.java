package org.example.library.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;



@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="books")
public class Book {
    @Id
    @Column(name = "bookId", nullable = false)
    private int bookId;

    private String bookName;

    private String bookAuthor;

    private int bookYear;

    private LocalDate DateOfIssue;

    private LocalDate DateOfSubmission;




    private int totalDaysOfIssueBook;


    private String fine;

    private int bookSemester;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "books_roll")
    private Student student;

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", bookName='" + bookName + '\'' +
                ", bookAuthor='" + bookAuthor + '\'' +
                ", bookYear=" + bookYear +
                ", DateOfIssue=" + DateOfIssue +
                ", DateOfSubmission=" + DateOfSubmission +
                ", student=" + student +
                ", totalDaysOfIssueBook=" + totalDaysOfIssueBook +
                ", fine='" + fine + '\'' +
                ", BookSemester=" + bookSemester +
                '}';
    }
}