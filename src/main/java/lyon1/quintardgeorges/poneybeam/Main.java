/*
 * Projet Poney MVC
 * Quintard Livai- Georges Antoine
 */
package lyon1.quintardgeorges.poneybeam;

import javafx.application.Application;
import javafx.stage.Stage;
import lyon1.quintardgeorges.poneybeam.Controller.RunnerController;
import lyon1.quintardgeorges.poneybeam.Model.*;
import lyon1.quintardgeorges.poneybeam.View.ConfigView;
import lyon1.quintardgeorges.poneybeam.View.GameView;

/**
 * Application main class
 * Launch the app stage,
 * create needed views, model and
 * linked them with a controller.
 * add observers
 * @author dyavil
 */
public class Main extends Application{

    /**
     * main app, just launch the app
     * @param args main args
     */
    public static void  main(String []args)
    {
        Application.launch(args);
    }



    @Override
    public void start(Stage stage) throws Exception {
        // Nom de la fenetre
        stage.setTitle(" Runner Beam");
        GameView gameField = new GameView(600, 600);
        ConfigView cv = new ConfigView ();
        Field f = new Field();
        
        f.addObserver(gameField);
        
        RunnerController p = new RunnerController(f, gameField, cv, stage);
       
       
        stage.show();
        // On cree le terrain de jeu et on l'ajoute a la racine de la scene
        //root.getChildren().add( gameField );

        //Stage run = new Stage();
        // On ajoute la scene a la fenetre et on affiche
        //run.setScene(gameField.getScene());
        //run.show();
    }
}
