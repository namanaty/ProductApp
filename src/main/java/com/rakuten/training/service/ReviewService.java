package com.rakuten.training.service;

import java.util.List;

import com.rakuten.training.domain.Review;

public interface ReviewService 
{

  /*int addNewReview(Review toBeAdded,int productId);
  void removeReview(int id);
  List<Review> findAll();
  Review findById(int id);*/
	
	public int addReviewToProduct(int productId,Review toBeAdded);
	public List<Review> getByProductId(int productId);

}
