package com.refeisoft.api.controller;

import com.refeisoft.api.dto.PageResponseDTO;
import com.refeisoft.api.dto.StudentRequestDTO;
import com.refeisoft.api.dto.StudentResponseDTO;
import com.refeisoft.domain.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<PageResponseDTO> getAll(@RequestParam int page) {
        PageResponseDTO pageResponseDTO = studentService.getAllStudents(page);
        return ResponseEntity.ok(pageResponseDTO);
    }

    @PostMapping
    public ResponseEntity<StudentResponseDTO> create(@RequestBody StudentRequestDTO requestDTO) {
        StudentResponseDTO studentResponseDTO = studentService.createStudent(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(studentResponseDTO);
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<StudentResponseDTO> update(@PathVariable Long studentId, @RequestBody StudentRequestDTO requestDTO) {
        StudentResponseDTO studentResponseDTO = studentService.updateStudent(studentId, requestDTO);
        return ResponseEntity.ok(studentResponseDTO);
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<Void> delete(@PathVariable Long studentId) {
        studentService.deleteStudent(studentId);
        return ResponseEntity.noContent().build();
    }
}
