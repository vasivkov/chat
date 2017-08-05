package com.vasivkov.chat.server.v2.vo;

import com.vasivkov.chat.common.Response;

import java.util.Collections;
import java.util.List;

public class ResponseWithRecipients {
    private List<Integer> recipients;
    private Response response;

    public ResponseWithRecipients(List<Integer> recipients, Response response) {
        this.recipients = recipients;
        this.response = response;
    }
    public ResponseWithRecipients(int id, Response response) {
        this.recipients = Collections.singletonList(id);
        this.response = response;
    }

    public List<Integer> getRecipients() {
        return recipients;
    }

    public Response getResponse() {
        return response;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResponseWithRecipients that = (ResponseWithRecipients) o;

        if (recipients != null ? !recipients.equals(that.recipients) : that.recipients != null) return false;
        return response != null ? response.equals(that.response) : that.response == null;
    }

    @Override
    public int hashCode() {
        int result = recipients != null ? recipients.hashCode() : 0;
        result = 31 * result + (response != null ? response.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ResponseWithRecipients{" +
                "recipients=" + recipients +
                ", response=" + response +
                '}';
    }
}
