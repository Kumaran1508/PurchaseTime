package com.vk.purchasetime.services;

import java.io.*;

import com.vk.purchasetime.models.InvoicePrimary;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.boot.SpringApplication;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class SaleDataGenerator {
///"+new Date().toString()+"

    public String excelSheetGenerator(List<InvoicePrimary> invoices) throws Exception{
        String fileLocation="reports.xlsx";

        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFSheet spreadsheet
                = workbook.createSheet(" Invoice Report ");
        XSSFRow row;

        double amount = 0.0;

        int rowid = 1;
        int headid=0;
        row = spreadsheet.createRow(0);
        Cell cell = row.createCell(headid++);
        cell.setCellValue("Invoice Id");

        cell = row.createCell(headid++);
        cell.setCellValue("Invoice Date");

        cell = row.createCell(headid++);
        cell.setCellValue("Transaction Type");

        cell = row.createCell(headid++);
        cell.setCellValue("Amount");

        for (InvoicePrimary invoice : invoices) {

            row = spreadsheet.createRow(rowid++);
            int cellid = 0;

            cell = row.createCell(cellid++);
            cell.setCellValue(invoice.getInvoiceId());

            cell = row.createCell(cellid++);
            cell.setCellValue(invoice.getInvoiceDate().toString());

            cell = row.createCell(cellid++);
            cell.setCellValue(invoice.getTransactionType().name());

            cell = row.createCell(cellid++);
            cell.setCellValue(invoice.getAmount());
            amount += invoice.getAmount();




        }
        row = spreadsheet.createRow(rowid++);
        cell = row.createCell(2);
        cell.setCellValue("Total");

        cell = row.createCell(3);
        cell.setCellValue(amount);


        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
        String newLocation="\\reports\\"+formatter.format(new Date())+fileLocation;
        FileOutputStream out = new FileOutputStream(new File(fileLocation));

        workbook.write(out);
        out.close();
        System.out.println("excel sheet created");

//
//        File originalWb = new File(fileLocation);
//        Path p= Paths.get(newLocation);
//        Files.createFile(p);
//        File clonedWb=new File(newLocation);
//        Files.copy(originalWb.toPath(), clonedWb.toPath());
        return fileLocation;
    }

}