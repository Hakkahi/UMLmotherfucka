/*
 * Projet Poney MVC
 * Quintard Livai- Georges Antoine
 */
package lyon1.quintardgeorges.poneybeam.Model;

import javafx.scene.image.Image;

/**
 * Class representing a SuperRunnner
 * This runner can speed up !
 * @author dyavil
 */
public class SuperRunner extends Runner{
    
    private boolean NianMode;
    private boolean speedy;

    /**
     * SuperRunner constructor, Subclass of Runner
     * 
     * @param color color of asset
     * @param yInit y init location
     * @param obs corresponding obstacle
     */
    public SuperRunner(String color, int yInit, Obstacle obs, String af) {
        super(color, yInit, obs, af);
        NianMode = true;
        speedy = false;
    }

   

    /**
     * Speed up this when called
     */
    public void Key()
    {
        if(isSpeedy() == false && isNianMode() == true){
            setRunnerImage(new Image("assets/"+assetDirectory+"/super/pony-" + getRunnerColor() + "-rainbow.gif"));
            setSpeedy(true);
            setSpeed(getSpeed() * 2);
            NianMode = false;
        }   
    }
    
    /**
     * Restore speed mode 
     */
    void restore(){
            setSpeedy(false);
    }

    /**
     * @return the NianMode state
     */
    public boolean isNianMode() {
        return NianMode;
    }

    /**
     * @param NianMode the NianMode to set
     */
    public void setNianMode(boolean NianMode) {
        this.NianMode = NianMode;
    }


    /**
     * @return the speedy
     */
    public boolean isSpeedy() {
        return speedy;
    }

    /**
     * @param speedy the speedy to set
     */
    public void setSpeedy(boolean speedy) {
        this.speedy = speedy;
    }
    
}
