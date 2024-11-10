package com.util.financialbackend.DTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ClientRequestDTO {
    private String name;
    private Double salary;
    private String phoneNumber;
    private String email;
}
