package AB2;

import AB1.Vector3;
import AB1.SpaceDraw;
import codedraw.CodeDraw;

import java.util.Objects;

/**
 * This class represents celestial bodies like stars, planets, asteroids, teapots, etc..
 */
public class Body {

    private double mass;
    private Vector3 massCenter; // position of the center of mass.
    private Vector3 currentMovement;

    public Body(double mass, Vector3 massCenter, Vector3 currentMovement) {
        this.mass = mass;
        this.massCenter = massCenter;
        this.currentMovement = currentMovement;
    }

    public double getMass() {
        return mass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Body body = (Body) o;
        return Double.compare(mass, body.mass) == 0 && Objects.equals(massCenter, body.massCenter) && Objects.equals(currentMovement, body.currentMovement);
    }

    public Vector3 getMassCenter() {
        return massCenter;
    }

    public void setMassCenter(Vector3 massCenter) {
        this.massCenter = massCenter;
    }

    public Vector3 getCurrentMovement() {
        return currentMovement;
    }

    public void setCurrentMovement(Vector3 currentMovement) {
        this.currentMovement = currentMovement;
    }

    /**
     * Returns the distance between the centers of mass of this body and the specified body 'b'.
     * @param b the specified body, b != null.
     * @return the distance between the centers of mass of this body and the specified body 'b'.
     */
    public double distanceTo(Body b) {

        return massCenter.distanceTo(b.getMassCenter());
    }

    /**
     * Returns the acceleration vector of 'this' that results from the gravitational interaction
     * with the specified body 'b'. The returned vector is computed according to Newton's laws of
     * gravitation.
     * Hint: see method 'acceleration' in Simulation.java to find out how this is done.
     * @param b the specified body, b != null.
     * @return the acceleration vector.
     */
    public Vector3 acceleration(Body b) {
        double G = 6.6743e-11;

        // "force" F between two masses m₁ and m₂:
        // F = Gm₁m₂/d² = m₁a₁ -> a₁ = Gm₂/d²
        Vector3 direction = b.getMassCenter().minus(this.massCenter);
        double distance = direction.length();
        direction.normalize();
        double length = G * b.getMass() / (distance * distance);
        return direction.times(length);
    }

    /**
     * Accelerates this body for one second according to the specified 'acceleration' vector
     * and updates the current movement accordingly.
     * Hint: see method 'accelerate' in Simulation.java to find out how this is done.
     * @param acceleration the acceleration vector, acceleration != null.
     */
    public void accelerate(Vector3 acceleration) {

        // accelerate for one second and update movement
        currentMovement = currentMovement.plus(acceleration);
        massCenter = massCenter.plus(currentMovement);
    }

    /**
     * Returns the approximate radius of this body.
     * (It is assumed that the radius r is related to the mass m of the body by
     * r = Math.pow(m, 0.5), where m and r measured in solar mass units.)
     * @return the radius of this body.
     */
    public double getRadius() {

        return Math.pow(mass, 0.5);
    }

    /**
     * Returns a new body that is formed by the collision of this body and 'b'. The mass of the
     * returned body is the sum of the masses of this body and 'b'. The current movement of the
     * returned body is given by the law of conservation of momentum. (The momentum of the
     * returned body is the sum of the momentums of 'this' and 'b').
     * Hint: see method 'merge' in Simulation.java to find out how this is done.
     * @param b the body with which 'this' is merged, b != null.
     * @return the body being formed by the collision.
     */
    public Body merge(Body b) {

        double mass = getMass() + b.getMass();
        Vector3 massCenter = getMassCenter().
                times(getMass()).plus(b.getMassCenter().times(b.getMass())).
                times(1 / mass);

        // Momentum of a body corresponds to its velocity (currentMovement) times its mass.
        // Momentum v₃m₃ of result b₃ is the sum of the momentums of b₁ and b₂:
        // v₃m₃ = v₁m₁ + v₂m₂ -> v₃ = (v₁m₁ + v₂m₂)/m₃
        Vector3 currentMovement =
                getCurrentMovement().
                        times(getMass()).
                        plus(b.getCurrentMovement().times(b.getMass())).
                        times(1.0 / mass);
        return new Body(mass, massCenter, currentMovement);
    }

    /**
     * Draws this body to the specified canvas as a filled circle.
     * The radius of the circle corresponds to the radius of the body
     * (use a conversion of the real scale to the scale of the canvas as
     * in 'Simulation.java').
     * Hint: call the method 'drawAsFilledCircle' implemented in 'Vector3'.
     * @param cd the CodeDraw object used for drawing, cd != null.
     */
    public void draw(CodeDraw cd) {

        cd.setColor(SpaceDraw.massToColor(mass));
        double radius = SpaceDraw.massToRadius(mass);
        massCenter.drawAsFilledCircle(cd, radius);
    }

    /**
     * Returns a string with the information about this body including
     * mass, position (mass center) and current movement. Example:
     * "5.972E24 kg, position: [1.48E11, 0.0, 0.0] m, movement: [0.0, 29290.0, 0.0] m/s."
     *
     * @return 'this' represented as a string.
     */
    public String toString() {
        return mass + " kg" +
                ", position: " + massCenter.toString() +
                ", movement: " + currentMovement.toString() + " m/s.";
    }
}

