package io.github.blablaablaa.drop;

class RedBird extends Bird {
    public RedBird(float x, float y) {
        super("Red Bird", 10.0f, 2, "red_bird.png", x, y, 0.075f);
    }

    @Override
    public void launch() {
        System.out.println("Red Bird launched!");
    }
}
