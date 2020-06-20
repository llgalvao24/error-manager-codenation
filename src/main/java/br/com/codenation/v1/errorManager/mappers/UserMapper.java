package br.com.codenation.v1.errorManager.mappers;

import br.com.codenation.v1.errorManager.dto.UserInfoDTO;
import br.com.codenation.v1.errorManager.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "username", target = "username"),
            @Mapping(source = "createdAt", target = "createdAt"),
            @Mapping(source = "updatedAt", target = "updatedAt"),
            @Mapping(source = "active", target = "active"),
            @Mapping(source = "profile", target = "profiles")
    })

    UserInfoDTO map(User user);

    List<UserInfoDTO> map(List<User> users);
}
