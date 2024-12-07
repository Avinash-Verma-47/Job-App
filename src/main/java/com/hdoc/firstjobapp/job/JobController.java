package com.hdoc.firstjobapp.job;

import com.hdoc.firstjobapp.company.Company;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/jobs")
public class JobController {
    private JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public ResponseEntity<List<Job>> findAll(){
        return ResponseEntity.ok(jobService.findAll());
    }

    @PostMapping
    public ResponseEntity<String> createJob(@RequestBody Job job){
        jobService.createJob(job);
        Company c= job.getCompany();
        if(c==null) new ResponseEntity<>("Company Does not Exist", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>("Job Added Successfully", HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> findJobById(@PathVariable Long id){
        Job job = jobService.getJobById(id);
        if(job!=null)
            return new ResponseEntity<>(job, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJobById(@PathVariable Long id){
        boolean delete = jobService.deleteJobById(id);
        if(delete)
            return new ResponseEntity<>("Job with Id: "+id+" Deleted Successfully",HttpStatus.OK);
        return new ResponseEntity<>("Job Id Not Found",HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    //We can alternatively use the request mapping as below:
//    @RequestMapping(value = "/jobs/{id}", method = RequestMethod.PUT)
    public ResponseEntity<String> updateJob(@PathVariable Long id, @RequestBody Job updatedJob){
        boolean update= jobService.updateJob(id, updatedJob);
        if(update)
            return new ResponseEntity<>("Job with Id: "+ id+" Updated Successfully",HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
