package com.rakuten.training.web;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rakuten.training.domain.Product;
import com.rakuten.training.domain.Review;
import com.rakuten.training.service.NoSuchProductException;
import com.rakuten.training.service.ProductService;
import com.rakuten.training.service.ReviewService;

@RestController
public class ReviewController
{
	
   @Autowired
   ReviewService reviewService;
   
   @Autowired
   ProductService productService;

   @GetMapping("products/{id}/reviews")
   public ResponseEntity<List<Review>> getReviewsForProduct(@PathVariable("id") int productId)
   {
	   Product p = productService.findById(productId);
	   List<Review> reviews = reviewService.getByProductId(productId);
	   if(p == null)
	   {
		   return new ResponseEntity<List<Review>> (reviews,HttpStatus.NOT_FOUND);
	   }
	  
	   return new ResponseEntity<List<Review>> (reviews,HttpStatus.OK);
	   
   }
   
   @PostMapping("products/{id}/reviews")
   public ResponseEntity<Review> addReviewToProduct(@PathVariable("id") int productId, @RequestBody Review reviews)
   {
	   try
	   {
		      int id = reviewService.addReviewToProduct(productId, reviews);
		      HttpHeaders headers = new HttpHeaders();
		      headers.setLocation(URI.create("/products/" + productId + "/reviews/" + id));
		      return new ResponseEntity<Review>(headers, HttpStatus.CREATED);
	   } 
	   
	   catch(NoSuchProductException e)
	   {
		      return new ResponseEntity<Review>(HttpStatus.NOT_FOUND);
	   }
   }
	   
   

}
