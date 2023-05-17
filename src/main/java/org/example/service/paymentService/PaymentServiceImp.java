package org.example.service.paymentService;

import org.example.model.Payment;
import org.example.model.Payment;

import java.sql.SQLException;
import java.util.List;

public class PaymentServiceImp implements PaymentService{
@Override
public int create(Payment payment) throws SQLException {
	return 0;
}

@Override
public boolean update(Payment payment) throws SQLException {
	return false;
}

@Override
public boolean delete(Payment payment) throws SQLException {
	return false;
}

@Override
public Payment get(int id) throws SQLException {
	return null;
}

@Override
public List<Payment> getAll() throws SQLException {
	return null;
}
}
