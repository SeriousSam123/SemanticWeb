
package javaModel;


public class Verein {
    
    String name;
    String bundesland;
    String urlRessource;
    String ort;
    String ortUrlRessource;
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getName(){
        return name;
    }
    
    public void setBundesland(String bundesland){
        this.bundesland = bundesland;
    }
    
    public String getBundesland(){
        return bundesland;
    }

    public void setUrlRessource(String urlRessource){
        this.urlRessource = urlRessource;
    }
    
    public String getUrlRessource(){
        return urlRessource;
    }
    
    public void setOrt(String ort){
        this.ort = ort;
    }
    
    public String getOrt(){
        return ort;
    }
    
    public void setOrtUrlRessource(String ortUrlRessource){
        this.ortUrlRessource = ortUrlRessource;
    }
    
    public String getOrtUrlRessource(){
        return ortUrlRessource;
    }
    
    public String toString(){
        return name + " " 
                + bundesland + " "
                + urlRessource + " "
                + ort;
    }
}
