package ru.senina.itmo.web.web_lab_3;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import ru.senina.itmo.web.web_lab_3.dao.AttemptsList;
import ru.senina.itmo.web.web_lab_3.entities.Attempt;
import ru.senina.itmo.web.web_lab_3.entities.Coordinates;
import ru.senina.itmo.web.web_lab_3.entities.Owner;
import ru.senina.itmo.web.web_lab_3.parser.AttemptsListJsonParser;

import java.util.logging.Level;

public class ParsingTests {

    public static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    }

    //fixme: deserialization
    @Test
    public void testFromObjectToJson() {
        //with Owner and id = null
        Attempt attempt1 = new Attempt(new Coordinates(1, 2, 4), true);

        //with full constructor
        Attempt attempt2 = new Attempt(new Coordinates(-4, -5, 2), false, 1L ,Owner.initOwner());

        //like in DB
        Owner owner = Owner.initOwner();
        Attempt attempt3 = new Attempt(new Coordinates(0, 0.3, 2.3), true, 1L , owner);
        attempt3.setOwner(owner);
        owner.getAttemptList().add(attempt3);
        attempt3.getCoordinates().setAttempt(attempt3);

        AttemptsList attemptsList = new AttemptsList();
        attemptsList.add(attempt1);
        attemptsList.add(attempt2);
        attemptsList.add(attempt3);

        AttemptsListJsonParser parser = new AttemptsListJsonParser();
        String json = parser.fromObjectToString(attemptsList);
        System.out.println(json);
//        AttemptsList newObject = parser.fromStringToObject(json);
        System.out.println("Test finished");
    }

    @Test
    public void attemptSerialization() {
        Attempt attempt = new Attempt(new Coordinates(1, 2, 4), true);
        AttemptsListJsonParser parser = new AttemptsListJsonParser();
        String json = parser.fromAttemptToString(attempt);
        System.out.println(json);
//        AttemptsList newAttempt = parser.fromStringToObject(json);
        System.out.println("Attempt test finished");
    }

}