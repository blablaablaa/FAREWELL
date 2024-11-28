package io.github.blablaablaa.drop;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Game;

public class COLE extends Game {
    private Screen tapToPlayScreen;
    public static LevelSelectionScreen levelSelectionScreen;
    private Screen victoryScreen;
    private Screen defeatScreen;
    private Screen newOrLoadedScreen;
    private Screen temp;
    private LevelSelectionScreen levelSelectionScreen2;


    @Override
    public void create() {
        tapToPlayScreen = new TapToPlayScreen(this);
        levelSelectionScreen = new LevelSelectionScreen(this);
        victoryScreen = new VictoryScreen(this, levelSelectionScreen);
        defeatScreen = new DefeatScreen(this, levelSelectionScreen);
        newOrLoadedScreen = new NewOrLoadScreen(this);
        temp = new Level2Screen(this, levelSelectionScreen2);

        setScreen(tapToPlayScreen);
    }

    public void switchToTapToPlayScreen() {
        setScreen(tapToPlayScreen);
    }

    public int switchToLevelSelection() {
        setScreen(levelSelectionScreen);
        return 1;
    }

    public int switchToLoadScreen(){
        setScreen(newOrLoadedScreen);
        return 1;
    }

    public int switchToDefeatScreen(){
        setScreen(defeatScreen);
        return 1;
    }

    public int switchToVictoryScreen(){
        setScreen(victoryScreen);
        return 1;
    }
}


