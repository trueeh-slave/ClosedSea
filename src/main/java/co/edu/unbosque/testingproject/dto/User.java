package co.edu.unbosque.testingproject.dto;

import com.opencsv.bean.CsvBindByName;

public class User {

    @CsvBindByName
    private String username;

    @CsvBindByName
    private String password;

    @CsvBindByName
    private String role;

    @CsvBindByName
    private String coins;

    public User() {}

    public User(String username, String password, String role, String coins) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.coins = coins;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCoins() {
        return coins;
    }

    public void setCoins(String coins) {
        this.coins = coins;
    }

    @Override
    public String toString() {
        return "Username: " + username + ", Password: " + password + ", Role: " + role + ", Coins: " + coins;
    }
}
