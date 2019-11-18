package creatures;

import huglife.Creature;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

import static huglife.HugLifeUtils.randomEntry;

/**
 * An implementation of a motile pacifist photosynthesizer.
 *
 * @author Josh Hug
 */
public class Plip extends Creature {

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

    private static final Double moveEnergyLose = 0.15;
    private static final Double stayEnergyGain = 0.2;
    private static final Double moveProbability = 0.5;

    /**
     * creates plip with energy equal to E.
     */
    public Plip(double e) {
        super("plip");
        r = 99;
        g = 63;
        b = 76;
        energy = e;
    }

    /**
     * creates a plip with energy equal to 1.
     */
    public Plip() {
        this(1);
    }

    /**
     * Should return a color with red = 99, blue = 76, and green that varies
     * linearly based on the energy of the Plip. If the plip has zero energy,
     * it should have a green value of 63. If it has max energy, it should
     * have a green value of 255. The green value should vary with energy
     * linearly in between these two extremes. It's not absolutely vital
     * that you get this exactly correct.
     */
    public Color color() {
        return color(r, (int) (g + 96 * energy), b);
    }

    private void energyRestrict() {
        energy = Math.min(energy, 2);
        energy = Math.max(energy, 0);
    }

    /**
     * Do nothing with C, Plips are pacifists.
     */
    public void attack(Creature c) {
        // do nothing.
    }

    /**
     * Plips should lose 0.15 units of energy when moving. If you want to
     * to avoid the magic number warning, you'll need to make a
     * private static final variable. This is not required for this lab.
     */
    public void move() {
        energy -= moveEnergyLose;
        energyRestrict();
    }


    /**
     * Plips gain 0.2 energy when staying due to photosynthesis.
     */
    public void stay() {
        energy += stayEnergyGain;
        energyRestrict();
    }

    /**
     * Plips and their offspring each get 50% of the energy, with none
     * lost to the process. Now that's efficiency! Returns a baby
     * Plip.
     */
    public Plip replicate() {
        energy /= 2;
        Plip pBaby = new Plip(energy);
        return pBaby;
    }

    /**
     * Plips take exactly the following actions based on NEIGHBORS:
     * 1. If no empty adjacent spaces, STAY.
     * 2. Otherwise, if energy >= 1, REPLICATE towards an empty direction
     * chosen at random.
     * 3. Otherwise, if any Cloruses, MOVE with 50% probability,
     * towards an empty direction chosen at random.
     * 4. Otherwise, if nothing else, STAY
     * <p>
     * Returns an object of type Action. See Action.java for the
     * scoop on how Actions work. See SampleCreature.chooseAction()
     * for an example to follow.
     */
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        // Rule 1
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        boolean anyClorus = false;
        for (Direction dir : neighbors.keySet()) {
            if (neighbors.get(dir).name().equals("empty")) {
                emptyNeighbors.addLast(dir);
            } else if (neighbors.get(dir).name().equals("clorus")) {
                anyClorus = true;
            }
        }

        if (emptyNeighbors.isEmpty()) {
            return new Action(Action.ActionType.STAY);
        }

        // Rule 2
        if (energy() >= 1.0) {
            return new Action(Action.ActionType.REPLICATE, randomEntry(emptyNeighbors));
        }

        // Rule 3
        if (anyClorus && Math.random() < moveProbability) {
            return new Action(Action.ActionType.MOVE, randomEntry(emptyNeighbors));
        }

        // Rule 4
        return new Action(Action.ActionType.STAY);
    }
}
