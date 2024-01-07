package com.bank.app.Classes;

public class Transaction {
    private String login;
    private String senderLogin;
    private String receiverLogin;
    private String transactionDate;
    private double transferAmount;
    private String message;

    public Transaction(String login, String senderLogin,
                       String receiverLogin, String getTransactionDate,
                       double transferAmount, String message) {
        this.login = login;
        this.senderLogin = senderLogin;
        this.receiverLogin = receiverLogin;
        this.transactionDate = getTransactionDate;
        this.transferAmount = transferAmount;
        this.message = message;
    }

    public Transaction() {}

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenderLogin() {
        return senderLogin;
    }

    public void setSenderLogin(String senderLogin) {
        this.senderLogin = senderLogin;
    }

    public String getReceiverLogin() {
        return receiverLogin;
    }

    public void setReceiverLogin(String receiverLogin) {
        this.receiverLogin = receiverLogin;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String getTransactionDate) {
        this.transactionDate = getTransactionDate;
    }

    public double getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(double transferAmount) {
        this.transferAmount = transferAmount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
