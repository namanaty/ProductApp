package com.rakuten.training.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rakuten.training.dal.ProductDAO;
import com.rakuten.training.domain.Product;
import com.rakuten.training.domain.Review;

@Service
@Transactional
public class ProductServiceImpl implements ProductService
{
      
	ProductDAO dao;  // = new ProductDAOInMemImpl();    
	
	// add the setter to use xml metadata configuration
	
	@Autowired
	public void setDao(ProductDAO dao)
	{
         this.dao = dao;
    }
	  @Override
	  public int addNewProduct(Product toBeAdded)
	  {
	    // TODO Auto-generated method stub
		  if(toBeAdded.getPrice() * toBeAdded.getQoh() >= 10000)
		  {
			 Product added = dao.save(toBeAdded); 
			 return added.getId();
		  }
		  else
		  {
			  throw new IllegalArgumentException("The monetory value of product is < 10000.");
		  }
	  }
	
	  @Override
	  public void removeProduct(int id)
	  {
	    // TODO Auto-generated method stub
		  //try
			  //{
				  Product existing = dao.findById(id);
				  if(existing != null)
				  {
					  if(existing.getPrice() * existing.getQoh() >= 1000000)
					  {
						  throw new IllegalStateException("Monetory value > 100000. Cannot delete.");
					  }
					  else
					  {
						  dao.deleteById(id);
					  }
				  }
				  else
				  {
					  throw new NullPointerException("Not yet implemented");
				  }
			  //}
		/*catch(NullPointerException e)
		{
			System.out.println("Not yet implemented");
		}*/
	    
	  }
	
	  @Override
	  public List<Product> findAll()
	  {
	    // TODO Auto-generated method stub
	     return dao.findAll();
	  }
	
	  @Override
	  public Product findById(int id) 
	  {
	    // TODO Auto-generated method stub
		  
	    return dao.findById(id);
	  }
		
	  public List<Review> getReviews(int prodid)
	  {
		  Product p= dao.findById(prodid);
		  return p.getReviews();
	  }
}
