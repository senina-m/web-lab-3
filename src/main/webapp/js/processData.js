processData = function (event) {
    event.preventDefault();
    const form = document.getElementsByClassName('form');
    console.log('form: ' + form)
    console.log('Start processing form')
    let coordinates = validateForm();
    if (coordinates != null){
        document.getElementById('form').submit();
    }else{
        console.log('data validation error');
    }
}