package com.rakuten.training.web;

import org.hamcrest.CoreMatchers;

//use this import for MockMvc request builder
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rakuten.training.domain.Product;
import com.rakuten.training.service.ProductService;
import com.rakuten.training.service.ReviewService;

@RunWith(SpringRunner.class)     // to use spring to run test cases
@WebMvcTest(ProductController.class)    //To indicate the springs that we are running test cases from this ProductController class
public class ProductControllerUnitTest 
{
	@Autowired
    MockMvc mockMvc;
	
	@MockBean           // To create and mock a bean to start injection on product service.... works like mockito mock only
	ProductService service; 
	
	@MockBean
	ReviewService s;
	
	@Test
	public void getProductById_Returns_OK_With_Correct_Product_If_Found() throws Exception 
	{
	    //fail("Not yet implemented");
		
		//Arrange
		Product found = new Product("test", 123.0f , 100);
		int id = 1;
		found.setId(id);
		Mockito.when(service.findById(id)). thenReturn(found);
		
		//Act and Assert
		
		//mockMvc.perform(MockMvcRequestBuilder.get()).... instead of this method... import it above for builder request of mock mvc
		
		mockMvc.perform(MockMvcRequestBuilders.get("/products/{id}",id))
		                .andExpect(MockMvcResultMatchers.status().isOk())
		                .andExpect(MockMvcResultMatchers.jsonPath("$.id",CoreMatchers.is(id)));
	}
	
	@Test
	public void getProductById_Returns_NOT_FOUND_If_Product_Not_Found() throws Exception 
	{
	    //fail("Not yet implemented");
		
		//Arrange
		Product not_found = new Product("test", 123.0f , 100);
		int id = 1;
		not_found.setId(id);
		Mockito.when(service.findById(id)). thenReturn(null);
		
		//Act and Assert
		
		//mockMvc.perform(MockMvcRequestBuilder.get()).... instead of this method... import it above for builder request of mock mvc
		
		mockMvc.perform(MockMvcRequestBuilders.get("/products/{id}",id))
		                .andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	
	@Test
	public void addProduct_Returns_CREATED_If_Created() throws Exception
	{
		//Arrange
		Product addProduct = new Product("ananyaa", 123.0f , 100);
		int id = 1;
		Mockito.when(service.addNewProduct(Mockito.any(Product.class))).thenReturn(id);
		
		//Act and assert
		String json = toJson(addProduct); 
		mockMvc.perform(MockMvcRequestBuilders.post("/products/").contentType("application/json").content(json))
		                .andExpect(MockMvcResultMatchers.status().isCreated())
		                .andExpect(MockMvcResultMatchers.header().string("location", "/products/"+id));
		Mockito.verify(service).addNewProduct(Mockito.any(Product.class));
	}

	@Test
	public void addNewProduct_Returns_BAD_REQUEST_If_Not_Created() throws Exception
	{
		//Arrange
		Product addProduct = new Product("ananyaa", 123.0f , 100);
		int id = 1;
		Mockito.when(service.addNewProduct(Mockito.any(Product.class))).thenThrow(new IllegalArgumentException());
		
		//Act and Assert
		String json = toJson(addProduct);
		mockMvc.perform(MockMvcRequestBuilders.post("/products/").contentType("application/json").content(json))
                       .andExpect(MockMvcResultMatchers.status().isBadRequest());
		
	}
	
	@Test
	public void removeProduct_Returns_NO_CONTENT_If_No_Content_Exists() throws Exception
	{
		
		int id = 1;
		Mockito.doNothing().when(service).removeProduct(id); 
		mockMvc.perform(MockMvcRequestBuilders.delete("/products/" + id))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
		Mockito.verify(service).removeProduct(id);
	}
	
	@Test
	public void removeProduct_Returns_NOT_FOUND_If_Not_Found() throws Exception
	{
		
		int id = 1;
		Mockito.doThrow(new NullPointerException()).when(service).removeProduct(id); 
		mockMvc.perform(MockMvcRequestBuilders.delete("/products/" + id))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
		Mockito.verify(service).removeProduct(id);
	}
	
	@Test
	public void removeProduct_Returns_CONFLICT_If_Conflict_Exists() throws Exception
	{
		
		int id = 1;
		Mockito.doThrow(new IllegalStateException()).when(service).removeProduct(id); 
		mockMvc.perform(MockMvcRequestBuilders.delete("/products/" + id))
                .andExpect(MockMvcResultMatchers.status().isConflict());
		Mockito.verify(service).removeProduct(id);
	}
	
	
	//create json class with an object to convert it into a json string
    private String toJson(Object o) 
    {
	    // TODO Auto-generated method stub
    	// to convert user object to Json string and return it
		  ObjectMapper mapper = new ObjectMapper();
		  try
		  {
			  return mapper.writeValueAsString(o);
		  }
		  catch(JsonProcessingException e)
		  {
			  e.printStackTrace();
		  }
	      return null;
      
    }

	  /*@Test
	  public void removeProduct_Throws_Exception_When_Product_Removed() throws Exception
	  {
	  	int id = 1;
	  	 if(service.findById(id) == null)
	  	 {
	  		 Mockito.when(service.findById(id)). thenReturn(null);
	  	 }
	  	 else
	  	 {
	  		 mockMvc.perform(MockMvcRequestBuilders.delete)
	  	 }
	  }
	  */
}
