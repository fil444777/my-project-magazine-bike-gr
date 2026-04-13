package spring.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import spring.database.entity.Gender;
import spring.database.entity.Role;

import java.time.LocalDate;

@Value
@FieldNameConstants
public class UserCreateEditDto {

     LocalDate birthday;

     @Size(min = 3, max = 30)
     String name;

     @Email
     String email;
     String password;
     Role role;
     Gender gender;
}
