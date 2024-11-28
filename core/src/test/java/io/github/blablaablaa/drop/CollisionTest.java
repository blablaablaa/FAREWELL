package io.github.blablaablaa.drop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CollisionTest {

    private BirdTest bird;
    private TargetTest target;

    @BeforeEach
    void setUp(){
        bird = new BirdTest(5);
        target = new TargetTest(7);
    }

    @Test
    void testHit() {
        handleBirdHit(bird, target);
        assertEquals(target.getHeatlh(), 2, "Health did not reduce correctly.");
        if(target.getHeatlh() == 2) System.out.println("Health = 2, i.e., reduced correctly.");
    }

    @Test
    void testAlive(){
        handleBirdHit(bird, target);
        handleBirdHit(bird, target);
        assertFalse(target.isAlive(), "Target did not die!");
        if(!target.isAlive()) System.out.println("Target destroyed correctly!");
    }

    private void handleBirdHit(BirdTest bird, TargetTest target) {
        int birdDamage = bird.getDamage();
        target.takeDamage(birdDamage);
    }

    public static class BirdTest {
        protected int damage;

        public BirdTest(int damage) {
            this.damage = damage;
        }

        public int getDamage() {
            return damage;
        }

    }

    public static class TargetTest{
        protected int heatlh;
        private boolean alive;
        public TargetTest(int heatlh) {
            this.heatlh = heatlh;
            alive = true;
        }

        public void takeDamage(int damage) {
            heatlh -= damage;
            if (heatlh <= 0) {
                alive = false;
            }
        }

        public int getHeatlh() {
            return heatlh;
        }

        public boolean isAlive() {
            return alive;
        }
    }


}
