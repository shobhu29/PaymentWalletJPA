package com.capg.service;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.capg.beans.Customer;
import com.capg.beans.Wallet;
import com.capg.exceptions.DuplicateMobileNoException;
import com.capg.exceptions.InsufficientBalanceException;
import com.capg.exceptions.InvalidInputGivenException;
import com.capg.repo.WalletRepo;
import com.capg.repo.WalletRepoImpl;

public class WalletServiceImpl implements WalletService {
	
	
	WalletRepo wrepo;

	/* (non-Javadoc)
	 * @see com.capg.service.WalletService#createAccount(java.lang.String, java.lang.String, java.math.BigDecimal)
	 */
	
	public WalletServiceImpl() {
		// TODO Auto-generated constructor stub
	
		wrepo = new WalletRepoImpl();
	
	}
	
	
	@Override
	public Customer createAccount(String name,String mobileno,BigDecimal balance) throws InvalidInputGivenException,DuplicateMobileNoException {
		
	//	if(wrepo.findCustomer(mobileno).getMobileno().equals(mobileno))	
			
	if(wrepo.findCustomer(mobileno) == null){							
		
		Wallet wallet = new Wallet(balance);
		Customer customer=new Customer(name, mobileno, wallet);
		
		if(wrepo.save(customer)){
			return customer;
			}
		}
		throw new DuplicateMobileNoException("Mobile number Already Exists");
	
	}

	/* (non-Javadoc)
	 * @see com.capg.service.WalletService#showBalance(java.lang.String)
	 */
	
	@Override
	public Customer showBalance(String mobileno) throws InvalidInputGivenException {
		
		Customer customer = wrepo.findCustomer(mobileno);
		if(customer == null)
			throw new InvalidInputGivenException("Mobile number is Not Registered");
		else
			return customer;
	}
	
	/* (non-Javadoc)
	 * @see com.capg.service.WalletService#fundTransfer(java.lang.String, java.lang.String)
	 */
	
	@Override
	public Customer fundTransfer(String sourceMobileno,String targetMobileno,BigDecimal balance) throws InvalidInputGivenException{
		
		Customer customer1=wrepo.findCustomer(sourceMobileno);
		if(customer1==null)
			throw new InvalidInputGivenException("Mobile number is Not Registered");
		
		Wallet wallet1=customer1.getWallet();
		BigDecimal currentbal = wallet1.getBalance();
		
		Customer customer2=wrepo.findCustomer(targetMobileno);
		
		if(customer2==null)
			throw new InvalidInputGivenException("Mobile number is Not Registered");
		
		Wallet wallet2=customer2.getWallet();
		BigDecimal currentbal2=wallet2.getBalance();
		
		if(currentbal.compareTo(balance)<0)
			throw new InvalidInputGivenException("Your account balance is less than amount you are transferring");
		
		currentbal2= currentbal2.add(balance);
		
		/*currentbal= currentbal.subtract(balance);
		wallet1.setBalance(currentbal);
		customer1.setWallet(wallet1);
		wrepo.save(customer1);
		
		wallet2.setBalance(currentbal2);
		customer2.setWallet(wallet2);
		wrepo.save(customer2);
		*/
		
		BigDecimal bd = customer1.getWallet().getBalance().subtract(balance);
		BigDecimal bd1 = customer2.getWallet().getBalance().add(balance);
		
		wrepo.update(sourceMobileno,bd);
		wrepo.update(targetMobileno,bd1);
		
		Customer customer3 = wrepo.findCustomer(sourceMobileno);
		
		return customer3;
	
	}
	
	/* (non-Javadoc)
	 * @see com.capg.service.WalletService#depositAmount(java.lang.String, java.math.BigDecimal)
	 */
	
	
	@Override
	public Customer depositAmount(String mobileno,BigDecimal balance) throws InvalidInputGivenException{
		
		Customer customer = wrepo.findCustomer(mobileno);
		if(customer == null)
			throw new InvalidInputGivenException("Mobile number is Not Registered");
		/*BigDecimal currentbal = wallet.getBalance();
		
		BigDecimal newbal= currentbal.add(balance);
		wallet.setBalance(newbal);
		customer.setWallet(wallet); */
		
		BigDecimal bd = customer.getWallet().getBalance().add(balance);
		wrepo.update(mobileno, bd);
		Customer customer1 = wrepo.findCustomer(mobileno);
	
		return customer1;
}
	
	/* (non-Javadoc)
	 * @see com.capg.service.WalletService#withdrawAmount(java.lang.String, java.math.BigDecimal)
	 */
	
	@Override
	public Customer withdrawAmount(String mobileno,BigDecimal balance) throws InsufficientBalanceException,InvalidInputGivenException {
		
		Customer customer = wrepo.findCustomer(mobileno);
		if(customer == null)
			throw new InvalidInputGivenException("Mobile number is Not Registered");
				
		Wallet wallet = customer.getWallet();
		BigDecimal currentbal = wallet.getBalance();
		
		
		if(currentbal.compareTo(balance)>0){
		/*
		BigDecimal newbal = currentbal.subtract(balance);
		wallet.setBalance(newbal);
		customer.setWallet(wallet);
		wrepo.save(customer);
		
		Customer customer1 = wrepo.findCustomer(mobileno);
		
		return customer1; */
			BigDecimal bd = customer.getWallet().getBalance().subtract(balance);
			wrepo.update(mobileno, bd);
			Customer customer1 = wrepo.findCustomer(mobileno);
			
			
			return customer1;
			
	}
		else
			throw new InsufficientBalanceException("Current Balance is less than Withdraw Balance");
	}

	@Override
	public boolean ValidateName(String name) throws InvalidInputGivenException {
		// TODO Auto-generated method stub
		if(name == null)
			throw new InvalidInputGivenException("Customer Name Field cannot be empty");
		Pattern pat=Pattern.compile("[A-Za-z ]{1,20}");
		Matcher mat=pat.matcher(name);
		if(!(mat.matches()))
			throw new InvalidInputGivenException("\nCustomer Name details are not valid");
		
		return mat.matches();
	}

	@Override
	public boolean ValidateMobileno(String mobileno) throws InvalidInputGivenException {
		// TODO Auto-generated method stub
		if(mobileno == null)
			throw new InvalidInputGivenException("Mobile Number Field cannot be empty");
		Pattern pat=Pattern.compile("[6-9]{1}[0-9]{9}");
		Matcher mat=pat.matcher(mobileno);
		if(!(mat.matches()))
			throw new InvalidInputGivenException("\nMobile Number details are not valid");

		return mat.matches();
	}

	@Override
	public boolean ValidateBalance(BigDecimal balance) throws InvalidInputGivenException {
		// TODO Auto-generated method stub
		if(balance == null)
			throw new InvalidInputGivenException("Balance is not valid");
		Pattern pat=Pattern.compile("[1-9]{1}[0-9]{1,5}");
		Matcher mat=pat.matcher(String.valueOf(balance));
		
		if(!(mat.matches()))
			throw new InvalidInputGivenException("\nAmount details are not valid");
		
		return mat.matches();
	}
	
	
	
	
}

 	
	