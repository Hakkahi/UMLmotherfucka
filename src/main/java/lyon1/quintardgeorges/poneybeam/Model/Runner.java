/*
 * Projet Poney MVC
 * Quintard Livai- Georges Antoine
 */
package lyon1.quintardgeorges.poneybeam.Model;

import java.util.ArrayList;
import java.util.Random;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

/**
 * 
 * Class representing a basic runner
 *  
 */
public class Runner 
{
    private double x;       // position horizontale du poney
    private double y; 	  // position verticale du poney
    private double speed;   // vitesse du poney
    private int tour;
    private boolean done;
    private String runnerColor;
    private Obstacle obs;
    private Rectangle hitbox; 
    
    protected String assetDirectory;
    
    private boolean isJumping;
    private boolean colide;
    private boolean onAir;

    /**
     * Base image name used if winner
     */
    protected String baseImage;
    
    /**
     * Other known runners
     */
    protected ArrayList<Runner> knownRunners;

    private Image runnerImage;
    private double baseY;
    private boolean actionAble;
    private boolean arrived;

    /**
     * Runner constructor
     * 
     * @param color runner color
     * @param yInit runner position
     * @param o linked obstacle
     */
    Runner(String color, int yInit, Obstacle o, String af) 
    {
            // Tous les poneys commencent a gauche du canvas, 
            // on commence a -100 pour les faire apparaitre progressivement
            x = -100.0;               
            y = yInit;
            assetDirectory = af;
            baseY = yInit;
            obs = o;
            runnerColor=color;
            tour = 1;
            done = false;
            knownRunners = new ArrayList();
            isJumping = false;
            colide = false;
            onAir = false;
            actionAble = false;
            arrived = false;
            // On charge l'image  du Runner
            System.out.println("assets/"+assetDirectory+"/move/pony-"+color+"-running.gif");
            runnerImage = new Image("assets/"+assetDirectory+"/move/pony-"+color+"-running.gif");
            baseImage = ("assets/"+assetDirectory+"/image/"+color+".gif");

            
            Random randomGenerator = new Random();
            speed = 1 + randomGenerator.nextFloat();
            hitbox = new Rectangle(x+105, y, runnerImage.getWidth()-140, runnerImage.getHeight());
    }



    /**
     * Moving runners
     * 
     * @param nbTours nb turns to run
     * @return 0
     */
    int move(int nbTours) 
    {
            getObs().slide();
            if(getHitbox().intersects(getObs().getActionbox().getBoundsInParent())) actionAble =true;
            else actionAble = false;
            if(getHitbox().intersects(getObs().getHitbox().getBoundsInParent())) colide =true;
            else colide =false;
            if(colide && !isJumping && speed > 0.5) {
                speed+=(-0.03);
            }
            jump();
            this.setX(getX() + getSpeed());
            hitbox.setX(getX()+100);
            if (getX() > 520) 
            {
                if(this instanceof SuperRunner) ((SuperRunner)this).restore();
                setTour(getTour() + 1);
                setX(-runnerImage.getWidth());
                Random randomGenerator = new Random();
                setSpeed(1+randomGenerator.nextFloat());
                getObs().reload();
                setRunnerImage(new Image("assets/"+assetDirectory+"/move/pony-" + getRunnerColor() + "-running.gif"));
            }
            if (getTour() > nbTours) {
                setSpeed(0);
                arrived = true;
                return 1;
            }
        return 0;
    }
    
    /**
     * if jump is set, this will jump !
     */
    public void jump()
    {
        if(isJumping)
        {
            if(getY() > baseY-50 && !onAir) setY(y-5);
            else if(getY() == baseY-50 && !onAir) onAir = true;
            else if(getY() < baseY && !isActionAble()) setY(y+2.5);
            else if(getY() == baseY && onAir) {
                onAir = false;
                isJumping = false;
            }
        }
        
    }
    
    
    /**
     * return the distance between this and Runner cmp
     * @param cmp the Runner to be compared with
     * @return distance (negative if cmp ahead)
     */
    public double getDistanceFrom(Runner cmp)
    {
        return (getX()*getTour())-(cmp.getX()*cmp.getTour());
    }
    

    /**
     * @return the x
     */
    public double getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public double getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * @return the speed
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * @param speed the speed to set
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * @return the tour
     */
    public int getTour() {
        return tour;
    }

    /**
     * @param tour the tour to set
     */
    public void setTour(int tour) {
        this.tour = tour;
    }

    /**
     * @return the done
     */
    public boolean isDone() {
        return done;
    }

    /**
     * @param done the done to set
     */
    public void setDone(boolean done) {
        this.done = done;
    }

    /**
     * @return the runnerColor
     */
    public String getRunnerColor() {
        return runnerColor;
    }

    /**
     * @param poneyColor the runnerColor to set
     */
    public void setRunnerColor(String poneyColor) {
        this.runnerColor = poneyColor;
    }

    /**
     * @return the runnerImage
     */
    public Image getRunnerImage() {
        return runnerImage;
    }

    /**
     * @param runnerImage the image to be set
     */
    public void setRunnerImage(Image runnerImage) {
        this.runnerImage = runnerImage;
    }

    /**
     *
     * @param nkp the runner to be added
     */
    public void addKnownRunner(Runner nkp){
        knownRunners.add(nkp);
    }
    
    /**
     *
     * @param nkp the runner to be removed
     */
    public void removeKnownRunner(Runner nkp){
        knownRunners.remove(nkp);
    }

    /**
     * @return the knownRunners
     */
    public ArrayList<Runner> getKnownRunners() {
        return knownRunners;
    }

    /**
     * @return the obs
     */
    public Obstacle getObs() {
        return obs;
    }

    /**
     * @return the hitbox
     */
    public Rectangle getHitbox() {
        return hitbox;
    }

    /**
     * @return the state of isjumping
     */
    public boolean isIsJumping() {
        return isJumping;
    }

    /**
     * @param isJumping the state to set isjumping
     */
    public void setIsJumping(boolean isJumping) {
        this.isJumping = isJumping;
    }

    /**
     * @return the colide
     */
    public boolean isColide() {
        return colide;
    }

    /**
     * @return the actionAble
     */
    public boolean isActionAble() {
        return actionAble;
    }

    /**
     * @return the arrived
     */
    public boolean isArrived() {
        return arrived;
    }

    /**
     * @param arrived the arrived to set
     */
    public void setArrived(boolean arrived) {
        this.arrived = arrived;
    }

    /**
     * @return the baseImage
     */
    public String getBaseImage() {
        return baseImage;
    }


}
