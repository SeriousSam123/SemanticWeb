package rdfMaker;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import javaModel.Bundesland;
import javaModel.Stadion;
import javaModel.Verein;
import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.vocabulary.XSD;


public class StadienRDFMaker {
    
    public void makeRDF(List<Stadion> stadien){
        
        OntModel m = this.makeRDFGraph(stadien);      // Graph erstellen
        
        this.makeRDFFile(m);        // RDF-Datei erzeugen und speichern
    }  
    
    private OntModel makeRDFGraph(List<Stadion> stadien){

        // -- RDF Ontologie erzeugen --
        // Verweis auf eigenen Namespace
        String nameSpaceHTWK = "http://www.imn.htwk-leipzig.de/~srau/semanticweb/ontologie#";
        // Verweise auf weitere Ontologien
        String rdfs = "http://www.w3.org/2000/01/rdf-schema#";

        // Neue Ontologie erstellen
        OntModel m = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);  
        
        // Präfixe setzen
        m.setNsPrefix("srau", nameSpaceHTWK);
        m.setNsPrefix("rdfs", rdfs);
        
        // Neue Ontologie-Klasse erstellen (Verein)
        OntClass stadionClass = m.createClass(nameSpaceHTWK + "Stadion");
        
        // Properties erstellen sowie Range und Domain definieren
        // Property: Name des Stadions
        DatatypeProperty stadionNameProperty = m.createDatatypeProperty(rdfs + "label");
        stadionNameProperty.addDomain(stadionClass);
        stadionNameProperty.addRange(XSD.xstring);
        stadionNameProperty.addComment("Name des Stadions", "DE");
      
        // ObjectProperty: Verein
        ObjectProperty vereinNameProperty = m.createObjectProperty(nameSpaceHTWK + "Verein");
        vereinNameProperty.addDomain(stadionClass);
        vereinNameProperty.addComment("Zugehöriger Verein", "DE");

        // Property: Anzahl Zuschauer
        DatatypeProperty stadionZuschauerProperty = m.createDatatypeProperty(nameSpaceHTWK + "zuschauerzahl");
        stadionZuschauerProperty.addDomain(stadionClass);
        stadionZuschauerProperty.addRange(XSD.xint);
        stadionZuschauerProperty.addComment("Anzahl der Zuschauer", "DE");
        
        // -- Graph mit Individuen/Individuals erstellen --
        // Individuen (Vereine) zum Model hinzufügen
        for (Stadion stadion : stadien) {
            // Individuum hinzufügen mit Ressource
            Individual i = m.createIndividual(stadion.getUrlRessource(), stadionClass);
            // Properties hinzufügen
            i.addProperty(vereinNameProperty, stadion.getVerein());
            
            i.addProperty(stadionNameProperty, stadion.getName());
            
            String zuschauer = Integer.toString(stadion.getZuschauer());
            i.addProperty(stadionZuschauerProperty, zuschauer);
        }        
        
        return m;       // OntModel zurückgeben

    }
    
    private void makeRDFFile(OntModel m){
        // Ausgabe in Datei
        File file = new File("rdf-files\\stadien.rdf");

        try {
            m.write(new FileOutputStream(file), "RDF/XML-ABBREV");

        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
