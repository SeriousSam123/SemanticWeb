package rdfMaker;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import javaModel.Bundesland;
import javaModel.Verein;
import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.vocabulary.RDFS;
import org.apache.jena.vocabulary.XSD;


public class VereineRDFMaker {
        
    public void makeRDF(List<Verein> vereine){
        
        OntModel m = this. makeRDFGraph(vereine);      // Graph erstellen
        
        this.makeRDFFile(m);        // RDF-Datei erzeugen und speichern
    }
    
    private OntModel makeRDFGraph(List<Verein> vereine){
    
        // -- RDF Ontologie erzeugen --
        // Verweis auf eigenen Namespace
        String nameSpaceHTWK = "http://www.imn.htwk-leipzig.de/~srau/semanticweb/ontologie#";
        // Verweise auf weitere Ontologien
        String gn = "http://www.geonames.org/ontology#";
        String rdfs = "http://www.w3.org/2000/01/rdf-schema#";
        String dbo = "http://dbpedia.org/ontology/";

        // Neue Ontologie erstellen
        OntModel m = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);  
        
        // Präfixe setzen
        m.setNsPrefix("srau", nameSpaceHTWK);
        m.setNsPrefix("rdfs", rdfs);
        
        // Neue Ontologie-Klasse erstellen (Verein)
        OntClass vereinClass = m.createClass(nameSpaceHTWK + "Verein");
        
        // Properties erstellen sowie Range und Domain definieren
        // Property: Name des Vereins
        DatatypeProperty vereinNameProperty = m.createDatatypeProperty(rdfs + "label");
        vereinNameProperty.addDomain(vereinClass);
        vereinNameProperty.addRange(XSD.xstring);
        vereinNameProperty.addComment("Name des Vereins", "DE");
      
        // ObjectProperty: Bundesland
        ObjectProperty vereinBundeslandProperty = m.createObjectProperty(nameSpaceHTWK + "Bundesland");
        vereinBundeslandProperty.addDomain(vereinClass);
        vereinBundeslandProperty.addComment("Zugehöriges Bundesland", "DE");
            
        // -- Graph mit Individuen/Individuals erstellen --
        // Individuen (Vereine) zum Model hinzufügen
        for (Verein verein : vereine) {
            // Individuum hinzufügen mit Wikipediaeintrag als Ressource
            Individual i = m.createIndividual(verein.getUrlRessource(), vereinClass);
            // Properties hinzufügen
            i.addProperty(vereinNameProperty, verein.getName());
            i.addProperty(vereinBundeslandProperty, verein.getBundesland());
        }
        
        return m;       // OntModel zurückgeben
    }
     
    private void makeRDFFile(OntModel m){
        // Ausgabe in Datei
        File file = new File("rdf-files\\vereine.rdf");

        try {
            m.write(new FileOutputStream(file), "RDF/XML-ABBREV");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }    
}
