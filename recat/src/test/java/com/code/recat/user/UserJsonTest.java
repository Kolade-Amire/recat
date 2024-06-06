package com.code.recat.user;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.core.io.Resource;

import static org.assertj.core.api.Assertions.assertThat;


import java.io.IOException;

@JsonTest
public class UserJsonTest {

    @Autowired
    private JacksonTester<User> userJson;

    @Autowired
    private JacksonTester<User[]> userJsonList;

    private User[] users;

    @Value("classpath:user/user_single.json")
    Resource singleFile;

    @Value("classpath:user/user_list.json")
    Resource listFile;

    @BeforeEach
    void setUp() {
        users = Arrays.array(
                new User(1, "Nate Giabucci", "ngiabucci0", "ngiabucci0@yelp.com", "F", "uP0~!$hpmIQ#~8", "user"),
                new User(2, "Rodolphe Prover", "rprover1", "rprover1@artisteer.com", "M", "gB5.5\\ZM(", "user"),
                new User(3, "Sonny Stean", "sstean2", "sstean2@berkeley.edu", "M", "sR2*+6PSw\\b", "user"),
                new User(4, "Valentijn Wabe", "vwabe3", "vwabe3@myspace.com", "F", "pJ7'N#Dc%A<", "user"),
                new User(5, "Tan Hellin", "thellin4", "thellin4@cdc.gov", "M", "hB3+{D)+{'=", "user"),
                new User(6, "Herbie Featherstone", "hfeatherstone5", "hfeatherstone5@netlog.com", "M", "pW8'g%B?.iUG't7{", "admin"),
                new User(7, "Olive Deppe", "odeppe6", "odeppe6@123-reg.co.uk", "F", "tN4\\ISE7aa", "user"),
                new User(8, "Alvis Darrigrand", "adarrigrand7", "adarrigrand7@ifeng.com", "M", "wE6.SetT8", "admin"),
                new User(9, "Celesta Buttrey", "cbuttrey8", "cbuttrey8@meetup.com", "M", "iM3&px)/=", "user"),
                new User(10, "Karie Hourican", "khourican9", "khourican9@hatena.ne.jp", "M", "pK2}>o.=Ief?!", "user")
        );
    }

    @Test
    void userSerializationTest() throws IOException {
        User user = users[0];
        assertThat(userJson.write(user)).isStrictlyEqualToJson(singleFile);
        assertThat(userJson.write(user)).hasJsonPathNumberValue("@.user_id");
        assertThat(userJson.write(user)).extractingJsonPathNumberValue("@.user_id").isEqualTo(1);
        assertThat(userJson.write(user)).hasJsonPathStringValue("@.name");
        assertThat(userJson.write(user)).extractingJsonPathStringValue("@.name").isEqualTo("Nate Giabucci");
        assertThat(userJson.write(user)).hasJsonPathStringValue("@.username");
        assertThat(userJson.write(user)).extractingJsonPathStringValue("@.username").isEqualTo("ngiabucci0");
        assertThat(userJson.write(user)).hasJsonPathStringValue("@.email");
        assertThat(userJson.write(user)).extractingJsonPathStringValue("@.email").isEqualTo("ngiabucci0@yelp.com");
        assertThat(userJson.write(user)).hasJsonPathStringValue("@.gender");
        assertThat(userJson.write(user)).extractingJsonPathStringValue("@.gender").isEqualTo("F");
        assertThat(userJson.write(user)).hasJsonPathStringValue("@.password");
        assertThat(userJson.write(user)).extractingJsonPathStringValue("@.password").isEqualTo("uP0~!$hpmIQ#~8");
        assertThat(userJson.write(user)).hasJsonPathStringValue("@.role");
        assertThat(userJson.write(user)).extractingJsonPathStringValue("@.role").isEqualTo("user");
    }

    @Test
    void userDeserializationTest() throws IOException {
        String expected = """
                {
                     "user_id": 1,
                      "name": "Nate Giabucci",
                      "username": "ngiabucci0",
                      "email": "ngiabucci0@yelp.com",
                      "gender": "F",
                      "password": "uP0~!$hpmIQ#~8",
                      "role": "user"
                }
                """;

        assertThat(userJson.parse(expected))
                .isEqualTo(new User(1, "Nate Giabucci", "ngiabucci0", "ngiabucci0@yelp.com", "F", "uP0~!$hpmIQ#~8", "user"));
        assertThat(userJson.parseObject(expected).user_id()).isEqualTo(1);
        assertThat(userJson.parseObject(expected).name()).isEqualTo("Nate Giabucci");
        assertThat(userJson.parseObject(expected).username()).isEqualTo("ngiabucci0");
        assertThat(userJson.parseObject(expected).email()).isEqualTo("ngiabucci0@yelp.com");
        assertThat(userJson.parseObject(expected).gender()).isEqualTo("F");
        assertThat(userJson.parseObject(expected).password()).isEqualTo("uP0~!$hpmIQ#~8");
        assertThat(userJson.parseObject(expected).role()).isEqualTo("user");
    }

    @Test
    void userListSerializationTest() throws  IOException {
        assertThat(userJsonList.write(users)).isStrictlyEqualToJson(listFile);
    }

    @Test
    void userListDeserializationTest() throws IOException {
        String expected = """
                [
                {
                    "user_id": 1,
                    "name": "Nate Giabucci",
                    "username": "ngiabucci0",
                    "email": "ngiabucci0@yelp.com",
                    "gender": "F",
                    "password": "uP0~!$hpmIQ#~8",
                    "role": "user"
                  },
                  {
                    "user_id": 2,
                    "name": "Rodolphe Prover",
                    "username": "rprover1",
                    "email": "rprover1@artisteer.com",
                    "gender": "M",
                    "password": "gB5.5\\\\ZM(",
                    "role": "user"
                  },
                  {
                    "user_id": 3,
                    "name": "Sonny Stean",
                    "username": "sstean2",
                    "email": "sstean2@berkeley.edu",
                    "gender": "M",
                    "password": "sR2*+6PSw\\\\b",
                    "role": "user"
                  },
                  {
                    "user_id": 4,
                    "name": "Valentijn Wabe",
                    "username": "vwabe3",
                    "email": "vwabe3@myspace.com",
                    "gender": "F",
                    "password": "pJ7'N#Dc%A<",
                    "role": "user"
                  },
                  {
                    "user_id": 5,
                    "name": "Tan Hellin",
                    "username": "thellin4",
                    "email": "thellin4@cdc.gov",
                    "gender": "M",
                    "password": "hB3+{D)+{'=",
                    "role": "user"
                  },
                  {
                    "user_id": 6,
                    "name": "Herbie Featherstone",
                    "username": "hfeatherstone5",
                    "email": "hfeatherstone5@netlog.com",
                    "gender": "M",
                    "password": "pW8'g%B?.iUG't7{",
                    "role": "admin"
                  },
                  {
                    "user_id": 7,
                    "name": "Olive Deppe",
                    "username": "odeppe6",
                    "email": "odeppe6@123-reg.co.uk",
                    "gender": "F",
                    "password": "tN4\\\\ISE7aa",
                    "role": "user"
                  },
                  {
                    "user_id": 8,
                    "name": "Alvis Darrigrand",
                    "username": "adarrigrand7",
                    "email": "adarrigrand7@ifeng.com",
                    "gender": "M",
                    "password": "wE6.SetT8",
                    "role": "admin"
                  },
                  {
                    "user_id": 9,
                    "name": "Celesta Buttrey",
                    "username": "cbuttrey8",
                    "email": "cbuttrey8@meetup.com",
                    "gender": "M",
                    "password": "iM3&px)/=",
                    "role": "user"
                  },
                  {
                    "user_id": 10,
                    "name": "Karie Hourican",
                    "username": "khourican9",
                    "email": "khourican9@hatena.ne.jp",
                    "gender": "M",
                    "password": "pK2}>o.=Ief?!",
                    "role": "user"
                  }
                ]
                """;

        assertThat(userJsonList.parse(expected)).isEqualTo(users);
    }


}
