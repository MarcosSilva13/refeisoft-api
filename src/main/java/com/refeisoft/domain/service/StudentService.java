package com.refeisoft.domain.service;

import com.refeisoft.api.dto.BlockStudentRequestDTO;
import com.refeisoft.api.dto.PageResponseDTO;
import com.refeisoft.api.dto.StudentRequestDTO;
import com.refeisoft.api.dto.StudentResponseDTO;
import com.refeisoft.api.filter.StudentFilter;
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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

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

    public PageResponseDTO getAllStudents(int page, StudentFilter filter) {
        Sort sort = Sort.by(Sort.Direction.ASC, "name");
        PageRequest pageRequest = PageRequest.of(page, PAGE_SIZE, sort);
        Page<StudentResponseDTO> studentPage = studentRepository.findAll(filter.toStudentSpecification(), pageRequest)
                .map(studentMapper::toStudentResponseDTO);

        return new PageResponseDTO(studentPage.getContent(), studentPage.getTotalPages());
    }

    public StudentResponseDTO getStudent(Long studentId) {
        Student student = getStudentById(studentId);
        return studentMapper.toStudentResponseDTO(student);
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
        Student student = getStudentById(studentId);
        boolean registrationNumberChanged = !requestDTO.registrationNumber().equals(student.getRegistrationNumber());
        boolean emailChanged = !requestDTO.email().equals(student.getEmail());

        if (registrationNumberChanged) {
            verifyRegistrationNumber(requestDTO.registrationNumber());
        }

        if (emailChanged) {
            verifyEmail(requestDTO.email());
        }

        studentMapper.toUpdateStudent(requestDTO, student);
        return studentMapper.toStudentResponseDTO(student);
    }

    @Transactional
    public void deleteStudent(Long studentId) {
        Student student = getStudentById(studentId);
        studentRepository.delete(student);
    }

    @Transactional
    public StudentResponseDTO blockStudent(BlockStudentRequestDTO requestDTO) {
        Student student = getStudentById(requestDTO.studentId());
        student.setStatus(Status.BLOCKED);
        student.setBlockedUntil(requestDTO.blockUntil());

        return studentMapper.toStudentResponseDTO(student);
    }

    //@Scheduled(cron = "0 0 0 * * ?")
    @Scheduled(fixedDelay = 30, timeUnit = TimeUnit.SECONDS) // para testes
    public void automaticUnblockStudents() {
        LocalDate date = LocalDate.now();
        studentRepository.unblockStudents(date);
    }

    @Transactional
    public StudentResponseDTO manualUnblockStudent(Long studentId) {
        Student student = getStudentById(studentId);
        student.setStatus(Status.ACTIVE);

        return studentMapper.toStudentResponseDTO(student);
    }

    private Student getStudentById(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Aluno(a) não encontrado."));
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
