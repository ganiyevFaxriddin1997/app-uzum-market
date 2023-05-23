package org.example.service.payment;

import org.example.entities.Order;
import org.example.entities.Payment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentServiceImp implements PaymentService {
    @Override
    public int create(Payment payment) throws SQLException {
        var connection = getConnection();
        String query = "select add_payment(?,?)";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, payment.getCardId());
        preparedStatement.setDouble(2, payment.getPrice());
        var resultSet = preparedStatement.executeQuery();
        var result = -1;
        if (resultSet.next()) {
            result = resultSet.getInt(1);
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return result;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        var connection = getConnection();
        String query = "delete from payment where id = ?";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        int result = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        return result > 0;
    }

    @Override
    public Payment get(int id) throws SQLException {
        var connection = getConnection();
        String query = "select * from payment where id = ?";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            var payment = Payment.builder()
                    .id(resultSet.getInt("id"))
                    .cardId(resultSet.getInt("card_id"))
                    .price(resultSet.getDouble("price"))
                    .createdDate(resultSet.getTimestamp("created_date"))
                    .build();
            return payment;
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return null;
    }

    @Override
    public List<Payment> getAll() throws SQLException {
        List<Payment> payments = new ArrayList<>();
        var connection = getConnection();
        String query = "select * from payment";
        var preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            var payment = Payment.builder()
                    .id(resultSet.getInt("id"))
                    .cardId(resultSet.getInt("card_id"))
                    .price(resultSet.getDouble("price"))
                    .createdDate(resultSet.getTimestamp("created_date"))
                    .build();
            payments.add(payment);
        }
        preparedStatement.close();
        connection.close();
        return payments;
    }

    @Override
    public boolean update(int id, Payment payment) throws SQLException {
        var connection = getConnection();
        String query = "update payment set card_id = ?, price = ?, created_date = ? where id = ?";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, payment.getId());
        preparedStatement.setDouble(2, payment.getPrice());
        preparedStatement.setTimestamp(3, payment.getCreatedDate());
        preparedStatement.setInt(4, id);
        int result = preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
        return result > 0;
    }

    @Override
    public boolean createPaymentOrder(int paymentId, int orderId) throws SQLException {
        var connection = getConnection();
        String query = "select create_payment_order(?,?)";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, paymentId);
        preparedStatement.setInt(2, orderId);
        var resultSet = preparedStatement.executeQuery();
        var result = false;
        if (resultSet.next()) {
            result = resultSet.getBoolean(1);
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return result;
    }

    @Override
    public boolean takeMoneyFromUserToProductOwner(String serialNumber,
                                                   double price,
                                                   int productOwnerId,
                                                   int amountInBasket,
                                                   int productId,
                                                   int systemOwnerId) throws SQLException {
        var connection = getConnection();
        String query = "select take_send_money(?,?,?,?,?,?)";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, serialNumber);
        preparedStatement.setDouble(2, price);
        preparedStatement.setInt(3, productOwnerId);
        preparedStatement.setInt(4, amountInBasket);
        preparedStatement.setInt(5, productId);
        preparedStatement.setInt(6, systemOwnerId);
        var resultSet = preparedStatement.executeQuery();
        var result = false;
        if (resultSet.next()) {
            result = resultSet.getBoolean(1);
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return result;

    }

    @Override
    public List<Integer> getOrderFromPaymentOrder(int paymentId) throws SQLException {
        List<Integer> orderIds = new ArrayList<>();
        var connection = getConnection();
        String query = "select order_id from payment_order where payment_id = ?";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, paymentId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            var result = resultSet.getInt(1);
            orderIds.add(result);
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return orderIds;
    }

    @Override
    public List<Integer> getPaymentIds(int ownerId) throws SQLException {
        List<Integer> paymentIds = new ArrayList<>();
        var connection = getConnection();
        String query = "select id from payment where card_id = (select id from card where owner_id = ?)";
        var preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        var result = 0;
        while (resultSet.next()) {
            result = resultSet.getInt(1);
            paymentIds.add(result);
        }
        preparedStatement.close();
        connection.close();
        return paymentIds;
    }

    @Override
    public List<Payment> getByCardId(int cardId) throws SQLException {
        List<Payment> payments = new ArrayList<>();
        var connection = getConnection();
        String query = "select * from payment where card_id = ?";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, cardId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            var payment = Payment.builder()
                    .id(resultSet.getInt("id"))
                    .cardId(resultSet.getInt("card_id"))
                    .price(resultSet.getDouble("price"))
                    .createdDate(resultSet.getTimestamp("created_date"))
                    .build();
            payments.add(payment);
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return payments;
    }

    @Override
    public List<Order> getOrderFromPO(int paymentId) throws SQLException {
        List<Order> orders = new ArrayList<>();
        var connection = getConnection();
        String query = "select o.id, o.user_id, o.product_id, o.amount from orders o\n" +
                "join payment_order po on o.id = po.order_id where po.payment_id = ?";
        var preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, paymentId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            var order = Order.builder()
                    .id(resultSet.getInt("id"))
                    .userId(resultSet.getInt("user_id"))
                    .productId(resultSet.getInt("product_id"))
                    .amountProduct(resultSet.getInt("amount"))
                    .build();
            orders.add(order);
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return orders;
    }
}
