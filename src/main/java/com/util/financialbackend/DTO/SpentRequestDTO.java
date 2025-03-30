package com.util.financialbackend.DTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SpentRequestDTO {
    private String clientUsername;
    private Double price;
    private String name;
}
