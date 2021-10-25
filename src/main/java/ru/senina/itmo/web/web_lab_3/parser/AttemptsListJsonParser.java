package ru.senina.itmo.web.web_lab_3.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.senina.itmo.web.web_lab_3.dao.AttemptsList;
import ru.senina.itmo.web.web_lab_3.entities.Attempt;
import ru.senina.itmo.web.web_lab_3.exceptions.ParsingException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;


@ApplicationScoped
public class AttemptsListJsonParser extends JsonParser<AttemptsList> {

    @Inject
    public AttemptsListJsonParser() {
        super(new ObjectMapper(), AttemptsList.class);
    }

    public String fromAttemptToString(Attempt attempt) throws ParsingException {
        try {
            return AttemptsListJsonParser.getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(attempt);
        } catch (JsonProcessingException e) {
            throw new ParsingException("Something wrong with parsing attempt with coordinates "
                    + attempt.getCoordinates().getX() + ", " +
                    + attempt.getCoordinates().getY() + ", " +
                    + attempt.getCoordinates().getR() + ", " +
                     attempt.isDoFitArea());
        }
    }

    @Deprecated
    public Attempt fromStringToAttempt(String json) throws ParsingException {
        try {
            return AttemptsListJsonParser.getObjectMapper().readValue(json, Attempt.class);
        } catch (JsonProcessingException e) {
            throw new ParsingException("Something wrong with attempt string -> object parsing.");
        }
    }

    @Override
    public String fromObjectToString(AttemptsList list) throws ParsingException {
        try {
            return AttemptsListJsonParser.getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(list.getList());
        } catch (JsonProcessingException e) {
            throw new ParsingException("Something wrong with parsing attempts list to string.");
        }
    }
}
