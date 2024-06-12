package app.rest;

import app.models.Result;
import app.services.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@RestController
public class ExcelController {

    @Autowired
    private ExcelService excelService;

    @Autowired
    private ResultController resultController;

    @GetMapping("/admin/excel/download/results")
    public ResponseEntity<byte[]> downloadExcel() throws IOException {

        ResponseEntity<List<Result>> results = resultController.findAll();

        if (!results.getStatusCode().is2xxSuccessful()) return ResponseEntity.notFound().build();

        ByteArrayInputStream in = excelService.resultsToExcel(results.getBody());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=results.xlsx");


        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(in.readAllBytes());
    }
}
