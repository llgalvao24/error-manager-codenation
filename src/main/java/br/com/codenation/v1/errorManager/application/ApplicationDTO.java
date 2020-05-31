package br.com.codenation.v1.errorManager.application;

public class ApplicationDTO {

    private Long id;
    private String name;

    public ApplicationDTO(Long id, String name) {
        this.name = name;
        this.id = id;
    }

    public ApplicationDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
