package org.example.model;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class Book implements Serializable {
    private Long id;
    private String title;
    private String author;
    private LocalDate publishedDate;
    private Integer availableCopies;
    private Long categoryId;
    private Boolean active;
}
