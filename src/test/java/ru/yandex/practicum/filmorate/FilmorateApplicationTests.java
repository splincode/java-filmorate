package ru.yandex.practicum.filmorate;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.controllers.FilmsController;
import ru.yandex.practicum.filmorate.controllers.UserController;
import ru.yandex.practicum.filmorate.mocks.FilmMock;
import ru.yandex.practicum.filmorate.mocks.UserMock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FilmorateApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserController userController;

    @Autowired
    private FilmsController filmsController;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void contextLoads() {
        assertThat(userController).isNotNull();
        assertThat(filmsController).isNotNull();
    }

    @Test
    public void createSuccessfulUser() throws Exception {
        UserMock json = new UserMock("mail@mail.ru", "dolore", "Nick Name", "1946-08-20");

        mockMvc.perform(post("/users").content(objectMapper.writeValueAsString(json)).contentType(MediaType.valueOf("application/json"))).andExpect(status().isOk());
    }

    @Test
    public void createFailureUser() throws Exception {
        UserMock json = new UserMock("mail@mail.ru", "dolore ullamco", null, "1946-08-20");

        mockMvc.perform(post("/users").content(objectMapper.writeValueAsString(json)).contentType(MediaType.valueOf("application/json"))).andExpect(status().isBadRequest());
    }

    @Test
    public void createFailEmail() throws Exception {
        UserMock json = new UserMock("mail.ru", "dolore", null, "1946-08-20");

        mockMvc.perform(post("/users").content(objectMapper.writeValueAsString(json)).contentType(MediaType.valueOf("application/json"))).andExpect(status().isBadRequest());
    }

    @Test
    public void createFailBirthday() throws Exception {
        UserMock json = new UserMock("test@mail.ru", "dolore", null, "2446-08-20");

        mockMvc.perform(post("/users").content(objectMapper.writeValueAsString(json)).contentType(MediaType.valueOf("application/json"))).andExpect(status().isBadRequest());
    }

    @Test
    public void createSuccessfulFilm() throws Exception {
        FilmMock json = new FilmMock("nisi eiusmod", "adipisicing", "1967-03-25", 100);

        mockMvc.perform(post("/films").content(objectMapper.writeValueAsString(json)).contentType(MediaType.valueOf("application/json"))).andExpect(status().isOk());
    }

    @Test
    public void createFailNameFilm() throws Exception {
        FilmMock json = new FilmMock("", "Description", "1900-03-25", 200);

        mockMvc.perform(post("/films").content(objectMapper.writeValueAsString(json)).contentType(MediaType.valueOf("application/json"))).andExpect(status().isBadRequest());
    }

    @Test
    public void createFailDescriptionFilm() throws Exception {
        FilmMock json = new FilmMock("Film name", "Пятеро друзей ( комик-группа «Шарло»), приезжают в город Бризуль. Здесь они хотят разыскать господина Огюста Куглова, который задолжал им деньги, а именно 20 миллионов. о Куглов, который за время «своего отсутствия», стал кандидатом Коломбани.\",\n" + "    \"releaseDate\": \"1900-03-25", "1900-03-25", 200);

        mockMvc.perform(post("/films").content(objectMapper.writeValueAsString(json)).contentType(MediaType.valueOf("application/json"))).andExpect(status().isBadRequest());
    }

    @Test
    public void createFailReleaseDateFilm() throws Exception {
        FilmMock json = new FilmMock("Name2", "Description", "1590-03-25", 200);

        mockMvc.perform(post("/films").content(objectMapper.writeValueAsString(json)).contentType(MediaType.valueOf("application/json"))).andExpect(status().isBadRequest());
    }

    @Test
    public void createFailDurationFilm() throws Exception {
        FilmMock json = new FilmMock("Name", "Description", "1980-03-25", -200);

        mockMvc.perform(post("/films").content(objectMapper.writeValueAsString(json)).contentType(MediaType.valueOf("application/json"))).andExpect(status().isBadRequest());
    }

}
