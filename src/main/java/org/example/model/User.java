package org.example.model;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User implements Serializable {
    private Long id;
    private String name;
    private String phoneNumber;
    private String address;
    private Boolean active;
}
