package parser;

import java.util.List;
import javaModel.Verein;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class VereinParser {    
    
    public void parse(List<Verein> vereine) {
        
       this.parseWikipedia(vereine);        // Vereinsnamen, URL zu Ressource extrahieren
        
    } 
    
    private void parseWikipedia(List<Verein> vereine){
        
        // ######################################    
        // --- Parsen der Daten von Wikipedia ---
        // ######################################    
        
        String[] urls = {    
            "http://de.wikipedia.org/wiki/Fu%C3%9Fball-Bundesliga_2015/16",
            "http://de.wikipedia.org/wiki/2._Fu%C3%9Fball-Bundesliga_2015/16",
            "http://de.wikipedia.org/wiki/3._Fu%C3%9Fball-Liga_2015/16"
        };  
         
        for (String url : urls) {
          
            try {

                Document doc = Jsoup.connect(url).timeout(20000).get();

                Element table = doc.select("table.wikitable").first();
                Elements rows = table.select("tr");

                for (int i = 1; i < rows.size(); i++) {

                    Element row = rows.get(i);
                    Elements columns = row.select("td");

                    // Neuen Verein erstellen
                    Verein verein = new Verein();


                    // --- Vereinsname ---
                    // Namen des Vereins extrahieren und übergeben
                    Element column = columns.get(1);
                    Element link = column.select("a").first();

                    verein.setName(link.text());

                    // Vereins-URL
                    // URL des Vereins extrahieren und übergeben
                    verein.setUrlRessource("http://de.wikipedia.org" + link.attr("href"));


                    // --- Bundesland ---
                    // Bundesland des Vereins extrahieren und übergeben
                    String bundesland;
                    String urlVerein = verein.getUrlRessource();
                    String ortVerein;
                    String urlVereinOrt;

                    // Zugriff auf Verlinkte Wikipediaseite des Vereins
                    Document docVerein = Jsoup.connect(urlVerein).timeout(20000).get();

                    Element tableVerein = docVerein.select("table.infobox").first();
                    // Sollte die Tabelle fehlen - nächster Schleifendurchlauf
                    if (tableVerein == null)                    
                        continue;
                        
                    // "Ort"-Zeile suchen (entweder 3. od. 4. Zeile)
                    Element row3 = tableVerein.select("tr").get(3); 
                    Element row4 = tableVerein.select("tr").get(4);

                    String row3col0 = row3.select("td").first().text();
                    String row4col0 = row4.select("td").first().text();

                    if (row3col0.equals("Ort")){
                        Element columnVerein = row3.select("td").get(1);
                        Element linkOrtVerein = columnVerein.select("a").first();
                        ortVerein = linkOrtVerein.attr("title");
                        urlVereinOrt = "http://de.wikipedia.org" + linkOrtVerein.attr("href");
                    } else {
                        Element columnVerein = row4.select("td").get(1);
                        Element linkOrtVerein = columnVerein.select("a").first();
                        ortVerein = linkOrtVerein.attr("title");
                        urlVereinOrt = "http://de.wikipedia.org" + linkOrtVerein.attr("href");
                    } 

                    verein.setOrt(ortVerein);
                    verein.setOrtUrlRessource(urlVereinOrt);

                    // Zugriff auf Verlinkte Wikipediaseite des Ortes
                    // Sonderbehandlung von Berlin, da Wikipediaseite zu Berlin keinen Bundeslandeintrag besitzt
                    if(verein.getOrt().equals("Berlin")){

                        bundesland = "Berlin";
                        verein.setBundesland(bundesland);

                    }else if(verein.getOrt().equals("Hamburg")){ // Sonderbehandlung von Hamburg

                        bundesland = "Hamburg";
                        verein.setBundesland(bundesland);                    

                    }else{  // alle anderen Bundesländer

                        Document docOrt = Jsoup.connect(urlVereinOrt).get();
                        Element tableOrt = docOrt.select("table#Vorlage_Infobox_Verwaltungseinheit_in_Deutschland").first();

                        // "Bundesland"-Zeile suchen
                        Element rowBundesland = tableOrt.select("tr:contains(Bundesland)").first();
                        Element columnBundesland = rowBundesland.select("td").get(1);

                        bundesland = columnBundesland.text();
                        verein.setBundesland(bundesland);
                    }

                    vereine.add(verein);
                } 

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
