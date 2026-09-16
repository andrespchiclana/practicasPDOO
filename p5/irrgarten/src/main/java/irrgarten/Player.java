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
public class Player extends LabyrinthCharacter{
    
    private static final int MAX_WEAPONS=2;
    private static final int MAX_SHIELDS=3;
    private static final int INITIAL_HEALTH=10;
    private static final int HITS2LOSE=3;

    private char number;
    private int consecutiveHits=0;

    private ArrayList<Weapon> weapons;
    private ArrayList<Shield> shields;

    private WeaponCardDeck weaponCardDeck;
    private ShieldCardDeck shieldCardDeck;

    
    public Player(char number, double intelligence, double strength){
        super("Player# " + number, intelligence, strength, INITIAL_HEALTH);
        this.number = number;
        
        weapons = new ArrayList<>();
        shields = new ArrayList<>();

        weaponCardDeck = new WeaponCardDeck();
        weaponCardDeck.addCards();

        shieldCardDeck = new ShieldCardDeck();
        shieldCardDeck.addCards();
        
    }

    public Player(Player other){
        super(other);
        number = other.number;

        weapons = other.weapons;
        shields = other.shields;

        weaponCardDeck = other.weaponCardDeck;
        shieldCardDeck = other.shieldCardDeck;

    }

    public void resurrect(){
        setHealth(INITIAL_HEALTH);
        weapons.clear();
        shields.clear();
        consecutiveHits = 0;
    }

    public char getNumber(){
        return number;
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

    @Override
    public double attack(){
        return (getStrength() + sumWeapons());
    }

    @Override
    public boolean defend(double receivedAttack){
        return manageHit(receivedAttack);
    }

    public void receiveReward(){
        
        int weaponsReward = Dice.weaponsReward();
        int shieldsReward = Dice.shieldsReward();

        for(int i=1; i<=weaponsReward; i++){
                
            Weapon wnew = this.weaponCardDeck.nextCard();
            
            receiveWeapon(wnew);
        }

        for(int i=1; i<=shieldsReward; i++){
            
            Shield snew = this.shieldCardDeck.nextCard();
            
            receiveShield(snew);
        }

        int extraHealth = Dice.healthReward();

        setHealth(getHealth() + extraHealth);
    }

    @Override
    public String toString(){
        return "Player{" + super.toString() + ", consecutiveHits= " + consecutiveHits + "\nweapons= " + weapons.toString() + "\nshields= " + shields.toString() + "}\n";
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

    /*
    private Weapon newWeapon(){
        return new Weapon(Dice.weaponPower(), Dice.usesLeft());
    }

    private Shield newShield(){
        return new Shield(Dice.shieldPower(), Dice.usesLeft());
    }
    */
    
    protected double sumWeapons(){
        double sum=0;
        for(Weapon w: weapons){
            sum += w.attack();
        }

        return sum;
    }

    protected double sumShields(){
        double sum=0;
        for(Shield s: shields){
            sum += s.protect();
        }

        return sum;
    }

    protected double defensiveEnergy(){
        return (getIntelligence() + sumShields());
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

    private void incConsecutiveHits(){
        consecutiveHits++;
    }
    
}
