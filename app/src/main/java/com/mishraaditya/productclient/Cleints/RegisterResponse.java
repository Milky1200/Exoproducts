package com.mishraaditya.productclient.Cleints;

public class RegisterResponse {
    String error;
    //@SerializedName("message") if you are not going to use same name convention
    String message;


    public RegisterResponse(String error, String message){
        this.error=error;
        this.message=message;
    }



    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
