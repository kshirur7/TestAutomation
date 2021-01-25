package com.eurowings.qa.ExcelUtilities;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

public class ExcelLibrary{

    public int getRowCount(String path, String sheetname){

        int rowCount = 0;
        try {

            FileInputStream fis = new FileInputStream(path);
            Workbook wb=WorkbookFactory.create(fis);
            Sheet sh = wb.getSheet(sheetname);
            rowCount = sh.getLastRowNum();


        } catch (FileNotFoundException e) {
            System.out.println(" File Not found");
            // TODO Auto-generated catch block
            e.printStackTrace();

        } catch (IOException e) {
            System.out.println("IO exception");
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        return rowCount;
    }

    public String getExcelData(String path, String sheetname, int row, int col){

        String excelData = "";
        try {
            FileInputStream fis = new FileInputStream(path);
            Workbook wb = WorkbookFactory.create(fis);
            Sheet sh = wb.getSheet(sheetname);
            Row rw = sh.getRow(row);
            Cell cl = rw.getCell(col);
            //excelData = cl.getStringCellValue();
            //cl.getCellTypeEnum();
            if(cl.getCellType() == CellType.STRING)
            {
                excelData = cl.getStringCellValue();

            }
            if(cl.getCellType() == CellType.NUMERIC)
            {
                excelData = Double.toString(cl.getNumericCellValue());//cl.getNumericCellValue();

            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return excelData;
    }



    public void SetExcelData(String path, String sheetname, int row, int col, int sData){

        try {

            FileInputStream fis = new FileInputStream(path);
            Workbook wb = WorkbookFactory.create(fis);
            Sheet sh = wb.getSheet(sheetname);
            Row r = sh.getRow(row);

            Cell c = r.getCell(col);
            if (c == null)
                c = r.createCell(col);
            c.setCellValue("null");


            if(c.getStringCellValue().equalsIgnoreCase("null")){
                c.setCellValue(sData);

            }

            FileOutputStream fos = new FileOutputStream(path);
            wb.write(fos);
            fos.close();
            fis.close();



        } catch (FileNotFoundException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }


    }

    public int getRowNumOfTestClass(String path, String sheetname, String sRowName){

        int retValue = 0;
        try {
            FileInputStream fis = new FileInputStream(path);
            Workbook wb = WorkbookFactory.create(fis);
            Sheet sh = wb.getSheet(sheetname);
            int totalRows = getRowCount(path, sheetname);
            for(int i=1;i<=totalRows;i++){

            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return retValue;
    }

}

