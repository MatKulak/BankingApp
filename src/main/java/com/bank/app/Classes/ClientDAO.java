package com.bank.app.Classes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {

    //add new client to database
    public static void addClient(Client client) {
        try {
            Connection connection = DataBaseHandler.getConnection();
            assert connection != null;
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO clients (first_name," +
                            " last_name, user_login, user_password, registration_date," +
                            " account_balance) VALUES (?, ?, ?, ?, ?, ?)");

            preparedStatement.setString(1, client.getFirstName());
            preparedStatement.setString(2, client.getLastName());
            preparedStatement.setString(3, client.getLogin());
            preparedStatement.setString(4, client.getPassword());
            preparedStatement.setString(5, client.getRegistrationDate());
            preparedStatement.setDouble(6, client.getAccountBalance());

            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //return client found with matching login parameter
    //return null if client doesn't exist in database
    public static Client getClient(String login) {
        try {
            Connection connection = DataBaseHandler.getConnection();
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM clients WHERE user_login = ?");
            preparedStatement.setString(1, login);

            ResultSet resultSet = preparedStatement.executeQuery();

            Client client = new Client();
            if (resultSet.next()) {
                client.setFirstName(resultSet.getString("first_name"));
                client.setLastName(resultSet.getString("last_name"));
                client.setLogin(resultSet.getString("user_login"));
                client.setPassword(resultSet.getString("user_password"));
                client.setRegistrationDate(resultSet.getString("registration_date"));
                client.setAccountBalance(resultSet.getDouble("account_balance"));
                return client;
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //replace client with passed login to new client passed as parameter
    public static void replaceClient(String login, Client newClient) {
        try {
            Connection connection = DataBaseHandler.getConnection();
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE clients SET first_name=?, last_name=?, user_password=?, registration_date=?, account_balance=? WHERE user_login=?");

            preparedStatement.setString(1, newClient.getFirstName());
            preparedStatement.setString(2, newClient.getLastName());
            preparedStatement.setString(3, newClient.getPassword());
            preparedStatement.setString(4, newClient.getRegistrationDate());
            preparedStatement.setDouble(5, newClient.getAccountBalance());
            preparedStatement.setString(6, login);

            preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //check if client already exist in database
    public static boolean doesClientExist(String login) {
        try {
            Connection connection = DataBaseHandler.getConnection();
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM clients WHERE user_login = ?");
            preparedStatement.setString(1, login);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    //return list of all client from database
    public static List<Client> retrieveAllClients() {
        List<Client> clients = new ArrayList<>();

        try {
            Connection connection = DataBaseHandler.getConnection();
            assert connection != null;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM clients");

            while (resultSet.next()) {
                Client client = new Client();
                client.setFirstName(resultSet.getString("first_name"));
                client.setLastName(resultSet.getString("last_name"));
                client.setLogin(resultSet.getString("user_login"));
                client.setPassword(resultSet.getString("user_password"));
                client.setRegistrationDate(resultSet.getString("registration_date"));
                client.setAccountBalance(resultSet.getDouble("account_balance"));

                clients.add(client);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    //return true if login is successful, otherwise false
    public static boolean validateClientLogin(String login, String password) {
        try {
            Connection connection = DataBaseHandler.getConnection();
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM clients WHERE user_login = ? AND user_password = ?");

            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            connection.close();

            if (resultSet.next())
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //delete client from database
    public static void deleteClient(Client client) {
        try {
            Connection connection = DataBaseHandler.getConnection();
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM clients WHERE user_login = ?");
            preparedStatement.setString(1, client.getLogin());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //check if clients database is empty
    public static boolean isClientsDatabaseEmpty() {
        try {
            Connection connection = DataBaseHandler.getConnection();
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM clients");
            ResultSet resultSet = preparedStatement.executeQuery();

            return !resultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
