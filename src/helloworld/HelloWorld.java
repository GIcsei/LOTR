package helloworld;

import engine.Character;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import engine.Character.*;

import java.util.ArrayList;

public class HelloWorld extends Application {










    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX Welcome");
        menu(primaryStage);
    }
    public static void main(String[] args) {
        launch(args);
    }
    public void menu(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER_RIGHT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 350, 25, 25));
        BackgroundImage myBI = new BackgroundImage(new Image("\\pictures\\sauron.gif", 1920, 1080, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true));
        //then you set to your node
        grid.setBackground(new Background(myBI));
        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        scenetitle.setFill(Color.WHITE);
        grid.add(scenetitle, 0, 0, 2, 1);


        Label userName = new Label("Karakter neve: ");
        userName.setTextFill(Color.WHITE);
        grid.add(userName, 0, 1);
        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);


        ChoiceBox<Character.Classes> cc = new ChoiceBox<>();
        cc.getItems().setAll(Classes.values());
        grid.add(cc, 1, 2);
        Label cl = new Label("Osztály:");
        cl.setTextFill(Color.WHITE);
        grid.add(cl, 0, 2);


        ChoiceBox<Character.Races> cr = new ChoiceBox<>();
        cr.getItems().setAll(Races.values());
        grid.add(cr, 1, 3);
        Label crs = new Label("Faj:");
        crs.setTextFill(Color.WHITE);
        grid.add(crs, 0, 3);


        Button btn = new Button("Sign in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);
        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);
        btn.setOnAction(e -> {
            actiontarget.setFill(Color.FIREBRICK);
            actiontarget.setText("Sign in button pressed\nVálasztott osztály: " + cc.getValue() + "\nVálasztott faj: " + cr.getValue() + "\nKarakter neve: " + userTextField.getText());
            ArrayList<String> data =new ArrayList<>();
            data.add(userTextField.getText());
            data.add(cc.getValue().toString());
            data.add(cr.getValue().toString());
            details(primaryStage, data);
        });
        Scene scene = new Scene(grid, 1920, 1080);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public void details(Stage primaryStage, ArrayList<String> data) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER_RIGHT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 350, 25, 25));
        BackgroundImage myBI = new BackgroundImage(new Image("\\pictures\\logo.jpg", 1920, 1080, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true));
        //then you set to your node
        grid.setBackground(new Background(myBI));
        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        scenetitle.setFill(Color.WHITE);
        grid.add(scenetitle, 0, 0, 2, 1);


        Label userName = new Label("Karakter neve: "+data.get(0));
        userName.setTextFill(Color.WHITE);
        grid.add(userName, 0, 1);


        Label cl = new Label("Osztály: "+data.get(1));
        cl.setTextFill(Color.WHITE);
        grid.add(cl, 0, 2);


        Label crs = new Label("Faj: "+data.get(2));
        crs.setTextFill(Color.WHITE);
        grid.add(crs, 0, 3);


        Button btn = new Button("Sign in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);
        btn.setOnAction(e->{menu(primaryStage);});

        Scene scene = new Scene(grid, 1920, 1080);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}