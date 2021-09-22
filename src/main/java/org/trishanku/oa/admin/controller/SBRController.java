package org.trishanku.oa.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.trishanku.oa.admin.entity.SBR;
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
    @GetMapping(path = "/{sbrId}")
    public ResponseEntity<SBRDTO> getSBRById(@PathVariable(name="sbrId") String sbrId)
    {
        return  new ResponseEntity<>(sbrService.getSBRById(sbrId), HttpStatus.OK);
    }



    //to get list of SBR's for a given anchor customer
    @GetMapping(path = "/anchorcustomer/{anchorCustomerId}")
    public ResponseEntity<List<SBRDTO>> getSBRsByAnchorCustomerId(@PathVariable(name="anchorCustomerId") String anchorCustomerId)
    {
        return  new ResponseEntity<>(sbrService.getSBRsByAnchorCustomer(anchorCustomerId), HttpStatus.OK);
    }

    //to get list of SBR's for a given counter party
    @GetMapping(path = "/counterparty/{counterPartyId}")
    public ResponseEntity<List<SBRDTO>> getSBRsByCounterPartyId(@PathVariable(name="counterPartyId") String counterPartyId)
    {
        return  new ResponseEntity<>(sbrService.getSBRsByCounterParty(counterPartyId), HttpStatus.OK);
    }

    //modify an SBR

    //add and SBR

    //authorise an SBR
    //to get a list of all master SBR's
    @PutMapping(path = "/authorise")
    public ResponseEntity<SBRDTO> authoriseSBR(@RequestBody SBRDTO sbrdto)
    {
        return  new ResponseEntity<>(sbrService.authorise(sbrdto), HttpStatus.OK);
    }

    //delete an SBR
    @PutMapping(path = "/delete")
    public ResponseEntity<SBRDTO> deleteSBR(@RequestBody SBRDTO sbrdto)
    {
        return  new ResponseEntity<>(sbrService.delete(sbrdto), HttpStatus.OK);
    }


}
