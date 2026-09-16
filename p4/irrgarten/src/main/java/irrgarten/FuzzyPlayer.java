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
public class FuzzyPlayer extends Player {
    
    public FuzzyPlayer(Player other){
        super(other);
    }

    @Override
    public Directions move(Directions direction, ArrayList<Directions> validMoves){
        Directions output;
        boolean contained = validMoves.contains(direction);
        
        if (contained && !validMoves.isEmpty()) {

            output = Dice.nextStep(direction, validMoves, getIntelligence());

        } else {
            validMoves.remove(direction);
            output = Dice.nextStep(validMoves.get(0), validMoves, getIntelligence());
        }
        return output;
    }

    @Override
    public double attack(){
        return  sumWeapons() + Dice.intensity(getStrength());
    }

    protected double deffensiveEnergy(){
        return sumShields() + Dice.intensity(getIntelligence());
    }

    @Override
    public String toString(){
        return "Fuzzy" + super.toString();
    }

}
