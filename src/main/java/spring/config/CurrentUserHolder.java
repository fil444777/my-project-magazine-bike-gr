package spring.config;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import spring.database.entity.Role;
import spring.dto.UserReadDto;

@Component
@SessionScope
@RequiredArgsConstructor
public class CurrentUserHolder {
    private UserReadDto user;

    public void setUser(UserReadDto user) {
        this.user = user;
    }

    public UserReadDto getUser() {
        return user;
    }

    public boolean isAdmin() {
        return user != null && user.getRole() == Role.ADMIN;
    }
}
