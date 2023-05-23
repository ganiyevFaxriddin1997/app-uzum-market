package org.example.service.card;


import org.example.entities.Card;
import org.example.enums.CardType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CardServiceImp implements CardService {
    @Override
    public int create(Card card) throws SQLException {
        var connection = getConnection();
        String query = "select add_card(?,?,?,?,?,?)";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, card.getSerialNumber());
        preparedStatement.setString(2, card.getCardType().name());
        preparedStatement.setTimestamp(3, card.getDate());
        preparedStatement.setInt(4, card.getPassword());
        preparedStatement.setDouble(5, card.getBalance());
        preparedStatement.setInt(6, card.getOwnerId());
        ResultSet resultSet = preparedStatement.executeQuery();
        var result = -1;
        if (resultSet.next())
            result = resultSet.getInt(1);
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return result;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        var connection = getConnection();
        String query = "delete from card where id = ?";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        int result = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        return result > 0;
    }

    @Override
    public Card get(int id) throws SQLException {
        var connection = getConnection();
        String query = "select * from card where id = ?";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            var card = Card.builder()
                    .id(resultSet.getInt("id"))
                    .serialNumber(resultSet.getString("serial_number"))
                    .cardType(CardType.getCardType(resultSet.getString("card_type")))
                    .date(resultSet.getTimestamp("expiration_date"))
                    .password(resultSet.getInt("password"))
                    .balance(resultSet.getDouble("balance"))
                    .balance(resultSet.getInt("owner_id"))
                    .build();
            return card;
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return null;
    }

    @Override
    public List<Card> getAll() throws SQLException {
        List<Card> cards = new ArrayList<>();
        var connection = getConnection();
        String query = "select * from card";
        var preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            var card = Card.builder()
                    .id(resultSet.getInt("id"))
                    .serialNumber(resultSet.getString("serial_number"))
                    .cardType(CardType.getCardType(resultSet.getString("card_type")))
                    .date(resultSet.getTimestamp("expiration_date"))
                    .password(resultSet.getInt("password"))
                    .balance(resultSet.getDouble("balance"))
                    .balance(resultSet.getInt("owner_id"))
                    .build();
            cards.add(card);
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return cards;
    }

    @Override
    public boolean update(int id, Card card) throws SQLException {
        var connection = getConnection();
        String query = "update card set serial_number = ?, card_type = ? card_date = ?, password = ?, balance = ?, owner_id = ? where id = ?";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, card.getSerialNumber());
        preparedStatement.setString(2, card.getCardType().name());
        preparedStatement.setTimestamp(3, card.getDate());
        preparedStatement.setInt(4, card.getPassword());
        preparedStatement.setDouble(5, card.getBalance());
        preparedStatement.setInt(6,card.getOwnerId());
        preparedStatement.setInt(7, id);
        int result = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        return result > 0;
    }

    @Override
    public Card getCardBySerialNumber(String serialNumber) throws SQLException {
        var connection = getConnection();
        String query = "select * from card where serial_number = ?";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, serialNumber);
        var resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            var card = Card.builder()
                    .id(resultSet.getInt("id"))
                    .serialNumber(resultSet.getString("serial_number"))
                    .cardType(CardType.getCardType(resultSet.getString("card_type")))
                    .date(resultSet.getTimestamp("card_date"))
                    .password(resultSet.getInt("password"))
                    .balance(resultSet.getDouble("balance"))
                    .ownerId(resultSet.getInt("owner_id"))
                    .build();
            return card;
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return null;
    }

    @Override
    public Card getByPassword(int password) throws SQLException {
        var connection = getConnection();
        String query = "select * from card where password = ?";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            var card = Card.builder()
                    .id(resultSet.getInt("id"))
                    .serialNumber(resultSet.getString("serial_number"))
                    .cardType(CardType.getCardType(resultSet.getString("card_type")))
                    .date(resultSet.getTimestamp("card_date"))
                    .password(resultSet.getInt("password"))
                    .balance(resultSet.getDouble("balance"))
                    .ownerId(resultSet.getInt("owner_id"))
                    .build();
            return card;
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return null;
    }

    @Override
    public List<Card> getByOwnerId(int ownerId) throws SQLException {
        var connection = getConnection();
        String query = "select * from card where owner_id = ?";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, ownerId);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Card> cards = new ArrayList<>();

        while (resultSet.next()) {
            var card = Card.builder()
                    .id(resultSet.getInt("id"))
                    .serialNumber(resultSet.getString("serial_number"))
                    .cardType(CardType.getCardType(resultSet.getString("card_type")))
                    .date(resultSet.getTimestamp("card_date"))
                    .password(resultSet.getInt("password"))
                    .balance(resultSet.getDouble("balance"))
                    .ownerId(resultSet.getInt("owner_id"))
                    .build();
            cards.add(card);
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return cards;
    }
}
