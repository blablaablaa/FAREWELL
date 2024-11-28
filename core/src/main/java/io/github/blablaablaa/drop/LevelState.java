package io.github.blablaablaa.drop;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LevelState implements Serializable {
    private static final long serialVersionUID = 1L;

    // Birds
    public static class BirdState implements Serializable {
        public float x, y;
        public float velocityX, velocityY;
        public float angularVelocity;
        public String type;

        public BirdState(float x, float y, float velocityX, float velocityY, float angularVelocity, String type) {
            this.x = x;
            this.y = y;
            this.velocityX = velocityX;
            this.velocityY = velocityY;
            this.angularVelocity = angularVelocity;
            this.type = type;
        }
    }

    // Pigs
    public static class PigState implements Serializable {
        public float x, y;
        public float velocityX, velocityY;
        public float angularVelocity;
        public int health;
        public String type;
        public PigState(float x, float y, float velocityX, float velocityY, float angularVelocity, int health, String type) {
            this.x = x;
            this.y = y;
            this.velocityX = velocityX;
            this.velocityY = velocityY;
            this.angularVelocity = angularVelocity;
            this.health = health;
            this.type = type;
        }
    }

    public static class MaterialState implements Serializable {
        float x, y, velocityX, velocityY, angularVelocity;
        int health;
        public String type;

        public MaterialState(float x, float y, float velocityX, float velocityY, float angularVelocity, int health, String type) {
            this.x = x;
            this.y = y;
            this.velocityX = velocityX;
            this.velocityY = velocityY;
            this.angularVelocity = angularVelocity;
            this.health = health;
            this.type = type;
        }
    }


    public List<BirdState> birds = new ArrayList<>();
    public List<PigState> pigs = new ArrayList<>();
    public List<MaterialState> materials = new ArrayList<>();
    public int birdIndex;
    public boolean isPaused;
    public int pigCount;
    public int birdCount;

    public LevelState(int birdIndex, boolean isPaused, int pigCount, int birdCount) {
        this.birdIndex = birdIndex;
        this.isPaused = isPaused;
        this.pigCount = pigCount;
        this.birdCount = birdCount;
    }
}
