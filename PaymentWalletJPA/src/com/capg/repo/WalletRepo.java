package com.capg.repo;

import java.math.BigDecimal;
import java.sql.SQLException;

import com.capg.beans.Customer;

public interface WalletRepo {

	public boolean save(Customer customer);
	public Customer findCustomer(String mobileno);
	public void update(String mobileno,BigDecimal amount);

}
