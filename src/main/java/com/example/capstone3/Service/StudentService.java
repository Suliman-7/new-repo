package com.example.capstone3.Service;

import com.example.capstone3.Api.ApiException;
import com.example.capstone3.DTO.RequestRideDTO;
//import com.example.capstone3.DTO.SubscriptionDTO;
import com.example.capstone3.Model.*;
import com.example.capstone3.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

public class StudentService {

    private final StudentRepository studentRepository;
    private final CaptainRepository captainRepository;
    private final DailyTripRepository dailyTripRepository;
    private final ReviewRepository reviewRepository;
    private final FacilityDeliveryGroupRepository facilityDeliveryGroupRepository;
    private final RequestRideRepository requestRideRepository;
    private final FacilityRepository facilityRepository;
    private final CompliantRepository reportRepository;
    private final QuestionRepository questionRepository;
    private final AdminRepository adminRepository;
    private final CompliantRepository compliantRepository;
    private final ParentRequestRideRepository parentRequestRideRepository;

    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    public void addStudent(Student student){

        if(student.getAge()<18){
            throw new ApiException("Student age cannot be less than 18");
        }
        studentRepository.save(student);
    }

    public void updateStudent(int id , Student student){
        Student stu = studentRepository.findStudentById(id);

        if(stu == null){
            throw new ApiException("student not found");
        }
        stu.setName(student.getName());
        stu.setAge(student.getAge());
        stu.setAddress(student.getAddress());
        stu.setAcademicLevel(student.getAcademicLevel());
        stu.setDisable(student.isDisable());
        studentRepository.save(stu);
    }


    public List<DailyTrip> displayCaptainsTrip(){
        return dailyTripRepository.findAll();
    }

    public void deleteStudent(int id){
        Student stu = studentRepository.findStudentById(id);
        if(stu == null){
            throw new ApiException("student not found");
        }
        studentRepository.delete(stu);
    }



    public void joinDailyTrip(int studentId , int dailyTripId){
        Student stu = studentRepository.findStudentById(studentId);
        if(stu == null){
            throw new ApiException("student not found");
        }
        DailyTrip dailyTrip = dailyTripRepository.findDailyTripById(dailyTripId);
        if(dailyTrip == null){
            throw new ApiException("daily trip not found");
        }

        for(Student student : dailyTrip.getStudents()){
            if(student.getId() == stu.getId()){
                throw new ApiException("you are already registered to this delivery group");
            }
        }

        stu.setCaptain(dailyTrip.getCaptain());
        stu.setDailyTrip(dailyTrip);
        dailyTrip.getStudents().add(stu);
        studentRepository.save(stu);
        dailyTripRepository.save(dailyTrip);
    }

    public void joinFacilityDelivery(int studentId , int fdId){
        //check
        Student stu = studentRepository.findStudentById(studentId);
        if(stu == null){
            throw new ApiException("student not found");
        }

        FacilityDeliveryGroup delgrp = facilityDeliveryGroupRepository.findFacilityDeliveryGroupById(fdId);
        if(delgrp == null){
            throw new ApiException("facility delivery group not found");
        }

        if(! stu.getRegion().equalsIgnoreCase(delgrp.getRegion())){
            throw new ApiException("facility delivery group does not match region");
        }

        if(delgrp.getCaptain()==null){
            throw new ApiException("there is no captain assigned to this delivery group yet");
        }

        for(Student student : delgrp.getStudents()){
            if(student.getId() == stu.getId()){
                throw new ApiException("you are already registered to this delivery group");
            }
        }

        stu.setCaptain(delgrp.getCaptain());
        stu.setDeliveryGroup(delgrp);
        delgrp.getStudents().add(stu);
        studentRepository.save(stu);
        facilityDeliveryGroupRepository.save(delgrp);
    }

    public void addCaptainReview(int studentId , int captainId , String comment , double rate ){
        Student stu = studentRepository.findStudentById(studentId);
        if(stu == null){
            throw new ApiException("student not found");
        }
        Captain cap = captainRepository.findCaptainById(captainId);
        if(cap == null){
            throw new ApiException("captain not found");
        }


        if(stu.getCaptain() != cap){
            throw new ApiException("captain doesn't match");
        }

        for(Review review : reviewRepository.findAll()){
            if(review.getStudent() == stu){
                throw new ApiException("you already reviewed");
            }
        }

        if(cap.getRate()==0){
            cap.setRate(rate);
            captainRepository.save(cap);
        }
        else {
            cap.setRate((cap.getRate() + rate) / 2);
            captainRepository.save(cap);

        }

        Review review = new Review();
        review.setComment(comment);
        review.setRate(rate);
        review.setStudent(stu);
        review.setStudentName(stu.getName());
        review.setCaptain(cap);
        stu.getReviews().add(review);
        cap.getReviews().add(review);
        reviewRepository.save(review);


    }




    //student apply request for ride
    public void requestRide( RequestRideDTO requestRideDTO){
        Student stu = studentRepository.findStudentById(requestRideDTO.getStudentId());
        if(stu == null){
            throw new ApiException("student not found");
        }

        if(stu.getRequestRide()!=null){
            throw new ApiException("you already requested ride");
        }


        if(requestRideDTO.getPeriod() == 1){
            requestRideDTO.setPrice(149);
        }
        if(requestRideDTO.getPeriod() == 2){
            requestRideDTO.setPrice(249);
        }
        if(requestRideDTO.getPeriod() == 3){
            requestRideDTO.setPrice(349);
        }
        double percent = stu.getDiscountPercentage()/100;
        requestRideDTO.setPrice(requestRideDTO.getPrice()-(requestRideDTO.getPrice()*percent));

        RequestRide rd = new RequestRide(null,requestRideDTO.getStartPoint(),requestRideDTO.getLeaveHour(),requestRideDTO.getDestination(),requestRideDTO.getPeriod(),requestRideDTO.getPrice(),stu,null);

        requestRideRepository.save(rd);
        stu.setRequestRide(rd);
        studentRepository.save(stu);
    }

    //student cancel request
    public void cancelRequest(int studentId){
        Student stu = studentRepository.findStudentById(studentId);
        if(stu == null){
            throw new ApiException("student not found");
        }
        RequestRide rd = requestRideRepository.findRequestRideById(stu.getRequestRide().getId());
        if (rd == null){
            throw new ApiException("requestRide not found");
        }
        rd.setStudent(null);
        requestRideRepository.deleteById(stu.getRequestRide().getId());
    }


    public List<Student> findStudentByAcademicLevel(String academicLevel)
    {
        if(studentRepository.findStudentByAcademicLevel(academicLevel).isEmpty())
        {
            throw new ApiException("It is empty");
        }
        return studentRepository.findStudentByAcademicLevel(academicLevel);
    }


    public List<Student> findStudentByGender(String gender)
    {
        if(studentRepository.findStudentByGender(gender).isEmpty())
        {
            throw new ApiException("It is null");
        }
        return studentRepository.findStudentByGender(gender);
    }


    // Abdulaziz
    public List<String> ReportAllStudent(){
        ArrayList<String> report = new ArrayList<>();
        for (Student student : studentRepository.findAll()){
            report.add("=================");
            report.add("Student name: "+student.getName());
            report.add("Student age: "+student.getAge());
            report.add("phone number: "+student.getPhoneNumber());
            report.add("student academic level: "+student.getAcademicLevel());
            report.add("disable "+student.isDisable());
            report.add("Gender: "+student.getGender());
            report.add("address: "+student.getAddress());
        }
        return report;
    }

    public void changeFacility(int studentId , int facilityId){
        Facility facility = facilityRepository.findFacilityById(facilityId);
        if(facility == null){
            throw new ApiException("facility not found");
        }
        Student stu = studentRepository.findStudentById(studentId);
        if(stu == null){
            throw new ApiException("student not found");
        }
        stu.setFacility(facility);
        facility.getStudents().remove(stu);
        facilityRepository.save(facility);
        studentRepository.save(stu);
    }

    public void makeCompliant(int studentId ,int captainId, Compliant compliant){
        Student stu = studentRepository.findStudentById(studentId);
        if(stu == null){
            throw new ApiException("student not found");
        }
        Captain captain = captainRepository.findCaptainById(captainId);
        if(captain == null){
            throw new ApiException("captain not found");
        }
        if(stu.getCaptain() != captain){
            throw new ApiException("captain doesn't match");
        }

        for(Compliant c : compliantRepository.findAll()){
            if(c.getCapId()==captainId && c.getStudent()==stu){
                throw new ApiException("you already make compliant to this captain");
            }
        }

        compliant.setStudent(stu);
        compliant.setCapId(captain.getId());
        compliant.setCaptainName(captain.getName());
        captain.setComplaints(captain.getComplaints()+1);
        captainRepository.save(captain);
        reportRepository.save(compliant);


    }

    public List<DailyTrip> suggestSimilarPathTrips(int sID) // StudentService
    {
        Student s = studentRepository.findStudentById(sID);
        if(s == null)
        {
            throw new ApiException("Student not found");
        }
        if(s.getRequestRide()==null){
            throw new ApiException("student doesn't have requestRide");
        }
        List<Captain> captains = captainRepository.findAll();
        if(captains.isEmpty()){
            throw new ApiException("captains not found");
        }
        List<DailyTrip> dt = new ArrayList<>();
        for (int i = 0; i < captains.size(); i++) {
            if (captains.get(i).getDailyTrips() != null) {
                for (int j = 0; j < captains.get(i).getDailyTrips().size(); j++) {
                    if (s.getRequestRide().getStartPoint().equalsIgnoreCase(captains.get(i).getDailyTrips().get(j).getStartPoint()) && s.getRequestRide().getDestination().equalsIgnoreCase(captains.get(i).getDailyTrips().get(j).getDestination())) {
                        dt.add(captains.get(i).getDailyTrips().get(j));
                    }
                }
            }
        }
        if(dt.isEmpty())
        {
            throw new ApiException("there is no trips match with student path");
        }
        return dt;
    }

    public void addQuestion(Integer sID,String question)
    {
        if(studentRepository.findStudentById(sID) == null)
        {
            throw new ApiException("Student not found");
        }
        Question q = new Question(null,question,null,studentRepository.findStudentById(sID));
        questionRepository.save(q);
    }



    public List<Student> getStudentByAddress(String address)  // in StudentService
    {
        if(studentRepository.findStudentByAddress(address).isEmpty())
        {
            throw new ApiException("It is empty");
        }
        return studentRepository.findStudentByAddress(address);
    }

    public void assignStudentToFacility(int studentId, int facilityId){
        Student student = studentRepository.findStudentById(studentId);
        Facility facility = facilityRepository.findFacilityById(facilityId);
        if(facility == null){
            throw new ApiException("facility not found");
        }
        if(student == null){
            throw new ApiException("student not found");
        }
        student.setFacility(facility);
        facility.getStudents().add(student);
        facilityRepository.save(facility);
        studentRepository.save(student);
    }





}
