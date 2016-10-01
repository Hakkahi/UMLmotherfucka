/*
 * Projet Poney MVC
 * Quintard Livai- Georges Antoine
 */
package lyon1.quintardgeorges.poneybeam.Model;

/**
 * Abstract class representing a player 
 * A player own a runner, possess a name
 * and is assigned a key
 * @author dyavil
 */
public abstract class Player {

    /**
     * Player name
     */
    protected String name;

    /**
     *  Player linked runner
     */
    protected Runner runner;

    /**
     * Player linked key
     */
    protected String key;
    
    Player(Runner pn, String k)
    {
        this.runner = pn;
        key = k;
    }
    

    /**
     * @return the runner
     */
    public Runner getRunner() {
        return runner;
    }

    /**
     * @param run the runner to set
     */
    public void setRunner(Runner run) {
        this.runner = run;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }
    
    /**
     * depends on type of player
     * @param c corresponding key
     */
    public abstract void Key(String c);
    
}
