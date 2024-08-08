package com.code.recat.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WithMockUser(username = "stephamire@gmail.com", roles = "USER")
public class UserControllerTest {

    @Autowired
    private UserController userController;
}
