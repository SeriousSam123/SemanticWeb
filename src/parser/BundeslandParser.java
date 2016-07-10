package parser;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javaModel.Bundesland;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
 

public class BundeslandParser {
       
    public void parse (List<Bundesland> bundeslaender){

        this.parseDEStatis(bundeslaender);      // Daten des Statistischen Bundesamtes:
                                                // Name der Bundesländer, Bierabsatz

    }    
    
    private void parseDEStatis(List<Bundesland> bundeslaender){
        
        // Bundesländer und deren Bierabsatz aus xlsx-Dateien extrahieren

        String[] xlsxDateien = {"resources/AbsatzBier2015_08.xlsx",
                                "resources/AbsatzBier2015_09.xlsx",
                                "resources/AbsatzBier2015_10.xlsx",
                                "resources/AbsatzBier2015_11.xlsx",
                                "resources/AbsatzBier2015_12.xlsx",
                                "resources/AbsatzBier2016_01.xlsx",
                                "resources/AbsatzBier2016_02.xlsx",
                                "resources/AbsatzBier2016_03.xlsx",
                                "resources/AbsatzBier2016_04.xlsx",
                                "resources/AbsatzBier2016_05.xlsx"
        };
        
        // Name der Bundesländer (aus der ersten xlsx-Datei) extrahieren
        try {
                               
            // Byte-Stream aus erster xlsx-Datei einlesen
            InputStream inp = new FileInputStream(xlsxDateien[0]);
            // Arbeitsmappe beziehen
            XSSFWorkbook wb = new XSSFWorkbook(inp);
            // 4. Arbeitsblatt beziehen
            XSSFSheet sheet = wb.getSheetAt(3); 

            // Interessierende Zeile durchlaufen
            for (int i = 7; i < 19; i++) {

                Row row = sheet.getRow(i);
                // Interessierende Zelle auswählen
                Cell cell0 = row.getCell(0);        // enthält das Bundesland

                String bundeslandName = cell0.getStringCellValue();
                // String anpassen (Leerzeichen entfernen, "/" zu "_")
                bundeslandName = bundeslandName.replace(" ", "");
                bundeslandName = bundeslandName.replace("/", "_");                
                
                // Neues Bundesland erstellen
                Bundesland bundesland = new Bundesland();
                // Name des Bundeslandes übergeben
                bundesland.setName(bundeslandName);
                     
                // Bundesland in Liste einfügen
                bundeslaender.add(bundesland);
    
            }
                      
        } catch (Exception e) {
            e.printStackTrace();

        }

        // Bierabsatz extrahieren
        for (String xlsxDatei : xlsxDateien) {
                    
            try {

                // Byte-Stream aus xlsx-Datei einlesen
                InputStream inp = new FileInputStream(xlsxDatei);
                // Arbeitsmappe beziehen
                XSSFWorkbook wb = new XSSFWorkbook(inp);
                // 4. Arbeitsblatt beziehen
                XSSFSheet sheet = wb.getSheetAt(3); 

                // Interessierende Zeile durchlaufen
                for (int i = 7; i < 19; i++) {

                    Row row = sheet.getRow(i);
                    // Interessierende Zelle auswählen
                    Cell cell1 = row.getCell(1);        // enthält den Bierabsatz

                    int bierabsatz = (int) cell1.getNumericCellValue();
                    
                    // Bierabsatz der Monate addieren
                    int bierabsatzAlt = bundeslaender.get(i-7).getBierabsatz();
                    int bierabsatzNeu = (bierabsatz + bierabsatzAlt);
                    bundeslaender.get(i-7).setBierabsatz(bierabsatzNeu);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }   
}
