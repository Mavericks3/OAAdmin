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

    @GetMapping()
    public ResponseEntity<List<SBRDTO>> getAllSBRs()
    {
        return  new ResponseEntity<>(sbrService.getAllSBRs(), HttpStatus.OK);
    }
}
