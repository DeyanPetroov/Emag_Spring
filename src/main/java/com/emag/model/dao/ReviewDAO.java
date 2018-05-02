package com.emag.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

import com.emag.model.Review;

@Component
public class ReviewDAO implements IReviewDAO {
	
	private static final String ADD_REVIEW = "INSERT INTO reviews(raiting, date, review_text, user_id, product_id) VALUES (?, ?, ?, ?, ?)";
	private static final String DELETE_REVIEW_BY_ID = "DELETE FROM review WHERE review_id = ?";
	private static final String GET_ALL_REVIEWS_FOR_USER = 
			 "SELECT r.review_id, r.raiting, r.date, r.review_text, p.name FROM reviews AS r "
			+"JOIN products AS p " 
			+"ON p.product_id = r.product_id AND user_id = ?";
	private static final String GET_REVIEW_BY_ID = 
			 "SELECT r.review_id, r.raiting, r.date, r.review_text, u.username, p.name FROM reviews AS r"
			+"JOIN users AS u" + "ON r.user_id = u.user_id" + "JOIN  products AS p"
			+"ON r.product_id = p.product_id AND r.review_id = ?";
	private static final String GET_ALL_REVIEWS_FOR_PRODUCT =  
			 "SELECT r.review_id, r.raiting, r.date, r.review_text, u.username FROM reviews AS r" 
		    +"JOIN users AS u" 
			+"ON u.user_id = r.user_id AND product_id = ?";
	
	private Connection connection;
	
	private ReviewDAO() {
		connection = DBManager.getInstance().getConnection();
	}
	
	@Override
	public void addReview(Review review) throws SQLException {
		try(PreparedStatement addReview = connection.prepareStatement(ADD_REVIEW);) {
			addReview.setInt(1, review.getRaiting());
			addReview.setObject(2, review.getDate());
			addReview.setString(3, review.getReviewText());
			addReview.setLong(4, review.getReviewerID());
			addReview.setLong(5, review.getProductID());
			addReview.executeUpdate();
		}
	}

	@Override
	public void removeReviewByID(long reviewID) throws SQLException {
		try(PreparedStatement deleteReview = connection.prepareStatement(DELETE_REVIEW_BY_ID);){
			deleteReview.setLong(1, reviewID);
			deleteReview.executeUpdate();
		}
	}

	@Override
	public Review getReviewByID(long reviewID) throws Exception {
		Review review = null;
		try(PreparedStatement getReview = connection.prepareStatement(GET_REVIEW_BY_ID);){
			getReview.setLong(1, reviewID);
			try(ResultSet result = getReview.executeQuery()) {
				while(result.next()) {
					review = new Review(
							result.getInt("raiting"), 
							result.getString("review_text"), 
							result.getLong("user_id"), 
							result.getLong("product_id"));
				}
			}
		}
		return review;
	}
	
	@Override
	public TreeMap<LocalDateTime, Review> getAllReviewsForUser(long userID) throws Exception {
		TreeMap<LocalDateTime, Review> reviews = new TreeMap<>(new DateComparator());
		try(PreparedStatement getAllReviews = connection.prepareStatement(GET_ALL_REVIEWS_FOR_USER);){
			getAllReviews.setLong(1, userID);
			try(ResultSet result = getAllReviews.executeQuery()){
				while(result.next()) {
					Review review = new Review(
							result.getInt("raiting"), 
							result.getString("review_text"), 
							result.getLong("user_id"), 
							result.getLong("product_id"));
					reviews.put(review.getDate(), review);
				}
			}
		}
		return reviews;
	}

	@Override
	public TreeMap<LocalDateTime, Review> getAllReviewsForProduct(long productID) throws Exception {
		TreeMap<LocalDateTime, Review> reviews = new TreeMap<>(new DateComparator());
		try(PreparedStatement getAllReviews = connection.prepareStatement(GET_ALL_REVIEWS_FOR_PRODUCT);) {
			getAllReviews.setLong(1, productID);
			try(ResultSet result = getAllReviews.executeQuery()){
				while(result.next()) {
					Review review = new Review(
							result.getInt("raiting"), 
							result.getString("review_text"), 
							result.getLong("user_id"), 
							result.getLong("product_id"));
					reviews.put(review.getDate(), review);
				}
			}
		}
		return reviews;
	}
	
	private class DateComparator implements Comparator<LocalDateTime> {
		@Override
		public int compare(LocalDateTime o1, LocalDateTime o2) {
			if(o1.isBefore(o2)) {
				return -1;
			}
			if(o1.isAfter(o2)) {
				return 1;
			}
			return 0;
		}
	}
}
