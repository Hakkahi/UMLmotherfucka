/*
 * Projet Poney MVC
 * Quintard Livai- Georges Antoine
 */
package lyon1.quintardgeorges.poneybeam.Model;

import java.util.Random;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

/**
 * Class Obstacle
 * Got a given position, hitbox and actionbox
 * @author dyavil
 */
public class Obstacle
{
    private double posX;
    private double posY;
    private Rectangle hitbox;
    private Rectangle actionbox;
    private Image obstacleImage;
    private String assetFolder;

    /**
     * Obstacle constructor
     * @param pX x position 
     * @param pY y position
     */
    public Obstacle(double pX, double pY, String af)
    {
        assetFolder = af;
        obstacleImage = new Image("assets/"+af+"/obstacle.png");
        posX = pX;
        posY = pY;
        hitbox = new Rectangle(pX, pY+72, obstacleImage.getWidth(), obstacleImage.getHeight()-72);
        actionbox = new Rectangle(pX-25, pY+72, obstacleImage.getWidth()+5, obstacleImage.getHeight()-72);
    }

    /**
     * @return the posX
     */
    public double getPosX() {
        return posX;
    }

    /**
     * @return the posY
     */
    public double getPosY() {
        return posY;
    }

    /**
     * @return the obstacleImage
     */
    public Image getObstacleImage() {
        return obstacleImage;
    }

    /**
     * Reload it position
     */
    public void reload() {
        Random r = new Random();
        posX = r.nextInt(540-40) + 40;
        hitbox.setX(posX);
        actionbox.setX(posX);
    }

    /**
     * @return the hitbox
     */
    public Rectangle getHitbox() {
        return hitbox;
    }

    /**
     * @return the actionbox
     */
    public Rectangle getActionbox() {
        return actionbox;
    }
    
    /**
     * Move it to the left
     */
    public void slide()
    {
        posX -= 1;
        hitbox.setX(posX);
        actionbox.setX(posX);
    }
    
}
