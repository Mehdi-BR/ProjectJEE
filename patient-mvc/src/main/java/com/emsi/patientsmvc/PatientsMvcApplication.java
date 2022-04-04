package com.emsi.patientsmvc;

import com.emsi.patientsmvc.entities.Patient;
import com.emsi.patientsmvc.repositories.PatientRepository;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class PatientsMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientsMvcApplication.class, args);
    }
    @Bean
CommandLineRunner commandLineRunner(PatientRepository PR){
        return args -> {
/*PR.save(new Patient(null,"hassan",new Date(),false,12));*/
           /* PR.save(
                    new Patient(  null, "Mohammed", new Date(),  true, 321));
            PR.save(
                    new Patient( null,  "Yasmine", new Date(),  true,  65));
            PR.save(
                    new Patient(  null,  "Hanae", new Date(), false,  32));

*/
            PR.findAll().forEach(patient -> {
                System.out.println(patient.getNom());
            });
        };
}
}
