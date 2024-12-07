package com.hdoc.firstjobapp.review.impl;

import com.hdoc.firstjobapp.company.Company;
import com.hdoc.firstjobapp.company.CompanyService;
import com.hdoc.firstjobapp.review.Review;
import com.hdoc.firstjobapp.review.ReviewController;
import com.hdoc.firstjobapp.review.ReviewRepository;
import com.hdoc.firstjobapp.review.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private ReviewRepository reviewRepository;
    private CompanyService companyService;

    public ReviewServiceImpl(ReviewRepository reviewRepository, CompanyService companyService) {
        this.reviewRepository = reviewRepository;
        this.companyService = companyService;
    }

    @Override
    public List<Review> getAllReviews(Long id) {
        List<Review> reviews = reviewRepository.findByCompanyId(id);
        return reviewRepository.findAll();
    }

    @Override
    public Review getReviewById(Long companyId,Long id) {
        List<Review> reviews = reviewRepository.findByCompanyId(companyId);
        return reviews.stream().filter(review ->  review.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public boolean createReview(Review review, Long companyId) {
        Company company = companyService.getCompanyById(companyId);
        if(company!=null) {
            review.setCompany(company);
            reviewRepository.save(review);
            return true;
      }
        return false;
    }

    @Override
    public boolean updateReviewById(Long companyId, Long id, Review review) {
        if(companyService.getCompanyById(companyId) !=null && reviewRepository.existsById(id)){
            review.setCompany(companyService.getCompanyById(companyId));
            review.setId(id);
            reviewRepository.save(review);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteReviewById(Long companyId, Long id) {
            try {
                if(companyService.getCompanyById(companyId) !=null && reviewRepository.existsById(id)){
                    Review review = reviewRepository.findById(id).orElse(null);
                    Company company = review.getCompany();
                    company.getReviews().remove(review);
                    review.setCompany(null);
                    companyService.updateCompany(companyId,company);
                    reviewRepository.deleteById(id);
                    return true;
                }
                return false;
            }
            catch (Exception e){
                return false;
            }
        }
    }

