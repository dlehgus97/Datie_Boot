package org.zerock.datie_boot.dto;

public class IdCheckRequest {
    private String id;

    // 기본 생성자
    public IdCheckRequest() {
    }

    // getter
    public String getId() {
        return id;
    }

    // setter
    public void setId(String id) {
        this.id = id;
    }
}