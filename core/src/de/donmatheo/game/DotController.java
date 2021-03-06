package de.donmatheo.game;

import box2dLight.RayHandler;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import de.donmatheo.game.entities.Dot;
import de.donmatheo.game.entities.HardcoreDot;
import de.donmatheo.game.entities.Relation;

import java.util.Random;

/**
 * Created by Eceeb on 02.11.2014.
 */
public class DotController {

    private Array<Dot> dots = new Array<Dot>();
    private Random random = new Random();

    public Array<Dot> createDots(int numberOfDots, RayHandler rayHandler, boolean hardcore) {
        for (int i = 0; i < numberOfDots; i++) {
            Dot dot = hardcore == true ? new HardcoreDot(rayHandler) : new Dot(rayHandler);
            dots.add(dot);
        }
        return dots;
    }

    public void setRandomRelations() {
        for (int i = 0; i < dots.size; i++) {
            int number1 = i;
            int number2 = i;
            while (number1 == i) {
                number1 = random.nextInt(dots.size);
            }
            while (number2 == i || number2 == number1) {
                number2 = random.nextInt(dots.size);
            }
            Dot selected = dots.get(i);
            selected.setRelation1(new Relation(selected, dots.get(number1)));
            selected.setRelation2(new Relation(selected, dots.get(number2)));
        }
    }

    public void setRandomLayout(float width, float height) {
        for (Dot dot : dots) {
            float x = Dot.DEFAULT_RADIUS + random.nextInt((int) (width - 2 * Dot.DEFAULT_RADIUS));
            float y = Dot.DEFAULT_RADIUS + random.nextInt((int) (height - 2 * Dot.DEFAULT_RADIUS));
            dot.updatePosition(x, y);
        }

        for (Dot dot : dots)
            if (dot.hasIsoscelesRelations())
                setRandomLayout(width, height);
    }

    public void addAllToStage(Stage stage) {
        // add relations first because they have to be rendered before dots
        for (Dot dot : dots) {
            stage.addActor(dot.getRelation1());
            stage.addActor(dot.getRelation2());
        }
        // afterwards add all dots to the stage
        for (Dot dot : dots)
            stage.addActor(dot);
    }

    public void resetHardcoreAction() {
        HardcoreDot.delay.setDuration(HardcoreDot.movementTimer = 500f);
    }
}
