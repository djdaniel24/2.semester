package AB1;

import codedraw.*;

import java.awt.*;
import java.util.Random;

/**
 * 1. Unter Datenkapselung versteht man die Elemente eines Objektes nicht für alle offen zur verfügung zu stellen,
 * sondern über andere Interfaces. Wie Getter/Setter
 * 2. Unter Data Hiding wird das Lokalisieren von Daten eines Objektes verstanden. Aka die Objektvariablen als
 * Privat definieren
 * 3. Auf der Linken seite von solch einem Punkt steht ein Objekt oder eine Instanz.
 */

/**
 * Simulates the formation of a massive solar system.
 */
public class Simulation {

    // one astronomical unit (AU) is the average distance between earth and sun.
    public static final double AU = 150e9; // meters

    // some further constants needed in the simulation
    public static final double SUN_MASS = 1.989e30; // kilograms
    public static final double SUN_RADIUS = 696340e3; // meters

    // set some system parameters
    public static final double SECTION_SIZE = 2 * AU; // the size of the square region in space
    public static final int NUMBER_OF_BODIES = 50;
    public static final double OVERALL_SYSTEM_MASS = 30 * SUN_MASS; // kilograms

    // all quantities are based on units of kilogram respectively second and meter.

    /**
     * The main simulation method using instances of other classes.
     * @param args not used.
     */
    public static void main(String[] args) {

        // simulation
        CodeDraw cd = new CodeDraw();
        Body[] bodies = new Body[NUMBER_OF_BODIES];
        Vector3[] accelerationOfBody = new Vector3[bodies.length];

        Random random = new Random(2024);

        for (int i = 0; i < bodies.length; i++) {
            double mass = Math.abs(random.nextGaussian()) * OVERALL_SYSTEM_MASS / bodies.length; // kg
            Vector3 massCenter = new Vector3(0.2 * random.nextGaussian() * AU, 0.2 * random.nextGaussian() * AU, 0.2 * random.nextGaussian() * AU);
            Vector3 currentMovement = new Vector3(random.nextGaussian() * 5e3, random.nextGaussian() * 5e3, random.nextGaussian() * 5e3);
            bodies[i] = new Body(mass, massCenter, currentMovement);
        }

        double seconds = 0;

        // simulation loop
        while (true) {
            seconds++; // each iteration computes the movement of the celestial bodies within one second.

            // merge bodies that have collided
            for (int i = 0; i < bodies.length; i++) {
                for (int j = i + 1; j < bodies.length; j++) {
                    if (bodies[j].getMassCenter().distanceTo(bodies[i].getMassCenter()) <
                            SpaceDraw.massToRadius(bodies[j].getMass()) + SpaceDraw.massToRadius(bodies[i].getMass())) {
                        // collision of bodies i and j
                        bodies[i] = bodies[i].merge(bodies[j]);

                        // generate a duplicate of the array with body j removed.
                        Body[] bodiesOneRemoved = new Body[bodies.length - 1];
                        for (int k = 0; k < bodiesOneRemoved.length; k++) {
                            bodiesOneRemoved[k] = bodies[k < j ? k : k + 1];
                        }
                        bodies = bodiesOneRemoved;

                        // since the body index i changed size there might be new collisions
                        // at all positions of bodies, so start all over again
                        i = -1;
                        j = bodies.length;
                    }
                }
            }

            // for each body (with index i): compute its total acceleration.
            for (int i = 0; i < bodies.length; i++) {
                accelerationOfBody[i] = new Vector3(0, 0, 0); // begin with zero
                for (int j = 0; j < bodies.length; j++) {
                    if (i != j) {
                        Vector3 toAdd = bodies[i].acceleration(bodies[j]);
                        accelerationOfBody[i] = accelerationOfBody[i].plus(toAdd);
                    }
                }
            }
            // now accelerationOfBody[i] holds the total acceleration vector for bodies[i].

            // for each body (with index i): accelerate it for one second.
            for (int i = 0; i < bodies.length; i++) {
                bodies[i].accelerate(accelerationOfBody[i]);
            }

            // show all movements in the canvas only every hour (to speed up the simulation)
            if (seconds % (3600) == 0) {
                // clear old positions (exclude the following line if you want to draw orbits).
                cd.clear(Color.BLACK);

                // draw new positions
                for (Body body : bodies) {
                    body.draw(cd);
                }

                // show new positions
                cd.show();
            }

        }

    }
}
