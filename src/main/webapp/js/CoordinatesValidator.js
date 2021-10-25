validateForm = () => {
    console.log('Start validating')
    let coordinates = this.getValues();
    this.removeErrors();
    return checkValues(coordinates) ? coordinates : null;
}

printError = (errorLogName, errorClass, errorMessage, element) => {
    console.log(errorLogName);
    const error = document.createElement('div');
    error.className = errorClass;
    error.innerHTML = errorMessage;
    element.parentElement.insertBefore(error, element);
}

checkValues = (coordinates) => {
    let anyErrors = 0;
    if (!printErrorIfBlank(coordinates.y, 'y') || !printErrorIfBlank(coordinates.r, 'r')) {
        anyErrors++;
    }

    if (coordinates.x < -4 || coordinates.x > 4 || isNaN(Number(coordinates.x))){
        this.printError('x is out of range', 'x error',
            'Has to be number from -4 to 4', document.getElementById('x'));
        anyErrors++;
    }

    if (coordinates.y > 5 || coordinates.y < -5 || isNaN(Number(coordinates.y))) {
        this.printError('y is out of range', 'y error',
            'Has to be number from -5 to 5', document.getElementById('y'));
        anyErrors++;
    }

    if (coordinates.r > 5 || coordinates.r < 2 || isNaN(Number(coordinates.r))) {
        this.printError('r is out of range', 'r error',
            'Has to be number from 2 to 5', document.getElementById('r'));
        anyErrors++;
    }
    return anyErrors === 0;
}

printErrorIfBlank = (field, fieldId) => {
    console.log('Field value:' + field)
    if (field === '') {
        this.printError(fieldId + ' field is blank', 'blankField error',
            'Cannot be blank', document.getElementById(fieldId));
        return false;
    }
    return true;
}

getValues = () => {
    console.log('Received values:');
    let x = document.getElementById('x');
    let y = document.getElementById('y');
    let r = document.getElementById('r');
    console.log('x: ', x.value);
    console.log('y: ', y.value);
    console.log('r: ', r.value);
    return {x: x.value, y: y.value, r: r.value};
}

removeErrors = () => {
    const errors = document.querySelectorAll('.error');
    for (let i = 0; i < errors.length; i++) {
        errors[i].remove();
    }
}

printFormError = (err) => {
    const submitButton = document.getElementById('submitButton');
    printError('Form error: ' + err.message, 'network error', err.message, submitButton);
}