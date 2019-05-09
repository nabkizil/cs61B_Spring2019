package creatures;

import huglife.Creature;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

public class Clorus extends Creature {

    /**
     * red color.
     */
    private int r;
    /**
     * green color.
     */
    private int g;
    /**
     * blue color.
     */
    private int b;


    public Clorus(double e) {
        super("plip");
        r = 0;
        g = 0;
        b = 0;
        energy = e;
    }

    public Clorus replicate() {
        Clorus child_cl = new Clorus(energy);
        this.energy = 0.5 * this.energy; //energy of current parent plip
        child_cl.energy = this.energy; //energy of child plip

        return child_cl; //return parent?? should return child
    }


    public void attack(Creature c){
        Clorus cl = new Clorus(energy);
        cl.energy = c.energy();
    }

    public void move() {
        energy -= 0.03;
    }

    public void stay() {
        energy -= 0.01;
    }

    public Action chooseAction(Map<Direction, Occupant> neighbors){
       return null;
    }

    public Color color() {
        r = 34;
        b = 231;
        g = 0;
        return color(r, g, b);
    }

}
