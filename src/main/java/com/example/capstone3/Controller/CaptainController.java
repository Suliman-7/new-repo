package com.example.capstone3.Controller;

import com.example.capstone3.Model.Captain;
import com.example.capstone3.Model.DailyTrip;
import com.example.capstone3.Service.CaptainService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/captain")
public class CaptainController {
    private final CaptainService captainService;

    // Abdulaziz
    @GetMapping("/get")
    public ResponseEntity getAllCaptaines() {
        return ResponseEntity.status(200).body(captainService.getAllCaptains());
    }

    // Abdulaziz
    @PostMapping("/add")
    public ResponseEntity addCaptain(@Valid @RequestBody Captain captain) {
        captainService.addCaptain(captain);
        return ResponseEntity.status(200).body("Captain added successfully");
    }

    // Abdulaziz
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteCaptain(@PathVariable int id) {
        captainService.deleteCaptain(id);
        return ResponseEntity.status(200).body("Captain deleted successfully");
    }

    // Abdulaziz
    @PutMapping("/update/{id}")
    public ResponseEntity updateCaptain(@PathVariable int id,@Valid @RequestBody Captain captain) {
        captainService.updateCaptain(id,captain);
        return ResponseEntity.status(200).body("Captain updated successfully");
    }

    //  Suliman
    @PutMapping("/publishtrip/{cid}")
    public ResponseEntity publishTrip(@PathVariable int cid , @Valid @RequestBody DailyTrip dailyTrip) {
        captainService.publishDailyTrip(cid,dailyTrip);
        return ResponseEntity.status(200).body("Trip published successfully");
    }

    // Suliman
    @PutMapping("{cid}/applydeliverygroup/{dgid}")
    public ResponseEntity applyDeliveryGroup(@PathVariable int cid, @PathVariable int dgid) {
        captainService.applyDeliveryGroup(cid,dgid);
        return ResponseEntity.status(200).body("captain assigned to delivery group successfully");
    }

    // Suliman
    @GetMapping("/showhighestrate")
    public ResponseEntity showHighestRate() {
        return ResponseEntity.status(200).body(captainService.showHighestCaptainsRate());
    }

    // Suliman
    @GetMapping("report")
    public ResponseEntity reportAllCaptains() {
        return ResponseEntity.status(200).body(captainService.reportAllCaptains());
    }


    // Abdulaziz
    @GetMapping("/display-request-rides")
    public ResponseEntity displayRequestRides() {
        return ResponseEntity.status(200).body(captainService.displayRequestRides());
    }

    // Abdulaziz
    @PutMapping("/approve-request/{captainid}/{requestid}")
    public ResponseEntity approveRequest(@PathVariable int captainid, @PathVariable int requestid) {
        captainService.ApproveRequestRide(captainid,requestid);
        return ResponseEntity.status(200).body("Request approved successfully");
    }

    // Abdulrahman
    @GetMapping("/find-captain-by-vehicle/{vehicle}")
    public ResponseEntity findCaptainByVehicle(@PathVariable String vehicle)
    {
        return ResponseEntity.status(200).body(captainService.findCaptainByVehicle(vehicle));
    }

    // Abdulrahman and Suliman
    @GetMapping("/suggestsimilarpath/{captainid}")
    public ResponseEntity suggestSimilarPathRequests(@PathVariable int captainid){
        return ResponseEntity.status(200).body(captainService.suggestSimilarPathRequests(captainid));
    }

    // Abdulaziz
    @PutMapping("/approve-parent-request/{captainid}/{requestid}")
    public ResponseEntity approveParentRequest(@PathVariable int captainid, @PathVariable int requestid) {
        captainService.ApproveParentRequestRide(captainid,requestid);
        return ResponseEntity.status(200).body("Request approved successfully");
    }

    // Abdulaziz
    @GetMapping("/display-parent-request-rides")
    public ResponseEntity displayParentRequestRides() {
        return ResponseEntity.status(200).body(captainService.displayParentRequestRides());
    }

}