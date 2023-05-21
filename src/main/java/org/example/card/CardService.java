package org.example.card;

import org.example.entities.Card;
import org.example.service.BaseService;

import java.sql.SQLException;

public interface CardService extends BaseService<Card> {
Card getCardBySerialNumber(String serialNumber) throws SQLException;
Card getByPassword(int password) throws SQLException;
Card getByOwnerId(int ownerId) throws SQLException;
}
