/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package Irrgarten;

/**
 *
 * @author chpoz
 */
public class TestP1 {

    public static void main(String[] args) {
        
        //Weapon
        Weapon myWeapon = new Weapon(7.0, 5);
        
        System.out.println("atack: " + myWeapon.attack());
        System.out.println("toString: " + myWeapon);
        System.out.println("discard: " + myWeapon.discard());
        
        //Shield
        Shield myShield = new Shield(5.0, 5);
        
        System.out.println("protect: " + myShield.protect());
        System.out.println("toString: " + myShield);
        System.out.println("discard: " + myShield.discard());
        
        //Dice

        System.out.println("randomPos: " + Dice.randomPos(10));
        System.out.println("whoStarts: " + Dice.whoStarts(5));
        System.out.println("randomIntelligence: " + Dice.randomIntelligence());
        System.out.println("randomStrength: " + Dice.randomStrength());
        System.out.println("resurrectPlayer: " + Dice.resurrectPlayer());
        System.out.println("weaponsReward: " + Dice.weaponsReward());
        System.out.println("shieldsReward: " + Dice.shieldsReward());
        System.out.println("healthReward: " + Dice.healthReward());
        System.out.println("weaponPower: " + Dice.weaponPower());
        System.out.println("shieldPower: " + Dice.shieldPower());
        System.out.println("usesLeft: " + Dice.usesLeft());
        System.out.println("intensity: " + Dice.intensity(10.0));
        System.out.println("discardElement: " + Dice.discardElement(4));
        
        //GameState
        GameState myGameState = new GameState("miLaberinto", "misJugadores", "misMonstruos", 5, true, "miLog");

        System.out.println("getLabyrinth: " + myGameState.getLabyrinth());
        System.out.println("getPlayers: " + myGameState.getPlayers());
        System.out.println("getMonsters: " + myGameState.getMonsters());
        System.out.println("getCurrentPlayer: " + myGameState.getCurrentPlayer());
        System.out.println("isWinner: " + myGameState.isWinner());
        System.out.println("getLog: " + myGameState.getLog());

        //Direction
        System.out.println("Direction.LEFT: " + Directions.LEFT);
        System.out.println("Direction.RIGHT: " + Directions.RIGHT);
        System.out.println("Direction.UP: " + Directions.UP);
        System.out.println("Direction.DOWN: " + Directions.DOWN);

        //GameCharacter
        System.out.println("GameCharacter.PLAYER: " + GameCharacter.PLAYER);
        System.out.println("GameCharacter.MONSTER: " + GameCharacter.MONSTER);

        //Orientation
        System.out.println("Orientation.VERTICAL: " + Orientation.VERTICAL);
        System.out.println("Orientation.HORIZONTAL: " + Orientation.HORIZONTAL);


    }
}
