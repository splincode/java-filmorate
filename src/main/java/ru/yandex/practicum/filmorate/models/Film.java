package ru.yandex.practicum.filmorate.models;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Value;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Value
@Builder
public class Film {
    long id;

    @NotBlank(message = "Название не может быть пустым")
    String name;

    @Length(max = 200)
    String description;

    LocalDate releaseDate;

    @Min(value = 0L, message = "Продолжительность фильма должна быть положительной")
    long duration;
}
