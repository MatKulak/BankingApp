package com.bank.app.Classes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    //add transaction to database
    public static void addTransaction(Transaction transaction) {
        try {
            Connection connection = DataBaseHandler.getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO transactions (user_login," +
                            " sender_login, receiver_login, transaction_date, transfer_amount," +
                            " message) VALUES (?, ?, ?, ?, ?, ?)");

            preparedStatement.setString(1, transaction.getLogin());
            preparedStatement.setString(2, transaction.getSenderLogin());
            preparedStatement.setString(3, transaction.getReceiverLogin());
            preparedStatement.setString(4, transaction.getTransactionDate());
            preparedStatement.setDouble(5, transaction.getTransferAmount());
            preparedStatement.setString(6, transaction.getMessage());

            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //returns sum of all positive transactions for passed login
    public static double getIncomeSum(String login) {
        double sum = 0.0;

        try {
            Connection connection = DataBaseHandler.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT SUM(transfer_amount) FROM transactions WHERE user_login = ? AND transfer_amount > 0");

            preparedStatement.setString(1, login);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                sum = resultSet.getDouble(1);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sum;
    }

    //returns sum of all negative transactions for passed login
    public static double getExpensesSum(String login) {
        double sum = 0.0;

        try {
            Connection connection = DataBaseHandler.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT SUM(transfer_amount) FROM transactions WHERE user_login = ? AND transfer_amount < 0");

            preparedStatement.setString(1, login);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                sum = resultSet.getDouble(1);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sum;
    }

    //return list of all transactions from database for provided login
    public static List<Transaction> retrieveAllTransactions(String login) {
        List<Transaction> transactions = new ArrayList<>();

        try {
            Connection connection = DataBaseHandler.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM transactions");

            while (resultSet.next()) {
                if (resultSet.getString("user_login").equals(login)) {
                    Transaction transaction = new Transaction();
                    transaction.setLogin(resultSet.getString("user_login"));
                    transaction.setSenderLogin(resultSet.getString("sender_login"));
                    transaction.setReceiverLogin(resultSet.getString("receiver_login"));
                    transaction.setTransactionDate(resultSet.getString("transaction_date"));
                    transaction.setTransferAmount(resultSet.getDouble("transfer_amount"));
                    transaction.setMessage(resultSet.getString("message"));

                    transactions.add(transaction);
                }
            }
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }
}

