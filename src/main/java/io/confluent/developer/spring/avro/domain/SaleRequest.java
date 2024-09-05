package io.confluent.developer.spring.avro.domain;

import lombok.Data;

@Data
public class SaleRequest {

    private String saleId;
    private String costumerId;
    private String status;

}
