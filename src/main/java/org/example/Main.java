package org.example;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection connection = Database.getInstance().getConnection();
        ClientService clientService = new ClientService(connection);
        long yuki = clientService.create("Yuki");
        System.out.println("Created client Yuki with Id " + yuki);
        String byId = clientService.getById(yuki);
        System.out.println("Client with Id " + yuki + " is " + byId);
        clientService.setName(yuki, "Haru");
        byId = clientService.getById(yuki);
        System.out.println("Changed client's name with Id " + yuki + ". Now it's " + byId);
        clientService.listAll().forEach(System.out::println);
        clientService.deleteById(yuki);
        System.out.println("Deleting client Haru with Id " + yuki);
        clientService.listAll().forEach(System.out::println);
        connection.close();
    }
}
