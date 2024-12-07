package com.hdoc.firstjobapp.company.impl;

import com.hdoc.firstjobapp.company.Company;
import com.hdoc.firstjobapp.company.CompanyRepository;
import com.hdoc.firstjobapp.company.CompanyService;
import com.hdoc.firstjobapp.job.Job;
import com.hdoc.firstjobapp.review.Review;
import com.hdoc.firstjobapp.review.ReviewRepository;
import com.hdoc.firstjobapp.review.ReviewService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {
    private CompanyRepository companyRepository;
    private ReviewRepository reviewRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository, ReviewRepository reviewRepository) {
        this.companyRepository = companyRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public boolean updateCompany(Long id, Company updatedCompany) {
        Optional<Company> companyOptional= companyRepository.findById(id);

        if (companyOptional.isPresent()){
            Company company= companyOptional.get();
            company.setDescription(updatedCompany.getDescription());
            company.setName(updatedCompany.getName());
            company.setJobs(updatedCompany.getJobs());
            companyRepository.save(company);
            return true;
        }
        return false;
    }

    @Override
    public void createCompany(Company company) {
        companyRepository.save(company);
    }

    @Override
    public boolean deleteCompany(Long id) {
        if(companyRepository.existsById(id)){
            Company c = companyRepository.findById(id).orElse(null);
            List<Review> reviews = c.getReviews();
            for(Review r:reviews){
                Long reviewId = r.getId();
                reviewRepository.deleteById(reviewId);
            }
            c.setReviews(null);
            companyRepository.save(c);
            companyRepository.deleteById(id);
            return true;
        }
        else return false;
    }

    @Override
    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }

}
