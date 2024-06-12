package app.services;

import app.models.Result;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelService {
    public ByteArrayInputStream resultsToExcel(List<Result> results) throws IOException {
        String[] COLUMNs = {"Question", "Answer", "Result"};
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Results");

            // Header
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < COLUMNs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(COLUMNs[col]);
            }

            // Data
            int rowIdx = 1;
            for (Result result : results) {
                Row row = sheet.createRow(rowIdx++);
                if (result.getAnswer() == null) continue;
                row.createCell(0).setCellValue(result.getQuestion().getQuestion());
                row.createCell(1).setCellValue(result.getAnswer().getAnswer());
                row.createCell(2).setCellValue(result.getResult());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}
