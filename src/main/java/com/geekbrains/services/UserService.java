package com.geekbrains.services;

import com.geekbrains.controllers.dto.UserDto;
import com.geekbrains.controllers.dto.UserType;
import com.geekbrains.entities.Role;
import com.geekbrains.entities.User;
import com.geekbrains.exceptions.ManagerIsEarlierThanNeedException;
import com.geekbrains.exceptions.NotFoundException;
import com.geekbrains.exceptions.UnknownUserTypeException;
import com.geekbrains.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;

    public UserService(UserRepository userRepository,
                       RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    public User saveUser(UserDto userDto) {
        if (userDto.getUserType().equals(UserType.MANAGER)) {
            saveManager(userDto);
        } else if (userDto.getUserType().equals(UserType.USER)) {
            saveTypicallyUser(userDto);
        }

        throw new UnknownUserTypeException();
    }

    private User saveTypicallyUser(UserDto userDto) {
        User user = createUserFromDto(userDto);

        Role role = roleService.getByName("ROLE_USER");
        user.setRoles(List.of(role));

        return userRepository.save(user);
    }

    private User saveManager(UserDto userDto) {
        if (userDto.getAge() > 18) {
            User user = createUserFromDto(userDto);

            Role role = roleService.getByName("ROLE_MANAGER");
            user.setRoles(List.of(role));

            return userRepository.save(user);
        }

        throw new ManagerIsEarlierThanNeedException("Пользователь младше восемнадцати лет");
    }

    private User createUserFromDto(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(userDto.getPassword());
        user.setPhone(userDto.getPhone());
        user.setAge(userDto.getAge());

        return user;
    }

    public List<User> getAllUsersWithType(UserType userType) {
        Role role;
        if (UserType.USER == userType) {
            role = roleService.getByName("ROLE_USER");
            return userRepository.findAllByRoles(role);
        } else if (UserType.MANAGER == userType) {
            role = roleService.getByName("ROLE_MANAGER");
            return userRepository.findAllByRoles(role);
        } else {
            return userRepository.findAll();
        }
    }

    public Optional<User> getUserById(Long id) {
        Optional<User> roleOptional = userRepository.findById(id);
        if(roleOptional.isPresent()) {
            return userRepository.findById(id);
        } else {
            throw new NotFoundException(String.format("Пользователь с ИД №%s не найден.", id));
        }
    }
}

