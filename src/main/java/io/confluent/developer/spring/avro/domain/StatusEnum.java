package io.confluent.developer.spring.avro.domain;

public enum StatusEnum {

    RECORDED("RECORDED"),
    CANCELLED("CANCELLED"),
    FINISHED("FINISHED");

    StatusEnum(String status){
        this.status = status;
    }

    private String status;

    public String getStatus() {
        return status;
    }
}
