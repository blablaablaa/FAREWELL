package io.github.blablaablaa.drop;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.*;

@TestMethodOrder(OrderAnnotation.class)
public class SavingMechanismTest {

    private SavingTest savingTest;
    private GameState gameState;

    @BeforeEach
    void setUp() {
        gameState = new GameState(true, false);
        savingTest = new SavingTest();
    }

    @Test
    @Order(1)
    void testSaving() {
        savingTest.saveGameState(gameState);
    }

    @Test
    @Order(2)
    void testLoading() {
        GameState gameState2 = savingTest.loadGameState();
        assertEquals(gameState.completedLevel1, gameState2.completedLevel1, "Level 1 not saved correctly");
        assertEquals(gameState.completedLevel2, gameState2.completedLevel2, "Level 2 not saved correctly");

        if (gameState.completedLevel1 == gameState2.completedLevel1 &&
            gameState.completedLevel2 == gameState2.completedLevel2) {
            System.out.println("Game state loaded correctly");
        }
    }

    static class SavingTest implements Serializable {
        private static final String SAVE_FILE = "C:\\Users\\Vikram\\Desktop\\2023595_2023614\\COLE\\core\\src\\test\\java\\io\\github\\blablaablaa\\drop\\save_file.txt";

        public SavingTest() {
        }

        public void saveGameState(GameState gameState) {
            try {
                FileOutputStream fos = new FileOutputStream(SAVE_FILE);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(gameState);
                oos.close();
                fos.close();
                System.out.println("Game state saved successfully!");
            } catch (Exception e) {
                System.out.println("Failed to save game state: " + e.getMessage());
                e.printStackTrace();
            }
        }

        public GameState loadGameState() {
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SAVE_FILE));
                GameState gameState = (GameState) ois.readObject();
                ois.close();
                return gameState;
            } catch (Exception e) {
                System.out.println("Failed to load game state. Returning default state.");
                return new GameState(false, false);
            }
        }
    }
}
