package com.capg.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class FactoryUtil {

	public EntityManager getUtil(){
		
		try{
			
			EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Shobhit");
			EntityManager entitymanager = emfactory.createEntityManager();
			
			return entitymanager;
			
		}catch(Exception e){
		
			System.out.println(e);
		}
		return null;
	}
}
