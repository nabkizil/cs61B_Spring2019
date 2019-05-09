package creatures;
import org.junit.Test;
import static org.junit.Assert.*;

import huglife.*;

import java.awt.*;
import java.util.HashMap;

public class TestClorus {

    @Test
    public void testBasics() {
        System.out.println("check 1");
        Clorus p = new Clorus(2);
        assertEquals(2.0, p.energy(), 0.01);
        assertEquals(new Color(34, 0, 231), p.color());
    }

    @Test
    public void testReplicate() {
        System.out.println("check 2");

        Clorus p = new Clorus(2);
        Clorus p_baby = p.replicate();
        assertEquals(1, p.energy(), 0.01);
        assertEquals(1, p_baby.energy(), 0.01);
        assertNotSame(p, p_baby);
    }

    @Test
    public void chooseAction1() {
        System.out.println("check 3");

        Clorus p = new Clorus(2);
        Clorus p_baby = p.replicate();
        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();


        surrounded.put(Direction.TOP, new Plip());
        surrounded.put(Direction.BOTTOM, new Plip());
        surrounded.put(Direction.LEFT, new Plip());
        surrounded.put(Direction.RIGHT, new Plip());
        //Direction randDir = HugLifeUtils.randomEntry(surrounded);
        //    System.out.println("randDir");
        Action actual = p.chooseAction(surrounded);
        Action expected = new Action(Action.ActionType.STAY);
        assertEquals(expected, actual);
    }
    @Test
    public void chooseActionw2() {
        System.out.println("check 4");

        Clorus p = new Clorus(2);
        //Clorus p_baby = p.replicate();
        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Plip());
        surrounded.put(Direction.BOTTOM, new Empty());
        surrounded.put(Direction.LEFT, new Empty());
        surrounded.put(Direction.RIGHT, new Empty());
        Action actual = p.chooseAction(surrounded);
        Action expected = new Action(Action.ActionType.ATTACK, Direction.TOP);
        assertEquals(expected, actual);

    }
    @Test
    public void chooseActionw3() {
        Clorus p = new Clorus(2);
        //Clorus p_baby = p.replicate();
        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Empty());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Empty());

        Action actual = p.chooseAction(surrounded);
        Action expected = new Action(Action.ActionType.REPLICATE, Direction.TOP);
        assertEquals(expected, actual);

    }
    @Test
    public void chooseActionw4() {
        Clorus p = new Clorus(0.8);
        //Clorus p_baby = p.replicate();
        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Empty());
        surrounded.put(Direction.BOTTOM, new Empty());
        surrounded.put(Direction.LEFT, new Empty());
        surrounded.put(Direction.RIGHT, new Empty());

        Action actual = p.chooseAction(surrounded);
        Action expected = new Action(Action.ActionType.MOVE, Direction.TOP);
        assertEquals(expected, actual);

    }


    public static void main(String[] args) {
        System.exit(jh61b.junit.textui.runClasses(TestClorus.class));
    }

}
