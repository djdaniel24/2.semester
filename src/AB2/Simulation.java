package AB2;

import AB1.SpaceDraw;
import AB1.Vector3;
import codedraw.CodeDraw;

import java.awt.*;
import java.util.Random;

/**
 * Simulates a cluster of bodies.
 */
public class Simulation {

    // gravitational constant
    public static final double G = 6.6743e-11;

    // one astronomical unit (AU) is the average distance between earth and sun.
    public static final double AU = 150e9; // meters

    // some further constants needed in the simulation
    public static final double SUN_MASS = 1.989e30; // kilograms
    public static final double SUN_RADIUS = 696340e3; // meters

    // set some system parameters
    public static final int NUMBER_OF_BODIES = 50;
    public static final double OVERALL_SYSTEM_MASS = 30 * SUN_MASS; // kilograms

    // all quantities are based on units of kilogram respectively second and meter.

    public static void main(String[] args) {

        // simulation
        CodeDraw cd = new CodeDraw();

        BodyQueue bodyQueue = new BodyQueue(NUMBER_OF_BODIES);
        BodyAccelerationMap bodyAccelerationMap = new BodyAccelerationMap(NUMBER_OF_BODIES);

        Random random = new Random(2024);

//        Body sun = new Body(1.989e30,new Vector3(0,0,0),new Vector3(0,0,0));
//        Body earth = new Body(5.972e24,new Vector3(-1.394555e11,5.103346e10,0),new Vector3
//                (-10308.53,-28169.38,0));
//        Body mercury = new Body(3.301e23,new Vector3(-5.439054e10,9.394878e9,0),new Vector3
//                (-17117.83,-46297.48,-1925.57));
//        Body venus = new Body(4.86747e24,new Vector3(-1.707667e10,1.066132e11,2.450232e9),new
//                Vector3(-34446.02,-5567.47,2181.10));
//
//        bodyQueue.add(sun);
//        bodyQueue.add(earth);
//        bodyQueue.add(mercury);
//        bodyQueue.add(venus);

        for (int i = 0; i < NUMBER_OF_BODIES; i++) {
            double mass = Math.abs(random.nextGaussian()) * OVERALL_SYSTEM_MASS / NUMBER_OF_BODIES; // kg
            Vector3 massCenter = new Vector3(
                    0.2 * random.nextGaussian() * AU,
                    0.2 * random.nextGaussian() * AU,
                    0.2 * random.nextGaussian() * AU);
            Vector3 currentMovement = new Vector3(
                    random.nextGaussian() * 5e3,
                    random.nextGaussian() * 5e3,
                    random.nextGaussian() * 5e3);
            bodyQueue.add(new Body(mass, massCenter, currentMovement));
        }

        double seconds = 0;

        // simulation loop
        while (true) {
            seconds++; // each iteration computes the movement of the celestial bodies within one second.


            {
                // merge bodies that have collided
//                BodyQueue newQ = new BodyQueue(NUMBER_OF_BODIES); // queue to replace with

                BodyQueue queCpyI = new BodyQueue(bodyQueue); // help queue to iterate through (i)
                for (Body bodyI = queCpyI.poll(); bodyI != null; bodyI = queCpyI.poll()) {

                    BodyQueue queCpyJ = new BodyQueue(queCpyI); // help queue to iterate through (j)
                    queCpyJ.poll(); // skip check with itself
                    for (Body bodyJ = queCpyJ.poll(); bodyJ != null; bodyJ = queCpyJ.poll()) {
                        if (bodyJ.getMassCenter().distanceTo(bodyI.getMassCenter()) <
                                SpaceDraw.massToRadius(bodyJ.getMass()) + SpaceDraw.massToRadius(bodyI.getMass())) {

                            Body newBodyI = bodyI.merge(bodyJ);

                            // remove bodyJ and replace bodyI from bodyQueue and restart this loop
                            bodyQueue.remove(bodyI, bodyJ);
                            bodyQueue.add(newBodyI);

                            // restarting since there might be a new collision after merge
                            queCpyI.remove(bodyJ);
                            queCpyJ = new BodyQueue(queCpyI);
                            queCpyJ.poll(); // skip check with itself
                        }
                    }
//                    newQ.add(bodyI); // filling the new queue
                }
            }

            {
                // for each body in bodyQueue: compute its total acceleration.
                BodyQueue queCpyI = new BodyQueue(bodyQueue); // help queue to iterate through (i)
                for (Body bodyI = queCpyI.poll(); bodyI != null; bodyI = queCpyI.poll()) {
                    bodyAccelerationMap.put(bodyI, new Vector3(0, 0, 0));

                    BodyQueue queCpyJ = new BodyQueue(bodyQueue); // help queue to iterate through (j)
                    for (Body bodyJ = queCpyJ.poll(); bodyJ != null; bodyJ = queCpyJ.poll()) {
                        if (!bodyI.equals(bodyJ)) {
                            Vector3 toAdd = bodyI.acceleration(bodyJ);
                            bodyAccelerationMap.put(bodyI, bodyAccelerationMap.get(bodyI).plus(toAdd));
                        }
                    }
                }
                // now bodyAccelerationMap.Get(i) holds the total acceleration vector for bodyQueue.poll() == i.
            }

            {
                // for each body in bodyQueue: accelerate it for one second.
                BodyQueue queCpyI = new BodyQueue(bodyQueue); // help queue to iterate through (i)
                for (Body bodyI = queCpyI.poll(); bodyI != null; bodyI = queCpyI.poll()) {
                    bodyI.accelerate(bodyAccelerationMap.get(bodyI));
                }
            }

            // show all movements in the canvas only every hour (to speed up the simulation)
            if (seconds % (3600) == 0) {
                // clear old positions (exclude the following line if you want to draw orbits).
                cd.clear(Color.BLACK);

                // draw new positions
                BodyQueue queCpyI = new BodyQueue(bodyQueue); // help queue to iterate through (i)
                for (Body bodyI = queCpyI.poll(); bodyI != null; bodyI = queCpyI.poll()) {
                    bodyI.draw(cd);
                }

                // show new positions
                cd.show();
            }
        }

    }

}

