/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

import java.util.Random;
import java.util.ArrayList;

/**
 *
 * @author chpoz
 */
public class Dice {
    
    private static final Random generator = new Random();
    private static final int MAX_USES = 5;
    private static final double MAX_INTELLIGENCE = 10.0;
    private static final double MAX_STRENGTH = 10.0;
    private static final double RESURRECTION_PROB = 0.3;
    private static final int WEAPONS_REWARD = 2;
    private static final int SHIELDS_REWARD = 3;
    private static final int HEALTH_REWARD = 5;
    private static final int MAX_ATTACK = 3;
    private static final int MAX_SHIELD = 2;

    
    public static boolean discardElement(int usesLeft){
           if(usesLeft >= MAX_USES){
               return false;
           }
           
           return generator.nextFloat() < (1.0f - (float) usesLeft / MAX_USES);
    }
    
    public static double intensity(double competence){
        return generator.nextDouble() * competence;
    }
    
    
    public static int randomPos(int max){
        return generator.nextInt(max);
    }

    public static int whoStarts(int nplayers){
        return generator.nextInt(nplayers);
    }

    public static double randomIntelligence(){
        return generator.nextDouble() * MAX_INTELLIGENCE;
    }

    public static double randomStrength(){
        return generator.nextDouble() * MAX_STRENGTH;
    }   

    public static boolean resurrectPlayer(){
        return generator.nextFloat() < RESURRECTION_PROB;
    }
    
    public static int weaponsReward(){
        return generator.nextInt(WEAPONS_REWARD+1);
    }
    
    public static int shieldsReward(){
        return generator.nextInt(SHIELDS_REWARD + 1);
    }
    
    public static int healthReward(){
        return generator.nextInt(HEALTH_REWARD + 1);
    }
    
    public static double weaponPower(){
        return generator.nextDouble(MAX_ATTACK);
    }
    
    public static double shieldPower(){
        return generator.nextDouble(MAX_SHIELD);
    }
    
    public static int usesLeft(){
        return generator.nextInt(MAX_USES + 1);
    }
    
    public static Directions nextStep(Directions preference, ArrayList<Directions> validMoves, double intelligence){
        boolean direction = generator.nextDouble(intelligence) < intelligence;
        if(direction){
            return preference;
        } else{
            int index = generator.nextInt(validMoves.size());
            return validMoves.get(index);
        }
    }
    
}
