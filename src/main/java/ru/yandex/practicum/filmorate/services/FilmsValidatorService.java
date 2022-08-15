package ru.yandex.practicum.filmorate.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.yandex.practicum.filmorate.models.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.Month;

@Service
@Validated
public class FilmsValidatorService {
    private final static Logger log = LoggerFactory.getLogger(FilmsValidatorService.class);

    void validate(@Valid Film input){
       log.debug(input.toString());

       if (input.getReleaseDate().isBefore(LocalDate.of(1895, Month.DECEMBER, 28))) {
           throw new RuntimeException("Дата релиза должна быть не раньше 28 декабря 1895 года (года рождения кино)");
       }
    }
}
