package com.example.capstone3.Controller;

import com.example.capstone3.Model.Review;
import com.example.capstone3.Service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/review")
public class ReviewController {

    private final ReviewService reviewService;


    // Abdulrahman
    @GetMapping("/get")
    public ResponseEntity getReviews()
    {
        return ResponseEntity.status(200).body(reviewService.getReviews());
    }

    // Abdulrahman
    @PostMapping("/add")
    public ResponseEntity addReview(@Valid@RequestBody Review review)
    {
        reviewService.addReview(review);
        return ResponseEntity.status(200).body("Review added");
    }

    // Abdulrahman
    @PutMapping("/update/{id}")
    public ResponseEntity updateReview(@PathVariable Integer id,@Valid@RequestBody Review review)
    {
        reviewService.updateReview(id, review);
        return ResponseEntity.status(200).body("Review updated");
    }

    // Abdulrahman
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteReview(@PathVariable Integer id)
    {
        reviewService.deleteReview(id);
        return ResponseEntity.status(200).body("Review deleted");
    }


}