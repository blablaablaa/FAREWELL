package io.github.blablaablaa.drop;

import java.io.Serializable;

public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;

    public boolean completedLevel1;
    public boolean completedLevel2;

    public GameState(boolean completedLevel1, boolean completedLevel2) {
        this.completedLevel1 = completedLevel1;
        this.completedLevel2 = completedLevel2;
    }
}
