package com.capg.test;

import static org.junit.Assert.*;
import java.math.BigDecimal;
import org.junit.Test;
import com.capg.beans.Customer;
import com.capg.beans.Wallet;
import com.capg.exceptions.DuplicateMobileNoException;
import com.capg.exceptions.InsufficientBalanceException;
import com.capg.exceptions.InvalidInputGivenException;
import com.capg.repo.WalletRepo;
import com.capg.service.WalletService;
import com.capg.service.WalletServiceImpl;

public class TestClass {
	
	WalletRepo wrepo;

	
	
	@Test(expected = com.capg.exceptions.DuplicateMobileNoException.class)
	public void whenthemobilenumberalreadyexist() throws DuplicateMobileNoException,InvalidInputGivenException {
		
		String mobileno = "9711377939";
		String name = "shobhit";
		Wallet wallet = new Wallet(BigDecimal.valueOf(2000));
		BigDecimal balance = wallet.getBalance();
		WalletService ws = new WalletServiceImpl();
		ws.createAccount(name, mobileno,balance);
		ws.createAccount(name, mobileno,balance);
	}

	@Test
	public void whenthecurrentbalanceislessthanwithdrawlbalance() throws InvalidInputGivenException,InsufficientBalanceException{
		WalletService ws = new WalletServiceImpl();
		ws.withdrawAmount("9711377939", BigDecimal.valueOf(1500));
		
	}
	
	@Test(expected = com.capg.exceptions.InvalidInputGivenException.class)
	public void whenthetargetmobilenumberisnotpresentinfundtransfer() throws InvalidInputGivenException{
		
		WalletService ws = new WalletServiceImpl();
		ws.fundTransfer("9711377939","9711292298",BigDecimal.valueOf(500));
	}
	
	@Test(expected = com.capg.exceptions.InvalidInputGivenException.class)
	public void whenthesourcemobilenumberisnotpresentinfundtransfer() throws InvalidInputGivenException{
		
		WalletService ws = new WalletServiceImpl();
		ws.fundTransfer("8750478489","9711292298",BigDecimal.valueOf(500));
	}
	
	@Test(expected = com.capg.exceptions.InvalidInputGivenException.class)
	public void whenthemobilenumberisnotpresentindepositamount() throws InvalidInputGivenException{
		
		WalletService ws = new WalletServiceImpl();
		ws.depositAmount("9711377939",BigDecimal.valueOf(500));
	}
	
	@Test
	public void whenallthedetailsarevalid() throws InvalidInputGivenException,DuplicateMobileNoException{
		
		Wallet wallet = new Wallet(BigDecimal.valueOf(2000));
		BigDecimal balance = wallet.getBalance();
		WalletService ws = new WalletServiceImpl();
		Customer cust = ws.createAccount("santosh","9811210939",balance);
		assertEquals(cust.getMobileno(),"9811210939");
	}

	
	@Test(expected = com.capg.exceptions.InvalidInputGivenException.class)
	public void whenthecustomernameisnotvalid() throws InvalidInputGivenException {
		
		String name="aaaa12@";
		WalletService ws = new WalletServiceImpl();
		boolean result = ws.ValidateName(name);
		assertEquals(false, result);
	}
	
	@Test(expected = com.capg.exceptions.InvalidInputGivenException.class)
	public void whenthecustomermobilenoisnotvalid() throws InvalidInputGivenException {
		String name="0123456789";
		WalletService ws = new WalletServiceImpl();
		boolean result=ws.ValidateMobileno(name);
		assertEquals(false, result);	
	}
	
	@Test
	public void whenthecustomermobilenumberisvalid() throws InvalidInputGivenException {
		String name="9540612658";
		WalletService ws = new WalletServiceImpl();
		boolean result = ws.ValidateMobileno(name);
		assertEquals(true, result);	
	}	
	
	@Test
	public void whentheamountgivenisvalid() throws InvalidInputGivenException {
		BigDecimal balance = new BigDecimal(10000);
		WalletService ws = new WalletServiceImpl();
		boolean result = ws.ValidateBalance(balance);
		assertEquals(true, result);
	}
	
//	@Test
//	public void whentheamountgivenisnotvalid() throws InvalidInputGivenException {
//		Wallet wallet = new Wallet(BigDecimal.valueOf(0));
//		BigDecimal balance = wallet.getBalance();
//		WalletService ws = new WalletServiceImpl();
//		ws.ValidateBalance(balance);
//	}
//	
	
	
}
