package org.trishanku.oa.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trishanku.oa.admin.model.SBRDTO;
import org.trishanku.oa.admin.service.SBRService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sbrs")
public class SBRController {

    @Autowired
    SBRService sbrService;

    //to get a list of all SBR'S
    @GetMapping()
    public ResponseEntity<List<SBRDTO>> getAllSBRs()
    {
        return  new ResponseEntity<>(sbrService.getAllSBRs(), HttpStatus.OK);
    }

    //to get a list of all pending SBR's
    @GetMapping(path = "/pending")
    public ResponseEntity<List<SBRDTO>> getAllPendingSBRs()
    {
        return  new ResponseEntity<>(sbrService.getAllPendingSBRs(), HttpStatus.OK);
    }

    //to get a list of all master SBR's
    @GetMapping(path = "/master")
    public ResponseEntity<List<SBRDTO>> getAllMasterSBRs()
    {
        return  new ResponseEntity<>(sbrService.getAllMasterSBRs(), HttpStatus.OK);
    }

    //to get a specific SBR

    //to get list of SBR's for a given anchor customer

    //to get list of SBR's for a given counter party

    //modify an SBR

    //add and SBR

    //authorise an SBR

    //delete an SBR


}
