package spring.http.advice;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import spring.config.CurrentUserHolder;
import spring.dto.UserReadDto;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalModelAttribute {
    private final CurrentUserHolder currentUserHolder;

    @ModelAttribute("currentUser")
    public UserReadDto addCurrentUser() {
        return currentUserHolder.getUser();
    }

    @ModelAttribute("isAdmin")
    public boolean addIsAdmin() {
        return currentUserHolder.isAdmin();
    }
}