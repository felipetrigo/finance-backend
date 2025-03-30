package com.util.financialbackend.security.DTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "security.config")
public class JWTValues {
    public String PREFIX;
    public String KEY;
    public Long EXPIRATION;

}
