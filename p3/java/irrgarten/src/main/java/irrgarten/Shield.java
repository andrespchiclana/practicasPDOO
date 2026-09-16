/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author chpoz
 */
public class Shield {
    
    private double protection;
    private int uses;

    public Shield(double protection, int uses) {
        this.protection = protection;
        this.uses = uses;
    }

    public double protect() {
        if (uses > 0) {
            uses--;
            return protection;
        } else {
            return 0;
        }
    }

    public boolean discard(){
        return Dice.discardElement(uses);
    }

    @Override
    public String toString() {
        return "S[" + protection + ", " + uses + "]";
    }
    
}
