package com.shyam.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Date;

@Service
@Slf4j
public class EmployeeService {
    @Autowired
    private WebClient webClient;

    public Mono<Employee> getEmployeeProfile() throws InterruptedException {
        Date start = new Date();
        log.info("Starting Getting employee profile: "+ start.getTime());
        Mono<Employee> employeeMono = webClient.get()
                .uri("/employee/profile")
                .retrieve()
                .bodyToMono(Employee.class);
       // Thread.sleep(2000);

        Date endTime = new Date();
        log.info("End of employee profile: "+endTime.getTime());
        long diff = endTime.getTime() - start.getTime();
        log.info("Time took to process employee profile: "+diff);
        return employeeMono;

    }

    public Mono<Address> getEmployeeAdress() throws InterruptedException {
        Date start = new Date();
        log.info("start of employee Address: "+start.getTime());
        Mono<Address> addressMono = webClient.get()
                .uri("/employee/address")
                .retrieve()
                .bodyToMono(Address.class);
        Date endTime = new Date();
        log.info("End of Emplpyee Address: "+endTime.getTime());
        //Thread.sleep(3000);
        long diff = endTime.getTime() - start.getTime();
        log.info("Time took to process getEmployeeAdress: "+diff);
        return addressMono;

    }

    public Employee getEmployeeBlocking() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity("http://localhost:8081/employee", Employee.class).getBody();

    }

    public Mono<EmployeeDetails> getEmployeeDetails() throws InterruptedException {
        Date start = new Date();
        log.info("start of employee details: "+start.getTime());
        Mono<Employee> employeeProfile = getEmployeeProfile();
        Mono<Address> employeeAdress = getEmployeeAdress();
        Mono<EmployeeDetails> employeeDetails = Mono.zip(employeeProfile, employeeAdress).map(objects -> {
            Employee employee = objects.getT1();
            Address address = objects.getT2();
            return EmployeeDetails.builder().employeeId(employee.getEmployeeId())
                    .employeeName(employee.getEmployeeName())
                    .street(address.getStreet())
                    .city(address.getCity())
                    .state(address.getState())
                    .zipCode(address.getZipCode())
                    .build();

        });
        Date endTime = new Date();
        log.info("End  of employee details: "+endTime.getTime());
        long diff = endTime.getTime() - start.getTime();
        log.info("Time took to process getEmployeeDetails: "+diff);
        return employeeDetails;
    }
}
