package org.trishanku.oa.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.trishanku.oa.admin.model.AgreementDTO;
import org.trishanku.oa.admin.model.UserDTO;
import org.trishanku.oa.admin.service.AgreementService;


import java.util.List;

@RestController
@RequestMapping("api/v1/agreements")
public class AgreementController {

    @Autowired
    AgreementService agreementService;

    //Get pending agreements
    @GetMapping
    public ResponseEntity<List<AgreementDTO>> getAgreements()
    {
        return new ResponseEntity(agreementService.getAllAgreements(),HttpStatus.OK);
    }

    //Get Agreement by Contact reference Number
    @GetMapping(path = "/byreferencenumber/{contractReferenceNumber}")
    public ResponseEntity<AgreementDTO> getAgreementByContractReferenceNumber(@PathVariable(name="contractReferenceNumber") String contractReferenceNumber)
    {
        return new ResponseEntity(agreementService.getAgreementByContractReferenceNumber(contractReferenceNumber),HttpStatus.OK);
    }


    //Get Agreement by Contract Document Number
    @GetMapping(path = "/bydocumentnumber/{contractDocumentNumber}")
    public ResponseEntity<AgreementDTO> getAgreementByContractDocumentNumber(@PathVariable(name="contractDocumentNumber") String contractDocumentNumber)
    {
        return new ResponseEntity(agreementService.getAgreementByContractDocumentNumber(contractDocumentNumber),HttpStatus.OK);
    }

    //Get Pending Agreements
    @GetMapping(path = "/pending")
    public ResponseEntity<List<AgreementDTO>> getPendingAgreements()
    {
        List<AgreementDTO> agreementDTOS = agreementService.getPendingAgrements();
        return new ResponseEntity<>(agreementDTOS, HttpStatus.OK);
    }

    //Add Agreement
    @PostMapping()
    public ResponseEntity<AgreementDTO> addAgreement(@RequestBody AgreementDTO agreementDTO)
    {

        return new ResponseEntity<>(agreementService.addAgreement(agreementDTO), HttpStatus.OK);
    }


    //Modify Agreement
    @PutMapping()
    public ResponseEntity<AgreementDTO> modifyAgreement( @RequestBody AgreementDTO agreementDTO)
    {

        return new ResponseEntity<>(agreementService.modifyAgreement(agreementDTO.getContractReferenceNumber(), agreementDTO), HttpStatus.OK);
    }

    //Authorise Agreement
    @PutMapping(path= "/authorise")
    public ResponseEntity<AgreementDTO> authoriseAgreement( @RequestBody AgreementDTO agreementDTO)
    {

        return new ResponseEntity<>(agreementService.authoriseAgreement(agreementDTO.getContractReferenceNumber(), agreementDTO), HttpStatus.OK);
    }

    //Delete Agreement
}
