function setDate(date) {
    let day = date.getDate();
    let month = date.getMonth() + 1;
    let year = date.getFullYear();

    day = (day < 10) ? '0' + day : day;
    month = (month < 10) ? '0' + month : month;

    document.getElementById('date').innerHTML = (day.toString() + "."  + month.toString() + "." + year.toString());
}

function setTime(date) {
    let hours = date.getHours();
    let minutes = date.getMinutes();
    let seconds = date.getSeconds();

    hours = (hours < 10) ? '0' + hours : hours;
    minutes = (minutes < 10) ? '0' + minutes : minutes;
    seconds = (seconds < 10) ? '0' + seconds : seconds;

    document.getElementById('time').innerHTML = hours.toString() + ":"  + minutes.toString() + ":" + seconds.toString();
}

function setCurrentDateTime() {
    let date = new Date();
    setDate(date);
    setTime(date);
}

