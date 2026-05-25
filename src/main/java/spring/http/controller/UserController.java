package spring.http.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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


    @GetMapping("/registration")
    public String registration(Model model, @ModelAttribute("user") UserCreateEditDto user) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        model.addAttribute("genders", Gender.values());
        return "user/registration";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Integer id, Model model,
                           @AuthenticationPrincipal UserDetails userDetails) {
        var currentUser = userService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        boolean isOwner = currentUser.getId().equals(id);
        boolean isAdminOrOperator = currentUser.getRole().equals(Role.ADMIN)
                || currentUser.getRole().equals(Role.OPERATOR);

        if (!isOwner && !isAdminOrOperator) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return userService.findById(id)
                .map(user -> {
                    model.addAttribute("user", user);
                    model.addAttribute("currentUser", currentUser);
                    model.addAttribute("isAdmin", isAdminOrOperator);
                    model.addAttribute("canEdit", isOwner || isAdminOrOperator);
                    model.addAttribute("roles", Role.values());
                    model.addAttribute("genders", Gender.values());
                    return "user/user";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("{id}/update")
    public String update(@PathVariable("id") Integer id, @ModelAttribute @Validated UserCreateEditDto user,
                         @AuthenticationPrincipal UserDetails userDetails) {
        var currentUser = userService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        if (!currentUser.getId().equals(id)
                && !currentUser.getRole().equals(Role.ADMIN)
                && !currentUser.getRole().equals(Role.OPERATOR)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
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
    public String delete(@PathVariable("id") Integer id,
                         @AuthenticationPrincipal UserDetails userDetails) {
        var currentUser = userService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        if (!currentUser.getId().equals(id)
                && !currentUser.getRole().equals(Role.ADMIN)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        if (!userService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/users";
    }
}
