initTimer = () => {
    const DELAY = 7000;
    setCurrentDateTime();
    setInterval(setCurrentDateTime, DELAY);
}