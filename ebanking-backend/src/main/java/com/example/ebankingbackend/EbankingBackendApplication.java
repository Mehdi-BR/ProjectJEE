package com.example.ebankingbackend;

import com.example.ebankingbackend.entities.*;
import com.example.ebankingbackend.enums.AccountStatus;
import com.example.ebankingbackend.enums.OperationType;
import com.example.ebankingbackend.exceptions.BalanceNotSufficientException;
import com.example.ebankingbackend.exceptions.BankAccountNotFoundException;
import com.example.ebankingbackend.exceptions.CustomerNotFoundException;
import com.example.ebankingbackend.repositories.AccountOperationsRepository;
import com.example.ebankingbackend.repositories.BankAccountRepository;
import com.example.ebankingbackend.repositories.CustomerRepository;
import com.example.ebankingbackend.services.BankAccountServices;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankingBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbankingBackendApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(BankAccountServices bankAccountServices){
        return args->{
           Stream.of("Slimani","Elbennani","ElMejati").forEach(name->{
               Customer customer=new Customer();
               customer.setName(name);
               customer.setEmail(name+"@mail.com");
               bankAccountServices.saveCustomer(customer);
           });
           bankAccountServices.lisCustomers().forEach(customer -> {
               try {
                   bankAccountServices.saveCurrentBankAccount(Math.random()*90000,90000,customer.getId());
                   bankAccountServices.saveSavingBankAccount(Math.random()*622542, Math.random()*6,customer.getId());
                     List<BankAccount> bankAccountList=bankAccountServices.bankAccountList();
                     for(BankAccount bankAccount:bankAccountList){
                        for(int i=0;i<10;i++){
                             bankAccountServices.credit(bankAccount.getID(), 10000 + Math.random() * 12000, "credit");
                             bankAccountServices.debit(bankAccount.getID(),1000+Math.random()*9000,"description");
                         }
                     }

               } catch (CustomerNotFoundException | BankAccountNotFoundException | BalanceNotSufficientException e) {
                   e.printStackTrace();
               }
           });
        };
    }
//@Bean
    CommandLineRunner start(CustomerRepository customerRepository,
                            BankAccountRepository bankAccountRepository,
                            AccountOperationsRepository accountOperationsRepository){
        return args -> {
          Stream.of("Slimani","BENMAATI","OUAZZANI").forEach(name->{
              Customer customer=new Customer();
              customer.setName(name);
              customer.setEmail(name+"@mail.com");

              customerRepository.save(customer);
          });
          customerRepository.findAll().forEach(customer -> {

              CurrentAccount currentAccount=new CurrentAccount();
              currentAccount.setID(UUID.randomUUID().toString());
              currentAccount.setBalance(Math.random()*980000);
              currentAccount.setCreationDate(new Date());
              currentAccount.setStatus(AccountStatus.CREATED);
              currentAccount.setCustomer(customer);
              currentAccount.setOverDraft(9000);
              bankAccountRepository.save(currentAccount);

              SavingAccount savingAccount=new SavingAccount();
              savingAccount.setID(UUID.randomUUID().toString());
              savingAccount.setBalance(Math.random()*980000);
              savingAccount.setCreationDate(new Date());
              savingAccount.setStatus(AccountStatus.CREATED);
              savingAccount.setCustomer(customer);
              savingAccount.setInterestRate(4.5);
              bankAccountRepository.save(savingAccount);
          });
          bankAccountRepository.findAll().forEach(bankAccount -> {
              for(int i=0;i<5;i++){
                  AccountOperations accountOperations=new AccountOperations();
                  accountOperations.setOperationDate(new Date());
                  accountOperations.setAmount(Math.random()*40000);
                  accountOperations.setOperationType(Math.random()>0.5? OperationType.DEBIT:OperationType.CREDIT);
                  accountOperations.setBankAccount(bankAccount);
                  accountOperationsRepository.save(accountOperations);

              }
          });
        };

    }

}
