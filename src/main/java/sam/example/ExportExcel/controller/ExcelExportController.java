package sam.example.ExportExcel.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sam.example.ExportExcel.service.ExcelExportService;

import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("/api/export/")
public class ExcelExportController {

    private final ExcelExportService excelExportService;

    @GetMapping("/toExcel")
    public ResponseEntity<Resource> exportToExcel() {
        try {
            Resource resource = excelExportService.generateExcel();
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Employees.xlsx")
                    .body(resource);
        } catch (IOException e) {
            // Handle exception
        }
        return null;
    }
}


