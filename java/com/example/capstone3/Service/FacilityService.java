package com.example.capstone3.Service;

import com.example.capstone3.Api.ApiException;
import com.example.capstone3.Model.Captain;
import com.example.capstone3.Model.Facility;
import com.example.capstone3.Model.FacilityDeliveryGroup;
import com.example.capstone3.Repository.CaptainRepository;
import com.example.capstone3.Repository.FacilityDeliveryGroupRepository;
import com.example.capstone3.Repository.FacilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FacilityService {

    private final FacilityRepository facilityRepository;
    private final CaptainRepository captainRepository;
    private final FacilityDeliveryGroupRepository facilityDeliveryGroupRepository;

    public List<Facility> getFacilities() {
        return facilityRepository.findAll();
    }

    public void addFacility(Facility facility) {
        facilityRepository.save(facility);
    }

    public void updateFacility(Integer id, Facility facility) {
        Facility f = facilityRepository.findFacilityById(id);
        if (f == null) {
            throw new ApiException("Facility not found");
        }
        f.setName(facility.getName());
        f.setType(facility.getType());
        f.setCity(facility.getCity());
        f.setAddress(facility.getAddress());
        facilityRepository.save(f);
    }

    public void deleteFacility(Integer id) {
        if (facilityRepository.findFacilityById(id) == null) {
            throw new ApiException("Facility not found");
        }
        facilityRepository.deleteById(id);
    }

    public void createFacilityDeliveryGroup(int facilityId, FacilityDeliveryGroup facdelgrp) {
        // check
        Facility facility = facilityRepository.findFacilityById(facilityId);
        if (facility == null) {
            throw new ApiException("Facility not found");
        }

        if (facdelgrp.getPeriod() == 1) {
            facdelgrp.setPrice(99);
        }
        if (facdelgrp.getPeriod() == 2) {
            facdelgrp.setPrice(149);
        }
        if (facdelgrp.getPeriod() == 3) {
            facdelgrp.setPrice(199);
        }


        facility.getDeliveryGroups().add(facdelgrp);
        facdelgrp.setFacility(facility);
        facilityDeliveryGroupRepository.save(facdelgrp);
        facilityRepository.save(facility);

    }

    public List<Facility> findFacilityByCity(String city) {
        if (facilityRepository.findFacilityByCity(city).isEmpty()) {
            throw new ApiException("Facility not found");
        }
        return facilityRepository.findFacilityByCity(city);
    }

    public List<Facility> findFacilitiesByType(String type) {
        List<Facility> byType = new ArrayList<>();
        for (Facility facility : facilityRepository.findAll()) {
            if (facility.getType().equalsIgnoreCase(type)) {
                byType.add(facility);

            }
        }
        return byType;
    }

    public void discountFacilityDeliveryGroupPrice(int facilityId) {
        Facility facility = facilityRepository.findFacilityById(facilityId);
        if (facility==null){
            throw new ApiException("Facility not found");
        }
        if (facility.getDeliveryGroups().isEmpty()){
            throw new ApiException("Facility doesn't have any deliveryGroups");
        }
        for (FacilityDeliveryGroup fdg : facility.getDeliveryGroups()) {
            if(fdg.getStudents().size()==0){
                fdg.setPrice(fdg.getPrice()-(fdg.getPrice()*0.20));
                facilityDeliveryGroupRepository.save(fdg);
            }
            if(fdg.getStudents().size()<=5 && fdg.getStudents().size()>0){
                fdg.setPrice(fdg.getPrice()-(fdg.getPrice()*0.15));
                facilityDeliveryGroupRepository.save(fdg);
            }
            if(fdg.getStudents().size()<=10 && fdg.getStudents().size()>5){
                fdg.setPrice(fdg.getPrice()-(fdg.getPrice()*0.10));
                facilityDeliveryGroupRepository.save(fdg);
            }
        }
    }
}