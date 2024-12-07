package com.hdoc.firstjobapp.review;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies/{companyId}/reviews")
public class ReviewController {
    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }


    @GetMapping
    private ResponseEntity<List<Review>> getAllReviews(@PathVariable Long companyId){
        return ResponseEntity.ok(reviewService.getAllReviews(companyId));
    }

    @GetMapping("/{id}")
    private ResponseEntity<Review> getReviewById(@PathVariable Long companyId, @PathVariable Long id){
        Review review= reviewService.getReviewById(companyId,id);
        if(review!=null)
            return new ResponseEntity<>(review, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    private ResponseEntity<String> createReview(@RequestBody Review review, @PathVariable Long companyId){
        boolean added = reviewService.createReview(review,companyId);
        if(added) {
            return new ResponseEntity<>("Review Added Successfully", HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>("Company Does not exist", HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/{id}")
    private ResponseEntity<String> updateReview(@PathVariable Long companyId, @RequestBody Review review, @PathVariable Long id){
        boolean updated= reviewService.updateReviewById(companyId,id,review);
        if(updated)
            return new ResponseEntity<>("Review with Id: "+ id+" Updated Successfully",HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<String> deleteReview(@PathVariable Long companyId, @PathVariable Long id){
        boolean deleted= reviewService.deleteReviewById(companyId, id);
        if(deleted)
            return new ResponseEntity<>("Review with Id: "+id+" Deleted Successfully",HttpStatus.OK);
        return new ResponseEntity<>("Review Id Not Found",HttpStatus.NOT_FOUND);
    }

}
