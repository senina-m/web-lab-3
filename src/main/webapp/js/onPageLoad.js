runPageOnloadFunctions = () => {
    console.log("hey, we are here in init page!")
    initCanvas();
    plotClickListener();
    // initTimer();
}

initCanvas = () => {
    CANVAS = SVG()
        .addTo('#plot')
        .size('100%', '100%')
        .viewbox(0, 0, WIDTH, HEIGHT);
    drawPlot(globalAttemptsArray);
}

plotClickListener = () => {
    let plot = document.getElementById('plot');
    plot.onclick = () => {
        console.log('Start drawing point after click! Received coords: ' + event.clientX + ', ' + event.clientY);
        let plot = document.getElementById('plot');
        let coordinates = getCoords(event, plot);
        if (!isNaN(coordinates.r) && coordinates.r <= 5 && coordinates.r >= 1) {
            console.log('Try to draw point after click. Coordinates: x: ' + coordinates.x + ', y: ' + coordinates.y + ', r: ' + coordinates.r);
            submitClick([{'name':'x_val', 'value': coordinates.x},{'name':'y_val', 'value': coordinates.y}, {'name':'r_val', 'value': coordinates.r}]);
        }
    }
}

initTimer = () => {
    const DELAY = 7000;
    setCurrentDateTime();
    setInterval(setCurrentDateTime, DELAY);
}