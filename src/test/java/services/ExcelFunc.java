package services;

import java.io.FileInputStream;

import java.io.FileOutputStream;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;


public class ExcelFunc {

    private static HSSFSheet ExcelWSheet;

    private static HSSFWorkbook ExcelWBook;

    private static HSSFCell Cell;

    private static HSSFRow Row;


    //This method is to set the File path and to open the Excel file, Pass Excel Path and Sheetname as Arguments to this method

    public void setExcelFile(String Path, String SheetName) throws Exception {

        try {

            String TestDataPath = System.getProperty("user.dir") + "\\ExcelFiles\\test.xls";
            // Open the Excel file
            FileInputStream ExcelFile = new FileInputStream(TestDataPath);
            // Access the required test data sheet
            ExcelWBook = new HSSFWorkbook(ExcelFile);
            ExcelWSheet = ExcelWBook.getSheet(SheetName);

        } catch (Exception e) {

            throw (e);

        }

    }

    //This method is to read the test data from the Excel cell, in this we are passing parameters as Row num and Col num

    public String getCellData(int RowNum, int ColNum) throws Exception {

        try {

            Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);

            String CellData = Cell.getStringCellValue();

            return CellData;

        } catch (Exception e) {

            return "";

        }

    }

    public int getRowCount() throws Exception {

        try {

              int rowcount =    ExcelWSheet.getLastRowNum();
           return rowcount;

        } catch (Exception e) {

            return 0;

        }

    }
    //This method is to write in the Excel cell, Row num and Col num are the parameters

    public  void setCellData(String Result, int RowNum, int ColNum) throws Exception {

        try {

            Row = ExcelWSheet.getRow(RowNum);

            Cell = Row.getCell(ColNum, Row.RETURN_BLANK_AS_NULL);

            if (Cell == null) {

                Cell = Row.createCell(ColNum);

                Cell.setCellValue(Result);

            } else {

                Cell.setCellValue(Result);

            }

            // Constant variables Test Data path and Test Data file name

            FileOutputStream fileOut = new FileOutputStream(System.getProperty("user.dir") + "\\ExcelFiles\\testout.xls");

            ExcelWBook.write(fileOut);

            fileOut.flush();

            fileOut.close();

        } catch (Exception e) {

            throw (e);

        }

    }

}

