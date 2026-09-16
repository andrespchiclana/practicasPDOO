package Irrgarten;

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
