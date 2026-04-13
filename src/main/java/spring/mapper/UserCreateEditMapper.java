package spring.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import spring.database.entity.Gender;
import spring.database.entity.Role;
import spring.database.entity.User;
import spring.dto.UserCreateEditDto;

import java.time.LocalDate;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserCreateEditMapper implements Mapper<UserCreateEditDto, User> {

    @Override
    public User map(UserCreateEditDto fromObject, User toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    @Override
    public User map(UserCreateEditDto object) {
        User user = new User();
        copy(object, user);
        return user;
    }

    private void copy(UserCreateEditDto object, User user) {
        user.setBirthday(object.getBirthday());
        user.setName(object.getName());
        user.setEmail(object.getEmail());
        user.setPassword(object.getPassword());
        user.setRole(object.getRole());
        user.setGender(object.getGender());
    }

}
