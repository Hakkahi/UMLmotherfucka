/*
 * Projet Poney MVC
 * Quintard Livai- Georges Antoine
 */
package lyon1.quintardgeorges.poneybeam.Model;

import java.util.Random;

/**
 * Class representing an auto player
 * Need a corresponding key to enable/disable its IA
 * @author dyavil
 */
public class IAPlayer extends Player {

    /**
     * boolean representing the IA state
     * when true this will use it IA
     */
    protected boolean enabled;
    
    /**
     * IAPlayer Constructor
     * @param pn corresponding runner
     * @param k corresponding key
     */
    public IAPlayer(Runner pn, String k){
        super(pn, k);
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        int index = (int) (rnd.nextFloat() * SALTCHARS.length());
        salt.append(SALTCHARS.charAt(index));
        SALTCHARS = "abcdefghijklmnopqrstuvwxyz";
        while (salt.length() < 5) {
            index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        this.name = ("IA " + saltStr);
        enabled = true;
    }

    /**
     * Method which implement IA comportment
     * do something only if IA enabled
     */
    public void play() {
        if(isEnabled()){
            if(this.runner.isActionAble()) this.runner.setIsJumping(true);
            if(this.runner instanceof SuperRunner){
                boolean go = false;
                int curPos = 0;
                for(Runner r : this.runner.getKnownRunners()){
                    if(this.runner.getDistanceFrom(r) < -20) curPos-=1;
                    else curPos+=1;
                }
                if(curPos < 0) go = true;
                if(go) ((SuperRunner)(runner)).Key();
            }
        }
    }

    /**
     * When a key is pressed 
     * it enable/disable current player IA
     * @param c corresponding key
     */
    @Override
    public void Key(String c) {
        if(c != null && getKey() != null && c.equals(getKey())){
            setEnabled((isEnabled()==true) ? false : true);
        }
    }

    /**
     * @return the enabled
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * @param enabled the enabled to set
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
}