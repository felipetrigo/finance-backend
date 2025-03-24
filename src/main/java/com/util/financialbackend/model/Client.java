package com.util.financialbackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Table(name = "tb_user")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @NotNull(message = "name can't be null")
    @NotBlank(message = "name can't be blank")
    private String name;
    private String username;
    private String password;
    @NotNull(message = "salary can't be null")
    private Double salary;
    @Column(name = "phone")
    private String phoneNumber;
    private String email;
    private Boolean deleted = Boolean.FALSE;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "tab_user_roles", joinColumns =  @JoinColumn(name = "user_id"))
    @Column(name = "role_id")
    private List<String> roles = List.of("USER");
    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Spent> spents = new ArrayList<>();
}
