package com.hdoc.firstjobapp.review;

import java.util.List;

public interface ReviewService {
    List<Review> getAllReviews(Long id);
    Review getReviewById(Long companyId, Long id);
    boolean createReview(Review review, Long companyId);
    boolean updateReviewById(Long companyId, Long id,Review review);
    boolean deleteReviewById(Long companyId,Long id);
}
