package com.code.recat.user;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;
import java.io.IOException;
import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@RunWith(SpringRunner.class)
public class UserJsonTest {

    @Autowired
    private JacksonTester<UserTestDto> userJson;

    @Autowired
    private JacksonTester<UserTestDto[]> userJsonList;

    private UserTestDto[] users;

    @Value("classpath:user/user_single.json")
    Resource singleFile;

    @Value("classpath:user/user_list.json")
    Resource listFile;

    @BeforeEach
    void setUp() {
        users = Arrays.array(
                UserTestDto.builder()
                        .userId(1)
                        .name("Nate Giabucci")
                        .username("ngiabucci0")
                        .email("ngiabucci0@yelp.com")
                        .gender("female")
                        .password("uP0~!$hpmIQ#~8")
                        .role(Role.USER)
                        .build(),
                UserTestDto.builder()
                        .userId(2)
                        .name("Rodolphe Prover")
                        .username("rprover1")
                        .email("rprover1@artisteer.com")
                        .gender("male").password("password(")
                        .role(Role.ADMIN)
                        .build()
        );
    }

    @Test
    void userSerializationTest() throws IOException {
        UserTestDto user = users[0];

        // Write the book to JSON
        JsonContent<UserTestDto> jsonNode = userJson.write(user);
        
        assertThat(jsonNode).isStrictlyEqualToJson(singleFile);
        assertThat(jsonNode).hasJsonPathNumberValue("@.userId");
        assertThat(jsonNode).extractingJsonPathNumberValue("@.userId").isEqualTo(1);
        assertThat(jsonNode).hasJsonPathStringValue("@.name");
        assertThat(jsonNode).extractingJsonPathStringValue("@.name").isEqualTo("Nate Giabucci");
        assertThat(jsonNode).hasJsonPathStringValue("@.username");
        assertThat(jsonNode).extractingJsonPathStringValue("@.username").isEqualTo("ngiabucci0");
        assertThat(jsonNode).hasJsonPathStringValue("@.email");
        assertThat(jsonNode).extractingJsonPathStringValue("@.email").isEqualTo("ngiabucci0@yelp.com");
        assertThat(jsonNode).hasJsonPathStringValue("@.gender");
        assertThat(jsonNode).extractingJsonPathStringValue("@.gender").isEqualTo("female");
        assertThat(jsonNode).hasJsonPathStringValue("@.password");
        assertThat(jsonNode).extractingJsonPathStringValue("@.password").isEqualTo("uP0~!$hpmIQ#~8");
        assertThat(jsonNode).hasJsonPathStringValue("@.role");
        assertThat(jsonNode).extractingJsonPathStringValue("@.role").isEqualTo("USER");
    }

    @Test
    void userDeserializationTest() throws IOException {
        String expected = """
                {
                  "userId": 1,
                  "name": "Nate Giabucci",
                  "username": "ngiabucci0",
                  "email": "ngiabucci0@yelp.com",
                  "gender": "female",
                  "password": "uP0~!$hpmIQ#~8",
                  "role": "USER"
                }
                """;

        UserTestDto newUser = new UserTestDto(
                1,
                "Nate Giabucci",
                "ngiabucci0",
                "ngiabucci0@yelp.com",
                "female",
                "uP0~!$hpmIQ#~8",
                Role.USER
        );

        assertThat(userJson.parse(expected))
                .isEqualTo(newUser);
        assertThat(userJson.parseObject(expected).getUserId()).isEqualTo(1);
        assertThat(userJson.parseObject(expected).getName()).isEqualTo("Nate Giabucci");
        assertThat(userJson.parseObject(expected).getUsername()).isEqualTo("ngiabucci0");
        assertThat(userJson.parseObject(expected).getEmail()).isEqualTo("ngiabucci0@yelp.com");
        assertThat(userJson.parseObject(expected).getGender()).isEqualTo("female");
        assertThat(userJson.parseObject(expected).getPassword()).isEqualTo("uP0~!$hpmIQ#~8");
        assertThat(userJson.parseObject(expected).getRole().toString()).isEqualTo("USER");
    }

    @Test
    void userListSerializationTest() throws IOException {
        assertThat(userJsonList.write(users)).isStrictlyEqualToJson(listFile);
    }

    @Test
    void userListDeserializationTest() throws IOException {
        String expected = """
                [
                   {
                     "userId": 1,
                     "name": "Nate Giabucci",
                     "username": "ngiabucci0",
                     "email": "ngiabucci0@yelp.com",
                     "gender": "female",
                     "password": "uP0~!$hpmIQ#~8",
                     "role": "USER"
                   },
                   {
                     "userId": 2,
                     "name": "Rodolphe Prover",
                     "username": "rprover1",
                     "email": "rprover1@artisteer.com",
                     "gender": "male",
                     "password": "password(",
                     "role": "ADMIN"
                   }
                 ]
                """;

        assertThat(userJsonList.parse(expected)).isEqualTo(users);
    }
}
