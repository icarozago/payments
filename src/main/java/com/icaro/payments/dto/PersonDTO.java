package com.icaro.payments.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

@Getter
@Setter
@NoArgsConstructor
public class PersonDTO {

    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String cpf;

    @NonNull
    private String email;
}
