package com.example.ebankingbackend.services;

import com.example.ebankingbackend.dtos.CustomerDTO;
import com.example.ebankingbackend.entities.*;
import com.example.ebankingbackend.enums.OperationType;
import com.example.ebankingbackend.exceptions.BalanceNotSufficientException;
import com.example.ebankingbackend.exceptions.BankAccountNotFoundException;
import com.example.ebankingbackend.exceptions.CustomerNotFoundException;
import com.example.ebankingbackend.mappers.BankAccountMapperImpl;
import com.example.ebankingbackend.repositories.AccountOperationsRepository;
import com.example.ebankingbackend.repositories.BankAccountRepository;
import com.example.ebankingbackend.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j


public class BankAccountServicesImpl implements BankAccountServices {


        private CustomerRepository customerRepository;
        private BankAccountRepository bankAccountRepository;
        private AccountOperationsRepository accountOperationsRepository;
        private BankAccountMapperImpl dtoMapper;


    @Override
    public Customer saveCustomer(Customer customer) {
        log.info("Saving new Customer");
        Customer SavedCustomer= customerRepository.save(customer);
        return SavedCustomer;
    }



    @Override
    public SavingAccount saveSavingBankAccount(double initialBalance, double interestRate, Long customerID) throws CustomerNotFoundException {
        SavingAccount bankAccount = new SavingAccount();

        Customer customer= customerRepository.findById(customerID).orElse(null);
        if(customer==null){
            throw new CustomerNotFoundException("Customer not Found");
        }
        bankAccount.setID(UUID.randomUUID().toString());
        bankAccount.setCreationDate(new Date());
        bankAccount.setBalance(initialBalance);
        bankAccount.setInterestRate(interestRate);
        bankAccount.setCustomer(customer);
        SavingAccount savedBankAccount=bankAccountRepository.save(bankAccount);
        return savedBankAccount;


    }

    @Override
    public CurrentAccount saveCurrentBankAccount(double initialBalance, double overDraft, Long customerID) throws CustomerNotFoundException
    {
        CurrentAccount bankAccount = new CurrentAccount();

        Customer customer= customerRepository.findById(customerID).orElse(null);
        if(customer==null){
            throw new CustomerNotFoundException("Customer not Found");
        }
        bankAccount.setID(UUID.randomUUID().toString());
        bankAccount.setCreationDate(new Date());
        bankAccount.setBalance(initialBalance);
        bankAccount.setOverDraft(overDraft);
        bankAccount.setCustomer(customer);
CurrentAccount savedBankAccount=bankAccountRepository.save(bankAccount);
        return savedBankAccount;
    }

    @Override
    public List<CustomerDTO> lisCustomers() {
       List<Customer> customers=customerRepository.findAll();
    List<CustomerDTO> collect= customers.stream().map(customer -> dtoMapper.fromCustomer(customer)).collect(Collectors.toList());
     return collect;
    }

    @Override
    public BankAccount getBankAccount(String accountID) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountID).orElseThrow(()->new BankAccountNotFoundException("BankAccount not Found!"));

    return bankAccount;
    }

    @Override
    public void debit(String accountID, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
BankAccount bankAccount =getBankAccount(accountID);
if(bankAccount.getBalance()<amount)
    throw new BalanceNotSufficientException("Balance_not_Sufficient");
        AccountOperations accountOperations=new AccountOperations();
        accountOperations.setOperationType(OperationType.DEBIT);
        accountOperations.setOperationDate(new Date());
        accountOperations.setAmount(amount);
        accountOperations.setDescription(description);
        accountOperations.setBankAccount(bankAccount);
        accountOperationsRepository.save(accountOperations);
        bankAccount.setBalance(bankAccount.getBalance()-amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void credit(String accountID, double amount, String description) throws BankAccountNotFoundException {
        BankAccount bankAccount =getBankAccount(accountID);
        AccountOperations accountOperations=new AccountOperations();
        accountOperations.setOperationType(OperationType.DEBIT);
        accountOperations.setOperationDate(new Date());
        accountOperations.setAmount(amount);
        accountOperations.setDescription(description);
        accountOperations.setBankAccount(bankAccount);
        accountOperationsRepository.save(accountOperations);
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        bankAccountRepository.save(bankAccount);

    }

    @Override
    public void transfer(String accountIDSource, String accountIDDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException {
        debit(accountIDSource,amount,"transfer to "+accountIDDestination);
        credit(accountIDDestination,amount,"transfer from "+accountIDSource);


    }
    @Override
    public List<BankAccount> bankAccountList(){
        return bankAccountRepository.findAll();
    }
}
