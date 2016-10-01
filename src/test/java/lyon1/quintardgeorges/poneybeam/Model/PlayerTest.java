/*
 * Projet Poney MVC
 * Quintard Liva� - Georges Antoine
 */
package lyon1.quintardgeorges.poneybeam.Model;
import javafx.application.Application;
import javafx.stage.Stage;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
/**
 *
 * @author dyavil
 */
public class PlayerTest{
    protected Player p;
    
    /**
     * necessary for javafx tests
     */
    public static class AsNonApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // noop
    }
    }

    @BeforeClass
    public static void initJFX() throws InterruptedException {
        Thread t = new Thread("JavaFX Init Thread") {
            @Override
            public void run() {
                Application.launch(AsNonApp.class, new String[0]);
            }
        };
        t.setDaemon(true);
        t.start();
        Thread.sleep(500);  
    }
          
    
    /**
     * creating test of human player,
     * check null values
     * and key value
     */
    @Test
    public void baseHumanTest(){
        p = new HumanPlayer(new Runner("yellow", 20, new Obstacle(150, 20, "base"), "base"), "A", "B");
        assertNotNull(p.getRunner());
        assertNotNull(p.getRunner().getObs());
        assertNotNull(p);
        assertEquals(p.getKey(), "A");
        assertTrue(p.getRunner() instanceof Runner);
        p = new HumanPlayer(new SuperRunner("yellow", 20, new Obstacle(150, 20, "base"), "base"), "A", "B");
        assertTrue(p.getRunner() instanceof SuperRunner);
    }
    
    /**
     * creating test of IA player,
     * check null values
     * and key value
     */
    @Test
    public void baseIATest()
    {
        p = new IAPlayer(new Runner("yellow", 20, new Obstacle(150, 20, "base"), "base"), "Z");
        assertNotNull(p.getRunner());
        assertNotNull(p.getRunner().getObs());
        assertNotNull(p);
        assertEquals(p.getKey(), "Z");
        assertTrue(p.getRunner() instanceof Runner);
        p = new IAPlayer(new SuperRunner("yellow", 20, new Obstacle(150, 20, "base"), "base"), "Y");
        assertTrue(p.getRunner() instanceof SuperRunner);
    }
    
    /**
     * key test of human player,
     * test key method (called when a key is pressed in the app)
     * jump and super mode
     */
    @Test
    public void keyHumanTest(){
        p = new HumanPlayer(new SuperRunner("yellow", 20, new Obstacle(150, 20, "base"), "base"), "A", "B");
        assertFalse(p.getRunner().isIsJumping());
        p.Key("C");
        assertFalse(p.getRunner().isIsJumping());
        p.Key("B");
        assertTrue(p.getRunner().isIsJumping());
        assertFalse(((SuperRunner)p.getRunner()).isSpeedy());
        p.Key("C");
        assertFalse(((SuperRunner)p.getRunner()).isSpeedy());
        p.Key("A");
        assertTrue(((SuperRunner)p.getRunner()).isSpeedy());
        
    }
    
    /**
     * key test of IA player,
     * test key method (called when a key is pressed in the app)
     * active/deactive IA
     */
    @Test
    public void keyIATest(){
        p = new IAPlayer(new SuperRunner("yellow", 20, new Obstacle(150, 20, "base"), "base"), "Y");
        assertTrue(((IAPlayer)p).isEnabled());
        p.Key("C");
        assertTrue(((IAPlayer)p).isEnabled());
        p.Key("Y");
        assertFalse(((IAPlayer)p).isEnabled());
        p.Key("Y");
        assertTrue(((IAPlayer)p).isEnabled());
        
    }
    
    
    @Test
    public void playIATest()
    {
        p = new IAPlayer(new SuperRunner("yellow", 20, new Obstacle(250, 20, "base"), "base"), "Y");
        p.getRunner().setX(70);
        p.getRunner().move(2);
        p.getRunner().move(2);
        assertFalse(p.getRunner().isIsJumping());
        ((IAPlayer)p).play();
        assertTrue(p.getRunner().isIsJumping());
        
    }
    
    
    
    
    
}
