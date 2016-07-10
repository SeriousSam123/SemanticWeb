package semanticweb;

import java.util.ArrayList;
import java.util.List;
import javaModel.Bundesland;
import javaModel.Stadion;
import javaModel.Verein;
import parser.*;
import rdfMaker.BundeslaenderRDFMaker;
import rdfMaker.StadienRDFMaker;
import rdfMaker.VereineRDFMaker;

public class SemanticWeb {


    public static void main(String[] args) {
        
        // ##############
        // --- Parsen ---
        // ##############
        
        // --- Bundesländer ---
        // leere Liste erzeugen
        List<Bundesland> bundeslaender = new ArrayList<Bundesland>();        
        // Bundesländer parsen und Liste füllen
        BundeslandParser bundeslandParser = new BundeslandParser();
        bundeslandParser.parse(bundeslaender);

        // Test-Ausgabe
        for (Bundesland bundesland : bundeslaender) {
            System.out.println(bundesland.toString());
        }

        
        // --- Fußballvereine --- 
        // leere Liste erzeugen
        List<Verein> vereine = new ArrayList<Verein>();
        // Fußballvereine parsen und Liste füllen
        VereinParser vereinParser = new VereinParser();
        vereinParser.parse(vereine);
        
        // Test-Ausgabe
        for (Verein verein : vereine) {
            System.out.println(verein.toString());
        }
        

        // --- Stadien --- 
        // leere Liste erzeugen
        List<Stadion> stadien = new ArrayList<Stadion>();
        // Stadien parsen und Liste füllen
        StadionParser stadionParser = new StadionParser();
        stadionParser.parse(stadien);
        
        //Test-Ausgabe
        for (Stadion stadion : stadien) {
            System.out.println(stadion.toString());
        }
       
        
        // --- Namensmatching herstellen ---
        Umbenennung.bundeslandNamenAnpassen(bundeslaender, vereine);
        Umbenennung.vereinsNamenAnpassen(vereine, stadien);
        
        for (Stadion stadion : stadien) {
            System.out.println(stadion.toString());
        }
        

                
        // ############################
        // --- RDF-Dateien Erzeugen ---
        // ############################
        
        // Bundesländer zu RDF
        BundeslaenderRDFMaker bundeslaenderRDFMaker = new BundeslaenderRDFMaker();
        bundeslaenderRDFMaker.makeRDF(bundeslaender);
        
        // Vereine zu RDF
        VereineRDFMaker vereineRDFMaker = new VereineRDFMaker();
        vereineRDFMaker.makeRDF(vereine);
        
        // Stadien zu RDF
        StadienRDFMaker stadienRDFMaker = new StadienRDFMaker();
        stadienRDFMaker.makeRDF(stadien);        
        
    }
    
}