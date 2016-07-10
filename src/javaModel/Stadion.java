package javaModel;


public class Stadion {

    String name;
    String verein;
    int zuschauer;
    String urlRessource;
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getName(){
        return name;
    }
    
    public void setVerein(String verein){
        this.verein = verein;
    }
    
    public String getVerein(){
        return verein;
    }   
    
    public void setZuschauer(int zuschauer){
        this.zuschauer = zuschauer;
    }
    
    public int getZuschauer(){
        return zuschauer;
    }
    
    public void setUrlRessource(String urlRessource){
        this.urlRessource = urlRessource;
    }
    
    public String getUrlRessource(){
        return urlRessource;
    }
    
    public String toString(){
        return name + " " 
                + verein + " " 
                + zuschauer + " " 
                + urlRessource;
    }    
}
