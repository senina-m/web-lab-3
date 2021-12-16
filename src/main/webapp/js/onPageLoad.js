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
    //fixme
    document.getElementById("plot").onclick = (event) => {
        console.log('Start drawing point after click! Received coords: ' + event.clientX + ', ' + event.clientY);
        let plot = document.getElementById('plot');
        let coordinates = getCoords(event, plot);
        document.querySelector(".x_field").value = coordinates.x;
        document.querySelector(".y_field").value = coordinates.y;
        let r = coordinates.r;
        if(Number.isInteger(r) && r <= 5 && r >= 1){
            submitClick({r:r});
        }
    };
}

initTimer = () => {
    const DELAY = 7000;
    setCurrentDateTime();
    setInterval(setCurrentDateTime, DELAY);
}