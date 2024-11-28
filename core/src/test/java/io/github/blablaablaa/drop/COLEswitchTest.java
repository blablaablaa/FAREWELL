package io.github.blablaablaa.drop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class COLEswitchTest {
    COLE cole;
    @BeforeEach
    void setUp() {
        cole = new COLE();
    }

    @Test
    void testLoadScreen() {
        int x = cole.switchToLoadScreen();
        assertEquals(x, 1, "Didnt switch correctly");
        if(x == 1){
            System.out.println("Switched Correctly to Load or New Screen");
        }
    }

    @Test
    void testLevelSelectionScreen() {
        int x = cole.switchToLevelSelection();
        assertEquals(x, 1, "Didnt switch correctly");
        if(x == 1){
            System.out.println("Switched Correctly to Level Selection Screen");
        }
    }

    @Test
    void testVictoryScreen() {
        int x = cole.switchToVictoryScreen();
        assertEquals(x, 1, "Didnt switch correctly");
        if(x == 1){
            System.out.println("Switched Correctly to Victory Screen");
        }
    }

    @Test
    void testDefeatScreen() {
        int x = cole.switchToDefeatScreen();
        assertEquals(x, 1, "Didnt switch correctly");
        if(x == 1){
            System.out.println("Switched Correctly to Defeat Screen");
        }
    }
}

