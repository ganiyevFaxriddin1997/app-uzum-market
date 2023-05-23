package org.example.service.payment;

import org.example.entities.Order;
import org.example.entities.Payment;
import org.example.service.BaseService;

import java.sql.SQLException;
import java.util.List;

public interface PaymentService extends BaseService<Payment> {
    boolean createPaymentOrder(int paymentId, int orderId) throws SQLException;

    boolean takeMoneyFromUserToProductOwner(String serialNumber, double price, int productOwnerId,
                                            int amountInBasket, int productId,
                                            int systemOwnerId) throws SQLException;
    List<Integer> getOrderFromPaymentOrder(int paymentId) throws SQLException;
    List<Integer> getPaymentIds(int ownerId) throws SQLException;
    List<Payment> getByCardId(int cardId) throws SQLException;
    List<Order> getOrderFromPO(int paymentId) throws SQLException;

}
