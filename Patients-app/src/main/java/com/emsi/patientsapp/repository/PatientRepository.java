package com.emsi.patientsapp.repository;

import com.emsi.patientsapp.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient,Long> {
}
