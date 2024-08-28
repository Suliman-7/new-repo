package com.example.capstone3.Service;

import com.example.capstone3.Api.ApiException;
import com.example.capstone3.DTO.DailyTripDTO;
import com.example.capstone3.Model.*;
import com.example.capstone3.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@RequiredArgsConstructor
public class CaptainService {

    private final DailyTripRepository dailyTripRepository;
    private final FacilityDeliveryGroupRepository facilityDeliveryGroupRepository;
    private final StudentRepository studentRepository;
    private final CaptainRepository captainRepository;
    private final RequestRideRepository requestRideRepository;
    private final ParentRequestRideRepository parentRequestRideRepository;

    public List<Captain> getAllCaptains() {
        return captainRepository.findAll();
    }

    public void addCaptain(Captain captain) {
        captainRepository.save(captain);
    }

    public void deleteCaptain(int captain_id) {
        Captain captain = captainRepository.findCaptainById(captain_id);

        if (captain == null){
            throw new ApiException("Captain not found");
        }
        captainRepository.delete(captain);

    }

    public void updateCaptain(int captain_id, Captain captain) {
        Captain captain1 = captainRepository.findCaptainById(captain_id);
        if (captain1 == null){
            throw new ApiException("Captain not found");
        }
        captain1.setName(captain.getName());
        captain1.setAge(captain.getAge());
        captain1.setPhoneNumber(captain.getPhoneNumber());
        captain1.setVehicle(captain.getVehicle());
        captain1.setLicensed(captain.isLicensed());
        captainRepository.save(captain1);
    }

    public void publishDailyTrip( DailyTripDTO dailyTripDTO) {
        Captain captain = captainRepository.findCaptainById(dailyTripDTO.getCaptainId());
        if (captain == null){
            throw new ApiException("Captain not found");
        }
        if(captain.getDailyTrip()!=null){
            throw new ApiException("Captain already have daily trip");
        }
        if (captain.isTemporarySuspend()){
            throw new ApiException("Captain is in suspend");
        }
        if(dailyTripDTO.getPeriod() == 1){
            dailyTripDTO.setPrice(149);
        }
        if(dailyTripDTO.getPeriod() == 2){
            dailyTripDTO.setPrice(249);
        }
        if(dailyTripDTO.getPeriod() == 3) {
            dailyTripDTO.setPrice(349);
        }

        DailyTrip dailyTrip = new DailyTrip(null,dailyTripDTO.getPeriod(),dailyTripDTO.getPrice(),dailyTripDTO.getStartPoint(),dailyTripDTO.getDestination(),dailyTripDTO.getLeaveHour(),captain,null,null);
        captain.setDailyTrip(dailyTrip);
        captainRepository.save(captain);
        dailyTripRepository.save(dailyTrip);
    }

    public void applyDeliveryGroup (int captainId, int delgrpId) {
        //check
        Captain captain = captainRepository.findCaptainById(captainId);
        if (captain == null){
            throw new ApiException("Captain not found");
        }
        if (captain.isTemporarySuspend()){
            throw new ApiException("Captain is in suspend");
        }
        FacilityDeliveryGroup facdelgrp = facilityDeliveryGroupRepository.findFacilityDeliveryGroupById(delgrpId);
        if (facdelgrp == null){
            throw new ApiException("FacilityDeliveryGroup not found");
        }

        if(facdelgrp.getCaptain() != null){
            throw new ApiException("there is a captain assigned to this delivery group");
        }

            facdelgrp.setCaptain(captain);
            captain.setDeliveryGroup(facdelgrp);
            facilityDeliveryGroupRepository.save(facdelgrp);
            captainRepository.save(captain);



    }



    public List<Captain> showHighestCaptainsRate() {


        List<Captain> highestRev = captainRepository.findAll();

        for (int i = 0; i < highestRev.size() - 1; i++) {
            for (int j = 0; j < highestRev.size() - i - 1; j++) {
                if (highestRev.get(j).getRate() < highestRev.get(j + 1).getRate()) {

                    Captain temp = highestRev.get(j);
                    highestRev.set(j, highestRev.get(j + 1));
                    highestRev.set(j + 1, temp);
                }
            }
        }

        return highestRev;

    }

    public List reportAllCaptains() {
        List<String> report = new ArrayList<>();
        for (Captain captain : captainRepository.findAll()) {

            int totalrev = 0;


            report.add("------------------------------------");
            report.add("Captain name : " + captain.getName());
            report.add("Captain Vehicle : " + captain.getVehicle());
            report.add("Captain age  : " + captain.getAge());
            report.add("Captain phone number : " + captain.getPhoneNumber());
            report.add("Captain rate  : " + captain.getRate());

            if(captain.getDailyTrip()!=null){
                totalrev += captain.getDailyTrip().getPrice() * captain.getDailyTrip().getStudents().size() ;

            }
            if(captain.getRequestRide()!=null){
                totalrev += captain.getRequestRide().getPrice();

            }
            if(captain.getDeliveryGroup()!=null) {
                totalrev += captain.getDeliveryGroup().getPrice();
            }


            report.add(" Captain Total revenue : " + totalrev);


        }

        return report;

    }

    //display student request rides
    public List<RequestRide> displayRequestRides() {
        return requestRideRepository.findAll();
    }

    //captain approved for student request ride
    public void ApproveRequestRide(int captain_id,int request_id) {
        Captain captain = captainRepository.findCaptainById(captain_id);
        if (captain == null){
            throw new ApiException("Captain not found");
        }

        if (captain.isTemporarySuspend()){
            throw new ApiException("Captain is in suspend");
        }
        RequestRide requestRide = requestRideRepository.findRequestRideById(request_id);
        if (requestRide == null){
            throw new ApiException("RequestRide not found");
        }
        Student stu = requestRide.getStudent();

        stu.setCaptain(captain);
        studentRepository.save(stu);

        captain.setRequestRide(requestRide);
        requestRide.setCaptain(captain);
        requestRideRepository.save(requestRide);
        captainRepository.save(captain);
    }

    public List<Captain> findCaptainByVehicle(String vehicle)
    {
        if(captainRepository.findCaptainByVehicle(vehicle).isEmpty())
        {
            throw new ApiException("Captain not found");
        }
        return captainRepository.findCaptainByVehicle(vehicle);
    }

    public List<RequestRide> suggestSimilarPathRequests(Integer cID) // StudentService
    {
        Captain cap = captainRepository.findCaptainById(cID);
        if(cap == null)
        {
            throw new ApiException("Student not found");
        }
        if(cap.getDailyTrip()==null){
            throw new ApiException("captain doesn't have daily trip");
        }
        List<Student> students = studentRepository.findAll();
        if(students.isEmpty()){
            throw new ApiException("Students not found");
        }
        List<RequestRide> rr = new ArrayList<>();
        for (int i = 0; i < students.size(); i++) {
            if(students.get(i).getRequestRide()!=null) {
                if (cap.getDailyTrip().getStartPoint().equalsIgnoreCase(students.get(i).getRequestRide().getStartPoint()) && cap.getDailyTrip().getDestination().equalsIgnoreCase(students.get(i).getRequestRide().getDestination())) {
                    rr.add(students.get(i).getRequestRide());
                }
            }
        }
        if(rr.isEmpty())
        {
            throw new ApiException("there is no request rides match with captain path");
        }
        return rr;
    }


    //display parent request rides
    public List<ParentRequestRide> displayParentRequestRides() {
        return parentRequestRideRepository.findAll();
    }

    //captain approved for student request ride
    public void ApproveParentRequestRide(int captain_id,int parentrequest_id) {
        Captain captain = captainRepository.findCaptainById(captain_id);
        if (captain == null){
            throw new ApiException("Captain not found");
        }

        if (captain.isTemporarySuspend()){
            throw new ApiException("Captain is in suspend");
        }
        ParentRequestRide parentRequestRide = parentRequestRideRepository.findParentRequestRideById(parentrequest_id);
        if (parentRequestRide == null){
            throw new ApiException("RequestRide not found");
        }



        captain.setParentRequestRide(parentRequestRide);
        parentRequestRide.setCaptain(captain);
        parentRequestRideRepository.save(parentRequestRide);
        captainRepository.save(captain);
    }



}