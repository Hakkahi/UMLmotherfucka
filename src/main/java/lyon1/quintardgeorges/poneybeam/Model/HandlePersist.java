/*
 * Projet Poney MVC
 * Quintard Livai- Georges Antoine
 */
package lyon1.quintardgeorges.poneybeam.Model;

import java.io.File;
import java.util.prefs.*;

/**
 * Class handling application persistence
 * @author dyavil
 */
public class HandlePersist {
    
    final static int nbControls = 10;
    final static int nbMaxFiles = 30;
    private Preferences prefs;
    
    /**
     * Get JSON to settle Game parameters
     */
    public HandlePersist()
    {
        prefs = Preferences.userNodeForPackage(this.getClass());
        prefs.put("nbPoney", "5");
        prefs.put("nbTours", "5");
        prefs.put("nbHumanPlayer", "2");
        prefs.put("asset", "base");
        prefs.put("assets", "[base, dresseur]");
        prefs.put("mode", "super");
        prefs.put("controls", "[A, Z, E, R, T, Y, U, I, O, P]");
        prefs.put("jumpcontrols", "[Q, S, D, F, G, H, J, K, L, M]"); 
    }
    

    
    
    /**
     * Load Base config on user command
     */
    public void loadBase()
    {
        prefs.put("nbPoney", "5");
        prefs.put("nbTours", "5");
        prefs.put("nbHumanPlayer", "2");
        prefs.put("asset", "base");
        prefs.put("assets", "[base, dresseur]");
        prefs.put("mode", "super");
        prefs.put("controls", "[A, Z, E, R, T, Y, U, I, O, P]");
        prefs.put("jumpcontrols", "[Q, S, D, F, G, H, J, K, L, M]"); 
    }
   
    
    
    /**
     * Modify the config modify according to user entry
     * @param mode selected mode
     * @param nbPlayers selected number of players
     * @param nbPoney selected number of poneys
     * @param nbTours selected number of tours
     * @param a asset name
     */
    public void setConfig(String mode, int nbPlayers, int nbPoney, int nbTours, String a)
    {
        prefs.put("nbPoney", Integer.toString(nbPoney));
        prefs.put("nbTours", Integer.toString(nbTours));
        prefs.put("nbHumanPlayer", Integer.toString(nbPlayers));
        prefs.put("asset", a);
    }

    
    /**
     * Check if the given directory contains correct assets
     * @param f folder
     * @return true if assets are correct, false otherwise
     */
    public boolean checkAssetDirectory(File f)
    {
        int countF = 0;
        int countD = 0;
        boolean obstaclesF = false;
        if (f==null)return false;
        for(File fi : f.listFiles()){
            if((fi.isFile() && fi.getName().toLowerCase().endsWith(".gif"))) countF++;
            if (fi.isDirectory()) countD++;
        }
        if (countF < HandlePersist.nbMaxFiles) return false;
        for(File fi : f.listFiles()){
            if (fi.isDirectory()){
                if(fi.getName().equals("obstacles")) obstaclesF = true;
            }
        }
        
        return false;
    }
    
    
    /**
     * Getter of nbPoney
     * @return current stored runner number
     */
    public int getNbRunner()
    {
        return Integer.parseInt(prefs.get("nbPoney", ""));
    }
    
    /**
     * Nb turns getter
     * @return nb tours get from json config file
     */
    public int getNbTours()
    {
        return Integer.parseInt(prefs.get("nbTours", ""));
    }
    
    /**
     * Mode getter
     * @return mode get from json config file
     */
    public String getMode()
    {
        return prefs.get("mode", "");
    }
    
    /**
     * Asset getter
     * @return asset get from config file
     */
    public String getAsset()
    {
        return prefs.get("asset", "");
    }
    
    /**
     * human player number getter
     * @return nbplayer get from json config file
     */
    public int getNbHumanPlayer()
    {
        return Integer.parseInt(prefs.get("nbHumanPlayer", ""));
    }
    
    /**
     * control array getter
     * @return a string array representing the usable control keys
     */
    public String[] getControlArray()
    {
        String res[] = ((prefs.get("controls", "")).replace("[", "").replace("]", "").replace(",", "").replace("\"\"", " ").replace("\"", "")).split(" ");
        return res;
    }
    
    /**
     * jump control array getter
     * @return a string array representing the usable jump control keys
     */
    public String[] getJumpControlArray()
    {
        String res[] = ((prefs.get("jumpcontrols", "")).replace("[", "").replace("]", "")
                .replace(",", "").replace("\"\"", " ").replace("\"", "")).split(" ");
        return res;
    }
    
    /**
     * assets array getter
     * @return a string array representing the usable assets
     */
    public String[] getAssetsArray()
    {
        String res[] = ((prefs.get("assets", "")).replace("[", "").replace("]", "").replace(",", "").replace("\"\"", " ").replace("\"", "")).split(" ");
        return res;
    }
    
}
