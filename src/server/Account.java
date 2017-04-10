package server;

import java.io.Serializable;

/**
 * Created by dulun on 27.12.2016.
 */
public class Account implements Serializable {

    private static final long serialVersionUID = 6790805821033141019L;

    private String username = null;

    private String password = null;

    private int credit = 0;

    public Account(String username, String password) {

        this.username = username;
        this.password = password;
    }

    public Account(String username, String password, int credit) {

        this.username = username;
        this.password = password;
        this.credit = credit;
    }

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {

        this.username = username;
    }

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {

        this.password = password;
    }

    public int getCredit() {

        return credit;
    }

    public void setCredit(int credit) {

        this.credit = credit;
    }

    @Override
    public String toString(){

        return "username: " + username + " , " + "password: " + password + " , " + "credit: " + credit + "\n";
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Account account = (Account) o;
        if (username != null ? !username.equals(account.username) : account.username != null)
            return false;

        return password != null ? password.equals(account.password) : account.password == null;
    }

    @Override
    public int hashCode() {

        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + credit;

        return result;
    }
}
