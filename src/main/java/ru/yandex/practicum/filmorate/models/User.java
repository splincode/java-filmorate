package ru.yandex.practicum.filmorate.models;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Value
@Builder
public class User {
    long id;

    @NotBlank(message = "Электронная почта не может быть пустой")
    @Email(message = "Электронная почта должна содержать символ @")
    String email;

    @Pattern(regexp = "^([a-zA-Z]+)$", message = "Логин не может содержать пробелы")
    @NotBlank(message = "Логин не может быть пустым")
    String login;

    String name;

    @Past(message = "Дата рождения не может быть в будущем")
    LocalDate birthday;
}
