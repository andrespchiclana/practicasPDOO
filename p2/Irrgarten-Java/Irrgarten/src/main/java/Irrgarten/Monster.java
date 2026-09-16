/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Irrgarten;

/**
 *
 * @author chpoz
 */
public class Monster {
    private static final int INITIAL_HEALTH=5;
    private String name;
    private double intelligence;
    private double strength;
    private double health;
    private int row=-1;
    private int col=-1;

    
    public Monster(String name, double intelligence, double strength) {
        this.name = name;
        this.intelligence = intelligence;
        this.strength = strength;
        this.health = INITIAL_HEALTH;
    }
    
    public boolean dead(){
        return health<=0;
    }
    
    public double attack(){
        return Dice.intensity(strength);
    }

    public boolean defend(double receivedAttack){
        throw new UnsupportedOperationException();
    }
    
    public void setPos(int row, int col){
        this.row = row;
        this.col = col;
    }
    
    @Override
    public String toString(){
        return "Monster{name= " + name + ", intelligence= " + intelligence + ", strength= " + strength + ", health= " + health + ", row= " + row + ", col= " + col + "}\n";
    }
    
    public void gotWounded(){
        if(health>0){
            health--;
        }
    }
    
    
    
}
