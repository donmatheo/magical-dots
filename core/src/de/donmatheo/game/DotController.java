package de.donmatheo.game;

import box2dLight.RayHandler;
import com.badlogic.gdx.utils.Array;
import java.util.Random;

/**
 * Created by Eceeb on 02.11.2014.
 */
public class DotController {

    private Array<Dot> dots = new Array<Dot>();
    private Random random = new Random();

    public Array<Dot> createDots(int numberOfDots, RayHandler rayHandler) {
        for (int i = 0; i < numberOfDots; i++) {
            dots.add(new Dot(rayHandler));
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
            dots.get(i).setRelation1(dots.get(number1));
            dots.get(i).setRelation2(dots.get(number2));
        }
    }

    public void setRandomLayout(float width, float height) {
        for (Dot dot : dots) {
            do {
                dot.x = dot.radius + random.nextInt((int) (width - 2 * dot.radius));
                dot.y = dot.radius + random.nextInt((int) (height - 2 * dot.radius));
                dot.getPointLight().setPosition(dot.x, dot.y);
            } while(dotIntersectingOtherDots(dot));
        }
    }

    private boolean dotIntersectingOtherDots(Dot standalone) {
        for(int i = dots.indexOf(standalone, true) - 1; i > -1; i--){
            if(standalone.overlaps(dots.get(i)))
                return true;
        }
        return false;
    }
}