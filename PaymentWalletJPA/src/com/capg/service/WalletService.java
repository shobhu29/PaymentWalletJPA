package com.capg.service;

import java.math.BigDecimal;
import com.capg.beans.Customer;
import com.capg.exceptions.DuplicateMobileNoException;
import com.capg.exceptions.InsufficientBalanceException;
import com.capg.exceptions.InvalidInputGivenException;

public interface WalletService {

	public Customer createAccount(String name, String mobileno, BigDecimal balance) throws InvalidInputGivenException,DuplicateMobileNoException;

	public Customer showBalance(String mobileno) throws InvalidInputGivenException;

	public Customer fundTransfer(String sourcemobileno, String targetmobileno, BigDecimal balance) throws InvalidInputGivenException;

	public Customer depositAmount(String mobileno, BigDecimal amount) throws InvalidInputGivenException;

	public Customer withdrawAmount(String mobileno, BigDecimal amount) throws InsufficientBalanceException,InvalidInputGivenException;
	
	public boolean ValidateName(String name) throws InvalidInputGivenException;

	public boolean ValidateMobileno(String mobileno) throws InvalidInputGivenException;

	public boolean ValidateBalance(BigDecimal balance) throws InvalidInputGivenException;

}