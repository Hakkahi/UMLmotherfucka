/*
 * Projet Poney MVC
 * Quintard Livai- Georges Antoine
 */
package lyon1.quintardgeorges.poneybeam.View;

import java.util.ArrayList;
import javafx.scene.canvas.Canvas;

import java.util.Observable;
import java.util.Observer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import lyon1.quintardgeorges.poneybeam.Model.Field;
import lyon1.quintardgeorges.poneybeam.Model.IAPlayer;
import lyon1.quintardgeorges.poneybeam.Model.Player;

/**
 * Game view of the project
 * filled with a canvas representing the observed field
 * @author dyavil
 */
public class GameView extends GridPane implements Observer {
    

    private GraphicsContext gc;
    private final int width;
    private final int height;
    private Button pause;
    private Button newGame;
    private Canvas drawZone;
    private Group root;
    private GridPane head;
    private GridPane bottom;
    private Label winners;
    private ArrayList<Label> IALabels;
    
    
    
    /**
     * GameView Constructor
     * @param w canva width
     * @param h canva height
     */
    public GameView(int w, int h)
    {
        this.setPrefSize(w, h+60);
        IALabels = new ArrayList();
        width = w;
        height = h;
        pause = new Button("Pause");
        newGame = new Button("New Game");
        drawZone = new Canvas(w, h);
        head = new GridPane();
        bottom = new GridPane();
        head.setPrefSize(w, 30);
        root = new Group();
        Scene scene = new Scene(getRoot());
        /** permet de capturer le focus et donc les evenements clavier et souris */
        this.setFocusTraversable(true);
        head.setStyle("-fx-background-color: #D3D3D3; -fx-border-style : solid;  -fx-border-width: 0 0 2 0;");

        gc = drawZone.getGraphicsContext2D();
        root.getChildren().add(this);
        this.add(head, 0, 0);
        this.add(drawZone, 0, 1);
        this.add(bottom, 0, 2);
        head.add(pause, 0, 0);
        pause.setMinHeight(27);
        pause.setMinWidth(width/2);
        head.add(newGame, 1, 0);
        newGame.setMinHeight(27);
        newGame.setMinWidth(width/2);
        
        winners = new Label();
        winners.setLayoutX(width);
        winners.setLayoutY(height-30);
        //bottom.add(winners, 0, 1);
        bottom.setVgap(10);
        bottom.setHgap(10);
        bottom.setMinHeight(70);
        bottom.getRowConstraints().add(new RowConstraints(5));
        bottom.getColumnConstraints().add(new ColumnConstraints(0));
        bottom.getColumnConstraints().add(new ColumnConstraints(90));
        bottom.getColumnConstraints().add(new ColumnConstraints(90));
        bottom.getColumnConstraints().add(new ColumnConstraints(90));
        bottom.getColumnConstraints().add(new ColumnConstraints(90));
        bottom.getColumnConstraints().add(new ColumnConstraints(90));
        bottom.getColumnConstraints().add(new ColumnConstraints(90));


        
        
    }
    
    
    @Override
    public boolean isResizable() {
      return true;
    }


    /**
     * Called on notify from observable
     * @param o concerned object
     * @param arg arguments
     */
    public void update(Observable o, Object arg) {
        if (o instanceof Field)
        {
            Field f = (Field) o;
            // On nettoie le canvas a chaque frame
            getWinners().setText("");
            /*getGc().setFill( Color.LIGHTGRAY);*/
            getGc().fillRect(0, 0, getWidth(), getHeight());
            getGc().drawImage(f.getBackground(), 0, 0);
            for(Player p : f.getRunningPlayers())
            {
                getGc().drawImage(p.getRunner().getRunnerImage(), p.getRunner().getX(), p.getRunner().getY());
                getGc().fillText(p.getName(), p.getRunner().getX()+90, p.getRunner().getY()+55);
                getGc().drawImage(p.getRunner().getObs().getObstacleImage(), p.getRunner().getObs().getPosX(), p.getRunner().getObs().getPosY());
                
                for(int j = 0; j < IALabels.size(); j++){
                    if(p instanceof IAPlayer && p.getKey().equals(IALabels.get(j).getUserData())){
                        if(((IAPlayer)p).isEnabled()) IALabels.get(j).setStyle("-fx-background-color: #adff2f;");
                                else IALabels.get(j).setStyle("-fx-background-color: cornsilk");
                    }
                }
               
            }
        }
    }
    
   

    /**
     * @return the gc
     */
    public GraphicsContext getGc() {
        return gc;
    }

    /**
     * When a game end, it filled the screen with the winners
     * @param f corresponding field
     */
    public void end(Field f) {
        getWinners().setText(f.getArrivedPlayers().get(0).getName() + " won !");
        getGc().setFill( Color.WHITE);
        getGc().fillRect(0, 0, getWidth(), getHeight());
        getGc().drawImage(f.getBackground(), 0, 0);
        getGc().drawImage(new Image(f.getArrivedPlayers().get(0).getRunner().getBaseImage()), 239, 110);
        getGc().fillText(f.getArrivedPlayers().get(0).getName(), 259, 110);
        if(f.getArrivedPlayers().size()>1)getGc().drawImage(f.getEndgame(), 109, 210);
        if(f.getArrivedPlayers().size()>1){
            getGc().drawImage(new Image(f.getArrivedPlayers().get(1).getRunner().getBaseImage()), 104, 160);
            getGc().fillText(f.getArrivedPlayers().get(1).getName(), 124, 160);
        }
        if(f.getArrivedPlayers().size()>2){
            getGc().drawImage(new Image(f.getArrivedPlayers().get(2).getRunner().getBaseImage()), 359, 210);
            getGc().fillText(f.getArrivedPlayers().get(2).getName(), 379, 210);
        }
        //getGc().drawImage(f.getEndgame(), 50, 110);
    }

    /**
     * @return the pause state of the game
     */
    public Button getPause() {
        return pause;
    }

    /**
     * @return the newGame button obj ref
     */
    public Button getNewGame() {
        return newGame;
    }

    /**
     * @return the drawZone ref
     */
    public Canvas getDrawZone() {
        return drawZone;
    }

    /**
     * @return the root
     */
    public Group getRoot() {
        return root;
    }

    /**
     * @return the head pane 
     */
    public GridPane getHead() {
        return head;
    }

    /**
     * @return the bottom pane
     */
    public GridPane getBottom() {
        return bottom;
    }

    /**
     * @return the winners label
     */
    public Label getWinners() {
        return winners;
    }

    /**
     * @return the IALabels
     */
    public ArrayList<Label> getIALabels() {
        return IALabels;
    }
    
    /**
     * add an IALabel to the list
     * @param k IA key
     * @param val IA name
     */
    public void addIALabel(String k, String val)
    {
        IALabels.add(new Label(k+" : "+val));
        IALabels.get(IALabels.size()-1).setStyle("-fx-background-color: #adff2f;");
        IALabels.get(IALabels.size()-1).setUserData(k);
        bottom.add(IALabels.get(IALabels.size()-1), (((IALabels.size()-1)+1) - ((IALabels.size()-1)/6)*6), ((int)(IALabels.size()-1)/6)+1);
    }
    
    /**
     * clear IALabels list
     */
    public void clearLabels(){
        IALabels.clear();
        bottom.getChildren().clear();
    }

    
}
