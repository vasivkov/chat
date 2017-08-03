package com.vasivkov.chat.common;

public class GeneralResponse implements Response {

    private String error;
    private boolean outcome;

    public GeneralResponse(boolean outcome,String error) {
        this.error = error;
        this.outcome = outcome;
    }

    public String getError() {
        return error;
    }

    public boolean isOutcome() {
        return outcome;
    }

    @Override
    public String toString() {
        return "GeneralResponse{" +
                "error='" + error + '\'' +
                ", outcome=" + outcome +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GeneralResponse that = (GeneralResponse) o;

        if (outcome != that.outcome) return false;
        return error != null ? error.equals(that.error) : that.error == null;
    }

    @Override
    public int hashCode() {
        int result = error != null ? error.hashCode() : 0;
        result = 31 * result + (outcome ? 1 : 0);
        return result;
    }
}

