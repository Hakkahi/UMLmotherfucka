/*
 * Projet Poney MVC
 * Quintard Livai- Georges Antoine
 */
package lyon1.quintardgeorges.poneybeam.Controller;

import java.io.File;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Toggle;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import lyon1.quintardgeorges.poneybeam.Model.Field;
import lyon1.quintardgeorges.poneybeam.Model.HandlePersist;
import lyon1.quintardgeorges.poneybeam.Model.IAPlayer;
import lyon1.quintardgeorges.poneybeam.Model.Player;
import lyon1.quintardgeorges.poneybeam.View.ConfigView;
import lyon1.quintardgeorges.poneybeam.View.GameView;

/**
 * Controller class linking game and config view to field model
 * @author dyavil
 */
public class RunnerController {
    
    private Field f;
    private GameView o;
    private ConfigView v;
    private Stage s;
    private HandlePersist hj;
    private String mode;
    //ref to field property
    private int nbPlayers;
    //ref to field property
    private int nbRunner;
    //ref to field property
    private int nbTours;
    private boolean paused;
    
    AnimationTimer currentGame;
    
    
    private File selectedD;
    
    private ArrayList<String> input = new ArrayList<String>();
    private String asset;
    private String[] assets;
    
    /**
     * PoneyController constructor
     * @param fi game field
     * @param go game view
     * @param cv config view
     * @param st application stage
     */
    public RunnerController(final Field fi, final GameView go, final ConfigView cv, final Stage st)
    {
        f = fi;
        o = go;
        v = cv;
        s = st;
        
        paused = false;
        
        hj = f.getParameter();
       
        nbRunner = hj.getNbRunner();
        nbPlayers = hj.getNbHumanPlayer();
        mode = hj.getMode();
        nbTours = hj.getNbTours();
        asset = hj.getAsset();
        assets = hj.getAssetsArray();
        
        majMode();
        majNbRunner();
        
        majNbTours();
        majNbPlayers();
        majAssets();
        
        
        
        
        v.getChooseAsset().setOnAction(new EventHandler<ActionEvent>() {
        @Override 
        public void handle(ActionEvent e) {
            asset = v.getChooseAsset().getValue().toString();
        }
        });
        
        v.getBaseConfig().setOnAction(new EventHandler<ActionEvent>() {
        @Override 
        public void handle(ActionEvent e) {
            hj.loadBase();
            nbRunner = hj.getNbRunner();
            nbPlayers = hj.getNbHumanPlayer();
            mode = hj.getMode();
            nbTours = hj.getNbTours();
            majNbRunner();
            majNbPlayers();
            majNbTours();
            majMode();
            majAssets();
        }

        });
        
        
        if (v.getGroup().getSelectedToggle() != null){
            System.out.println(v.getGroup().getSelectedToggle().getUserData().toString());
            mode = v.getGroup().getSelectedToggle().getUserData().toString();
        }
        v.getGroup().selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                  if (v.getGroup().getSelectedToggle() != null){
                      System.out.println(v.getGroup().getSelectedToggle().getUserData().toString());
                      mode = v.getGroup().getSelectedToggle().getUserData().toString();
                  }
            }
        
        });
        
        v.getRunnerSpin().valueProperty().addListener(new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observable,
                Number oldValue, Number newValue) {
                System.out.println(newValue);
                nbRunner = newValue.intValue();
                int temp = (Integer)v.getPlayerSpin().getValue();
                v.getPlayerSpin().setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
        0, newValue.intValue(), temp));
        }
    });
        
        v.getPlayerSpin().valueProperty().addListener(new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observable,
                Number oldValue, Number newValue) {
                nbPlayers = newValue.intValue();
        }
        });
        
        v.getTourSpin().valueProperty().addListener(new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observable,
                Number oldValue, Number newValue) {
                nbTours = newValue.intValue();
                f.setNbTours(newValue.intValue());
                System.out.println(newValue.intValue());
        }
        });
        
        
        o.getPause().setOnAction(new EventHandler<ActionEvent>() {
        @Override 
        public void handle(ActionEvent e) {
            if(paused){
                currentGame.start();
                paused = false;
            }
            else{
                currentGame.stop();
                paused = true;
            }
        }
        });
        
        o.getNewGame().setOnAction(new EventHandler<ActionEvent>() {
        @Override 
        public void handle(ActionEvent e) {
            currentGame.stop();
            currentGame = null;
            s.setScene(v.getScene());
        }
        });
       
        
        
        s.setScene(v.getScene());
        v.getValidate().setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                s.setScene(o.getScene());
                hj.setConfig(mode, nbPlayers, nbRunner, nbTours, asset);
                f.load();
                linkIA();
                animate();
            }

       });

        
    }
    
    /**
     * Called to launch the game
     */
    private void animate() {
        /** 
        * 
        * Boucle principale du jeu
        * 
        * handle() est appelee a chaque rafraichissement de frame
        * soit environ 60 fois par seconde.
        * 
        */
       currentGame = new AnimationTimer() 
       {
           
            public void handle(long currentNanoTime)
            {	
                //check key pressed
                o.getScene().setOnKeyPressed(new EventHandler<KeyEvent>()
                {
                    public void handle(KeyEvent e)
                    {
                        String code = e.getCode().toString();

                        // only add once... prevent duplicates
                        if ( !input.contains(code) )
                            input.add( code );
                        for (int j=0; j < f.getPlayers().size(); j++){
                            if(f.getPlayers().get(j).getRunner().isArrived() == false)
                            {
                                f.getPlayers().get(j).Key(code);
                            }
                        }
                    }

                });
                for (int j=0; j < f.getPlayers().size(); j++){
                            if(f.getPlayers().get(j) instanceof IAPlayer && f.getPlayers().get(j).getRunner().isArrived() == false)
                            {
                                ((IAPlayer)f.getPlayers().get(j)).play();
                            }
                        }
                // Deplacement et affichage des poneys
                if(f.getCurrentRunnersNumber() > 0) f.moveRunners();
                else{
                    o.end(f);
                    currentGame.stop();
                }
                
            }
       };
       currentGame.start();// On lance la boucle de rafraichissement 
    }

    /**
     * link each IA to corresponding field on view (used to know live IA state)
     */
    private void linkIA()
    {
        o.clearLabels();
        for (Player p : f.getPlayers())
        {
            if(p instanceof IAPlayer) o.addIALabel(p.getKey(), p.getName());
        }
    }
    
    /**
     * update the view based on current nb runners
     */
    private void majNbRunner() {
        v.getRunnerSpin().setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
        0, Field.getMaxRunners(), nbRunner));
    }
    /**
     * update the view based on current nb players
     */
    private void majNbPlayers() {
        v.getPlayerSpin().setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
        0, nbRunner, nbPlayers));
    }
    /**
     * update the view based on current nb tours
     */
    private void majNbTours() {
        v.getTourSpin().setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
        0, Field.getMaxRunners(), nbTours));
    }
    /**
     * update the view based on current mode
     */
    private void majMode() {
        v.getModeButton(mode).setSelected(true);
    }

    private void majAssets() {
        ObservableList<String> l =  FXCollections.observableArrayList();
        for(String s : assets) l.add(s);
        v.getChooseAsset().setItems(l);
        v.getChooseAsset().setValue(l.get(0));
    }
    
}
