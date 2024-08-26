package com.example.capstone3.Controller;

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

    @PutMapping("{sid}/subscribe/{cid}/{period}")
    public ResponseEntity subscribeStudent(@PathVariable int sid, @PathVariable int cid, @PathVariable @Min(value = 1) @Max(value = 3) int period) {
        studentService.subscribeStudent(sid, cid, period);
        return ResponseEntity.status(200).body("Student subscribed successfully");
    }

}
