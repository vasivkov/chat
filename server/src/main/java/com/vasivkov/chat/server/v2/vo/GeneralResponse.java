package com.vasivkov.chat.server.v2.vo;

/**
 * Created by eugene on 7/28/17.
 */
public class GeneralResponse implements Response {

    private Result result;
    private String error;
    boolean outcome;

    public GeneralResponse(Boolean outcome, String error) {
//        this.result = result;
        this.outcome = outcome;
        this.error = error;
    }

    public Result getResult() {
        return result;
    }

    public String getError() {
        return error;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GeneralResponse that = (GeneralResponse) o;

        if (result != that.result) return false;
        return error != null ? error.equals(that.error) : that.error == null;
    }

    @Override
    public int hashCode() {
        int result1 = result != null ? result.hashCode() : 0;
        result1 = 31 * result1 + (error != null ? error.hashCode() : 0);
        return result1;
    }

    @Override
    public String toString() {
        return "GeneralResponse{" +
                "result=" + result +
                ", error='" + error + '\'' +
                '}';
    }
}

 enum Result{
    OK,
    FAILED
}
