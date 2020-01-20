package com.rakuten.training.service;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.mockito.Mockito;

import com.rakuten.training.dal.ProductDAO;
import com.rakuten.training.domain.Product;

public class ProductServiceImplTest
{

  @Test
  public void addNewProduct_Returns_Valid_Id_When_ProductValue_GTEQ_MinValue() 
  {
	  //fail("Not yet implemented");
	  
	  //Arrange
	  ProductServiceImpl service = new ProductServiceImpl();
	  Product toBeAdded = new Product("namana", 10000, 1);   // notice if 10000 * 1 is >= 10000
	  
	  //use mockito to dynamically train mock object
	  ProductDAO mockDAO = Mockito.mock(ProductDAO.class);
	  Product saved = new Product("namana", 10000, 1);
	  saved.setId(1);
	  Mockito.when(mockDAO.save(toBeAdded)).thenReturn(saved);
	  service.setDao(mockDAO);
	  
	  //Act
	  int id = service.addNewProduct(toBeAdded);
	  
	  //Assert
	  assertTrue(id>0);
  }
  
  //gives null pointer exception due to springs and new is used to create a product but its not the convenient method to use get beans
  // requires a dao object dependency to work... so inject a dependency injection of dao object to run only the logic in service layer
  // can use in-memory-impl to inject dependency but its not reliable... dummy 

  @Test(expected = IllegalArgumentException.class)
  public void addNewProduct_Throws_When_ProductValue__LT_MinValue()
  {
	  //Arrange
	  ProductServiceImpl service = new ProductServiceImpl();
	  Product toBeAdded = new Product("namana", 9999, 1);    //notice if 9999 * 1 is >= 10000
	  
	  //Act
	  service.addNewProduct(toBeAdded);
	  
	  //Assert
	 	  
  }
  
  @Test(expected = IllegalStateException.class)
  public void removeProduct_ThrowsException_When_ProductValue_GT_MinValue()
  {
	  //Arrange
	  ProductServiceImpl service = new ProductServiceImpl();
	  Product existing = new Product("namana", 100000, 1);          // notice if 100000 * 1 is >= 10000
	  existing.setId(1);
	  
	  ProductDAO mockDAO = Mockito.mock(ProductDAO.class);
	  Mockito.when(mockDAO.findById(1)).thenReturn(existing);       //emulate DAO to check for deleteById
	  service.setDao(mockDAO);
	  int id = 1;
	  //Act
	  service.removeProduct(1);
	  //Assert
	  Mockito.verify(mockDAO).deleteById(id);
	  
  }

  @Test
  public void removeProduct_When_ProductValue_LT_MinValue()
  {
	  //Arrange
	  ProductServiceImpl service = new ProductServiceImpl();
	  Product existing = new Product("namana", 10000, 1);          // notice if 10000 * 1 is < 100000
	  existing.setId(1);
	  
	  ProductDAO mockDAO = Mockito.mock(ProductDAO.class);
	  Mockito.when(mockDAO.findById(1)).thenReturn(existing);       //emulate DAO to check for deleteById
	  
	  service.setDao(mockDAO); 
	  int id = 1;
	  //Act
	  service.removeProduct(1);
	  Mockito.verify(mockDAO).deleteById(id);
  }
  
  @Test
  public void removeProduct_When_ProductValue_Null()
  {
	  
		  ProductServiceImpl service = new ProductServiceImpl();
		  Product existing = null; 
		  
		  ProductDAO mockDAO = Mockito.mock(ProductDAO.class);
		  Mockito.when(mockDAO.findById(1)).thenReturn(existing);       
		  
		  service.setDao(mockDAO); 
		  int id = 1;
		  
		  service.removeProduct(1);
		  Mockito.verify(mockDAO, Mockito.times(0)).deleteById(id);
	  
	  
  }
  
  
  
}
