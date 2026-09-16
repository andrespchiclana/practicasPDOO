/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author chpoz
 */
abstract class LabyrinthCharacter {
    
    private String name;
    private double intelligence;
    private double strength;
    private double health;
    private int row;
    private int col;


    public LabyrinthCharacter(String name, double intelligence, double strength, double health) {
        this.name = name;
        this.intelligence = intelligence;
        this.strength = strength;
        this.health = health;
    }


    public LabyrinthCharacter(LabyrinthCharacter other) {
        this.name = other.name;
        this.intelligence = other.intelligence;
        this.strength = other.strength;
        this.health = other.health;
        this.row = other.row;
        this.col = other.col;
    }


    public boolean dead() {
        return health <= 0;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    protected double getIntelligence() {
        return intelligence;
    }

    protected double getStrength() {
        return strength;
    }

    protected double getHealth() {
        return health;
    }

    protected void setHealth(double health) {
        this.health = health;
    }

    public void setPos(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public String toString() {
        return "name= " + name + ", intelligence= " + intelligence + ", strength= " + strength + ", health= " + health + ", row= " + row + ", col= " + col + "\n";
    }

    protected void gotWounded(){
        if(health>0){
            health--;
        }
    }

    public abstract double attack();

    public abstract boolean defend(double attack);

}
