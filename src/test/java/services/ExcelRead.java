package services;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

public class ExcelRead  {


    public static HashMap<String, HashMap<String, String>> hm1 = new HashMap();
    static String s3;

    public void ReadExcel(String xlpath, String SheetName) throws IOException{

        String saparetor = System.getProperty("file.separator");
         String TestDataPath = System.getProperty("user.dir") + saparetor+"ExcelFiles"+saparetor+xlpath+".xls";

        System.out.println("The Excel Path:...."+TestDataPath);

        FileInputStream file = new FileInputStream(TestDataPath);
        HSSFWorkbook workbook = new HSSFWorkbook(file);
        HSSFSheet sheet = workbook.getSheet(SheetName);
        Row HeaderRow = sheet.getRow(0);
        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            Row currentRow = sheet.getRow(i);
            HashMap<String, String> currentHash = new HashMap<String, String>();
            for (int j = 0; j < currentRow.getPhysicalNumberOfCells(); j++) {

                Cell currentCell1 = currentRow.getCell(0);
                switch (currentCell1.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        s3 = currentCell1.getStringCellValue();
                        System.out.println(s3);
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        s3 = String.valueOf(currentCell1.getNumericCellValue());
                        System.out.println(s3);
                        break;
                }

                Cell currentCell = currentRow.getCell(j);
                switch (currentCell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        currentHash.put(HeaderRow.getCell(j).getStringCellValue(),
                                currentCell.getStringCellValue());
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        currentHash.put(HeaderRow.getCell(j).getStringCellValue(),
                                String.valueOf(currentCell.getNumericCellValue()));
                        break;
                }

            }

            hm1.put(s3, currentHash);

        }

        //System.out.println(hm1.get("Check Customer with No First Account").get("UserName"));

    }


}