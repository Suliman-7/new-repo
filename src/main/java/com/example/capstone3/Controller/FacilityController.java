package com.example.capstone3.Controller;

import com.example.capstone3.Model.Facility;
import com.example.capstone3.Model.FacilityDeliveryGroup;
import com.example.capstone3.Service.FacilityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/facility")
public class FacilityController {

    private final FacilityService facilityService;

    // Abdulrahman
    @GetMapping("/get")
    public ResponseEntity getFacilities()
    {
        return ResponseEntity.status(200).body(facilityService.getFacilities());
    }

    // Abdulrahman
    @PostMapping("/add")
    public ResponseEntity addFacility(@Valid@RequestBody Facility facility)
    {
        facilityService.addFacility(facility);
        return ResponseEntity.status(200).body("Facility added");
    }

    // Abdulrahman
    @PutMapping("/update/{id}")
    public ResponseEntity updateFacility(@PathVariable Integer id,@Valid@RequestBody Facility facility)
    {
        facilityService.updateFacility(id, facility);
        return ResponseEntity.status(200).body("Facility updated");
    }

    // Abdulrahman
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteFacility(@PathVariable Integer id)
    {
        facilityService.deleteFacility(id);
        return ResponseEntity.status(200).body("Facility deleted");
    }

    // Suliman
    @PutMapping("/createdeliverygroup/{fid}")
    public ResponseEntity createFacilityDeliveryGroup(@PathVariable Integer fid, @Valid @RequestBody FacilityDeliveryGroup fdg){
        facilityService.createFacilityDeliveryGroup(fid, fdg);
        return ResponseEntity.status(200).body("Facility delivery group created");
    }

    // Abdulrahman
    @GetMapping("/find-facility-by-city/{city}")
    public ResponseEntity findFacilityByCity(@PathVariable String city)
    {
        return ResponseEntity.status(200).body(facilityService.findFacilityByCity(city));
    }

    // Abdulrahman
    @GetMapping("/findbytype/{type}")
    public ResponseEntity findFacilityByType(@PathVariable String type){
        return ResponseEntity.status(200).body(facilityService.findFacilitiesByType(type));
    }

    // Suliman
    @PutMapping("/discountdeliverygroup/{fid}")
    public ResponseEntity discountDeliveryGroup(@PathVariable int fid){
        facilityService.discountFacilityDeliveryGroupPrice(fid);
        return ResponseEntity.status(200).body(" group price discount successfully ");
    }
}