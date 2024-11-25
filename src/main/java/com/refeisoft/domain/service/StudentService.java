package com.refeisoft.domain.service;

import com.refeisoft.api.dto.PageResponseDTO;
import com.refeisoft.api.dto.StudentRequestDTO;
import com.refeisoft.api.dto.StudentResponseDTO;
import com.refeisoft.api.mapper.StudentMapper;
import com.refeisoft.domain.entity.Credit;
import com.refeisoft.domain.entity.Student;
import com.refeisoft.domain.enums.MealType;
import com.refeisoft.domain.enums.Status;
import com.refeisoft.domain.repository.CreditRepository;
import com.refeisoft.domain.repository.StudentRepository;
import com.refeisoft.infra.exception.DuplicateAttributeException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final CreditRepository creditRepository;
    private final StudentMapper studentMapper;
    private static final int PAGE_SIZE = 10;

    public StudentService(StudentRepository studentRepository, CreditRepository creditRepository, StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.creditRepository = creditRepository;
        this.studentMapper = studentMapper;
    }

    public PageResponseDTO getAllStudents(int page) {
        Sort sort = Sort.by(Sort.Direction.ASC, "name");
        PageRequest pageRequest = PageRequest.of(page, PAGE_SIZE, sort);
        Page<StudentResponseDTO> studentPage = studentRepository.findAll(pageRequest).map(studentMapper::toStudentResponseDTO);

        return new PageResponseDTO(studentPage.getContent(), studentPage.getTotalPages());
    }

    @Transactional
    public StudentResponseDTO createStudent(StudentRequestDTO requestDTO) {
        verifyRegistrationNumber(requestDTO.registrationNumber());
        verifyEmail(requestDTO.email());
        Student student = studentMapper.toStudentEntity(requestDTO);
        student.setStatus(Status.ACTIVE);
        Student studentCreated = studentRepository.save(student);

        createStudentCredits(studentCreated);
        return studentMapper.toStudentResponseDTO(studentCreated);
    }

    private void createStudentCredits(Student student) {
        LocalDateTime dateTime = LocalDateTime.now();
        for (MealType mealType : MealType.values()) {
            Credit credit = new Credit(student, mealType, 0, dateTime);
            creditRepository.save(credit);
        }
    }

    @Transactional
    public StudentResponseDTO updateStudent(Long studentId, StudentRequestDTO requestDTO) {
        Student studentToUpdate = getStudentById(studentId);
        boolean registrationNumberChanged = !requestDTO.registrationNumber().equals(studentToUpdate.getRegistrationNumber());
        boolean emailChanged = !requestDTO.email().equals(studentToUpdate.getEmail());

        if (registrationNumberChanged) {
            verifyRegistrationNumber(requestDTO.registrationNumber());
        }

        if (emailChanged) {
            verifyEmail(requestDTO.email());
        }

        studentMapper.toUpdateStudent(requestDTO, studentToUpdate);
        Student studentUpdated = studentRepository.save(studentToUpdate);

        return studentMapper.toStudentResponseDTO(studentUpdated);
    }

    @Transactional
    public void deleteStudent(Long studentId) {
        Student student = getStudentById(studentId);
        studentRepository.delete(student);
    }

    private Student getStudentById(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado."));
    }

    private void verifyRegistrationNumber(String registrationNumber) {
        if (studentRepository.existsByRegistrationNumber(registrationNumber)) {
            throw new DuplicateAttributeException("O número de matrícula informado já existe.");
        }
    }

    private void verifyEmail(String email) {
        if (studentRepository.existsByEmail(email)) {
            throw new DuplicateAttributeException("O email informado já está existe.");
        }
    }
}
