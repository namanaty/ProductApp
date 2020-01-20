package com.rakuten.training.dal;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.rakuten.training.domain.Product;

@Repository
@Transactional
public class ProductDAOJpaImpl implements ProductDAO
{
      @Autowired
      EntityManager em;
      
	  @Override
	  public Product save(Product toBeSaved) 
	  {
	    // TODO Auto-generated method stub
     	  em.persist(toBeSaved);
	      return toBeSaved;                 // returns detached state of the object
	  }
	
	  @Override
	  public Product findById(int id) 
	  {
	    // TODO Auto-generated method stub
		  Product p = em.find(Product.class,  id);
		  //System.out.println("This product has : " + p.getReviews().size() + "reviews"); 
		  
		  return p;
	    //return null;
	  }
	
	  @Override
	  public List<Product> findAll() 
	  {
	    // TODO Auto-generated method stub
		  Query q = em.createQuery("select p from Product as p");
		  List<Product> all = q.getResultList();
	      return all;
	  }

  @Override
  public void deleteById(int id) {
    // TODO Auto-generated method stub
    // Product p = em.find(Product.class,  id);          // used only to get that p into managed
    // state... its data is not used anywhere
    /*Product p = em.getReference(Product.class,  id);      // optimised method
    em.remove(p);*/

    Query q = em.createQuery("delete from Product as p where p.id =:idParam");
    q.setParameter("idParam", id);               //data manipulation in dml operation
    q.executeUpdate();

    // cascaded works with both types
  }
}
