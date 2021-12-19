package org.trishanku.oa.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
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
    @PreAuthorize("hasAnyAuthority({'BANK_USER_MAKER','BANK_USER_VIEWER','BANK_USER_CHECKER' })")
    @Transactional
    public ResponseEntity<List<SBRDTO>> getAllSBRs()
    {
        return  new ResponseEntity<>(sbrService.getAllSBRs(), HttpStatus.OK);
    }

    //Mapping to get reference
    @GetMapping(path = "/getNewReference")
    @PreAuthorize("hasAnyAuthority({'BANK_USER_MAKER','BANK_USER_VIEWER','BANK_USER_CHECKER' })")
    @Transactional
    public ResponseEntity<String> getNewReference()
    {

        String newReference= sbrService.getNewReference();
        return new ResponseEntity<>(newReference, HttpStatus.OK);
    }

    //to get a list of all pending SBR's
    @GetMapping(path = "/pending")
    @PreAuthorize("hasAnyAuthority({'BANK_USER_MAKER','BANK_USER_VIEWER','BANK_USER_CHECKER' })")
    @Transactional
    public ResponseEntity<List<SBRDTO>> getAllPendingSBRs()
    {
        return  new ResponseEntity<>(sbrService.getAllPendingSBRs(), HttpStatus.OK);
    }

    //to get a list of all master SBR's
    @GetMapping(path = "/master")
    @PreAuthorize("hasAnyAuthority({'BANK_USER_MAKER','BANK_USER_VIEWER','BANK_USER_CHECKER' })")
    @Transactional
    public ResponseEntity<List<SBRDTO>> getAllMasterSBRs()
    {
        return  new ResponseEntity<>(sbrService.getAllMasterSBRs(), HttpStatus.OK);
    }

    //to get a specific SBR
    @GetMapping(path = "/{sbrId}")
    @PreAuthorize("hasAnyAuthority({'BANK_USER_MAKER','BANK_USER_VIEWER','BANK_USER_CHECKER' })")
    @Transactional
    public ResponseEntity<SBRDTO> getSBRById(@PathVariable(name="sbrId") String sbrId)
    {
        return  new ResponseEntity<>(sbrService.getSBRById(sbrId), HttpStatus.OK);
    }



    //to get list of SBR's for a given anchor customer
    @GetMapping(path = "/anchorcustomer/{anchorCustomerId}")
    @PreAuthorize("hasAnyAuthority({'BANK_USER_MAKER','BANK_USER_VIEWER','BANK_USER_CHECKER' })")
    @Transactional
    public ResponseEntity<List<SBRDTO>> getSBRsByAnchorCustomerId(@PathVariable(name="anchorCustomerId") String anchorCustomerId)
    {
        return  new ResponseEntity<>(sbrService.getSBRsByAnchorCustomer(anchorCustomerId), HttpStatus.OK);
    }

    //to get list of SBR's for a given counter party
    @GetMapping(path = "/counterparty/{counterPartyId}")
    @PreAuthorize("hasAnyAuthority({'BANK_USER_MAKER','BANK_USER_VIEWER','BANK_USER_CHECKER' })")
    @Transactional
    public ResponseEntity<List<SBRDTO>> getSBRsByCounterPartyId(@PathVariable(name="counterPartyId") String counterPartyId)
    {
        return  new ResponseEntity<>(sbrService.getSBRsByCounterParty(counterPartyId), HttpStatus.OK);
    }

    //modify an SBR
    @PutMapping()
    @PreAuthorize("hasAnyAuthority({'BANK_USER_MAKER'})")
    @Transactional
    public ResponseEntity<SBRDTO> editSBR(@RequestBody SBRDTO sbrdto)
    {
        return  new ResponseEntity<>(sbrService.editSBR(sbrdto), HttpStatus.OK);
    }

    //add an SBR
    @PostMapping()
    @PreAuthorize("hasAnyAuthority({'BANK_USER_MAKER' })")
    @Transactional
    public ResponseEntity<SBRDTO> addSBR(@RequestBody SBRDTO sbrdto)
    {
        return  new ResponseEntity<>(sbrService.addSBR(sbrdto), HttpStatus.OK);
    }

    //authorise an SBR
    @PutMapping(path = "/authorise")
    @PreAuthorize("hasAnyAuthority({'BANK_USER_CHECKER' })")
    @Transactional
    public ResponseEntity<SBRDTO> authoriseSBR(@RequestBody SBRDTO sbrdto)
    {
        return  new ResponseEntity<>(sbrService.authoriseSBR(sbrdto), HttpStatus.OK);
    }

    //delete an SBR
    @PutMapping(path = "/delete")
    @PreAuthorize("hasAnyAuthority({'BANK_USER_MAKER' })")
    @Transactional
    public ResponseEntity<SBRDTO> deleteSBR(@RequestBody SBRDTO sbrdto)
    {
        return  new ResponseEntity<>(sbrService.deleteSBR(sbrdto), HttpStatus.OK);
    }


}
