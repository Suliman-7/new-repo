package com.example.capstone3.Controller;

import com.example.capstone3.Model.Admin;
import com.example.capstone3.Service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")

public class AdminController {

    private final AdminService adminService;

    @GetMapping("/get")
    public ResponseEntity getAllAdmin() {
        return ResponseEntity.status(200).body(adminService.getAllAdmins());
    }

    @PostMapping("/post")
    public ResponseEntity addAdmin(@Valid @RequestBody Admin admin) {
        adminService.addAdmin(admin);
        return ResponseEntity.status(200).body("admin successfully added");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateAdmin(@PathVariable int id, @Valid @RequestBody Admin admin) {
        adminService.updateAdmin(id, admin);
        return ResponseEntity.status(200).body("admin successfully updated");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteAdmin(@PathVariable int id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.status(200).body("admin successfully deleted");
    }


    @DeleteMapping("/delete-by-comment/{comment}")
    public ResponseEntity deleteByComment(@PathVariable String comment)
    {
        adminService.deleteByComment(comment);
        return ResponseEntity.status(200).body("Comment deleted");
    }

    @GetMapping("showtransaction")
    public ResponseEntity showTransactions(){
        return ResponseEntity.status(200).body(adminService.showTransactions());
    }

    @PutMapping("discountstudent")
    public ResponseEntity discountStudent(){
        adminService.discountStudent();
        return ResponseEntity.status(200).body("discount set to students successfully !");
    }

    @GetMapping("showhighestrev")
    public ResponseEntity showHighestRev(){
        return ResponseEntity.status(200).body(adminService.showHighestCaptainRevenue());
    }

    @GetMapping("/display-complaints")
    public ResponseEntity displayComplaints() {
        return ResponseEntity.status(200).body(adminService.displayAllComplaints());
    }

    @PutMapping("/warn-captain/{captainid}")
    public ResponseEntity warnCaptain(@PathVariable int captainid) {
        adminService.makeWarnToCaptain(captainid);
        return ResponseEntity.status(200).body("captain warned successfully");
    }

    @PutMapping("/recovercaptainaccount")
    public ResponseEntity recoverCaptainAccount(@PathVariable int captainid) {
        adminService.recoverCaptainAccount(captainid);
        return ResponseEntity.status(200).body("captain account recovered successfully");
    }

    @GetMapping("/regioncount")
    public ResponseEntity regionCount() {
        return ResponseEntity.status(200).body(adminService.getCountPerRegion());
    }

    @PutMapping("/add-answer/{aid}/{qid}/{answer}")
    public ResponseEntity addAnswer(@PathVariable Integer aid,@PathVariable Integer qid,@PathVariable String answer)
    {
        adminService.answerQuestion(aid,qid, answer);
        return ResponseEntity.status(200).body(("Question answered successfully"));
    }

}
