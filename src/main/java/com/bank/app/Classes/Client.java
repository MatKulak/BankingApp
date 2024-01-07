package com.bank.app.Classes;

import java.time.LocalDate;
import java.util.Random;

public class Client {
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private String registrationDate;

    private  Double accountBalance;

    private static String initialLogin;

    public Client(String firstName, String lastName, String login, String password, String registrationDate, Double accountBalance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.registrationDate = registrationDate;
        this.accountBalance = accountBalance;
    }

    //constructor used to add new client
    public Client(String firstName, String lastName, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = generateLogin();
        this.password = password;
        this.registrationDate = LocalDate.now().toString();
        this.accountBalance = 0.0;
    }

    public Client() {}

    public static void setInitialLogin(String initialLogin) {
        Client.initialLogin = initialLogin;
    }

    public static String getInitialLogin() {
        return initialLogin;
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

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(Double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public void deposit(double deposit) {
        accountBalance += deposit;
    }

    public void withdraw(double withdraw) {
        accountBalance -= withdraw;
    }

    //returns unique user login
    private String generateLogin() {
        Random random = new Random();
        int randomThreeDigitNumber = random.nextInt(900) + 100;
        String login = "@" + getFirstThreeChars(firstName.toLowerCase()) + getFirstThreeChars(lastName.toLowerCase()) + randomThreeDigitNumber;

        //if login already exist, generate new login
        if (ClientDAO.doesClientExist(login))
            generateLogin();
        return login;
    }

    //used to generate login
    private static String getFirstThreeChars(String str) {
        if (str.length() >= 3) {
            return str.substring(0, 3);
        } else {
            return str;
        }
    }
}
