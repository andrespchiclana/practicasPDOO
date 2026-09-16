/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author chpoz
 */
public class Monster extends LabyrinthCharacter{
    
    private static final int INITIAL_HEALTH=5;
    

    
    public Monster(String name, double intelligence, double strength) {
        super(name, intelligence, strength, INITIAL_HEALTH);
    }
    
    @Override
    public double attack(){
        return Dice.intensity(getStrength());
    }

    @Override
    public boolean defend(double receivedAttack){
        boolean isDead=dead();
        
        if(!isDead){
            double defensiveEnergy = Dice.intensity(getIntelligence());
            
            if(defensiveEnergy < receivedAttack){
                gotWounded();
                
                isDead=dead();
            }
        }
        
        return isDead;
    }
    
    @Override
    public String toString(){
        return "Monster{" + super.toString() + "}\n";
    }
    
    
}
