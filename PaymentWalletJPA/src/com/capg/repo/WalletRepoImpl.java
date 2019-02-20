package com.capg.repo;

import java.math.BigDecimal;
import javax.persistence.EntityManager;
import com.capg.beans.Customer;
import com.capg.beans.Wallet;
import com.capg.util.FactoryUtil;

public class WalletRepoImpl implements WalletRepo{
	
	FactoryUtil conn =new FactoryUtil();

	public WalletRepoImpl() {
		super();
		
	}

	
		@Override
		public boolean save(Customer customer) {
			
			EntityManager em = conn.getUtil();
			
			Customer cust = new Customer();
			Wallet wal = new Wallet();
			em.getTransaction().begin();
			
			cust.setName(customer.getName());
			cust.setMobileno(customer.getMobileno());
			wal.setBalance(customer.getWallet().getBalance());
			
			em.persist(customer);
			em.getTransaction().commit();
			em.close();
			return true;
			
		}
		
		@Override
		public Customer findCustomer(String mobileno){
			
			try{
			
				
				EntityManager em = conn.getUtil();
				em.getTransaction().begin();
				Customer cust = em.find(Customer.class,mobileno);
				em.close();
				return cust;

				}catch(Exception se){
					
					System.out.println(se);
				}
			
				return null;
		}
			
				
		@Override
		public void update(String mobileno,BigDecimal amount) {
			
			
			try {
				EntityManager em = conn.getUtil();
				em.getTransaction().begin();
				Customer cust = em.find(Customer.class,mobileno);
				
				cust.setWallet(new Wallet(amount));
				em.getTransaction().commit();
				em.close();
			} catch (Exception e) {

				System.out.println(e);
			}
			
		}
		
}
