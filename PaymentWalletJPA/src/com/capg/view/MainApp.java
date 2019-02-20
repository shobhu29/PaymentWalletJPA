package com.capg.view;

import java.math.BigDecimal;
import java.util.Scanner;

import com.capg.beans.Customer;
import com.capg.beans.Wallet;
import com.capg.exceptions.DuplicateMobileNoException;
import com.capg.exceptions.InsufficientBalanceException;
import com.capg.exceptions.InvalidInputGivenException;
import com.capg.service.WalletService;
import com.capg.service.WalletServiceImpl;

public class MainApp {
	static Scanner sc;
	public static void main(String[] args) throws Exception {
		
		WalletService service=new WalletServiceImpl();
		
		
		 sc=new Scanner(System.in);
		int ch=0;
		
		do{
			System.out.println("________Payment Wallet________\n");
			System.out.println(" 1.Create Account");
			System.out.println(" 2.Show Balance");
			System.out.println(" 3.Deposit Amount");
			System.out.println(" 4.Withdraw Amount");
			System.out.println(" 5.Fund Transfer");			
			System.out.println("\nEnter Your Choice :\n");
			ch=sc.nextInt();
			switch(ch)
			{
			case 1:
				System.out.println("Please Enter Your Mobile Number");
				String custMobNo=sc.next();
				System.out.println("Please Enter Your Name");
				String custName=sc.next();
				System.out.println("Please Enter Money you wanted to add");
				BigDecimal money=sc.nextBigDecimal();
						
				try {
					if(service.ValidateMobileno(custMobNo) && service.ValidateName(custName) &&service.ValidateBalance(money)){
						Customer cust = null;
						try {
							cust = service.createAccount(custName, custMobNo, money);
						
						} catch (DuplicateMobileNoException d){
							
							System.out.println(d.getMessage());
						}
						catch (InvalidInputGivenException e) {

							System.out.println(e.getMessage());
							
						}
						
						
						if(cust!=null)
							System.out.println(cust);
						
						
					} else
						try {
							throw new InvalidInputGivenException("Enter Details of Required Format");
						} catch (InvalidInputGivenException e) {

							System.out.println(e.getMessage());
							
						}
				} catch (InvalidInputGivenException e) {

					System.out.println(e.getMessage());
				}
				
				
				break;
			
			case 2:
				System.out.println("Please Enter Your Mobile Number");
				String custMobNo1=sc.next();
				try {
					if(service.ValidateMobileno(custMobNo1)){
						Customer customer=service.showBalance(custMobNo1);
						
							Wallet w=customer.getWallet();
							System.out.println("Hi "+customer.getName()+", your balance is "+w.getBalance() +" !!\n");
									
						}
					else
						throw new InvalidInputGivenException("Enter Ten Digit Number starting with 6/7/8/9  ");
				} catch (InvalidInputGivenException e) {

					System.out.println(e.getMessage());
				}
				
				break;
				
			
			case 3:
				System.out.println("Please Enter Your Mobile Number");
				custMobNo1=sc.next();
				try {
					if(service.ValidateMobileno(custMobNo1)){
					 Customer customer=service.showBalance(custMobNo1);
					 Wallet w=customer.getWallet();
					 System.out.println("Hi "+customer.getName()+", your current balance is "+w.getBalance() +" !!");
					 System.out.println("Please Enter the amount you want to deposit ");
					 BigDecimal dep=sc.nextBigDecimal();
					 customer=service.depositAmount(custMobNo1, dep);
					 w=customer.getWallet();
					 System.out.println("Hi "+customer.getName()+", your updated balance is "+w.getBalance() +" !!");
					}
					else
						throw new InvalidInputGivenException("Enter  Ten Digit Mobile Number  ");
				} catch (InvalidInputGivenException e) {

					System.out.println(e.getMessage());
				}
					
				 break;
				 
			case 4:
				System.out.println("Please Enter Your Mobile Number");
				custMobNo1=sc.next();
				try {
					if(service.ValidateMobileno(custMobNo1)){
					 Customer customer=service.showBalance(custMobNo1);
					 Wallet w=customer.getWallet();
					 System.out.println("Hi "+customer.getName()+", your current balance is "+w.getBalance() +"  !!");
					 System.out.println("Please Enter the amount you want to withdraw ");
					 BigDecimal withdraw=sc.nextBigDecimal();
					 try {
						customer=service.withdrawAmount(custMobNo1, withdraw);
					} catch (InsufficientBalanceException e) {

						System.out.println(e.getMessage());
					}
					 w=customer.getWallet();
					 
					 System.out.println("Hi "+customer.getName()+", your updated balance is "+w.getBalance() +"  !!");
					}
					else 
						throw new InvalidInputGivenException("Enter Ten Digit Mobile Number  ");
				} catch (InvalidInputGivenException e) {

					System.out.println(e.getMessage());
				}
				 break;
				 
				 
			case 5:
				System.out.println("Please Enter Your Mobile Number");
				String custMobNoS=sc.next();
				try {
					if(service.ValidateMobileno(custMobNoS)){
						Customer customer=service.showBalance(custMobNoS);
						Wallet w=customer.getWallet();
						System.out.println("Hi "+customer.getName()+", your current balance is "+w.getBalance() +"  !!\n");
						System.out.println("Please Enter the mobile number  you want to transfer money ");
						String custMobNoT=sc.next();
						if(service.ValidateMobileno(custMobNoT)){
						System.out.println("Please Enter amount to be transferred ");
						
						BigDecimal ft=sc.nextBigDecimal();
					 
						customer=service.fundTransfer(custMobNoS, custMobNoT, ft);
						w=customer.getWallet();
						System.out.println("Hi "+customer.getName()+", your updated balance is "+w.getBalance() +"  !!");
					} }
					else
						try {
							throw new InvalidInputGivenException("Enter Ten Digit Mobile Number  ");
						} catch (InvalidInputGivenException e) {
						
							System.out.println(e.getMessage());
						}
				} catch (InvalidInputGivenException e) {
					
					System.out.println(e.getMessage());
				}
				
				 break;
				 
			}
			
		}while(ch!=6);
		
System.out.println("Thank You !!");
	}

}
