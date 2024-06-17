package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientService {
    private final PreparedStatement createST;
    private final PreparedStatement readByIdST;
    private final PreparedStatement updateST;
    private final PreparedStatement deleteST;
    private final PreparedStatement selectAllST;

    public ClientService(Connection connection) throws SQLException {
        createST = connection.prepareStatement("INSERT INTO client(name) values (?)", PreparedStatement.RETURN_GENERATED_KEYS);
        readByIdST = connection.prepareStatement("SELECT name FROM client where id = ?");
        updateST = connection.prepareStatement("UPDATE client SET name = ? WHERE id = ?");
        deleteST = connection.prepareStatement("DELETE FROM client WHERE id = ?");
        selectAllST = connection.prepareStatement("SELECT * FROM client ORDER BY id");
    }

    public long create(String name) throws SQLException {
        try {
            validateName(name);
            createST.setString(1, name);
            createST.executeUpdate();
            ResultSet rs = createST.getGeneratedKeys();
            rs.next();
            return rs.getLong(1);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    public String getById(long id) throws SQLException {
        try {
            validateId(id);
            readByIdST.setLong(1, id);
            ResultSet rs = readByIdST.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    public void setName(long id, String name) throws SQLException {
        try {
            validateId(id);
            validateName(name);
            updateST.setLong(2, id);
            updateST.setString(1, name);
            updateST.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    public void deleteById(long id) throws SQLException {
        try {
            validateId(id);
            deleteST.setLong(1, id);
            deleteST.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    public List<Client> listAll() throws SQLException {
        try {
            List<Client> clients = new ArrayList<>();
            selectAllST.executeQuery();
            ResultSet rs = selectAllST.getResultSet();
            while (rs.next()) {
                clients.add(new Client(rs.getLong(1), rs.getString(2)));
            }
            return clients;
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    private void validateId(Long id) throws SQLException {
        ResultSet rs = selectAllST.executeQuery();
        ArrayList<Long> ids = new ArrayList<>();
        while (rs.next()) {
            ids.add(rs.getLong(1));
        }
        if (!ids.contains(id)) {
            throw new IllegalArgumentException("ID " + id + " not found");
        }
    }

    private void validateName(String name) {
        if (name == null ||
                name.length() <= 2 ||
                name.length() >= 1000 ||
                name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name length has to be between 2 and 1000 characters");
        }
    }
}
