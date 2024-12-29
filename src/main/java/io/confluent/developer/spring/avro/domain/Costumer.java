package io.confluent.developer.spring.avro.domain;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Costumer {

    private String name;
    private LocalDate birthDate;

}
