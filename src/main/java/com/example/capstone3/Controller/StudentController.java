package com.example.capstone3.Controller;

import com.example.capstone3.DTO.RequestRideDTO;
import com.example.capstone3.Model.Compliant;
import com.example.capstone3.Model.Student;
import com.example.capstone3.Service.StudentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/student")

public class StudentController {

    private final StudentService studentService;

    @GetMapping("/get")
    public ResponseEntity findAllStudents() {
        return ResponseEntity.status(200).body(studentService.getAllStudents());
    }

    @PostMapping("/post")
    public ResponseEntity addStudent(@Valid @RequestBody Student student) {
        studentService.addStudent(student);
        return ResponseEntity.status(200).body("student added successfully");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateStudent(@PathVariable int id, @Valid @RequestBody Student student) {
        studentService.updateStudent(id, student);
        return ResponseEntity.status(200).body("student updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteStudent(@PathVariable int id) {
        studentService.deleteStudent(id);
        return ResponseEntity.status(200).body("student deleted successfully");
    }


    // Suliman
    @PutMapping("/{sid}/join/{dtid}")
    public ResponseEntity joinDailyTrip(@PathVariable int sid, @PathVariable int dtid) {
        studentService.joinDailyTrip(sid,dtid);
        return ResponseEntity.status(200).body("Student joined daily trip successfully");
    }

    // Suliman
    @PutMapping("/{sid}/joindeliverygroup/{dgid}")
    public ResponseEntity joinDeliveryGroup(@PathVariable int sid, @PathVariable int dgid) {
        studentService.joinFacilityDelivery(sid,dgid);
        return ResponseEntity.status(200).body("Student joined to delivery group successfully");
    }

    // Abdulrahman
    @PutMapping("/{sid}/review/{cid}/{comment}/{rate}")
    public ResponseEntity addReview(@PathVariable int sid, @PathVariable int cid, @PathVariable String comment ,  @PathVariable @Min(value = 1) @Max(value = 5) double rate) {
        studentService.addCaptainReview(sid,cid,comment,rate);
        return ResponseEntity.status(200).body("Review added successfully");
    }


    // Abdulaziz
    @GetMapping("/students-report")
    public ResponseEntity studentReport(){
        return ResponseEntity.status(200).body(studentService.ReportAllStudent());
    }

    // Abdulrahman
    @GetMapping("/find-student-by-academic-level/{academicLevel}")
    public ResponseEntity findStudentByAcademicLevel(@PathVariable String academicLevel)
    {
        return ResponseEntity.status(200).body(studentService.findStudentByAcademicLevel(academicLevel));
    }

    // Abdulrahman
    @GetMapping("/find-student-by-gender/{gender}")
    public ResponseEntity findStudentByGender(@PathVariable String gender)
    {
        return ResponseEntity.status(200).body(studentService.findStudentByGender(gender));
    }

    // Abdulaziz
    @PutMapping("/request-ride")
    public ResponseEntity requestRideStudent(@Valid @RequestBody RequestRideDTO rd) {
        studentService.requestRide(rd);
        return ResponseEntity.status(200).body("Student requested successfully");
    }

    // Abdulaziz
    @DeleteMapping("/cancel-request/{studentid}")
    public ResponseEntity cancelRequestStudent(@PathVariable int studentid) {
        studentService.cancelRequest(studentid);
        return ResponseEntity.status(200).body("Student request cancelled successfully");
    }


    // Suliman
    @PutMapping("{sid}/changefacility/{fid}")
    public ResponseEntity changeFacility(@PathVariable int sid, @PathVariable int fid) {
        studentService.changeFacility(sid,fid);
        return ResponseEntity.status(200).body("Facility changed successfully");
    }


    // Abdulaziz
    @PutMapping("/make-compliant/{studentid}/{captainid}")
    public ResponseEntity makeCompliant (@PathVariable int studentid,@PathVariable int captainid ,@Valid @RequestBody Compliant compliant){
        studentService.makeCompliant(studentid,captainid, compliant);
        return ResponseEntity.status(200).body("Report make successfully");
    }

    // Abdulaziz
    @GetMapping("/show-all-trips")
    public ResponseEntity showAllTrips() {
        return ResponseEntity.status(200).body(studentService.displayCaptainsTrip());
    }


    // Suliman and Abdulrahman
    @GetMapping("/suggestsimilarpath/{studentid}")
    public ResponseEntity suggestSimilarPathTrips(@PathVariable int studentid){
        return ResponseEntity.status(200).body(studentService.suggestSimilarPathTrips(studentid));
    }

    // Abdulrahman
    @PostMapping("/add-question/{sid}/{question}")
    public ResponseEntity addQuestion(@PathVariable Integer sid,@PathVariable String question)
    {
        studentService.addQuestion(sid, question);
        return ResponseEntity.status(200).body(("question added"));
    }

    // Abdulrahman
    @GetMapping("/get-student-by-address/{address}")  // in StudentController
    public ResponseEntity getStudentByAddress(@PathVariable String address)
    {
        return ResponseEntity.status(200).body(studentService.getStudentByAddress(address));
    }

    // Suliman
    @PutMapping("/assignstudenttofacility/{sid}/{fid}")
    public ResponseEntity assignFacilityToStudent(@PathVariable int sid, @PathVariable int fid) {
        studentService.assignStudentToFacility(sid,fid);
        return ResponseEntity.status(200).body(("student facility assigned successfully"));
    }



}
