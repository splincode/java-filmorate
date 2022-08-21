package ru.yandex.practicum.filmorate.models;

import lombok.Builder;
import lombok.Value;
import org.hibernate.validator.constraints.Length;
import ru.yandex.practicum.filmorate.validators.ReleaseDate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Value
@Builder
public class Film {
    long id;

    @NotBlank(message = "Название не может быть пустым")
    String name;

    @Length(max = 200)
    String description;

    @ReleaseDate(message = "Дата релиза должна быть не раньше 28 декабря 1895 года")
    LocalDate releaseDate;

    @Min(value = 0L, message = "Продолжительность фильма должна быть положительной")
    long duration;
}
