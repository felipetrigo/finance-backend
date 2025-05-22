package com.util.financialbackend.security.DTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SpentDTO {
    private Long id;
    private String username;
    private Double price;
    private String name;
}
