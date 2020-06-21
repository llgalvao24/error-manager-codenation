package br.com.codenation.v1.errorManager.mappers;

import br.com.codenation.v1.errorManager.dto.LogInfoDTO;
import br.com.codenation.v1.errorManager.entity.Log;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LogMapper {

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "description", target = "description"),
            @Mapping(source = "details", target = "details"),
            @Mapping(source = "log", target = "log"),
            @Mapping(source = "environment", target = "environment"),
            @Mapping(source = "level", target = "level"),
            @Mapping(source = "application", target = "application"),
            @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-dd HH:mm"),
            @Mapping(source = "updatedAt", target = "updatedAt", dateFormat = "yyyy-MM-dd HH:mm"),
            @Mapping(source = "numberEvents", target = "numberEvents")
    })

    LogInfoDTO map(Log log);

    List<LogInfoDTO> map(List<Log> logs);
}
