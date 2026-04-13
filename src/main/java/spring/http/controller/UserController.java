package spring.http.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import spring.database.entity.Gender;
import spring.database.entity.Role;
import spring.dto.PageResponse;
import spring.dto.UserCreateEditDto;
import spring.dto.UserFilter;
import spring.dto.UserReadDto;
import spring.service.UserService;

import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public String findAll(Model model, UserFilter filter, Pageable pageable) {
        Page<UserReadDto> page = userService.findAll(filter, pageable);
        model.addAttribute("users", PageResponse.of(page));
        model.addAttribute("filter", filter);
        return "user/users";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "user/login";
    }

    @PostMapping("/login")
    public String login (@RequestParam("email") String email,
                         @RequestParam String password) {
        System.out.println("Попытка входа " + email);
        Optional<UserReadDto> maybeUser = userService.authenticate(email, password);
        System.out.println("Result: " + maybeUser.isPresent());

        if (maybeUser.isEmpty()) {
            return "redirect:/login?error";
        }
        UserReadDto user = maybeUser.get();
        if (user.getRole() == Role.ADMIN) {
            return "redirect:/users";
        }
        return "redirect:/users/" + user.getId();
    }

    @GetMapping("/registration")
    public String registration(Model model, @ModelAttribute("user") UserCreateEditDto user) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        model.addAttribute("genders", Gender.values());
        return "user/registration";
    }

    @PostMapping("{id}/update")
    public String update(@PathVariable("id") Integer id, @ModelAttribute @Validated UserCreateEditDto user) {
//        userService.update(id, user);
        return userService.update(id, user)
                .map(it -> "redirect:/users/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public String create(@ModelAttribute @Validated UserCreateEditDto user,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("user", user);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/users/registration";
        }
        UserReadDto dto = userService.create(user);
        return "redirect:/users/" + dto.getId();
    }


    //    @DeleteMapping("/{id}")
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Integer id) {
        if (!userService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/users";
    }
}
