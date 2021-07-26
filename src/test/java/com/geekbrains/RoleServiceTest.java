package com.geekbrains;

import com.geekbrains.entities.Role;
import com.geekbrains.entities.User;
import com.geekbrains.repositories.RoleRepository;
import com.geekbrains.services.RoleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@SpringBootTest(classes = Application.class)
public class RoleServiceTest {
    @Autowired
    private RoleService roleService;

    @MockBean
    private RoleRepository roleRepository;

    @Test
    public void findOneRoleTest() {
        Role roleFromDB = new Role();
        roleFromDB.setId(1L);
        roleFromDB.setName("Admin");
        Mockito.doReturn(Optional.of(roleFromDB)).when(roleRepository).findByName("Admin");
        Role roleAdmin = roleService.getByName("Admin");
        Assertions.assertNotNull(roleAdmin);
        Mockito.verify(roleRepository, Mockito.times(1)).findByName("Admin");
    }
}
