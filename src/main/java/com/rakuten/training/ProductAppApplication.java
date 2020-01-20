package com.rakuten.training;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.rakuten.training.dal.ProductDAO;
import com.rakuten.training.domain.Product;

@SpringBootApplication
public class ProductAppApplication 
{

	public static void main(String[] args) 
	{
		//ApplicationContext springContainer = SpringApplication.run(ProductAppApplication.class, args);
		SpringApplication.run(ProductAppApplication.class, args);
	    // ProductConsoleUI ui = springContainer.getBean(ProductConsoleUI.class);
	
	    // ui.createProductWithUI();
	    // ui.findById(id);
	    // ui.deleteById(id);
	
	    /*ReviewDAO reviewDAO = springContainer.getBean(ReviewDAO.class);
	    Review sample = new Review("self", "this is good", 5);
	    Review saved = reviewDAO.save(sample, 1);
	    System.out.println("Created Review ID with : " + saved.getId() ); */
	
	    /*ProductDAO productDAO = springContainer.getBean(ProductDAO.class);
	    Product p = productDAO.findById(3);
	    List<Product> l = productDAO.findAll();
	    for(Product i : l)
	    {
	    	System.out.println(i.getName() + " " );
	    }
	    //System.out.println(l);
	    System.out.println(p.getName());
	    System.out.println("This product has : " + p.getReviews().size() + "reviews"); */

    // productDAO.deleteById(id);
  }
}
