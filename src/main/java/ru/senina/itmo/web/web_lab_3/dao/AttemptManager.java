package ru.senina.itmo.web.web_lab_3.dao;

import lombok.Getter;
import lombok.Setter;
import ru.senina.itmo.web.web_lab_3.entities.Attempt;
import ru.senina.itmo.web.web_lab_3.validators.PlotAreaChecker;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named()
@SessionScoped
public class AttemptManager implements Serializable {

    @Getter @Setter
    private Attempt attempt;
    @Inject private PlotAreaChecker checker;
    @Inject private AttemptsList attemptsList;

    public AttemptManager(){
        this.attempt = new Attempt();
    }
    private boolean checkAttemptsCoordinates(){return  true;} //todo: read how to validate data in jsf page

    private boolean checkAttemptDoFitArea(){
        return checker.check(attempt.getCoordinates());
    }

    public void addToList(){
        attempt.setDoFitArea(checkAttemptDoFitArea());
        attemptsList.add(this.attempt);
        attempt = new Attempt();
    }

}
