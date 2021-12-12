runPageOnloadFunctions = () => {
    console.log("hey, we are here in init page!")
    initCanvas();
    rButtonsListeners();
}

rButtonsListeners = () => {
    let rBlock = document.getElementById('form:navigation_block');
    if (currentR === 0) {
        currentR = DEFAULT_R;
    }

    if (rBlock.value === "0.0") {
        rBlock.value = currentR;
        console.log("no blocks was chosen use default(current) value: " + currentR);
    } else {
        currentR = rBlock.value;
        console.log("block was chosen, change currentR value to " + currentR);
        //set current r button as active
        document.querySelectorAll(".RButton b" + currentR)
            .forEach(button => button.addClass("active"));
    }

    document.querySelectorAll('#r-input .r_button')
        .forEach(button => {
            button.on('click', function () {
                button.addClass('active');
                document.querySelectorAll('#r-input .r_button')
                    .forEach(otherButtons => otherButtons.not(button).removeClass('active'));
                currentR = button.attr("id").split('').pop();
                console.log("currentR value: " + currentR);
                rBlock.value = currentR;
                drawPlot(globalAttemptsArray);
            });
        });
}

initCanvas = () => {
    CANVAS = SVG()
        .addTo('#plot')
        .size('100%', '100%')
        .viewbox(0, 0, WIDTH, HEIGHT);
}