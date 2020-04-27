package ua.nure.diulher.SummaryTask4.db.entity;

import java.util.Date;

public class User extends Entity {

    private static final long serialVersionUID = -6889036256149495388L;

    private String login;

    private String password;

    private String firstName;

    private String lastName;

    private int roleId;

    private String position;

    private String date;

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder(firstName
                + " "+ lastName );
        if(roleId==3){
            sb.append(", "+position);
        }
        return sb.toString();
    }

}
