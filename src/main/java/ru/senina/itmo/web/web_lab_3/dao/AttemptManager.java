package ru.senina.itmo.web.web_lab_3.dao;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import ru.senina.itmo.web.web_lab_3.database.DBManager;
import ru.senina.itmo.web.web_lab_3.entities.Attempt;
import ru.senina.itmo.web.web_lab_3.entities.Coordinates;
import ru.senina.itmo.web.web_lab_3.validators.CoordinatesValidator;
import ru.senina.itmo.web.web_lab_3.validators.PlotAreaChecker;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;

@Named()
@SessionScoped
@Log
public class AttemptManager implements Serializable {

    @Getter @Setter
    private Attempt attempt;
    @Inject private PlotAreaChecker checker;
    @Inject private CoordinatesValidator validator;
    @Inject private AttemptsList attemptsList;
    @Inject private DBManager dbManager;

    public AttemptManager(){
        this.attempt = Attempt.initAttempt();
    }

    private boolean checkAttemptDoFitArea(){
        return checker.check(attempt.getCoordinates());
    }

    private void checkCoordinates(Coordinates coordinates){
        validator.validate(coordinates);
    }

    public void addToList(){
        try{
        checkCoordinates(attempt.getCoordinates());
        attempt.setDoFitArea(checkAttemptDoFitArea());
        attemptsList.add(this.attempt);
        log.log(Level.WARNING, "New Attempt added: " + attempt + " User id: " +
                FacesContext.getCurrentInstance().getExternalContext().getSessionId(true));
        dbManager.addElement(attempt, FacesContext.getCurrentInstance().getExternalContext().getSessionId(true));
        }catch (Exception ignored){
            log.log(Level.WARNING, "Incorrect data came to JSF.");
        }finally {
            attempt = Attempt.initAttempt();
        }
    }

    public AttemptsList getAttemptsList(){
        return attemptsList;
    }

    public String getJsonList(){
        return attemptsList.listToJson();
    }

}
