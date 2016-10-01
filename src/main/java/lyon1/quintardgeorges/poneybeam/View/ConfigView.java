/*
 * Projet Poney MVC
 * Quintard Livai- Georges Antoine
 */
package lyon1.quintardgeorges.poneybeam.View;

import java.io.File;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.DirectoryChooser;

/**
 * Configuration view of the game
 * allow user to set its parameters
 * @author dyavil
 */
public class ConfigView{
    
    private Scene scene;
    private Button validate;
    private Button assetsPath;
    private DirectoryChooser chooser;
    private ToggleGroup group = new ToggleGroup();
    private Spinner runnerSpin;
    private Spinner playerSpin;
    private Spinner tourSpin;
    static final int MaxRunner = 10;
    
    private RadioButton normal;
    private RadioButton superm;
    private RadioButton random;
    private Button baseConfig;
    
    private ChoiceBox chooseAsset;

    /**
     * ConfigView constructor
     */
    public ConfigView()
    {
        
        /**
         * creating base gridpanes
         */
        GridPane root = new GridPane();
        root.setPrefSize(600, 600);
        root.setVgap(8);
        root.setHgap(8);
        
        
        GridPane head = new GridPane();
        head.setPrefSize(600, 50);
        head.getColumnConstraints().add(new ColumnConstraints(8));
        head.getRowConstraints().add(new RowConstraints(40));
        head.getColumnConstraints().add(new ColumnConstraints(120));
        head.getColumnConstraints().add(new ColumnConstraints(120));
        head.getColumnConstraints().add(new ColumnConstraints(120));
        head.setVgap(8);
        head.setHgap(8);
        
        GridPane middle = new GridPane();
        middle.setPrefSize(600, 100);
        middle.getColumnConstraints().add(new ColumnConstraints(8));
        middle.getColumnConstraints().add(new ColumnConstraints(80));
        middle.getColumnConstraints().add(new ColumnConstraints(20));
        middle.getColumnConstraints().add(new ColumnConstraints(80));
        middle.getColumnConstraints().add(new ColumnConstraints(20));
        middle.getColumnConstraints().add(new ColumnConstraints(80));
        middle.getColumnConstraints().add(new ColumnConstraints(20));
        middle.getColumnConstraints().add(new ColumnConstraints(100));
        middle.getRowConstraints().add(new RowConstraints(10));
        middle.getRowConstraints().add(new RowConstraints(60));
        middle.setVgap(0);
        middle.setHgap(8);
        
        GridPane bottom = new GridPane();
        bottom.setPrefSize(600, 50);
        bottom.getColumnConstraints().add(new ColumnConstraints(8));
        bottom.getColumnConstraints().add(new ColumnConstraints(500));
        bottom.getColumnConstraints().add(new ColumnConstraints(70));
        bottom.getRowConstraints().add(new RowConstraints(50));
        bottom.setVgap(8);
        bottom.setHgap(8);
        
        root.add(head, 0, 0);
        root.add(middle, 0, 1);
        root.add(bottom, 0, 2);
        
        
        validate = new Button("Valider");
        baseConfig = new Button("Set default");
        Text modes = new Text("Runners :");
        normal = new RadioButton("Normal");
        normal.setToggleGroup(group);
        normal.setUserData("normal");
        normal.setSelected(true);
        superm = new RadioButton("Super");
        superm.setToggleGroup(group);
        superm.setUserData("super");
        random = new RadioButton("Random");
        random.setToggleGroup(group);
        random.setUserData("random");
        
        
        chooseAsset = new ChoiceBox();
        
        Label runnerSpinLabel = new Label("Nb Runner :");
        Label playerSpinLabel = new Label("Nb Player :");
        Label tourSpinLabel = new Label("Nb Tours :");
        Label assetschoice = new Label("Assets :");
        assetschoice.setMinWidth(80);
        assetschoice.setAlignment(Pos.CENTER_RIGHT);
        runnerSpin = new Spinner();
        runnerSpin.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
        1, MaxRunner, 1));
        
        playerSpin = new Spinner();
        playerSpin.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
        0, 1, 0));
        
        tourSpin = new Spinner();
        tourSpin.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(
        0, MaxRunner, 0));
        
        //assetsPath = new Button("Add assets ... ");
        
        
        chooser = new DirectoryChooser();
        chooser.setTitle("Choose assets folder");
        File defaultD = new File("/");
        chooser.setInitialDirectory(defaultD);
        
        middle.add(chooseAsset, 7, 2);
        middle.add(assetschoice, 5, 2);
        
        head.add(modes, 1, 0);
        head.add(normal, 2, 0);
        head.add(superm, 3, 0);
        head.add(random, 4, 0);
        
        middle.add(runnerSpinLabel, 1, 0);
        middle.add(playerSpinLabel, 3, 0);
        middle.add(tourSpinLabel, 5, 0);
        middle.add(runnerSpin, 1, 1);
        middle.add(playerSpin, 3, 1);
        middle.add(tourSpin, 5, 1);
        middle.add(baseConfig, 7, 1);
        
        /*createAccountButton.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                  stage.setScene(CreateAccountScene());
            }
       });*/
        bottom.add(validate, 2, 0);
        scene = new Scene(root);
 
        //Stage run = new Stage();
        
    }

    /**
     * @return the scene
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * @return the validate
     */
    public Button getValidate() {
        return validate;
    }

    /**
     * @return the chooser
     */
    public DirectoryChooser getChooser() {
        return chooser;
    }

    /**
     * @return the assetsPath
     */
    public Button getAssetsPath() {
        return assetsPath;
    }

    /**
     * @return the group
     */
    public ToggleGroup getGroup() {
        return group;
    }

    /**
     * @return the runnerSpin
     */
    public Spinner getRunnerSpin() {
        return runnerSpin;
    }

    /**
     * @param runnerSpin the runnerSpin to set
     */
    public void setRunnerSpin(Spinner runnerSpin) {
        this.runnerSpin = runnerSpin;
    }

    /**
     * @return the playerSpin
     */
    public Spinner getPlayerSpin() {
        return playerSpin;
    }

    /**
     * @param playerSpin the playerSpin to set
     */
    public void setPlayerSpin(Spinner playerSpin) {
        this.playerSpin = playerSpin;
    }

    /**
     * Get the button matching with the string corresponding mode
     * null otherwise
     * @param s string to compare
     * @return corresponding button
     */
    public RadioButton getModeButton(String s)
    {
        if(s.equals(normal.getUserData().toString())) return normal;
        else if(s.equals(superm.getUserData().toString())) return superm;
        else if(s.equals(random.getUserData().toString())) return random;
        return null;
    }

    /**
     * @return the tourSpin
     */
    public Spinner getTourSpin() {
        return tourSpin;
    }

    /**
     * @param tourSpin the tourSpin to set
     */
    public void setTourSpin(Spinner tourSpin) {
        this.tourSpin = tourSpin;
    }

    /**
     * @return the baseConfig
     */
    public Button getBaseConfig() {
        return baseConfig;
    }

    /**
     * @return the chooseAsset
     */
    public ChoiceBox getChooseAsset() {
        return chooseAsset;
    }


    
}
