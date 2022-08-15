package ru.yandex.practicum.filmorate.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.yandex.practicum.filmorate.models.User;

import javax.validation.Valid;

@Service
@Validated
public class UsersValidatorService {
    private final static Logger log = LoggerFactory.getLogger(UsersValidatorService.class);

    void validate(@Valid User input){
       log.debug(input.toString());
    }
}
