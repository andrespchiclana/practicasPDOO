/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author chpoz
 */
abstract class CombatElement {
    
    private double effect;
    private int uses;

    public CombatElement(double effect, int uses) {
        this.effect = effect;
        this.uses = uses;
    }

    protected double produceEffect() {
        if (uses > 0) {
            uses--;
            return effect;
        } else {
            return 0;
        }
    }

    public boolean discard() {
        return Dice.discardElement(uses);
    }

    @Override
    public String toString() {
        return effect + ", " + uses;
    }

}
