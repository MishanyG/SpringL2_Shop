package com.geekbrains;

import com.geekbrains.Application;
import com.geekbrains.entities.User;
import com.geekbrains.exceptions.RoleNotFoundException;
import com.geekbrains.exceptions.UserNotFoundException;
import com.geekbrains.repositories.UserRepository;
import com.geekbrains.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@SpringBootTest(classes = Application.class)
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void findOneUserTest() {
        User userFromDB = new User();
        userFromDB.setId(1L);
        userFromDB.setPhone("123");
        userFromDB.setEmail("123@mail.ru");

        Mockito.doReturn(Optional.of(userFromDB))
                .when(userRepository)
                .findById(1L);

        User userVitalii = userService.findById(1);
        Assertions.assertNotNull(userVitalii);
        Mockito.verify(userRepository, Mockito.times(1)).findById(ArgumentMatchers.eq(1L));
//        Mockito.verify(userRepository, Mockito.times(1)).findOneByPhone(ArgumentMatchers.any(String.class));
    }

    @Test
    public void checkThrow() {
        User userFromDB = new User();
        userFromDB.setId(1L);
        userFromDB.setPhone("123");
        userFromDB.setEmail("123@mail.ru");

        Mockito.doReturn(Optional.of(userFromDB))
                .when(userRepository)
                .findById(1L);

        Assertions.assertThrows(UserNotFoundException.class, () -> userService.findById(2));
    }
}
