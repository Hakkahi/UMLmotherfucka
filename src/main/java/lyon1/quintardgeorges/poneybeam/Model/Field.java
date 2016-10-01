/*
 * Projet Poney MVC
 * Quintard Livai- Georges Antoine
 */
package lyon1.quintardgeorges.poneybeam.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Random;
import javafx.scene.image.Image;


/**
 * class representing the field
 * it created all others model entities
 * needed for the current run
 * @author dyavil
 */
public class Field extends Observable {

    //list of players
    private ArrayList<Player> players;
    //list of arrived players
    private ArrayList<Player> arrivedPlayers;
    //linked persitent handler class
    private HandlePersist parameter;
    //current running runners number
    private int currentRunnersNumber;
    
    //static value setting the maximum allowed runners
    private static final int MaxRunners = 10;
    
    //background field image
    private Image background;
    
    //setted number of turns
    private int nbTours;
    
    //endgame image(podium)
    private Image endgame;
    
    private String assets;
    
    /** Couleurs possibles */
    private ArrayList<String> colorMap = new ArrayList<String>(Arrays.asList("blue", "green", "orange", "purple", "yellow", "cyan", "red", "black", "brown", "pink"));


   
    /**
     * Base constructor
     * (no need to set things at start for now)
     */
    public Field()
    {    
       parameter = new HandlePersist();
       assets = parameter.getAsset();
       endgame = new Image("assets/podium.gif");
       background = new Image("assets/"+assets+"/field.gif");
       nbTours = parameter.getNbTours();
    }
    
    /**
     * Set field's runners
     * load config from linked json
     * @return 0
     */
    public int load()
    {
        String mode = parameter.getMode();
        assets = parameter.getAsset();
        background = new Image("assets/"+assets+"/field.gif");
        int j = 0;
        int space = 540/parameter.getNbRunner();
        int nbHumanPlayer = parameter.getNbHumanPlayer();
        players = new ArrayList(parameter.getNbRunner());
        arrivedPlayers = new ArrayList(parameter.getNbRunner());
        String[] controls = parameter.getControlArray();
        String[] jumpControls = parameter.getJumpControlArray();
        currentRunnersNumber = parameter.getNbRunner();
        
        Random obsp = new Random();
        
        //if super was selected
        if(mode.equals("super")){
            for (int i = 0; i < parameter.getNbRunner(); i++) 
            {
                if(nbHumanPlayer > 0) getPlayers().add(new HumanPlayer((new SuperRunner(getColorMap().get(j), i*space, new Obstacle(obsp.nextInt(540-40) + 40, i*space, assets), assets)), controls[j], jumpControls[j]));
                else getPlayers().add(new IAPlayer(new SuperRunner(getColorMap().get(j), i*space, new Obstacle(obsp.nextInt(540-40) + 40, i*space, assets), assets), controls[j]));
                if(j < getColorMap().size()-1) j++;
                else j = 0;
                nbHumanPlayer--;
            }
        } 
        //random was selected
        else if(mode.equals("random")){
            int temp = (Math.random() <= 0.5) ? 1 : 2;
            for (int i = 0; i < parameter.getNbRunner(); i++) 
            {
                Runner ptmp;
                if(temp == 1) ptmp = new SuperRunner(getColorMap().get(j), i*space, new Obstacle(obsp.nextInt(540-40) + 40, i*space, assets), assets);
                else ptmp = new Runner(getColorMap().get(j), i*space, new Obstacle(obsp.nextInt(540-40) + 40, i*space, assets), assets);
                if(nbHumanPlayer > 0) getPlayers().add(new HumanPlayer(ptmp, controls[j], jumpControls[j]));
                else getPlayers().add(new IAPlayer(ptmp, controls[j]));
                if(j < getColorMap().size()-1) j++;
                else j = 0;
                temp = (Math.random() <= 0.5) ? 1 : 2;
                nbHumanPlayer--;
            }
        }
        //normal mode selected
        else
        {
            for (int i = 0; i < parameter.getNbRunner(); i++) 
            {
                if(nbHumanPlayer > 0) getPlayers().add(new HumanPlayer((new Runner(getColorMap().get(j), i*space, new Obstacle(obsp.nextInt(540-40) + 40, i*space, assets), assets)), controls[j], jumpControls[j]));
                else getPlayers().add(new IAPlayer(new Runner(getColorMap().get(j), i*space, new Obstacle(obsp.nextInt(540-40) + 40, i*space, assets), assets), controls[j]));
                if(j < getColorMap().size()-1) j++;
                else j = 0;
                nbHumanPlayer--;
            }
        }
        
        //making all runner knowing each-others
        for (int i = 0; i < parameter.getNbRunner(); i++){
            for (int k = 0; k < parameter.getNbRunner(); k++){
                if(i != k) getPlayers().get(i).getRunner().addKnownRunner(getPlayers().get(k).getRunner());
            }
        }
        
        
        return 0;
    }
    

    /**
     * Move all runners and notify to observers
     */
    public void moveRunners() {
    for (int i = 0; i < parameter.getNbRunner(); i++) 
        {
            if(getPlayers().get(i).getRunner().isArrived() == false && getPlayers().get(i).getRunner().move(getNbTours()) == 1) setArrived(getPlayers().get(i));    
            setChanged();
            notifyObservers();
        }
    }
    
    /**
     * Called when a runner is arrived
     * add him to the corresponding list
     * end its runner to arrived
     * @param p 
     */
    private void setArrived(Player p) {
        currentRunnersNumber--;
        System.out.println("poney left : " + currentRunnersNumber);
        for (int i = 0; i < parameter.getNbRunner(); i++){
            if(p != getPlayers().get(i) && getPlayers().get(i).getRunner().isArrived() == false) getPlayers().get(i).getRunner().removeKnownRunner(p.getRunner());
        }
        getArrivedPlayers().add(p);
    }

    /**
     * Return the current parameter object
     * (linked to json)
     * @return the parameter
     */
    public HandlePersist getParameter() {
        return parameter;
    }

    /**
     * @return the colorMap of the runners
     */
    public ArrayList<String> getColorMap() {
        return colorMap;
    }

        /**
     * @return the MaxRunners field
     */
    public static int getMaxRunners() {
        return MaxRunners;
    }


    /**
     * Base on arrived players, return a list 
     * of currently running runners
     * @return the corresponding list (can be empty)
     */
    public ArrayList<Player> getRunningPlayers() {
        ArrayList<Player> running = new ArrayList(getCurrentRunnersNumber());
        for(int i = 0; i < parameter.getNbRunner(); i++)
        {
            if(getPlayers().get(i).getRunner().isArrived() == false) running.add(getPlayers().get(i));
        }
        return running;
    }

    /**
     * @return the players
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * @return the currentRunnersNumber
     */
    public int getCurrentRunnersNumber() {
        return currentRunnersNumber;
    }

    /**
     * @return the endgame
     */
    public Image getEndgame() {
        return endgame;
    }

    /**
     * @return the arrivedPlayers
     */
    public ArrayList<Player> getArrivedPlayers() {
        return arrivedPlayers;
    }

    /**
     * @return the nbTours
     */
    public int getNbTours() {
        return nbTours;
    }

    /**
     * @param nbTours the nbTours to set
     */
    public void setNbTours(int nbTours) {
        this.nbTours = nbTours;
    }

    /**
     * @return the background
     */
    public Image getBackground() {
        return background;
    }



   
   
   
}
