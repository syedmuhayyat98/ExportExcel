package sam.example.ExportExcel.service;

import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import sam.example.ExportExcel.domain.Employee;
import sam.example.ExportExcel.repository.EmployeeRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class ExcelExportService {

    private final EmployeeRepository employeeRepository;

    public Resource generateExcel() throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Employee Data");

        // Add data to the sheet, for example:
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("First Name");
        headerRow.createCell(2).setCellValue("Last Name");
        headerRow.createCell(3).setCellValue("Email");
        headerRow.createCell(4).setCellValue("Joined Date");
        headerRow.createCell(5).setCellValue("Status");
        headerRow.createCell(6).setCellValue("Designation");
        // Add more columns as needed

        // You can fetch data from a database or any other source
        List<Employee> employeeList = employeeRepository.findAll();
        // and add it to the Excel sheet
        int rowNum = 1;
        for (Employee employee : employeeList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(employee.getId());
            row.createCell(1).setCellValue(employee.getFirstName());
            row.createCell(2).setCellValue(employee.getLastName());
            row.createCell(3).setCellValue(employee.getEmail());
            row.createCell(4).setCellValue(employee.getJoinedDate().toString()); // Assuming joinedDate is a Date or LocalDate
            row.createCell(5).setCellValue(employee.getStatus());
            row.createCell(6).setCellValue(employee.getDesignation());
            // Add more columns as needed
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        byte[] bytes = outputStream.toByteArray();
        return new ByteArrayResource(bytes);
    }
}

