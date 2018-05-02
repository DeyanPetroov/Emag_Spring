package com.emag.model.dao;

import java.time.LocalDateTime;
import java.util.TreeMap;

import com.emag.model.Review;

public interface IReviewDAO {

	void addReview(Review review) throws Exception;
	void removeReviewByID(long reviewID) throws Exception;
	Review getReviewByID(long reviewID) throws Exception;
	TreeMap<LocalDateTime, Review> getAllReviewsForUser(long userID) throws Exception;
	TreeMap<LocalDateTime, Review> getAllReviewsForProduct(long productID) throws Exception;
}
