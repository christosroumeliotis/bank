package com.bank.bank.Services;

import com.bank.bank.Entities.Customer;
import com.bank.bank.Entities.Employee;
import com.bank.bank.Entities.Loan;
import com.bank.bank.Entities.UserEntity;
import com.bank.bank.Repositories.CustomerRepository;
import com.bank.bank.Repositories.EmployeeRepository;
import com.bank.bank.Repositories.LoanRepository;
import com.bank.bank.Repositories.UserRepository;
import com.bank.bank.ResourceNotFoundException;
import com.bank.bank.Security.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PdfGenerationService pdfGenerationService;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    PasswordEncoder passwordEncoder;


    public String createEmployee(Employee employee) {
        if (userRepository.findByAfm(employee.getAfm()).isPresent()) {
            // Return message if the user with the same AFM already exists
            return "Employee with AFM " + employee.getAfm() + " already exists.";
        } else {

            Employee employeeUsername=employeeRepository.findByUsername(employee.getUsername());
            if(employeeUsername!=null){
                return"Username already in use, choose different";
            }

            List<Employee> employees=employeeRepository.findByPassword(employee.getPassword());
            if(!employees.isEmpty()){
                return"Password already in use, choose different";
            }

            employee.setPassword(passwordEncoder.encode(employee.getPassword()));
            employee.setRole("EMPLOYEE");
            // Save the new user and return a success message
            employeeRepository.save(employee);
            return "Employee with AFM " + employee.getAfm() + " has been successfully added.";
        }
    }

    public String createCustomer(Customer customer) {
        if (userRepository.findByAfm(customer.getAfm()).isPresent()) {
            // Return message if the user with the same AFM already exists
            return "Customer with AFM " + customer.getAfm() + " already exists.";
        } else {
            Customer customerUsername=customerRepository.findByUsername(customer.getUsername());
            if(customerUsername!=null){
                return"Username already in use, choose different";
            }

            List<Customer> customers=customerRepository.findByPassword(customer.getPassword());
            if(!customers.isEmpty()){
                return"Password already in use, choose different";
            }

            customer.setPassword(passwordEncoder.encode(customer.getPassword()));
            customer.setRole("CUSTOMER");
            // Save the new user and return a success message
            customerRepository.save(customer);
            return "Customer with AFM " + customer.getAfm() + " has been successfully added.";
        }
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Transactional
    public String deleteCustomer(String afm) {
        if (customerRepository.findByAfm(afm).isPresent()) {
            customerRepository.deleteByAfm(afm);
            return "Customer with AFM " + afm + " has been successfully deleted.";
        } else {
            throw new ResourceNotFoundException("Customer not found with AFM: " + afm);
        }
    }

    @Transactional
    public String deleteEmployee(String afm) {
        if (employeeRepository.findByAfm(afm).isPresent()) {
            employeeRepository.deleteByAfm(afm);
            return "User with AFM " + afm + " has been successfully deleted.";
        } else {
            throw new ResourceNotFoundException("Employee not found with AFM: " + afm);
        }
    }


    public String getLoan(String afm,BigDecimal amount) {

        Optional<Customer> user = customerRepository.findByAfm(afm);
        userNotFound(user,afm);

        if(user.get().getLoan()!=null){
            return "Customer with AFM: "+user.get().getAfm()+" already has a Loan and can't take second before pay his debt";
        }

        Loan loan = new Loan(amount,user.get());
        loanRepository.save(loan);
        user.get().setLoan(loan);

        BigDecimal value;
        value = user.get().getLoanDebt().add(amount);

        user.get().setLoanDebt(value);
        userRepository.save(user.get());
        return "Customer with AFM: "+user.get().getAfm()+" took a Loan of amount: "+loan.getAmount();

    }

    public String payLoan(String afm, BigDecimal amount) {

        Optional<Customer> user = customerRepository.findByAfm(afm);
        userNotFound(user,afm);

        BigDecimal value=user.get().getLoanDebt();
        boolean isEqualInt = value.compareTo(BigDecimal.valueOf(0.0)) == 0;

        if(isEqualInt){
            return "The debt is 0.0";
        }else if (value.compareTo(amount) < 0){
            return "The debt is less than the amount you entered.";
        }else{

            user.get().setLoanDebt(value.subtract(amount));
            userRepository.save(user.get());
            if(user.get().getLoanDebt().compareTo(BigDecimal.valueOf(0.0)) == 0){
                loanRepository.delete(user.get().getLoan());
                user.get().setLoan(null);
                userRepository.save(user.get());
            }

            return "The amount of your debt is " +user.get().getLoanDebt();
        }
    }

    public String deposit(String afm,BigDecimal amount) {
        Optional<Customer> user = customerRepository.findByAfm(afm);
        userNotFound(user,afm);

        Customer user1 = user.get();
        user1.setMoneyDeposited(user1.getMoneyDeposited().add(amount));
        userRepository.save(user.get());
        return "Your amount of savings is: " + user1.getMoneyDeposited();
    }

    public void userNotFound(Optional user,String afm){
        if(user.isEmpty()){
            throw new ResourceNotFoundException("User not found with AFM: " + afm);
        }
    }

    public String withdraw(String afm, BigDecimal amount) {
        Optional<Customer> user = customerRepository.findByAfm(afm);
        userNotFound(user,afm);

        Customer user1 = user.get();
        if(user1.getMoneyDeposited().compareTo(amount)<0){
            return "You don't have so much money to withdraw";
        }
        user1.setMoneyDeposited(user1.getMoneyDeposited().subtract(amount));
        userRepository.save(user.get());
        return "Your amount of savings is: " + user1.getMoneyDeposited();
    }

    public String getSavings(String afm) {
        Optional<Customer> user = customerRepository.findByAfm(afm);
        userNotFound(user,afm);

        return "Customer's savings are: "+user.get().getMoneyDeposited();
    }

    public String getDebt(String afm) {
        Optional<Customer> user = customerRepository.findByAfm(afm);
        userNotFound(user,afm);

        return"Customer's debt is: "+user.get().getLoanDebt();
    }

    public ResponseEntity<byte[]> printPdfByAfm(String afm) {

        try {
            byte[] pdfBytes =pdfGenerationService.createPdfWithDatabaseData(afm);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "database-data.pdf");
            headers.setContentLength(pdfBytes.length);

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

