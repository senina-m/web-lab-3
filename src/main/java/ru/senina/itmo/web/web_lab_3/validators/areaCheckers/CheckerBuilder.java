package ru.senina.itmo.web.web_lab_3.validators.areaCheckers;

public class CheckerBuilder {
    public void addTriangleIn4Quoter(AreaChecker checker){
        AreaCheckCondition triangleIn4QuoterCondition = (double x, double y, double r) ->
                (x - r / 2 <= y && y <= 0 && x >= 0);
        checker.getListOfConditions().add(triangleIn4QuoterCondition);
    }

    public void addCircleIn3Quoter(AreaChecker checker){
        AreaCheckCondition circleIn3QuoterCondition = (double x, double y, double r) ->
                (x <= 0 && y <= 0 && (Math.pow(x, 2) + Math.pow(y, 2) <= Math.pow((r / 2), 2)));
        checker.getListOfConditions().add(circleIn3QuoterCondition);
    }

    public void addSquare1Quoter(AreaChecker checker){
        AreaCheckCondition squareIn1QuoterCondition = (double x, double y, double r) ->
                (x >= 0 && x <= r && y >= 0 && y <= r);
        checker.getListOfConditions().add(squareIn1QuoterCondition);
    }
}
