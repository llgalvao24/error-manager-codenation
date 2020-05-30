package br.com.codenation.v1.errorManager.application;

public class ApplicationDTO {

    private Long userId;
    private String name;

    public ApplicationDTO(Long userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    public ApplicationDTO() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
