package com.util.financialbackend.DTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SpentRequestDTO {

    private Double price;
    private String name;
}
