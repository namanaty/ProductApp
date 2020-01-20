
package com.rakuten.training.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rakuten.training.dal.ProductDAO;
import com.rakuten.training.dal.ReviewDAO;
import com.rakuten.training.domain.Product;
import com.rakuten.training.domain.Review;

	@Service
	@Transactional
	public class ReviewServiceImpl implements ReviewService 
	{

		@Autowired
		ReviewDAO reviewDAO;     // = new ReviewDAOInMemImpl();
		@Autowired
        ProductDAO prodDAO;
		
		@Override
		public int addReviewToProduct(int productId, Review toBeAdded)
		{
			Product product = prodDAO.findById(productId);
			if(product == null)
			{
				throw new NoSuchProductException();
			}
			
		    toBeAdded.setProduct(product);
		    Review added= reviewDAO.save(toBeAdded);
		    return added.getId();
			
		}
		
        @Override
        public List<Review> getByProductId(int productId)
        {
        	return reviewDAO.findByProductId(productId);
        }
		
		
}

