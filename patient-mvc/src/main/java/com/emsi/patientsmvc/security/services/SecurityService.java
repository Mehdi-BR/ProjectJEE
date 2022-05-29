package com.emsi.patientsmvc.security.services;

import com.emsi.patientsmvc.entities.Patient;
import com.emsi.patientsmvc.security.entities.AppUser;
import com.emsi.patientsmvc.security.entities.AppRole;

import java.util.List;

public interface SecurityService {
    AppUser saveNewUser(String username,String password,String rePassword);
    AppRole saveNewRole(String role,String description);
    void addRoleToUser(String username,String role);
    void addPatientToUsersPatients(String username,Long patientID);
    void removeRoleFromUser(String username,String role);
    AppUser loadUserByUsername(String username);
}
