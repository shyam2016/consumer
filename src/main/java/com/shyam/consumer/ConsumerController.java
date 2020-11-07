package com.shyam.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("emp")
@Slf4j
public class ConsumerController {
    @Autowired
    private EmployeeService service;

    @GetMapping("/non-blocking")
    public ResponseEntity<Mono<EmployeeDetails>> getEmployee() throws InterruptedException {
        // call http://localhost:8081/employee
        log.info("Starting method: non blocking");
        Mono<EmployeeDetails> employeeDetails = service.getEmployeeDetails();
        return ResponseEntity.ok(employeeDetails);
    }

    @GetMapping("/blocking")
    public ResponseEntity<Employee> getEmployeeBlocking() {
        // call http://localhost:8081/employee

        log.info("Strting method: blocking");
        Employee employeeBlocking = service.getEmployeeBlocking();
        log.info("Print result:",employeeBlocking.getEmployeeName());
        log.info("Ending Method: blocking");
        return ResponseEntity.ok(employeeBlocking);
    }

    @GetMapping(value = "/num",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Flux<Integer>> getNumbers(){
        return ResponseEntity.ok().body(Flux.just(1,2,3).delayElements(Duration.ofSeconds(1)).log());
    }
}
