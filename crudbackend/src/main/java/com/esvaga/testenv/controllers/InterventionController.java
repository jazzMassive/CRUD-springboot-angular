package com.esvaga.testenv.controllers;

import com.esvaga.testenv.model.Intervention;
import com.esvaga.testenv.services.InterventionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api")
public class InterventionController {

    @Autowired
    private InterventionService interventionService;

    @GetMapping("intervention")
    public List<Intervention> GetAllInterventions(){
        return interventionService.getAllInterventions();
    }

    @PostMapping("intervention/export")
    public ResponseEntity<String> ExportInterventionData() throws Exception {
        String status = interventionService.generateInterventionSpreadsheet();
        return ResponseEntity.ok(status);
    }

    @GetMapping("intervention/export/download")
    public ResponseEntity<Resource> downloadInterventionExport(HttpServletRequest request){
        Resource resource = null;

        try {
            String exportedFilePath = interventionService.generateInterventionSpreadsheet();
            resource = interventionService.loadFileAsResource(exportedFilePath);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String contentType = null;

        try{
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (contentType == null){
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
