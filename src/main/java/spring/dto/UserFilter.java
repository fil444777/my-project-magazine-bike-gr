package spring.dto;

import java.time.LocalDate;

public record UserFilter(String name,
                         String email,
                         LocalDate birthday) {
}
