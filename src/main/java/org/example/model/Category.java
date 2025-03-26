package org.example.model;

import lombok.*;

import java.io.Serializable;
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Category implements Serializable {
    private Long id;
    private String name;
    private Boolean active;
}

