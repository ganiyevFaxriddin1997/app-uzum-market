package org.example.service.card;

import org.example.entities.Card;
import org.example.service.BaseService;

import java.sql.SQLException;
import java.util.List;

public interface CardService extends BaseService<Card> {
Card getCardBySerialNumber(String serialNumber) throws SQLException;
Card getByPassword(int password) throws SQLException;
List<Card> getByOwnerId(int ownerId) throws SQLException;
}
