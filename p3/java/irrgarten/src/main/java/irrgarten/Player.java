/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

import java.util.ArrayList;

/**
 *
 * @author chpoz
 */
public class Player {
    
    private static final int MAX_WEAPONS=2;
    private static final int MAX_SHIELDS=3;
    private static final int INITIAL_HEALTH=10;
    private static final int HITS2LOSE=3;

    private String name;
    private char number;
    private double intelligence;
    private double strength;
    private double health;
    private int row;
    private int col;
    private int consecutiveHits=0;

    private ArrayList<Weapon> weapons;
    private ArrayList<Shield> shields;

    
    public Player(char number, double intelligence, double strength){
        this.name = "Player " + number;
        this.number = number;
        this.intelligence = intelligence;
        this.strength = strength;
        this.health = INITIAL_HEALTH;

        this.weapons = new ArrayList<>();
        this.shields = new ArrayList<>();
    }

    public void resurrect(){
        weapons.clear();
        shields.clear();
        health = INITIAL_HEALTH;
        consecutiveHits = 0;
    }

    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }

    public char getNumber(){
        return number;
    }

    public void setPos(int row, int col){
        this.row = row;
        this.col = col;
    }

    public boolean dead(){
        return health<=0;
    }

    public Directions move(Directions direction, ArrayList<Directions> validMoves){
        
        int size = validMoves.size();

        boolean contained = validMoves.contains(direction);

        if(size > 0 && !contained){

            Directions firstElement = validMoves.get(0);
            return firstElement;

        } else{
            return direction;
        }


    }

    public double attack(){
        return (strength + sumWeapons());
    }

    public boolean defend(double receivedAttack){
        return manageHit(receivedAttack);
    }

    public void receiveReward(){
        
        int weaponsReward = Dice.weaponsReward();
        int shieldsReward = Dice.shieldsReward();

        for(int i=1; i<=weaponsReward; i++){
            
            Weapon wnew = newWeapon();
            
            receiveWeapon(wnew);
        }

        for(int i=1; i<=shieldsReward; i++){
            
            Shield snew = newShield();
            
            receiveShield(snew);
        }

        int extraHealth = Dice.healthReward();

        health += extraHealth;

    }

    @Override
    public String toString(){
        return "Player{name= " + name + ", intelligence= " + intelligence + ", strength= " + strength + ", health= " + health + ", row= " + row + ", col= " + col + ",consecutiveHits= " + consecutiveHits + ", weapons= " + weapons.toString() + ", shields= " + shields.toString() + "}\n";
    }

    private void receiveWeapon(Weapon w){
        
        for(int i=0; i<weapons.size(); i++){
            Weapon wi = weapons.get(i);

            boolean discard = wi.discard();

            if(discard){
                weapons.remove(i);
            
            }
        }

        int size = weapons.size();

        if(size < MAX_WEAPONS){
            weapons.add(w);
        }

    }

    private void receiveShield(Shield s){
        

        for(int i=0; i<shields.size(); i++){
            Shield si = shields.get(i);

            boolean discard = si.discard();

            if(discard){
                shields.remove(i);
            }
        }

        int size = shields.size();

        if(size < MAX_SHIELDS){
            shields.add(s);
        }

    }

    private Weapon newWeapon(){
        return new Weapon(Dice.weaponPower(), Dice.usesLeft());
    }

    private Shield newShield(){
        return new Shield(Dice.shieldPower(), Dice.usesLeft());
    }

    private double sumWeapons(){
        double sum=0;
        for(Weapon w: weapons){
            sum += w.attack();
        }

        return sum;
    }

    private double sumShields(){
        double sum=0;
        for(Shield s: shields){
            sum += s.protect();
        }

        return sum;
    }

    private double defensiveEnergy(){
        return (intelligence + sumShields());
    }

    private boolean manageHit(double receivedAttack){
        
        double defense = defensiveEnergy();

        if (defense < receivedAttack){
            gotWounded();
            incConsecutiveHits();
        } else{
            resetHits();
        }

        boolean lose = ((consecutiveHits == HITS2LOSE) || (dead()));

        if (lose){
            resetHits();
            return true;
        } else{
            return false;
        }

    }

    private void resetHits(){
        consecutiveHits = 0;
    }

    private void gotWounded(){
        if(health>0){
            health--;
        }
    }

    private void incConsecutiveHits(){
        consecutiveHits++;
    }
    
}
