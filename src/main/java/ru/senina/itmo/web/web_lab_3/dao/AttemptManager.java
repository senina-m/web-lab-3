package ru.senina.itmo.web.web_lab_3.dao;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import ru.senina.itmo.web.web_lab_3.database.DBManager;
import ru.senina.itmo.web.web_lab_3.entities.Attempt;
import ru.senina.itmo.web.web_lab_3.entities.Coordinates;
import ru.senina.itmo.web.web_lab_3.exceptions.CoordinatesOutOfBoundsException;
import ru.senina.itmo.web.web_lab_3.validators.CoordinatesValidator;
import ru.senina.itmo.web.web_lab_3.validators.areaCheckers.PlotAreaChecker;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Map;
import java.util.logging.Level;

@Named("attemptManager")
@SessionScoped
@Log
public class AttemptManager implements Serializable {
    @Getter
    @Setter
    private Attempt attempt;
    @Inject
    private PlotAreaChecker checker; //todo: use new areaCheckBuilder
    @Inject
    private CoordinatesValidator validator;
    @Inject private DBManager dbManager;


    public AttemptManager() {
        this.attempt = Attempt.initAttempt();
        log.info("Timestamp (attemptManager constructor): " + System.currentTimeMillis());
    }

    private boolean checkAttemptDoFitArea(Attempt attempt) {
        return checker.check(attempt.getCoordinates());
    }

    private void checkCoordinates(Coordinates coordinates) {
        validator.validate(coordinates);
    }

    public void processClick(){
        try {
            log.info("Timestamp (processClick): " + System.currentTimeMillis());
            FacesContext context = FacesContext.getCurrentInstance();
            Map<String, String> map = context.getExternalContext().getRequestParameterMap();
            attempt.getCoordinates().setX(Double.parseDouble(map.get("x_val")));
            attempt.getCoordinates().setY(Double.parseDouble(map.get("y_val")));
            addToList(Double.parseDouble(map.get("r_val")));
        }catch (NullPointerException e){
            log.warning("Some coordinates were null!");
        }
    }

    public void addToList(double r) {
        try {
            log.info("Timestamp (addToList): " + System.currentTimeMillis());
            attempt.getCoordinates().setR(r);
            log.log(Level.INFO, attempt.getCoordinates().toString());

            checkCoordinates(attempt.getCoordinates());
            attempt.setDoFitArea(checkAttemptDoFitArea(attempt));
//            attemptsList.add(attempt);
            log.log(Level.INFO, "New Attempt added: " + attempt + " User id: " +
                    FacesContext.getCurrentInstance().getExternalContext().getSessionId(true));
            dbManager.addElement(attempt, FacesContext.getCurrentInstance().getExternalContext().getSessionId(true));
        } catch (CoordinatesOutOfBoundsException exception) {
            log.log(Level.WARNING, "Incorrect data came to JSF: \n" + exception.getMessage());
        } catch (Exception exception) {
            log.log(Level.WARNING, exception.getMessage());
        } finally {
            attempt = Attempt.initAttempt();
        }
    }

    public String getJsonList() {
        log.info("Timestamp (getJsonList): " + System.currentTimeMillis());
        AttemptsList attemptList =  new AttemptsList();
        attemptList.setList(dbManager.readAll());
        return attemptList.listToJson();
    }


    public Attempt[] getList(){
        return dbManager.readAll().toArray(new Attempt[0]);
    }
}
