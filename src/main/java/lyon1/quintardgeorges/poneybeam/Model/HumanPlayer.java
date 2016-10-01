/*
 * Projet Poney MVC
 * Quintard Livai- Georges Antoine
 */
package lyon1.quintardgeorges.poneybeam.Model;

/**
 * Class representing a human player
 * It extends Player
 * A human player possess a second key used to jump
 * @author dyavil
 */
public class HumanPlayer extends Player {
    //jumping key
    private String jKey;
    
    /**
     * Create a human player with given args
     * and name it
     * @param pn linked runner
     * @param k key
     * @param jk jumping key
     */
    public HumanPlayer(Runner pn, String k, String jk){
        super(pn, k);
        jKey = jk;
        System.out.println(k);
        this.name = "Player " + key;
    }

    /**
     * When a key is pressed for a human player,
     * this method should be called.
     * Depending on the runner, actions can be different.
     * @param c given key
     */
    @Override
    public void Key(String c) {
        if(c != null && getKey() != null && c.equals(getKey())){
            if(this.getRunner() instanceof SuperRunner)
            {
                ((SuperRunner)this.getRunner()).Key();
            }
        }
        if(c != null && jKey != null && c.equals(jKey)){
            this.runner.setIsJumping(true);
        }
    }


    
}
