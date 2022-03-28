package com.emsi.patientsapp;

import com.emsi.patientsapp.entities.Patient;
import com.emsi.patientsapp.repository.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class PatientsAppApplication implements CommandLineRunner {

    private PatientRepository PR;
    public static void main(String[] args) {

        SpringApplication.run(PatientsAppApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        PR.save(new Patient(null,"hassan",new Date(),false,40));

    }
}
