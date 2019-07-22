package Util;

public class Feedback {

    public final boolean SUCCESS;
    public final String MESSAGE;
    public final int STATUS;

    public Feedback(boolean success){
        this.SUCCESS = success;
        this.MESSAGE = "";
        this.STATUS = 0;
    }

    public Feedback(boolean success, int status){
        this.SUCCESS = success;
        this.MESSAGE = "";
        this.STATUS = status;
    }

    public Feedback(boolean success, String message){
        this.SUCCESS = success;
        this.MESSAGE = message;
        this.STATUS = 0;
    }

    public Feedback(boolean success, int status, String message){
        this.SUCCESS = success;
        this.MESSAGE = message;
        this.STATUS = status;
    }

}
