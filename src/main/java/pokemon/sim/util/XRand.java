/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pokemon.sim.util;

import java.util.List;
import java.util.Random;

/**
 *
 * @author May5th
 */
public class XRand {
    
    private static final Random rand = new Random();

    /**
     * Returns a random boolean with 50% probability.
     */
    public static boolean flip() {
        return rand.nextBoolean();
    }

    /**
     * Returns true with the given percent chance (0–100).
     */
    public static boolean chance(int percent) {
        return rand.nextInt(100) < percent;
    }

    /**
     * Returns a random integer between min (inclusive) and max (inclusive).
     */
    public static int range(int min, int max) {
        return rand.nextInt(max - min + 1) + min;
    }

    /**
     * Returns a random element from a list.
     */
    public static <T> T pick(List<T> list) {
        if (list == null || list.isEmpty()) return null;
        return list.get(rand.nextInt(list.size()));
    }

    /**
     * Returns one of the given elements at random.
     */
    @SafeVarargs
    public static <T> T oneOf(T... options) {
        if (options == null || options.length == 0) return null;
        return options[rand.nextInt(options.length)];
    }

    /**
     * Simulates a critical hit roll (e.g., 1 in 16 chance).
     */
    public static boolean crit(int rate) {
        return rand.nextInt(rate) == 0;
    }

    /**
     * Shuffles a list in-place.
     */
    public static <T> void shuffle(List<T> list) {
        if (list != null) java.util.Collections.shuffle(list, rand);
    }

    /**
     * Returns a new seedable Random instance.
     */
    public static Random newSeeded(long seed) {
        return new Random(seed);
    }

    /**
     * Returns the shared internal Random instance.
     */
    public static Random get() {
        return rand;
    }
}
