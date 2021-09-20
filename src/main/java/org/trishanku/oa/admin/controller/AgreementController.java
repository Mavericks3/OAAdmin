package org.trishanku.oa.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trishanku.oa.admin.model.AgreementDTO;
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

    //Get Agreement by Contract Document Number

    //Get Pending Agreements

    //Add Agreement

    //Modify Agreement

    //Authorise Agreement

    //Delete Agreement
}
