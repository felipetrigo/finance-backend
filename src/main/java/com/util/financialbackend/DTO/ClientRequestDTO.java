package com.util.financialbackend.DTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ClientRequestDTO {
    private String name;
    private String username;
    private String password;
    private Double salary;
    private String phoneNumber;
    private String email;
}
