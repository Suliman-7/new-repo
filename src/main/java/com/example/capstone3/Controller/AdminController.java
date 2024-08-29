package com.example.capstone3.Controller;

import com.example.capstone3.Model.Admin;
import com.example.capstone3.Service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")

public class AdminController {

    private final AdminService adminService;

    // Suliman
    @GetMapping("/get")
    public ResponseEntity getAllAdmin() {
        return ResponseEntity.status(200).body(adminService.getAllAdmins());
    }

    // Suliman
    @PostMapping("/post")
    public ResponseEntity addAdmin(@Valid @RequestBody Admin admin) {
        adminService.addAdmin(admin);
        return ResponseEntity.status(200).body("admin successfully added");
    }

    // Suliman
    @PutMapping("/update/{id}")
    public ResponseEntity updateAdmin(@PathVariable int id, @Valid @RequestBody Admin admin) {
        adminService.updateAdmin(id, admin);
        return ResponseEntity.status(200).body("admin successfully updated");
    }

    // Suliman
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteAdmin(@PathVariable int id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.status(200).body("admin successfully deleted");
    }


    // Abdulrahman
    @DeleteMapping("/delete-by-comment/{comment}")
    public ResponseEntity deleteByComment(@PathVariable String comment)
    {
        adminService.deleteByComment(comment);
        return ResponseEntity.status(200).body("Comment deleted");
    }

    // Suliman
    @GetMapping("showtransaction")
    public ResponseEntity showTransactions(){
        return ResponseEntity.status(200).body(adminService.showTransactions());
    }

    // Suliman
    @PutMapping("discountstudent")
    public ResponseEntity discountStudent(){
        adminService.discountStudent();
        return ResponseEntity.status(200).body("discount set to students successfully !");
    }

    // Suliman
    @GetMapping("showhighestrev")
    public ResponseEntity showHighestRevenue(){
        return ResponseEntity.status(200).body(adminService.showHighestCaptainRevenue());
    }

    // Abdulaziz
    @GetMapping("/display-complaints")
    public ResponseEntity displayComplaints() {
        return ResponseEntity.status(200).body(adminService.displayAllComplaints());
    }

    // Abdulaziz
    @PutMapping("/warn-captain/{captainid}")
    public ResponseEntity warnCaptain(@PathVariable int captainid) {
        adminService.makeWarnToCaptain(captainid);
        return ResponseEntity.status(200).body("captain warned successfully");
    }




    // Suliman
    @PutMapping("/recovercaptainaccount/{captainid}")
    public ResponseEntity recoverCaptainAccount(@PathVariable int captainid) {
        adminService.recoverCaptainAccount(captainid);
        return ResponseEntity.status(200).body("captain account recovered successfully");
    }

    // Abdulrahman
    @GetMapping("/regioncount")
    public ResponseEntity regionCount() {
        return ResponseEntity.status(200).body(adminService.getCountPerRegion());
    }

    // Abdulrahman
    @PutMapping("/add-answer/{aid}/{qid}/{answer}")
    public ResponseEntity addAnswer(@PathVariable Integer aid,@PathVariable Integer qid,@PathVariable String answer)
    {
        adminService.answerQuestion(aid,qid, answer);
        return ResponseEntity.status(200).body(("Question answered successfully"));
    }

    // Suliman
    @PutMapping("/activatecaptainaccount/{captainid}")
    public ResponseEntity activateCaptainAccount(@PathVariable int captainid) {
        adminService.activateCaptainAccount(captainid);
        return ResponseEntity.status(200).body(("Captain account activated successfully"));
    }

}
