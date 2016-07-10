package javaModel;


public class Bundesland {
    
    String name;
    int bierabsatz;
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getName(){
        return name;
    }
    
    public void setBierabsatz(int bierabsatz){
        this.bierabsatz = bierabsatz;
    }
    
    public int getBierabsatz(){
        return bierabsatz;
    }
    
    @Override
    public String toString(){
        return name + " " + bierabsatz;
    }
}
