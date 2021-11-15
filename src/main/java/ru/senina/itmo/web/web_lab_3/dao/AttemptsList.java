package ru.senina.itmo.web.web_lab_3.dao;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.java.Log;
import ru.senina.itmo.web.web_lab_3.database.DBManager;
import ru.senina.itmo.web.web_lab_3.entities.Attempt;
import ru.senina.itmo.web.web_lab_3.parser.AttemptsListJsonParser;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

@Log
@Named()
@SessionScoped
@NoArgsConstructor
public class AttemptsList implements Serializable {
    private @Inject
    AttemptsListJsonParser parser;
    private @Inject
    DBManager dbManager;

    @Setter
    private List<Attempt> list = Collections.synchronizedList(new LinkedList<>());

    public void add(Attempt attempt) {
        list.add(attempt);
        //todo: send request to db to add attempt there too
    }

    public Attempt[] getList() {
        return this.list.toArray(new Attempt[0]);
    }

    public String listToJson() {
        String json = parser.fromObjectToString(this);
        log.log(Level.FINE, "Output Json: \"" + json + "\"");
        return json;
    }

    public void clearList() {
        this.list.clear();
    }
}