package io.github.blablaablaa.drop;

class BombBird extends Bird {
    public BombBird(float x, float y) {
        super("Bomb Bird", 8.0f, 3, "bomb_bird.png", x, y, 0.04f);
    }

    @Override
    public void launch() {
        System.out.println("Bomb Bird launched!");
    }
}
