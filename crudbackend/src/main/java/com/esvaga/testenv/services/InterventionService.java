package com.esvaga.testenv.services;

import com.esvaga.testenv.model.Intervention;
import com.esvaga.testenv.repositories.InterventionRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@Service
public class InterventionService {

    private final InterventionRepository interventionRepository;

    private final String rootFilePath = "src/main/resources/exports/xls/";

    private final Path exportLocation = Paths.get(rootFilePath).toAbsolutePath().normalize();

    HashMap<String, String> fieldNameToHumanReadableMap = new HashMap<String, String>(){{
        put("ucInterventionId","ID");
        put("createdOn","Datum-Vrijeme"); 
        put("interventionStatus","Status");
        put("contactPersonName","Kontakt - ime");
        put("contactPersonPhoneNumber","Kontakt - telefon");
        put("identifier","Identifikator");
        put("vehicleRegNumber","Reg.oznaka");
        put("chassisNumber","Broj šasije");
        put("cbkVehicleMakeId","Marka");
        put("model","Model");
        put("geoLocationInput","Adresa/lokacija");
        put("productServiceName","Usluga");
        put("serviceStatus","Status usluge");
        put("associateName","Suradnik");
        put("executorFullName","Izvršitelj");
        put("ascWoStatus","Sur.nalog-status");
        put("ascWoAmount","Sur.nalog-iznos");
        put("ascWoPriceList","Sur.nalog-cjenik");
        put("productServiceDescription","Opis proizvoda-usluge");
        put("productName","Proizvod");
        put("associateCategories","Kategorije suradnika");
        put("associateType","Tip suradnika");
        put("cliWoStatus","Kor.nalog-status");
        put("cliWoAmount","Kor.nalog-iznos");
        put("cliWoPriceList","Kor.nalog-cjenik");
        put("salesPartnerName","Prodajni partner");
        put("createdBy","Kreirao");
        put("callCenterCbkCountryId","Država");
    }};

    public InterventionService(@Qualifier("interventionRepository")InterventionRepository interventionRepository) {
        this.interventionRepository = interventionRepository;
    }

    public List<Intervention> getAllInterventions(){
        return interventionRepository.getAll();
    }

    public String generateInterventionSpreadsheet() throws Exception {
        // Filename and sheet name string
        String sheetAndFileName = "Intervention_report_" + LocalDate.now().format(DateTimeFormatter.ofPattern("dd_MM_yyyy"));

        // Query data
        List<Intervention> interventionData = interventionRepository.getAll();

        // Create workbook and worksheet
        Workbook workbook = new XSSFWorkbook();;
        Sheet sheet = workbook.createSheet(sheetAndFileName);

        // Font style
        Font headerFontStyle = workbook.createFont();
        headerFontStyle.setBold(true);
        headerFontStyle.setColor(IndexedColors.WHITE.getIndex());

        // Header style
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFillForegroundColor(IndexedColors.RED1.getIndex());
        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerCellStyle.setFont(headerFontStyle);

        Iterator<Intervention> iterator = interventionData.iterator();
        Field[] fields = Intervention.class.getDeclaredFields();

        // Write column names in first row of worksheet
        Row headerRow = sheet.createRow(0);
        headerRow.setRowStyle(headerCellStyle);
        for (int i = 0; i < fields.length; i++){
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(fieldNameToHumanReadableMap.get(fields[i].getName()));
            cell.setCellStyle(headerCellStyle);
            sheet.setColumnWidth(i, 6000);
        }

        // Write query data in rows of worksheet
        int rowIndex = 1;
        while (iterator.hasNext()) {
            Intervention intervention = iterator.next();
            Row row = sheet.createRow(rowIndex++);
            int cellIndex = 0;
            for (Field field: fields){
                field.setAccessible(true);
                Object value = field.get(intervention);
                Cell cell = row.createCell(cellIndex++);
                if (value != null){
                    cell.setCellValue(value.toString());
                }
            }

        }

        //Save workbook to file
        String filePath = rootFilePath + sheetAndFileName + ".xlsx";
        FileOutputStream fos = new FileOutputStream(filePath);
        workbook.write(fos);
        fos.close();
        System.out.println(filePath + " written successfully");
        return sheetAndFileName + ".xlsx";
    }

    public Resource loadFileAsResource (String filename) throws FileNotFoundException {
        try {
            Path filePath = exportLocation.resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()){
                return resource;
            } else {
                throw new FileNotFoundException("File not found" + filePath);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }


}
