package rdfMaker;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import javaModel.Bundesland;
import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.vocabulary.XSD;


public class BundeslaenderRDFMaker {
    
    public void makeRDF(List<Bundesland> bundeslaender){
        
        OntModel m = this. makeRDFGraph(bundeslaender);      // Graph erstellen
        
        this.makeRDFFile(m);        // RDF-Datei erzeugen und speichern
    }
    
    private OntModel makeRDFGraph(List<Bundesland> bundeslaender){
    
        // -- RDF Ontologie erzeugen --
        // Verweis auf eigenen Namespace
        String nameSpaceHTWK = "http://www.imn.htwk-leipzig.de/~srau/semanticweb/ontologie#";
        // Verweise auf weitere Ontologien
        String gn = "http://www.geonames.org/ontology#";
        String rdfs = "http://www.w3.org/2000/01/rdf-schema#";

        // Neue Ontologie erstellen
        OntModel m = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);  
        
        // Präfixe setzen
        m.setNsPrefix("srau", nameSpaceHTWK);
        m.setNsPrefix("gn", gn);
        m.setNsPrefix("rdfs", rdfs);

        // Neue Ontologie-Klasse erstellen (Bundesland)
        OntClass bundeslandClass = m.createClass(nameSpaceHTWK + "Bundesland");
        
        // Properties erstellen sowie Range und Domain definieren
        // Property: Name des Bundeslandes
        DatatypeProperty bundeslandNameProperty = m.createDatatypeProperty(gn + "name");
        bundeslandNameProperty.addDomain(bundeslandClass);
        bundeslandNameProperty.addRange(XSD.xstring);
        bundeslandNameProperty.addComment("Name des Bundeslandes bzw. der Bundeslaender", "DE");
        
        // Property: Bierabsatz
        DatatypeProperty bundeslandBierabsatzProperty = m.createDatatypeProperty(nameSpaceHTWK + "bierabsatz");
        bundeslandBierabsatzProperty.addDomain(bundeslandClass);
        bundeslandBierabsatzProperty.addRange(XSD.xint);
        bundeslandBierabsatzProperty.addComment("Bierabsatzmenge des Bundeslandes bzw. der Bundeslaender", "DE");
        
            
        // -- Graph mit Individuen/Individuals erstellen --
        // Individuen (Bundesländer) zum Model hinzufügen
        for (Bundesland bundesland : bundeslaender) {
            // Individuum hinzufügen
            Individual i = m.createIndividual(nameSpaceHTWK + bundesland.getName(), bundeslandClass);
            // Properties hinzufügen
            i.addProperty(bundeslandNameProperty, bundesland.getName());
            
            String bierabsatz = Integer.toString(bundesland.getBierabsatz());
            i.addProperty(bundeslandBierabsatzProperty, bierabsatz);
        }
        
        return m;       // OntModel zurückgeben
    }
     
    private void makeRDFFile(OntModel m){
        // Ausgabe in Datei
        File file = new File("rdf-files\\bundeslaender.rdf");

        try {
            m.write(new FileOutputStream(file), "RDF/XML-ABBREV");

        } catch (Exception e) {
            e.printStackTrace();

        }
    }    
}
