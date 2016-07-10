package semanticweb;

import java.util.List;
import javaModel.Bundesland;
import javaModel.Stadion;
import javaModel.Verein;


public class Umbenennung {
    
    public static void bundeslandNamenAnpassen(List<Bundesland> bundeslaender, List<Verein> vereine){
        
        // Liste der Vereine durchlaufen und Umbenennungen durchführen
        for (Verein verein : vereine) {
            // Bundesland des Vereins in Bundeslandliste suchen
            for (int i = 0; i < bundeslaender.size(); i++) {
                // Bei Übereinstimmung nichts tun und aus Schleife springen
                if(bundeslaender.get(i).getName().equals(verein.getBundesland())){
                    break;
                }
                // Sollte das Ende der Bundesländer-Liste ohne Matching erreicht worden sein, erneuter "weicherer" Durchlauf
                if(i == (bundeslaender.size() - 1)){
                    for (int j = 0; j < bundeslaender.size(); j++) {
                        // Bei Übereinstimmung Bundeslandnamen ersetzen
                        if(bundeslaender.get(j).getName().contains(verein.getBundesland())){
                            verein.setBundesland(bundeslaender.get(j).getName());
                            break;
                        }
                    }
                }
            }
        }
    }


    public static void vereinsNamenAnpassen(List<Verein> vereine, List<Stadion> stadien){            
        
        // Liste der Stadien durchlaufen und Umbenennungen durchführen
         for (Stadion stadion : stadien) {
            // Vereinsnamen (aus Stadion-Objekt) in Vereinsliste suchen
            for (int i = 0; i < vereine.size(); i++) {
                // Bei Übereinstimmung nichts tun und aus Schleife springen
                if(vereine.get(i).getName().equals(stadion.getVerein())){
                    break;
                }
                // Sollte das Ende der Vereins-Liste ohne Matching erreicht worden sein, erneuter "weicherer" Durchlauf
                if(i == (vereine.size() - 1)){
                    for (int j = 0; j < vereine.size(); j++) {
                        // Vereinsnamen ersetzen bei teilweiser Übereinstimmung und ignorieren der Leerzeichen
                        if(stadion.getVerein().replace(" ", "").contains(vereine.get(j).getName().replace(" ", ""))){
                            stadion.setVerein(vereine.get(j).getName());
                            break;
                        }
                    }
                }
            }
        }

        // Sonderbehandlung von "RB Leipzig" und "FC Rot-Weiß Erfurt"
        for (Stadion stadion : stadien) {
            switch (stadion.getVerein()) {
                case "RasenBallsport Leipzig":
                    stadion.setVerein("RB Leipzig");
                    break;                
                case "Rot-Weiß Erfurt":
                    stadion.setVerein("FC Rot-Weiß Erfurt");
                    break;                
            }
        }
    }
}
