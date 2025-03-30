package com.util.financialbackend.security.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record Login(@NotNull @NotEmpty String username, @NotNull @NotEmpty String password) {
}
