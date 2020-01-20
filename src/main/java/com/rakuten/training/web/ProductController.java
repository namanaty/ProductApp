package com.rakuten.training.web;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rakuten.training.domain.Product;
import com.rakuten.training.domain.Review;
import com.rakuten.training.service.ProductService;
import com.rakuten.training.service.ReviewService;

@RestController
public class ProductController 
{
   @Autowired
   ProductService service;
   @Autowired
   ReviewService revservice;
   
   public ReviewService getRevservice()
   {
	   return revservice;
   }

   public void setRevservice(ReviewService revservice)
   {
	    this.revservice = revservice;
   }
	   
	   
   //@RequestMapping(method = RequestMethod.GET, value = "/products")
   @GetMapping("/products")
   public List<Product> getAllProducts()
   {
	   return service.findAll();
   }
   
   @GetMapping("/products/{prodid}")
   //public Product getProductById(@PathVariable("prodid")int id)
   public ResponseEntity<Product> getProductById(@PathVariable("prodid") int id)
   {
	   Product p = service.findById(id);
	   if(p!=null)
	   {
		   return new ResponseEntity<Product>(p, HttpStatus.OK);
	   }
	   else
	   {
		   return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
	   }
	   //return p;
   }
   
   // the link is in the brackets
   @PostMapping("/products")
   public ResponseEntity<Product> addProduct(@RequestBody Product toBeAdded)    // @RequestBody is to have body of the request
   {
	   try
	   {
		   int id = service.addNewProduct(toBeAdded);           //should return 201 created message
		   HttpHeaders headers = new HttpHeaders();
		   headers.setLocation(URI.create("/products/"+id));
		   return new ResponseEntity<Product>(headers, HttpStatus.CREATED);
	   }
	   catch(IllegalArgumentException e)
	   {
		   return new ResponseEntity<Product>(HttpStatus.BAD_REQUEST);      // use try catch to find created and uncreated pages
	   }
	   
   }
   
   @DeleteMapping("/products/{prodid}")
   public ResponseEntity<Product> deleteProduct(@PathVariable("prodid") int id)
   {
	   try
	   {
		   service.removeProduct(id);
		   return new ResponseEntity<Product>(HttpStatus.NO_CONTENT);
	   }
	   catch(NullPointerException e)
	   {
		   return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
	   }
	   catch(IllegalStateException e)
	   {
		   return new ResponseEntity<Product>(HttpStatus.CONFLICT);
	   }
   }

  /*Put this code in reviewcontroller
   
    @PostMapping("/products/{prodid}/reviews")
  public ResponseEntity<Product> addReview(@RequestBody Review toBeAdded, @PathVariable int prodid)
  {
   try
   {
    int id = revservice.addNewReview(toBeAdded, prodid);           //should return 201 created message
    HttpHeaders headers = new HttpHeaders();
    headers.setLocation(URI.create("products/"+ prodid));
    return new ResponseEntity<Product>(HttpStatus.CREATED);
   }
   catch(IllegalArgumentException e)
   {
    return new ResponseEntity<Product>(HttpStatus.BAD_REQUEST);      // use try catch to find created and uncreated pages
   }
  }

  @GetMapping("/products/{prodid}/reviews")
  public ResponseEntity<List<Review>> getReviewProduct(@PathVariable int prodid)
  {
   Product p = service.findById(prodid);

    if(p!=null)
    {
     List<Review> s = p.getReviews();
     return new ResponseEntity<List<Review>>(s, HttpStatus.OK);
    }
    else
    {
     return new ResponseEntity<List<Review>>(HttpStatus.NOT_FOUND);
    }


  }*/

}
