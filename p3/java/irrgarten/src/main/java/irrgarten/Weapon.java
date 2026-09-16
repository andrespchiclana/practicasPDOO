/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author chpoz
 */
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

    public boolean discard(){
        return Dice.discardElement(uses);
    }
    
   @Override
    public String toString(){
        return "W[" + power + ", " + uses + "]";
    }
    
}
