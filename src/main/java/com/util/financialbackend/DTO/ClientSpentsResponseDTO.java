package com.util.financialbackend.DTO;

import com.util.financialbackend.model.Spent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClientSpentsResponseDTO {
    private Long id;
    private String name;
    private Double salary;
    private List<Spent> spents;
}
