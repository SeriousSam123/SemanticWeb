package parser;

import java.util.List;
import javaModel.Stadion;
import javaModel.Verein;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class StadionParser {
    
    public void parse(List<Stadion> stadien){
        
        this.parseTransfermarktDE(stadien);     // Stadionname, Zuschauerzahl, Ressourcen-URL, Verein extrahieren
        
    }       

    private void parseTransfermarktDE(List<Stadion> stadien){
        
        // #############################################    
        // --- Parsen der Daten von transfermarkt.de ---
        // #############################################    

        String[] urls = {                    
                    "http://www.transfermarkt.de/1-bundesliga/besucherzahlen/wettbewerb/L1/plus/?saison_id=2015",
                    "http://www.transfermarkt.de/2-bundesliga/besucherzahlen/wettbewerb/L2/plus/?saison_id=2015",
                    "http://www.transfermarkt.de/3-liga/besucherzahlen/wettbewerb/L3/plus/?saison_id=2015"
        };
        
        for (String url : urls) {
  
            try {

                Document doc = Jsoup.connect(url)
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                        .referrer("http://www.google.com")
                        .get();

                Element table = doc.select("table").get(3);     // ist die 4. Tabelle auf der Seite
                Element tbody = table.select("tbody").first();
                Elements rows = tbody.select("tr");

                String vereinsName = "";
                String stadionName = "";
                String stadionRessourcenURL = "";
                String zuschauerStr = "";
                int zuschauer = 0;

                for (int i = 0; i < rows.size(); i++) {     

                    if ((i%3) == 0){                    // Extraktion der Zuschauerzahl
                        Element row = rows.get(i);
                        Element column = row.select("td").get(6);
                        zuschauerStr = column.text();
                        zuschauerStr = zuschauerStr.replace(".", "");
                        zuschauer = Integer.parseInt(zuschauerStr);
                    }
                    else if((i%3) == 1){               // Extraktion des Stadionnamens
                        Element row = rows.get(i);      
                        Element column = row.select("td").get(1);
                        stadionName = column.text();
                        Element link = column.select("a").first();
                        stadionRessourcenURL = "http://www.transfermarkt.de" + link.attr("href");
                    }
                    else if((i%3) == 2){               // Extraktion des Vereinsnamens
                        Element row = rows.get(i);      
                        Element column = row.select("td").first();
                        vereinsName = column.text();
                        
                        // Neues Stadion-Objekt erstellen
                        Stadion stadion = new Stadion();
                        // Extrahierte Werte übergeben
                        stadion.setZuschauer(zuschauer);
                        stadion.setName(stadionName);
                        stadion.setUrlRessource(stadionRessourcenURL);
                        stadion.setVerein(vereinsName);
                        
                        // Stadien-Liste mit extrahierten Daten befüllen
                        stadien.add(stadion);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
