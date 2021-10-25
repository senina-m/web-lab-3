class httpError  extends Error {
    constructor(message){
        super(message);
        this.name = "httpError";
    }
}
