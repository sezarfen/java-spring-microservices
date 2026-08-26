package com.pm.patientservice.service;

import billing.BillingResponse;
import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.exception.EmailAlreadyExistsException;
import com.pm.patientservice.exception.PatientNotFoundException;
import com.pm.patientservice.grpc.BillingServiceGrpcClient;
import com.pm.patientservice.kafka.KafkaProducer;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PatientService {

    private static final Logger log = LoggerFactory.getLogger(PatientService.class);
    private final PatientRepository patientRepository;
    private final KafkaProducer kafkaProducer;

    private final BillingServiceGrpcClient billingServiceGrpcClient;

    public PatientService(PatientRepository patientRepository,
                          BillingServiceGrpcClient billingServiceGrpcClient,
                          KafkaProducer kafkaProducer){
        this.patientRepository = patientRepository;
        this.billingServiceGrpcClient = billingServiceGrpcClient;
        this.kafkaProducer = kafkaProducer;
    }

    public List<PatientResponseDTO> getPatients(){
        return patientRepository.findAll().stream().map(PatientMapper::toDTO).toList();
    }

    public PatientResponseDTO getPatient(UUID uuid){
        return PatientMapper.toDTO(patientRepository.getReferenceById(uuid));
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO){
        // Kendi custom exception classımızı yazmak log trace yaparken iş görüyor.
        if (patientRepository.existsByEmail(patientRequestDTO.getEmail())){
            throw new EmailAlreadyExistsException("A patient with this " +
                    "email already exists " + patientRequestDTO.getEmail());
        }
        Patient newPatient =
                patientRepository.save(PatientMapper.toModel(patientRequestDTO));

        BillingResponse billingResponse = billingServiceGrpcClient.createBillingAccount(newPatient.getId().toString(),
                newPatient.getName(),
                newPatient.getEmail(),
                "ACTIVE");

        kafkaProducer.sendEvent(newPatient);

        log.info("BillingResponse received inside PatientService: {}", billingResponse);

        return PatientMapper.toDTO(newPatient);

    }

    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO){
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException("Patient not found with ID: " + id));

        // bu email bu patient'a ait değil mi kontrol için PatientRepository'de existsByEmailAndIdNot(String, UUID) yazabiliriz
        // if (patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(), id))
        if (!patientRequestDTO.getEmail().equals(patient.getEmail()) && patientRepository.existsByEmail(patientRequestDTO.getEmail()))
        {
            throw new EmailAlreadyExistsException("A patient with this " +
                    "email already exists " + patientRequestDTO.getEmail());
        }

        patient.setName(patientRequestDTO.getName());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));

        return PatientMapper.toDTO(patientRepository.save(patient));
    }

    public void deletePatient(UUID id){
        patientRepository.deleteById(id);
    }
}
