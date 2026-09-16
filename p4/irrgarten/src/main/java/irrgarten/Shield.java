/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author chpoz
 */
public class Shield extends CombatElement {
    
    public Shield(double power, int uses){
        super(power,uses);
    }
    
    public double protect(){
        return produceEffect();
    }
    
    @Override
    public String toString(){
        return "S[" + super.toString() + "]";
    }
    
}
