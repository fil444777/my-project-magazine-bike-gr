package spring.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import spring.database.entity.User;
import spring.dto.UserReadDto;

@Component
@RequiredArgsConstructor
public class UserReadMapper implements Mapper<User, UserReadDto> {


    @Override
    public UserReadDto map(User user) {
        return new UserReadDto(
                user.getId(),
                user.getBirthday(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole(),
                user.getGender()
        );
    }
}
