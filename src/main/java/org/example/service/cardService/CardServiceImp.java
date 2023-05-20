package org.example.service.cardService;

import org.example.enums.CardType;
import org.example.model.Card;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CardServiceImp implements CardService{

@Override
public int create(Card card) throws SQLException {
	Connection connection = getConnection();
	PreparedStatement preparedStatement = connection.
			prepareStatement("select add_card(?,?,?,?,?)");
	preparedStatement.setString(1, card.getNumber());
	preparedStatement.setString(2, card.getType().name());
	preparedStatement.setTimestamp(3, card.getDate());
	preparedStatement.setString(4, card.getPassword());
	preparedStatement.setDouble(5, card.getBalance());
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
public boolean update(Card card) throws SQLException {
	var connection = getConnection();
	String query = "update card set serial_number = ?, card_type = ? card_date = ?, password = ?, balance = ? where id = ?";
	var preparedStatement = connection.prepareStatement(query);
	preparedStatement.setString(1, card.getNumber());
	preparedStatement.setString(2, card.getType().name());
	preparedStatement.setTimestamp(3, card.getDate());
	preparedStatement.setString(4, card.getPassword());
	preparedStatement.setDouble(5, card.getBalance());
	int result = preparedStatement.executeUpdate();
	preparedStatement.close();
	connection.close();
	return result > 0;

}

@Override
public boolean delete(Card card) throws SQLException {
	var connection = getConnection();
	String query = "delete from card where id = ?";
	var preparedStatement = connection.prepareStatement(query);
	preparedStatement.setInt(1, card.getId());
	int result = preparedStatement.executeUpdate();
	preparedStatement.close();
	connection.close();
	return result > 0;

}

@Override
public Card get(int id) throws SQLException {
	var connection = getConnection();
	String query = "select * from users where id = ?";
	var preparedStatement = connection.prepareStatement(query);
	preparedStatement.setInt(1, id);
	ResultSet resultSet = preparedStatement.executeQuery();
	if (resultSet.next()) {
		var card = Card.builder()
				.id(resultSet.getInt("id"))
				.number(resultSet.getString("serial_number"))
				.type(CardType.valueOf(resultSet.getString("card_type")))
				.date(resultSet.getTimestamp("expiration_date"))
				.password(resultSet.getString("password"))
				.balance(resultSet.getDouble("balance"))
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
				.number(resultSet.getString("serial_number"))
				.type(CardType.valueOf(resultSet.getString("card_type")))
				.date(resultSet.getTimestamp("expiration_date"))
				.password(resultSet.getString("password"))
				.balance(resultSet.getDouble("balance"))
				.build();
		cards.add(card);
	}
	resultSet.close();
	preparedStatement.close();
	connection.close();
	return cards;
}
}
