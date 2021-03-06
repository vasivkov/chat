package com.vasivkov.chat.common;

public class RegistrationRequest extends Request {
    private String login;
    private String password;

    public RegistrationRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public void setId(int id) {
        super.setId(id);
    }
    public int getId() {
        return super.getId();
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RegistrationRequest that = (RegistrationRequest) o;

        if (login != null ? !login.equals(that.login) : that.login != null) return false;
        return password != null ? password.equals(that.password) : that.password == null;
    }

    @Override
    public int hashCode() {
        int result = login != null ? login.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RegistrationRequest{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public String getSender() {
        return login;
    }
}
