package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.cinema.controller.UserController;
import ru.job4j.cinema.cinema.model.User;
import ru.job4j.cinema.cinema.service.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    private UserService userService;
    private UserController userController;
    private HttpServletRequest httpServletRequest;
    private HttpSession httpSession;

    @BeforeEach
    public void initServices() {
        httpSession = mock(HttpSession.class);
        userService = mock(UserService.class);
        httpServletRequest = mock(HttpServletRequest.class);
        userController = new UserController(userService);
    }

    @Test
    public void whenRegisterThenGetIndex() {
        User user = new User(1, "name", "email", "1111");
        var userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        when(userService.save(userArgumentCaptor.capture())).thenReturn(Optional.of(user));
        var model = new ConcurrentModel();
        var view = userController.register(model, user);
        assertThat(view).isEqualTo("redirect:/index");

    }

    @Test
    public void whenRegisterThenGetException() {
        User user = new User(1, "name", "email", "1111");
        var userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        when(userService.save(userArgumentCaptor.capture())).thenReturn(Optional.empty());

        var model = new ConcurrentModel();
        var view = userController.register(model, user);
        var actualMessage = model.getAttribute("message");

        assertThat(view).isEqualTo("errors/404");
    }

    @Test
    public void whenLoginUserThenTimetable() {
        User user = new User(1, "name", "email", "1111");
        var userEmail = ArgumentCaptor.forClass(String.class);
        var userPassword = ArgumentCaptor.forClass(String.class);
        when(userService.findByEmailAndPassword(userEmail.capture(),
                userPassword.capture()))
                .thenReturn(Optional.of(user));
        when(httpServletRequest.getSession()).thenReturn(httpSession);
        when(httpSession.getAttribute("user")).thenReturn(user);

        var model = new ConcurrentModel();
        var view = userController.loginUser(user, model, httpServletRequest);
        var actualUser = httpSession.getAttribute("user");

        assertThat(view).isEqualTo("redirect:/filmSessions/timetable");
        assertThat(actualUser).isEqualTo(user);
    }
}
