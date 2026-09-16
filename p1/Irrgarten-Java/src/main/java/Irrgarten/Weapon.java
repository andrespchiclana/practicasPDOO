package Irrgarten;

public class Weapon {
    private double power;
    private int uses;
    
    public Weapon(double power, int uses){
        this.power = power;
        this.uses = uses;
    }
    
    public double attack(){
        if(uses > 0){
            uses--;
            return power;
        }else{
            return 0;
        }
    }

    @Override
    public String toString(){
        return "W[" + power + ", " + uses + "]";
    }

    public boolean discard(){
        return Dice.discardElement(uses);
    }
    
   
    
    
}

