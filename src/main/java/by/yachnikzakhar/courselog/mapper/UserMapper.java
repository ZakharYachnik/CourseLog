package by.yachnikzakhar.courselog.mapper;

import by.yachnikzakhar.courselog.dto.UserDto;
import by.yachnikzakhar.courselog.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class );

    UserDto userToUserDto(User user);
}