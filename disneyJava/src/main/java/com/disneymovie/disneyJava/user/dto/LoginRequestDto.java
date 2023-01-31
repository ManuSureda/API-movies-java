package com.disneymovie.disneyJava.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.platform.commons.util.StringUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {
    String email;
    String password;

    public Boolean isValid() {
        return !StringUtils.isBlank(email) && !StringUtils.isBlank(password);
    }
}
