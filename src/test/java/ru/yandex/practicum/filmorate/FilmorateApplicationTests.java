package ru.yandex.practicum.filmorate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.mocks.FilmMock;
import ru.yandex.practicum.filmorate.mocks.UserMock;

import java.util.List;

import static com.rabriel.GivenWhenThen.given;
import static ru.yandex.practicum.filmorate.utils.MockMvcUtils.delete;
import static ru.yandex.practicum.filmorate.utils.MockMvcUtils.get;
import static ru.yandex.practicum.filmorate.utils.MockMvcUtils.jsonArray;
import static ru.yandex.practicum.filmorate.utils.MockMvcUtils.jsonObject;
import static ru.yandex.practicum.filmorate.utils.MockMvcUtils.post;
import static ru.yandex.practicum.filmorate.utils.MockMvcUtils.put;
import static ru.yandex.practicum.filmorate.utils.MockMvcUtils.setMockMvc;
import static ru.yandex.practicum.filmorate.utils.MockMvcUtils.setObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class FilmorateApplicationTests {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Autowired
    FilmorateApplicationTests(
            MockMvc mockMvc,
            ObjectMapper objectMapper
    ) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @BeforeEach
    public void beforeEach() {
        setMockMvc(mockMvc);
        setObjectMapper(objectMapper);
    }

    @Test
    @Order(1)
    @DisplayName("Users / user create")
    public void userCreate() {
        given(new UserMock(0, "mail@mail.ru", "dolore", "Nick Name", "1946-08-20"))
                .when("POST {{baseUrl}}/users", model -> post("/users", model))
                .then("Status code is 200", result -> result.getResponse().getStatus() == 200);
    }

    @Test
    @Order(1)
    @DisplayName("Users / user create fail login")
    public void createFailureUser() {
        given(new UserMock(0, "mail@mail.ru", "dolore ullamco", null, "1946-08-20"))
                .when("POST {{baseUrl}}/users", model -> post("/users", model))
                .then("Status code is 400", result -> result.getResponse().getStatus() == 400);
    }

    @Test
    @Order(2)
    @DisplayName("Users / user create fail birthday")
    public void createFailBirthday() {
        given(new UserMock(0, "test@mail.ru", "dolore", null, "2446-08-20"))
                .when("POST {{baseUrl}}/users", model -> post("/users", model))
                .then("Status code is 400", result -> result.getResponse().getStatus() == 400);
    }


    @Test
    @Order(3)
    @DisplayName("Users / user create fail email")
    public void createFailEmail() {
        given(new UserMock(0, "mail.ru", "dolore", null, "1946-08-20"))
                .when("POST {{baseUrl}}/users", model -> post("/users", model))
                .then("Status code is 400", result -> result.getResponse().getStatus() == 400);
    }

    @Test
    @Order(4)
    @DisplayName("Users / user update")
    public void userUpdate() {
        given(List.of(
                new UserMock(0, "mail@mail.ru", "dolore", "Nick Name", "1946-08-20"),
                new UserMock(1, "mail@yandex.ru", "doloreUpdate", "est adipisicing", "1976-09-20")
        ))
                .when("PUT {{baseUrl}}/users", list -> {
                    post("/users", list.get(0));
                    return put("/users", list.get(1));
                })
                .then("User should be updated", result -> {
                    JsonObject json = jsonObject(result.getResponse());
                    return json.get("id").getAsLong() == 1
                            && json.get("email").getAsString().equals("mail@yandex.ru")
                            && json.get("birthday").getAsString().equals("1976-09-20");
                });
    }

    @Test
    @Order(5)
    @DisplayName("Users / user all")
    public void userGetAll() {
        given(null)
                .when("GET {{baseUrl}}/users", (empty) -> get("/users"))
                .then("List user should be has two elements",
                        (result) -> jsonArray(result.getResponse()).size() == 1);
    }

    @Test
    @Order(6)
    @DisplayName("User / friend create")
    public void friendCreate() {
        given(new UserMock(0, "friend@mail.ru", "friend", "friend adipisicing", "1976-08-20"))
                .when("POST {{baseUrl}}/users", model -> post("/users", model))
                .then("Status code is 200", result -> result.getResponse().getStatus() == 200);
    }

    @Test
    @Order(7)
    @DisplayName("User / common friend create")
    public void commonFriendCreate() {
        given(new UserMock(0, "friend@common.ru", "common", "", "2000-08-20"))
                .when("POST {{baseUrl}}/users", model -> post("/users", model))
                .then("Status code is 200", result -> result.getResponse().getStatus() == 200);
    }

    @Test
    @Order(8)
    @DisplayName("User / user get")
    public void userGet() {
        given(1)
                .when("GET {{baseUrl}}/users/:id", id -> get("/users/" + id))
                .then("Status code is 200 and correct user", result -> {
                    return jsonObject(result.getResponse()).get("name").getAsString().equals("est adipisicing");
                });
    }

    @Test
    @Order(9)
    @DisplayName("User / user get unknown")
    public void userGetUnknown() {
        given(-1)
                .when("GET {{baseUrl}}/users/:id", id -> get("/users/" + id))
                .then("Status code is 404", result -> result.getResponse().getStatus() == 404);
    }

    @Test
    @Order(10)
    @DisplayName("User / friend get")
    public void friendGet() {
        given(2)
                .when("GET {{baseUrl}}/users/:id", id -> get("/users/" + id))
                .then("Status code is 200 and correct user", result -> {
                    return jsonObject(result.getResponse()).get("name").getAsString().equals("friend adipisicing");
                });
    }

    @Test
    @Order(10)
    @DisplayName("Friend / user get friends common empty")
    public void userGetFriendsCommonEmpty() {
        given(List.of(1, 2))
                .when("GET {{baseUrl}}/users/:id/friends/common/:otherId", list -> get("/users/" + list.get(0) +
                        "/friends/common/" + list.get(1)))
                .then("Status code is 200", result -> jsonArray(result.getResponse()).size() == 0 && result.getResponse().getStatus() == 200);
    }

    @Test
    @Order(11)
    @DisplayName("Friend / user add friend")
    public void userAddFriend() {
        given(List.of(1, 2))
                .when("PUT {{baseUrl}}/users/:id/friends/:friendId", list -> put("/users/" + list.get(0) + "/friends/" + list.get(1)))
                .then("Status code is 200", result -> result.getResponse().getStatus() == 200);
    }

    @Test
    @Order(12)
    @DisplayName("Friend / user add friend unknown")
    public void userAddFriendUnknown() {
        given(List.of(1, -1))
                .when("PUT {{baseUrl}}/users/:id/friends/:friendId", list -> put("/users/" + list.get(0) + "/friends/" + list.get(1)))
                .then("Status code is 404", result -> result.getResponse().getStatus() == 404);
    }

    @Test
    @Order(12)
    @DisplayName("Friend / user get friends")
    public void userGetFriends() {
        given(1)
                .when("GET {{baseUrl}}/users/:id/friends",
                        id -> get("/users/" + id + "/friends/"))
                .then("Status code is 200 and has one element", result -> {
                    JsonArray arr = jsonArray(result.getResponse());
                    JsonObject json = jsonArray(result.getResponse()).get(0).getAsJsonObject();

                    return json.get("id").getAsLong() == 2
                            && json.get("email").getAsString().equals("friend@mail.ru")
                            && json.get("name").getAsString().equals("friend adipisicing")
                            && json.get("login").getAsString().equals("friend")
                            && arr.size() == 1;
                });
    }

    @Test
    @Order(13)
    @DisplayName("Friend / friend get friends")
    public void friendGetFriends() {
        given(2)
                .when("GET {{baseUrl}}/users/:id/friends",
                        id -> get("/users/" + id + "/friends/"))
                .then("Status code is 200 and has one element", result -> {
                    JsonArray arr = jsonArray(result.getResponse());
                    JsonObject json = jsonArray(result.getResponse()).get(0).getAsJsonObject();

                    return json.get("id").getAsLong() == 1
                            && json.get("email").getAsString().equals("mail@yandex.ru")
                            && json.get("name").getAsString().equals("est adipisicing")
                            && json.get("login").getAsString().equals("doloreUpdate")
                            && arr.size() == 1;
                });
    }

    @Test
    @Order(14)
    @DisplayName("Friend / user get friends mutual")
    public void userGetFriendsMutual() {
        given(List.of(1, 2))
                .when("{{baseUrl}}/users/:id/friends/common/:otherId",
                        list -> get("/users/" + list.get(0) + "/friends/common/" + list.get(1)))
                .then("Status code is 200 and empty list", result -> {
                    JsonArray arr = jsonArray(result.getResponse());

                    return result.getResponse().getStatus() == 200 && arr.size() == 0;
                });
    }

    @Test
    @Order(15)
    @DisplayName("Friend / user add common friend")
    public void userAddCommonFriend() {
        given(List.of(1, 3))
                .when("PUT {{baseUrl}}/users/:id/friends/:friendId",
                        list -> put("/users/" + list.get(0) + "/friends/" + list.get(1)))
                .then("Status code is 200", result -> result.getResponse().getStatus() == 200);
    }

    @Test
    @Order(16)
    @DisplayName("Friend / user get 2 friends")
    public void useGetTwoFriends() {
        given(1)
                .when("GET {{baseUrl}}/users/:id/friends",
                        id -> get("/users/" + id + "/friends/"))
                .then("Status code is 200 and has two elements", result -> {
                    JsonArray arr = jsonArray(result.getResponse());

                    return result.getResponse().getStatus() == 200 && arr.size() == 2;
                });
    }

    @Test
    @Order(17)
    @DisplayName("Friend / friend add common friend")
    public void friendAddCommonFriend() {
        given(List.of(2, 3))
                .when("PUT {{baseUrl}}/users/:id/friends/:friendId",
                        list -> put("/users/" + list.get(0) + "/friends/" + list.get(1)))
                .then("Status code is 200", result -> result.getResponse().getStatus() == 200);
    }

    @Test
    @Order(18)
    @DisplayName("Friend / friend get 2 friends")
    public void friendGetTwoFriends() {
        given(2)
                .when("GET {{baseUrl}}/users/:id/friends",
                        id -> get("/users/" + id + "/friends/"))
                .then("Status code is 200 and has two elements", result -> {
                    JsonArray arr = jsonArray(result.getResponse());

                    return result.getResponse().getStatus() == 200 && arr.size() == 2;
                });
    }

    @Test
    @Order(19)
    @DisplayName("Friend / user get friends common")
    public void userGetFriendsCommon() {
        given(List.of(1, 2))
                .when("GET {{baseUrl}}/users/:id/friends/common/:otherId",
                        list -> get("/users/" + list.get(0) + "/friends/common/" + list.get(1)))
                .then("Status code is 200", result -> {
                    JsonArray arr = jsonArray(result.getResponse());

                    return result.getResponse().getStatus() == 200 && arr.size() == 1;
                });
    }

    @Test
    @Order(20)
    @DisplayName("Friend / user remove friend")
    public void userRemoveFriend() {
        given(List.of(1, 2))
                .when("DELETE {{baseUrl}}/users/:id/friends/:friendId",
                        list -> delete("/users/" + list.get(0) + "/friends/" + list.get(1)))
                .then("Status code is 200", result -> result.getResponse().getStatus() == 200);
    }

    @Test
    @Order(21)
    @DisplayName("Friend / user get friends common")
    public void userGetFriendsCommonAfterCleanup() {
        given(List.of(1, 2))
                .when("GET {{baseUrl}}/users/:id/friends/common/:otherId",
                        list -> get("/users/" + list.get(0) + "/friends/common/" + list.get(1)))
                .then("Status code is 200", result -> {
                    JsonArray arr = jsonArray(result.getResponse());
                    JsonObject json = jsonArray(result.getResponse()).get(0).getAsJsonObject();

                    return json.get("id").getAsLong() == 3
                            && json.get("email").getAsString().equals("friend@common.ru")
                            && json.get("name").getAsString().equals("common")
                            && json.get("login").getAsString().equals("common")
                            && arr.size() == 1;
                });
    }

    @Test
    @Order(22)
    @DisplayName("Friend / friend get friends common")
    public void friendGetFriendsCommon() {
        given(List.of(2, 1))
                .when("GET {{baseUrl}}/users/:id/friends/common/:otherId",
                        list -> get("/users/" + list.get(0) + "/friends/common/" + list.get(1)))
                .then("Status code is 200", result -> {
                    JsonArray arr = jsonArray(result.getResponse());
                    JsonObject json = jsonArray(result.getResponse()).get(0).getAsJsonObject();

                    return json.get("id").getAsLong() == 3
                            && json.get("email").getAsString().equals("friend@common.ru")
                            && json.get("name").getAsString().equals("common")
                            && json.get("login").getAsString().equals("common")
                            && arr.size() == 1;
                });
    }

    @Test
    @Order(23)
    @DisplayName("Films / film get All")
    public void filmGetAll() {
        given(null)
                .when("GET {{baseUrl}}/films", empty -> get("/films"))
                .then("Status code is 200", result -> {
                    JsonArray arr = jsonArray(result.getResponse());

                    return arr.size() == 0;
                });
    }

    @Test
    @Order(24)
    @DisplayName("Films / film create")
    public void filmCreate() {
        given(new FilmMock(0, "labore nulla", "Duis in consequat esse", "1979-04-17", 100))
                .when("POST {{baseUrl}}/films", model -> post("/films", model))
                .then("Status code is 200", result -> {
                    JsonObject json = jsonObject(result.getResponse());

                    return json.get("id").getAsLong() == 1
                            && json.get("name").getAsString().equals("labore nulla")
                            && json.get("description").getAsString().equals("Duis in consequat esse")
                            && json.get("duration").getAsLong() == 100;
                });
    }

    @Test
    @Order(24)
    @DisplayName("Films / film create fail name")
    public void filmCreateFailName() {
        given(new FilmMock(0, "", "Description", "1900-03-25", 200))
                .when("POST {{baseUrl}}/films", model -> post("/films", model))
                .then("Status code is 400", result -> result.getResponse().getStatus() == 400);
    }

    @Test
    @Order(25)
    @DisplayName("Films / film create fail description")
    public void filmCreateFailDescription() {
        given(new FilmMock(0, "Film name", "Пятеро друзей ( комик-группа «Шарло»), приезжают в город Бризуль. Здесь они хотят разыскать господина Огюста Куглова, который задолжал им деньги, а именно 20 миллионов. о Куглов, который за время «своего отсутствия», стал кандидатом Коломбани.", "1900-03-25", 200))
                .when("POST {{baseUrl}}/films", model -> post("/films", model))
                .then("Status code is 400", result -> result.getResponse().getStatus() == 400);
    }

    @Test
    @Order(26)
    @DisplayName("Films / Film create fail releaseDate")
    public void filmCreateFailReleaseDate() {
        given(new FilmMock(0, "Film name", "Description", "1890-03-25", 200))
                .when("POST {{baseUrl}}/films", model -> post("/films", model))
                .then("Status code is 400", result -> result.getResponse().getStatus() == 400);
    }

    @Test
    @Order(27)
    @DisplayName("Films / Film create fail duration")
    public void filmCreateFailDuration() {
        given(new FilmMock(0, "Film name", "Description", "1980-03-25", -200))
                .when("POST {{baseUrl}}/films", model -> post("/films", model))
                .then("Status code is 400", result -> result.getResponse().getStatus() == 400);
    }

    @Test
    @Order(28)
    @DisplayName("Films / film update")
    public void filmUpdate() {
        given(new FilmMock(1, "Film Updated", "New film update description", "1989-04-17", 190))
                .when("PUT {{baseUrl}}/films", model -> put("/films", model))
                .then("Status code is 200", result -> {
                    JsonObject json = jsonObject(result.getResponse());

                    return json.get("id").getAsLong() == 1
                            && json.get("name").getAsString().equals("Film Updated")
                            && json.get("description").getAsString().equals("New film update description")
                            && json.get("duration").getAsLong() == 190;
                });
    }

    @Test
    @Order(29)
    @DisplayName("Films / film update unknown")
    public void filmUpdateUnknown() {
        given(new FilmMock(-1, "Film Updated", "New film update description", "1989-04-17", 190))
                .when("PUT {{baseUrl}}/films", model -> put("/films", model))
                .then("Status code is 404", result -> result.getResponse().getStatus() == 404);
    }

    @Test
    @Order(30)
    @DisplayName("Films / film get popular")
    public void filmGetPopular() {
        given(null)
                .when("GET {{baseUrl}}/films/popular", empty -> get("/films/popular"))
                .then("Status code is 200 and one element returned",
                        result -> {
                            JsonArray arr = jsonArray(result.getResponse());
                            JsonObject json = jsonArray(result.getResponse()).get(0).getAsJsonObject();

                            return json.get("name").getAsString().equals("Film Updated")
                                    && json.get("description").getAsString().equals("New film update description")
                                    && json.get("releaseDate").getAsString().equals("1989-04-17")
                                    && arr.size() == 1
                                    && result.getResponse().getStatus() == 200;
                        });
    }

    @Test
    @Order(31)
    @DisplayName("Films / film create friend")
    public void filmCreateFriend() {
        given(new FilmMock(0, "New film", "New film about friends", "1999-04-30", 120))
                .when("POST {{baseUrl}}/films", model -> post("/films", model))
                .then("Status code is 200 and one element returned",
                        result -> {
                            JsonObject json = jsonObject(result.getResponse());

                            return json.get("name").getAsString().equals("New film")
                                    && json.get("description").getAsString().equals("New film about friends")
                                    && json.get("releaseDate").getAsString().equals("1999-04-30")
                                    && result.getResponse().getStatus() == 200;
                        });
    }

    @Test
    @Order(32)
    @DisplayName("Films / film get")
    public void filmGet() {
        given(1)
                .when("GET {{baseUrl}}/films/:id", id -> get("/films/" + id))
                .then("Status code is 200 and one element returned",
                        result -> result.getResponse().getStatus() == 200);
    }

    @Test
    @Order(33)
    @DisplayName("Films / film get unknown")
    public void filmGetUnknown() {
        given(-1)
                .when("GET {{baseUrl}}/films/:id", id -> get("/films/" + id))
                .then("Status code is 200 and one element returned",
                        result -> result.getResponse().getStatus() == 404);
    }

    @Test
    @Order(34)
    @DisplayName("Like / film add Like")
    public void filmAddLike() {
        given(List.of(2, 1))
                .when("PUT {{baseUrl}}/films/:id/like/:userId",
                        list -> put("/films/" + list.get(0) + "/like/" + list.get(1)))
                .then("Status code is 200 and one element returned",
                        result -> result.getResponse().getStatus() == 200);
    }

    @Test
    @Order(35)
    @DisplayName("Like / film get Popular count")
    public void filmGetPopularCountOne() {
        given(1)
                .when("GET {{baseUrl}}/films/popular?count=1", count -> get("/films/popular?count=" + count))
                .then("Status code is 200 and one element returned",
                        result -> {
                            JsonArray arr = jsonArray(result.getResponse());
                            JsonObject json = jsonArray(result.getResponse()).get(0).getAsJsonObject();

                            return json.get("id").getAsLong() == 2
                                    && json.get("name").getAsString().equals("New film")
                                    && json.get("description").getAsString().equals("New film about friends")
                                    && json.get("releaseDate").getAsString().equals("1999-04-30")
                                    && json.get("duration").getAsLong() == 120
                                    && arr.size() == 1
                                    && result.getResponse().getStatus() == 200;
                        });
    }

    @Test
    @Order(36)
    @DisplayName("Like / film remove Like")
    public void filmRemoveLike() {
        given(List.of(2, 1))
                .when("DELETE {{baseUrl}}/films/:id/like/:userId",
                        list -> delete("/films/" + list.get(0) + "/like/" + list.get(1)))
                .then("Status code is 200",
                        result -> result.getResponse().getStatus() == 200);
    }

    @Test
    @Order(37)
    @DisplayName("Like / film get popular count 2")
    public void filmGetPopularCountTwo() {
        given(null)
                .when("GET {{baseUrl}}/films/popular", empty -> get("/films/popular"))
                .then("Status code is 200",
                        result -> result.getResponse().getStatus() == 200 && jsonArray(result.getResponse()).size() == 2);
    }

    @Test
    @Order(38)
    @DisplayName("Like / film remove Like not found")
    public void filmRemoveLikeNotFound() {
        given(List.of(1, -2))
                .when("DELETE {{baseUrl}}/films/:id/like/:userId",
                        list -> delete("/films/" + list.get(0) + "/like/" + list.get(1)))
                .then("Status code is 404",
                        result -> result.getResponse().getStatus() == 404);
    }
}
