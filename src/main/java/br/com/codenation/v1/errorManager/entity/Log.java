package br.com.codenation.v1.errorManager.entity;

import br.com.codenation.v1.errorManager.enums.Level;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "logs")
@EntityListeners(AuditingEntityListener.class)
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String description;

    @Column
    private String details;

    @Column
    private String log;

    @Column
    @NotNull
    private String environment;

    @Column
    @NotNull
    private Integer level;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "application_id")
    private Application application;

    @Column(columnDefinition = "INTEGER DEFAULT 1")
    private Long numberEvents;

    public Log() {
    }

    public Log(
        Long id,
        String description,
        String details,
        String log,
        Level level,
        Application application,
        String environment
    ) {
        this.id = id;
        this.description = description;
        this.details = details;
        this.log = log;
        this.level = level.getCode();
        this.application = application;
        this.environment = environment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Long getNumberEvents() {
        return numberEvents;
    }

    public void addEvent() {
        if (this.numberEvents == null) {
            this.numberEvents = 1L;
        }else {
            this.numberEvents += 1;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Log log = (Log) o;
        return Objects.equals(id, log.id);
    }


    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}



