package io.github.blablaablaa.drop;

class BlueBird extends Bird {
    public BlueBird(float x, float y) {
        super("Blue Bird", 12.0f, 1, "blue_bird.png", x, y, 0.04f);
    }

    @Override
    public void launch() {
        System.out.println("Blue Bird launched!");
    }
}
