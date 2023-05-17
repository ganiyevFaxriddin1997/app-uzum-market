package org.example.service.cardService;

import org.example.model.Card;

import java.sql.SQLException;
import java.util.List;

public class CardServiceImp implements CardService{

@Override
public int create(Card card) throws SQLException {
	return 0;
}

@Override
public boolean update(Card card) throws SQLException {
	return false;
}

@Override
public boolean delete(Card card) throws SQLException {
	return false;
}

@Override
public Card get(int id) throws SQLException {
	return null;
}

@Override
public List<Card> getAll() throws SQLException {
	return null;
}
}
