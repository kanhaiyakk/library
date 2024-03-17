package org.example.library.dto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.library.entities.Student;

import java.time.LocalDate;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    @NotEmpty
    @NotBlank
    private String bookName;

    @NotBlank(message ="book author should not be balnk")
    private String bookAuthor;

    @NotBlank(message = "please enter a book year")
    private int bookYear;



    private StudentDto studentDto;

}
