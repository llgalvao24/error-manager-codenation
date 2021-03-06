package br.com.codenation.v1.errorManager.dto;

import br.com.codenation.v1.errorManager.enums.Level;

public class LogDTO {

    private String description;
    private String details;
    private String log;
    private String environment;
    private Integer level;
    private ApplicationDTO application;
    private String origin;

    public LogDTO(){
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public Level getLevel() {
        return Level.toEnum(level);
    }

    public void setLevel(Level level) {
        this.level = level.getCode();
    }

    public ApplicationDTO getApplication() {
        return application;
    }

    public void setApplication(ApplicationDTO application) {
        this.application = application;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }
}
