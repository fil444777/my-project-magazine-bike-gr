package spring.dto;

import lombok.Value;
import spring.database.entity.Gender;
import spring.database.entity.Role;

import java.time.LocalDate;

@Value
public class UserReadDto {
    Integer id;
    LocalDate birthday;
    String name;
    String email;
    String password;
    Role role;
    Gender gender;
}
