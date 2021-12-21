let WIDTH = 1000;
let HEIGHT = 1000;
const X_CENTER = 0;
const Y_CENTER = 0;
const BACKGROUND_COLOR = '#fff';
let CANVAS = null;
const AXES_COLOR = '#000'
const AREA_COLOR = 'lightgray';
let scale = 0.017;
const scaleLastPoint = 10;
const pointsScale = 5;

let clearedAt = 0;
let lastElementNum = 0;
const DEFAULT_R = 2;
let currentR = DEFAULT_R;
let globalAttemptsArray = [];

drawPlot = (attemptsArray) => {
    console.log("Полученный массив точек: \"" + attemptsArray + "\"");
    if (CANVAS === null) {
        console.log("CANVAS wasn't init yet!")
    } else if (attemptsArray.length === 0) {
        initPlot();
    } else {
        globalAttemptsArray = attemptsArray;
        drawPlotWithPoints(attemptsArray);
    }
}

initPlot = () => {
    console.log('Init plot!');
    CANVAS.rect(WIDTH, HEIGHT).fill(BACKGROUND_COLOR);
    console.log("R value while init:" + DEFAULT_R);
    drawArea(DEFAULT_R);
    drawAxes();
    drawGrid();
    drawAxesScaleLabels(DEFAULT_R);
    drawRValue(DEFAULT_R);
}

drawPlotWithPoints = (attemptsArray) => {
    console.log('Ready to draw plot!');
    let pointsArray = [];
    attemptsArray.forEach(point => {
        pointsArray.push({
            x: (point.coordinates).x,
            y: (point.coordinates).y,
            r: (point.coordinates).r,
            result: point.doFitArea,
        });
    });
    lastElementNum = pointsArray.length - 1;
    scale = countScale(pointsArray);
    let lastPoint = pointsArray[pointsArray.length - 1];
    const r = lastPoint.r;
    currentR = r;
    console.log('R = ' + r);
    drawArea(r);

    drawAxes();
    drawAxesScaleLabels(r);
    drawGrid();

    for (let i = clearedAt; i <= lastElementNum - 1; i++) {
        let point = pointsArray[i];
        drawPoint(point.x, point.y, point.result, pointsScale);
    }
    drawPoint(lastPoint.x, lastPoint.y, lastPoint.result, scaleLastPoint);
    drawRValue(r);
}

//fixme fix clearing
clearPlot = () => {
    if (submitGetRequest({session: "clear"}, "/web-lab-2-1.2/controller")) {
        initPlot();
        $("#table_body").empty();
    } else {
        removeErrors();
        printError("can't clear session", "clear session error",
            "Can't clear plot and table", document.getElementById("clearButton"));
    }
}

convertX = (x) => {
    // console.log('convert x: \n width/2 + x/scale ==> ' + WIDTH + '/' + 2 + ' + ' + x + '/' + scale + ' - ' + X_CENTER + ' =\n'
    //     + (WIDTH / 2 + x / scale - X_CENTER));
    return (WIDTH / 2 + x / scale + X_CENTER / scale);
}

convertY = (y) => {
    return (HEIGHT / 2 - y / scale - Y_CENTER / scale)
}

convertToCoordinatesX = (xPoint) => {
    return (xPoint - WIDTH / 2) * scale - X_CENTER;
}

convertToCoordinatesY = (yPoint) => {
    return (HEIGHT / 2 - yPoint) * scale - Y_CENTER;
}

countScale = (pointsArray) => {
    const scaleNum = 200; //todo: find better value
    console.log(JSON.stringify(pointsArray));
    let max = Math.abs(pointsArray[0].x);
    let newScale;
    pointsArray.forEach(point => {
        newScale = max =
            (Math.abs(point.x) > max || (Math.abs(point.y) > max)) ?
                Math.max(Math.abs(point.x), (Math.abs(point.y))) / scaleNum :
                scale;
    });
    console.log('scale = ' + newScale)
    // return newScale;
    return 0.017;
}

drawAxes = () => {
    //todo: причесать метод, вынести дублирующийся код
    const arrowSize = 10
    //axis x
    CANVAS.line(0, (HEIGHT / 2), WIDTH, (HEIGHT / 2)).stroke({width: 2, color: AXES_COLOR});
    //axis arrow
    const triangleX = (WIDTH - arrowSize) + ',' + (HEIGHT / 2 - arrowSize / 2) + ' ' +
        (WIDTH - arrowSize) + ',' + (HEIGHT / 2 + arrowSize / 2) + ' ' +
        (WIDTH) + ',' + (HEIGHT / 2)
    console.log('x arrow coordinates ' + triangleX)
    CANVAS.polygon(triangleX).fill(AXES_COLOR)
    CANVAS.text('x').font({
        size: 16,
        family: 'Menlo, sans-serif',
        anchor: 'end',
        fill: AXES_COLOR
    }).move(WIDTH - 2 * arrowSize, HEIGHT / 2 - 2 * arrowSize)

    //axis y
    CANVAS.line(WIDTH / 2, 0, WIDTH / 2, HEIGHT).stroke({width: 2, color: AXES_COLOR});
    //axis arrow
    const triangleY = (WIDTH / 2 - arrowSize / 2) + ',' + (arrowSize) + ' ' +
        (WIDTH / 2 + arrowSize / 2) + ',' + (arrowSize) + ' ' +
        (WIDTH / 2) + ',' + (0);
    console.log('y arrow coordinates ' + triangleY)
    CANVAS.polygon(triangleY).fill(AXES_COLOR)
    CANVAS.text('y').font({
        size: 16,
        family: 'Menlo, sans-serif',
        anchor: 'end',
        fill: AXES_COLOR
    }).move(WIDTH / 2 - 1.5 * arrowSize, 1.7 * arrowSize)
}

function drawScaleLabel(xStart, xStop, yStart, yStop, labelX, labelY, label) {
    // console.log('Label stroke input coordinates for ' + label + ': ' + xStart + ' ' + yStart + ' ' + xStop + ' ' + yStop)
    // console.log('Label stroke coordinates ' + label + ': ' + convertX(xStart) + ' ' + convertY(yStart) + ' ' + convertX(xStop) + ' ' + convertY(yStop) + '\n');
    CANVAS.line(convertX(xStart), convertY(yStart), convertX(xStop), convertY(yStop))
        .stroke({width: 2, color: AXES_COLOR});
    CANVAS.text(label).font({
        size: 16,
        family: 'Menlo, sans-serif',
        anchor: 'end',
        fill: AXES_COLOR
    }).move(convertX(labelX), convertY(labelY));
}

drawRValue = (r) => {
    CANVAS.text('R = ' + r).font({
        size: 16,
        family: 'Menlo, sans-serif',
        anchor: 'end',
        fill: AXES_COLOR
    }).move(WIDTH - 50, HEIGHT - 50);
}

drawAxesScaleLabels = (r) => {
    console.log('Start drawing axes labels')
    const hatchLen = 0.1;
    console.log("R value while drawing labels: " + r);
    //x axis labels
    drawScaleLabel(-r, -r, hatchLen, -hatchLen, -r, -2 * hatchLen, "-R");
    drawScaleLabel(-r / 2, -r / 2, hatchLen, -hatchLen, -r / 2, -2 * hatchLen, "-R/2");
    drawScaleLabel(r / 2, r / 2, hatchLen, -hatchLen, r / 2, -2 * hatchLen, "R/2");
    drawScaleLabel(r, r, hatchLen, -hatchLen, r, -2 * hatchLen, "R");

    //y axis labels
    drawScaleLabel(hatchLen, -hatchLen, -r, -r, -4 * hatchLen, -r, "-R");
    drawScaleLabel(hatchLen, -hatchLen, -r / 2, -r / 2, -4 * hatchLen, -r / 2, "-R/2");
    drawScaleLabel(hatchLen, -hatchLen, r / 2, r / 2, -4 * hatchLen, r / 2, "R/2");
    drawScaleLabel(hatchLen, -hatchLen, r, r, -4 * hatchLen, r, "R");
}

drawGrid = () => {
    let numOfLines = WIDTH * scale / 2;
    for (let i = 1; i < numOfLines; i++) {
        //vertical lines
        let lineLeft = CANVAS.line(convertX(i), 0, convertX(i), HEIGHT);
        let lineRight = CANVAS.line(convertX(-i), 0, convertX(-i), HEIGHT);
        //horizontal lines
        let lineUp = CANVAS.line(0, convertX(i), WIDTH, convertX(i));
        let lineBottom = CANVAS.line(0, convertX(-i), WIDTH, convertX(-i));

        lineLeft.stroke({width: 0.5, color: AXES_COLOR, dasharray: '5,5'});
        lineRight.stroke({width: 0.5, color: AXES_COLOR, dasharray: '5,5'});

        lineUp.stroke({width: 0.5, color: AXES_COLOR, dasharray: '5,5'});
        lineBottom.stroke({width: 0.5, color: AXES_COLOR, dasharray: '5,5'});
    }
}


drawArea = (r) => {
    CANVAS.rect(WIDTH, HEIGHT).fill(BACKGROUND_COLOR);
    //here diameter needed
    CANVAS.circle(r / scale).fill(AREA_COLOR).move(convertX(-r / 2), convertY(r / 2))
    const fillUnusedCircle = (convertX(0)) + ',' + (convertY(0)) + ' ' +
        (convertX(-r / 2)) + ',' + (convertY(0)) + ' ' +
        (convertX(-r / 2)) + ',' + (convertY(r / 2)) + ' ' +
        (convertX(r / 2)) + ',' + (convertY(r / 2)) + ' ' +
        (convertX(r / 2)) + ',' + (convertY(-r / 2)) + ' ' +
        (convertX(0)) + ',' + (convertY(-r / 2));

    CANVAS.polygon(fillUnusedCircle).fill(BACKGROUND_COLOR)
    const area = (convertX(0)) + ',' + (convertY(0)) + ' ' +
        (convertX(0)) + ',' + (convertY(r)) + ' ' +
        (convertX(r)) + ',' + (convertY(r)) + ' ' +
        (convertX(r)) + ',' + (convertY(0)) + ' ' +
        (convertX(0)) + ',' + (convertY(-r / 2));
    console.log('area coordinates ' + area)
    CANVAS.polygon(area).fill(AREA_COLOR)
}

drawPoint = (x, y, result, pointScale) => {
    let color = result === true ? '#0f0' : '#f00';
    const pointX = convertX(x) - pointScale / 2;
    const pointY = convertY(y) - pointScale / 2;
    CANVAS.circle(pointScale + 1).fill(AREA_COLOR).move(pointX, pointY);
    CANVAS.circle(pointScale).fill(color).move(pointX, pointY);
}

getCoords = (event, element) => {
    let coordinates = {};
    let xPosition = element.getBoundingClientRect().left;
    let yPosition = element.getBoundingClientRect().top;
    let width = element.getBoundingClientRect().width;
    let height = element.getBoundingClientRect().height;

    console.log('xPosition: ' + xPosition + ' X: ' + (event.clientX - xPosition) * (WIDTH / width));
    console.log('yPosition: ' + yPosition + ' Y: ' + (event.clientY - yPosition) * (HEIGHT / height));
    let plot = document.getElementById("plot");
    coordinates.x = roundToHalf(convertToCoordinatesX((event.clientX - xPosition) * (WIDTH / width)));
    coordinates.y = convertToCoordinatesY((event.clientY - yPosition) * (HEIGHT / height));
    coordinates.r = currentR;
    console.log('X: ' + coordinates.x);
    console.log('Y: ' + coordinates.y);
    console.log('R: ' + coordinates.r);
    return coordinates;
}

roundToHalf = (number) => {
    let rounded = Math.round(number);
    if (rounded < number) {
        if (number < rounded + 0.25) {
            return rounded;
        } else {
            return rounded + 0.5;
        }
    } else {
        if (number + 0.25 < rounded) {
            return rounded + 0.5;
        } else {
            return rounded;
        }
    }
}