package com.example.capstone3.Service;

import com.example.capstone3.Api.ApiException;
import com.example.capstone3.Model.*;
import com.example.capstone3.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

public class AdminService {

    private final AdminRepository adminRepository;
    private final ReviewRepository reviewRepository;
    private final DailyTripRepository dailyTripRepository;
    private final RequestRideRepository requestRideRepository;
    private final FacilityDeliveryGroupRepository facilityDeliveryGroupRepository;
    private final StudentRepository studentRepository;
    private final CaptainRepository captainRepository;
    private final CompliantRepository compliantRepository;

    public List<Admin> getAllAdmins(){
        return adminRepository.findAll();
    }

    public void addAdmin(Admin admin){
        adminRepository.save(admin);
    }

    public void updateAdmin( int id ,Admin admin1){
        Admin admin = adminRepository.findAdminById(id);
        if(admin == null){
            throw new ApiException("Admin not found");
        }
        admin.setName(admin1.getName());
        admin.setEmail(admin1.getEmail());
        admin.setPhoneNumber(admin1.getPhoneNumber());
    }

    public void deleteAdmin(int id){
        Admin admin = adminRepository.findAdminById(id);
        if(admin == null){
            throw new ApiException("Admin not found");
        }
        adminRepository.delete(admin);
    }

    public void deleteByComment(String comment)
    {
        if(reviewRepository.findReviewByCommentContains(comment).isEmpty())
        {
            throw new ApiException("It is null");
        }
        reviewRepository.deleteAll(reviewRepository.findReviewByCommentContains(comment));
    }



    public List<String> showTransactions(){
        List<String> transactions = new ArrayList<>();
        int counter = 1;
        int totalrev = 0;

        if(!dailyTripRepository.findAll().isEmpty()) {
            for (DailyTrip dt : dailyTripRepository.findAll()) {
                transactions.add(" ---------------------------------");
                transactions.add(" Transaction : " + counter);
                transactions.add(" Daily Trip ");
                transactions.add(" Associated with Captain : " + dt.getCaptain().getName());
                transactions.add(" With Price : " + dt.getPrice() + "$");
                transactions.add(" From : " + dt.getStartPoint() + " To : " + dt.getDestination());
                transactions.add(" has : " + dt.getStudents().size() + " Students registered");

                totalrev = dt.getPrice() * dt.getStudents().size();
                transactions.add(" With Total Revenue : " + totalrev);
                counter++;
                totalrev = 0;
            }
        }
            if(!requestRideRepository.findAll().isEmpty()) {

                for (RequestRide rr : requestRideRepository.findAll()) {
                    transactions.add(" ---------------------------------");
                    transactions.add(" Transaction : " + counter);
                    transactions.add(" Request Ride ");
                    transactions.add(" Requested By Student : " + rr.getStudent().getName());
                    if (rr.getCaptain() == null) {
                        transactions.add(" Not Associated with Any Captain Yet !");
                        break;
                    }
                    transactions.add(" Associated with Captain : " + rr.getCaptain().getName());
                    transactions.add(" With Price : " + rr.getPrice() + "$");
                    transactions.add(" From : " + rr.getStartPoint() + " To : " + rr.getDestination());

                    counter++;


                }
            }
            if(!facilityDeliveryGroupRepository.findAll().isEmpty()) {

                for (FacilityDeliveryGroup fdg : facilityDeliveryGroupRepository.findAll()) {
                    transactions.add(" ---------------------------------");
                    transactions.add(" Transaction : " + counter );
                    transactions.add(" Facility Delivery Group "  );
                    transactions.add(" Created by Facility : " +fdg.getFacility().getName());
                    if(fdg.getCaptain()==null){
                        transactions.add(" Not Associated with Any Captain Yet !");
                        break;
                    }
                    transactions.add(" Associated with Captain : " +fdg.getCaptain().getName());
                    transactions.add(" With Price : " + fdg.getPrice() + "$");
                    transactions.add(" has : " + fdg.getStudents().size() + " Students registered");

                    totalrev *= fdg.getStudents().size();
                    transactions.add(" With Total Revenue : " + totalrev);
                    counter++;
                    totalrev = 0;
                }

        }
        return transactions;
    }

    public void discountStudent(){
        if(studentRepository.findAll().isEmpty()) {
            throw new ApiException("No students found");
        }
        for(Student stu : studentRepository.findAll()){
            if(stu.isDisable()){
                stu.setDiscountPercentage(20);
                studentRepository.save(stu);
            }
            if(stu.getAge()<18){
                stu.setDiscountPercentage(15);
                studentRepository.save(stu);

            }

            if( stu.getDailyTrip()==null  && stu.getRequestRide()==null && stu.getDeliveryGroup()==null ){
                stu.setDiscountPercentage(10);
                studentRepository.save(stu);
            }
        }
    }

    public List<String> showHighestCaptainRevenue(){
        List<String> highestCaptainRevenue = new ArrayList<>();
        List<Integer> revenue = new ArrayList<>();

        for(Captain captain : captainRepository.findAll()){
            int totalRev = 0;

            if(captain.getDailyTrip()!=null){
                totalRev += captain.getDailyTrip().getPrice() * captain.getDailyTrip().getStudents().size() ;

            }
            if(captain.getDeliveryGroup()!=null){
                totalRev += captain.getDeliveryGroup().getPrice();
            }
            if(captain.getRequestRide()!=null){
                totalRev += captain.getRequestRide().getPrice();
            }
            revenue.add(totalRev);
        }


        for (int i = 0; i < revenue.size() - 1; i++) {
            for (int j = 0; j < revenue.size() - i - 1; j++) {
                if (revenue.get(j) < revenue.get(j + 1)) {
                    // Swap elements
                    int temp = revenue.get(j);
                    revenue.set(j, revenue.get(j + 1));
                    revenue.set(j + 1, temp);
                }
            }
        }
        for (int i = 0; i < revenue.size(); i++) {
            highestCaptainRevenue.add(" =================================");
            highestCaptainRevenue.add(" Captain : " + (i+1) + " " );
            highestCaptainRevenue.add(" Captain Name : " + captainRepository.findAll().get(i).getName() + " " );
            highestCaptainRevenue.add(" Total Revenue : " + revenue.get(i) + " " );
        }
        return highestCaptainRevenue;
    }

    public void makeWarnToCaptain(int captainId){
        Captain captain = captainRepository.findCaptainById(captainId);

        if(captain == null){
            throw new ApiException("Captain not found");
        }
        Compliant report = compliantRepository.findCompliantById(captain.getId());
        if(report == null){
            throw new ApiException("Selected captain doesn't had any Report on him");
        }

        if (captain.isTemporarySuspend()){
            throw new ApiException("Captain is suspended");
        }

        captain.setWarns(captain.getWarns()+1);

        LocalDate suspendDate = LocalDate.now();

        if (captain.getWarns()==3){
            captain.setTemporarySuspend(true);
            captain.setTemporarySuspendDate(suspendDate);
            captainRepository.save(captain);
        }
        if (captain.getWarns()==5){
            captainRepository.delete(captain);
        }
    }

    public List<Compliant> displayAllComplaints(){
        return compliantRepository.findAll();
    }

    public void recoverCaptainAccount(int captainId){
        Captain captain = captainRepository.findCaptainById(captainId);
        if(captain == null){
            throw new ApiException("Captain not found");
        }
        if(!captain.isTemporarySuspend()){
            throw new ApiException("Captain is not suspended");
        }
        if( captain.getTemporarySuspendDate().datesUntil(LocalDate.now()).count() <= 30  ) {
            throw new ApiException("to recover captain account should be left more than 30 days");
        }

        captain.setTemporarySuspend(false);
    }



}
