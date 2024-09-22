package com.bank.bank.Security;
import com.bank.bank.Entities.Customer;
import com.bank.bank.Entities.Employee;
import com.bank.bank.Repositories.CustomerRepository;
import com.bank.bank.Repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer userCustomer = customerRepository.findByUsername(username);
        Employee userEmployee = employeeRepository.findByUsername(username);
        if (userCustomer == null && userEmployee == null) {
            throw new UsernameNotFoundException("User not found");
        }
        if(userCustomer!=null){
            return new org.springframework.security.core.userdetails.User(
                    userCustomer.getUsername(), userCustomer.getPassword(),
                    AuthorityUtils.createAuthorityList("ROLE_" + userCustomer.getRole()));
        }
        return new org.springframework.security.core.userdetails.User(
                userEmployee.getUsername(), userEmployee.getPassword(),
                AuthorityUtils.createAuthorityList("ROLE_" + userEmployee.getRole()));
    }
}
