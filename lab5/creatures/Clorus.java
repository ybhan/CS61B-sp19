package creatures;

import huglife.Creature;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;

import java.awt.Color;
import java.util.Map;
import java.util.Deque;
import java.util.ArrayDeque;

import static huglife.HugLifeUtils.randomEntry;

public class Clorus extends Creature {

    /** Color of Clorus. */
    private int r = 34;
    private int g = 0;
    private int b = 231;

    private static final Double moveEnergyLose = 0.03;
    private static final Double stayEnergyLose = 0.01;

    public Clorus(double e) {
        super("clorus");
        energy = e;
    }

    public Clorus() {
        this(1);
    }

    @Override
    public double energy() {
        return super.energy();
    }

    @Override
    public Color color() {
        return color(r, g, b);
    }

    @Override
    public void move() {
        energy -= moveEnergyLose;
    }

    @Override
    public void attack(Creature c) {
        energy += c.energy();
    }

    @Override
    public Clorus replicate() {
        energy /= 2;
        return new Clorus(energy);
    }

    @Override
    public void stay() {
        energy -= stayEnergyLose;
    }

    @Override
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        Deque<Direction> plipNeighbors = new ArrayDeque<>();
        boolean anyPlip = false;

        for (Direction dir : neighbors.keySet()) {
            if (neighbors.get(dir).name().equals("empty")) {
                emptyNeighbors.addLast(dir);
            } else if (neighbors.get(dir).name().equals("plip")) {
                plipNeighbors.addLast(dir);
                anyPlip = true;
            }
        }

        if (emptyNeighbors.isEmpty()) {
            return new Action(Action.ActionType.STAY);
        } else if (anyPlip) {
            return new Action(Action.ActionType.ATTACK, randomEntry(plipNeighbors));
        } else if (energy >= 1.0) {
            return new Action(Action.ActionType.REPLICATE, randomEntry(emptyNeighbors));
        } else {
            return new Action(Action.ActionType.MOVE, randomEntry(emptyNeighbors));
        }
    }
}
