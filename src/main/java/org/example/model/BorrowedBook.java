package org.example.model;

import com.mysql.cj.log.Log;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BorrowedBook implements Serializable {
    private Long id;
    private Long userId;
    private Long bookId;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private Double depositAmount;
    private Boolean returned;
}

