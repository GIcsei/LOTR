package helloworld;

import database.ModifyDatabase;
import engine.Character;
import engine.DataHandler;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import engine.Character.*;
import javafx.util.Duration;
import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;
import static javafx.scene.media.MediaPlayer.INDEFINITE;

public class HelloWorld extends Application {
    public static final int maxPoints=75;
    public static final int maxSkillPoints=20;
    private TableView table = new TableView();
    private static MediaPlayer mediaPlayer;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("LOTR Karakteralkotó");
        primaryStage.getIcons().add(new Image("\\pictures\\logo.jpg"));
        final Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                Media media = new Media(new File("lotr.mp3").toURI().toString());
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                mediaPlayer.play();
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.start();
        menu(primaryStage);
    }
    public static void main(String[] args) {
        launch(args);
}
    public void menu(Stage primaryStage) {
        DataHandler load=new DataHandler();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        BackgroundImage myBI = new BackgroundImage(new Image("\\pictures\\sauron.gif", 1920, 1080, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true));
        grid.setBackground(new Background(myBI));


        Button charGenerator = new Button("Új karakter létrehozása");
        HBox hbcharGenerator = new HBox(10);
        hbcharGenerator.setAlignment(Pos.CENTER);
        hbcharGenerator.getChildren().add(charGenerator);
        grid.add(hbcharGenerator, 125, 20);
        try {
            charGenerator.setOnAction(e -> generateCharacter(primaryStage));
        }
        catch (Exception e){
            e.printStackTrace();
        }

        Button charLoad = new Button("Utolsó karakter betöltése");
        HBox hbcharLoad = new HBox(8);
        hbcharLoad.setAlignment(Pos.CENTER);
        hbcharLoad.getChildren().add(charLoad);
        grid.add(hbcharLoad, 125, 40);
        try {
            charLoad.setOnAction(e -> {
                CharInfo(primaryStage, load.Loader());});
        }
        catch (Exception e){
            e.printStackTrace();
        }

        Button charList = new Button("Karakter kiválasztása");
        HBox hbcharList = new HBox(8);
        hbcharList.setAlignment(Pos.CENTER);
        hbcharList.getChildren().add(charList);
        grid.add(hbcharList, 125, 60);
        try {
            charList.setOnAction(e -> chooseCharacter());
        }
        catch (Exception e){
            e.printStackTrace();
        }

        Button Exit = new Button("Kilépés");
        HBox hbExit = new HBox(8);
        hbExit.setAlignment(Pos.CENTER);
        hbExit.getChildren().add(Exit);
        grid.add(hbExit, 125, 80);
        try {
            Exit.setOnAction(e -> primaryStage.close());
        }
        catch (Exception e){
            e.printStackTrace();
        }

        Text version=new Text();
        version.setFill(Color.WHITE);
        version.setText("Version number: "+load.versionNumber());
        grid.add(version, 0,92);

        Scene scene = new Scene(grid, 1920, 1080);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    public void generateCharacter(Stage primaryStage){
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        BackgroundImage myBI = new BackgroundImage(new Image("\\pictures\\logo.jpg", 1920, 1080, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true));
        //then you set to your node
        grid.setBackground(new Background(myBI));
        Text scenetitle = new Text("Karakter Háttere");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 24));
        scenetitle.setFill(Color.WHITE);
        grid.add(scenetitle, 125, 0, 2, 1);


        Label userName = new Label("Karakter neve: ");
        userName.setTextFill(Color.WHITE);
        grid.add(userName, 124, 10);
        TextField userTextField = new TextField();
        grid.add(userTextField, 125, 10);


        ChoiceBox<Character.Classes> cc = new ChoiceBox<>();
        cc.getItems().setAll(Classes.values());
        grid.add(cc, 125, 15);
        Label cl = new Label("Osztály:");
        cl.setTextFill(Color.WHITE);
        grid.add(cl, 124, 15);


        ChoiceBox<Character.Races> cr = new ChoiceBox<>();
        cr.getItems().setAll(Races.values());
        grid.add(cr, 125, 20);
        Label crs = new Label("Faj:");
        crs.setTextFill(Color.WHITE);
        grid.add(crs, 124, 20);


        Button btn = new Button("Következő");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 125, 30);
        //TODO hibakezelés
        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);
        try {
            btn.setOnAction(e -> {
                actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setText("Sign in button pressed\nVálasztott osztály: " + cc.getValue() + "\nVálasztott faj: " + cr.getValue() + "\nKarakter neve: " + userTextField.getText());
                ArrayList<String> data = new ArrayList<>();
                data.add(userTextField.getText());
                data.add(cc.getValue().toString());
                data.add(cr.getValue().toString());
                details(primaryStage, data);
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
        Scene scene = new Scene(grid, 1920, 1080);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public void details(Stage primaryStage, ArrayList<String> data) {
        GridPane grid = new GridPane();
        grid.setGridLinesVisible(true);
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 0));
        BackgroundImage myBI = new BackgroundImage(new Image("\\pictures\\logo.jpg", 1920, 1080, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true));
        grid.setBackground(new Background(myBI));

        Text scenetitle = new Text("Tulajdonságok meghatározása");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 24));
        scenetitle.setFill(Color.WHITE);
        grid.add(scenetitle, 115, 0, 2, 1);

        Label text=new Label("Felhasznált pontok");
        grid.add(text, 115, 25);
        text.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        text.setAlignment(Pos.CENTER);
        final Label usedPoints = new Label("0");
        grid.add(usedPoints, 117, 25);
        usedPoints.setAlignment(Pos.CENTER);
        usedPoints.setTextFill(Color.BLACK);
        usedPoints.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        //1. Strength

        final Label strengthPoints = new Label("0");
        grid.add(strengthPoints, 117, 12,1,2);
        strengthPoints.setAlignment(Pos.CENTER);
        strengthPoints.setTextFill(Color.BLACK);
        strengthPoints.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        Button up1 = new Button("△");
        HBox hbUp1 = new HBox(10);
        hbUp1.getChildren().add(up1);
        grid.add(hbUp1, 116, 12);
        up1.setOnAction(e-> increase(usedPoints, strengthPoints));
        Label strength = new Label("Erő");
        strength.setTextFill(Color.WHITE);
        grid.add(strength, 115, 12,1,2);
        Button down1 = new Button("▽");
        HBox hbdown1 = new HBox(10);
        hbdown1.getChildren().add(down1);
        grid.add(hbdown1, 116, 13);
        down1.setOnAction(e-> decrease(usedPoints, strengthPoints));


        //2. Dexterity

        final Label dexterityPoints = new Label("0");
        grid.add(dexterityPoints, 117, 15,1,2);
        dexterityPoints.setAlignment(Pos.CENTER);
        dexterityPoints.setTextFill(Color.BLACK);
        dexterityPoints.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        Button up2 = new Button("△");
        HBox hbUp2 = new HBox(10);
        hbUp2.getChildren().add(up2);
        grid.add(hbUp2, 116, 15);
        up2.setOnAction(e-> increase(usedPoints,dexterityPoints));
        Label dexterity = new Label("Ügyesség");
        dexterity.setTextFill(Color.WHITE);
        grid.add(dexterity, 115, 15,1,2);
        Button down2 = new Button("▽");
        HBox hbdown2 = new HBox(10);
        hbdown2.getChildren().add(down2);
        grid.add(hbdown2, 116, 16);
        down2.setOnAction(e->decrease(usedPoints,dexterityPoints));


        //3. Intelligence

        final Label intelligencePoints = new Label("0");
        grid.add(intelligencePoints, 117, 18,1,2);
        intelligencePoints.setAlignment(Pos.CENTER);
        intelligencePoints.setTextFill(Color.BLACK);
        intelligencePoints.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        Button up3 = new Button("△");
        HBox hbUp3 = new HBox(10);
        hbUp3.getChildren().add(up3);
        grid.add(hbUp3, 116, 18);
        up3.setOnAction(e-> increase(usedPoints,intelligencePoints));
        Label intellgince = new Label("Intelligencia");
        intellgince.setTextFill(Color.WHITE);
        grid.add(intellgince, 115, 18,1,2);
        Button down3 = new Button("▽");
        HBox hbdown3 = new HBox(10);
        hbdown3.getChildren().add(down3);
        grid.add(hbdown3, 116, 19);
        down3.setOnAction(e-> decrease(usedPoints,intelligencePoints));


        //4.Constitution

        final Label constitutionPoints = new Label("0");
        grid.add(constitutionPoints, 121, 12,1,2);
        constitutionPoints.setAlignment(Pos.CENTER);
        constitutionPoints.setTextFill(Color.BLACK);
        constitutionPoints.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        Button up4 = new Button("△");
        HBox hbUp4 = new HBox(10);
        hbUp4.getChildren().add(up4);
        grid.add(hbUp4, 120, 12);
        up4.setOnAction(e-> increase(usedPoints,constitutionPoints));
        Label constitution = new Label("Kitartás");
        constitution .setTextFill(Color.WHITE);
        grid.add(constitution , 119, 12,1,2);
        Button down4 = new Button("▽");
        HBox hbdown4 = new HBox(10);
        hbdown4.getChildren().add(down4);
        grid.add(hbdown4, 120, 13);
        down4.setOnAction(e-> decrease(usedPoints,constitutionPoints));


        //5. Luck

        final Label luckPoints = new Label("0");
        grid.add(luckPoints, 121, 16,1,2);
        luckPoints.setAlignment(Pos.CENTER);
        luckPoints.setTextFill(Color.BLACK);
        luckPoints.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        Button up5 = new Button("△");
        HBox hbUp5 = new HBox(10);
        hbUp5.getChildren().add(up5);
        grid.add(hbUp5, 120, 15);
        up5.setOnAction(e-> increase(usedPoints,luckPoints));
        Label luck = new Label("Szerencse");
        luck.setTextFill(Color.WHITE);
        grid.add(luck, 119, 16,1,2);
        Button down5 = new Button("▽");
        HBox hbdown5 = new HBox(10);
        hbdown5.getChildren().add(down5);
        grid.add(hbdown5, 120, 17);
        down5.setOnAction(e-> decrease(usedPoints,luckPoints));

        Button create = new Button("Karakter létrehozása");
        HBox hbCreate = new HBox(10);
        hbCreate.getChildren().add(create);
        grid.add(hbCreate, 120, 25);
        create.setOnAction(e-> {if((parseInt(usedPoints.getText()))==maxPoints) {
            int Id=create(data, strengthPoints,dexterityPoints,intelligencePoints,constitutionPoints,luckPoints);
            DataHandler save=new DataHandler();
            save.Saver(Id);
            menu(primaryStage);
        }
        //TODO Megjelnő villogás, nem lehet
        });
        String path="\\pictures\\"+data.get(1)+".jpg";
        HBox image=new HBox(new ImageView(new Image(path,450,750,false,false)));
        grid.add(image,25,10, 10,15);
        Scene scene = new Scene(grid, 1920, 1080);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    //TODO Táblaértékek megjelenítése
    public void chooseCharacter(){
        Stage secondaryStage=new Stage();
        Scene scene = new Scene(new Group(), 1366,768);
        secondaryStage.setTitle("Karakterválasztó");
        secondaryStage.setWidth(1366);
        secondaryStage.setHeight(768);

        final Label label = new Label("Karakterválasztó");
        label.setFont(new Font("Arial", 20));
        label.setTextFill(Color.WHITE);

        TableColumn NameCol = new TableColumn("Karakter neve");
        TableColumn RaceCol = new TableColumn("Faj");
        TableColumn ClassCol = new TableColumn("Osztály");
        TableColumn StrCol = new TableColumn("Erő");
        TableColumn DexCol = new TableColumn("Ügyesség");
        TableColumn IntCol = new TableColumn("Intelligencia");
        TableColumn ConCol = new TableColumn("Kitartás");
        TableColumn LckCol = new TableColumn("Szerencse");
        TableColumn ExpCol = new TableColumn("Tapasztalat");
        TableColumn LevCol = new TableColumn("Aktuális Szint");

        table.getColumns().addAll(NameCol,LevCol,ClassCol,RaceCol,StrCol,DexCol,IntCol,ConCol,LckCol,ExpCol);
        ModifyDatabase list =new ModifyDatabase();
        ObservableList<Character> data=FXCollections.<Character>observableArrayList();
        final ArrayList<Character> characters=list.getCharacters();
        final VBox vbox = new VBox();
        data.addAll(characters);
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);
        BackgroundImage myBI = new BackgroundImage(new Image("\\pictures\\logo.jpg", 1920, 1080, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true));
        vbox.setBackground(new Background(myBI));
        table.setBackground(new Background(myBI));
        table.setPrefSize(1366,768);
        table.setFixedCellSize(137);

        NameCol.setCellValueFactory(new PropertyValueFactory<Character, String>("NameCol"));
        LevCol.setCellValueFactory(new PropertyValueFactory<Character, Integer>("LevCol"));
        RaceCol.setCellValueFactory(new PropertyValueFactory<Character, String>("RaceCol"));
        ClassCol.setCellValueFactory(new PropertyValueFactory<Character, String>("ClassCol"));
        ExpCol.setCellValueFactory(new PropertyValueFactory<Character, Integer>("ExpCol"));
        ConCol.setCellValueFactory(new PropertyValueFactory<Character, Integer>("ConCol"));
        DexCol.setCellValueFactory(new PropertyValueFactory<Character, Integer>("DexCol"));
        IntCol.setCellValueFactory(new PropertyValueFactory<Character, Integer>("IntCol"));
        LckCol.setCellValueFactory(new PropertyValueFactory<Character, Integer>("LckCol"));
        StrCol.setCellValueFactory(new PropertyValueFactory<Character, Integer>("StrCol"));


        table.setItems(data);



        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        secondaryStage.setScene(scene);
        secondaryStage.show();
}


    public void CharInfo(Stage primaryStage, int id){
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setPadding(new Insets(25, 25, 25, 25));
        BackgroundImage myBI = new BackgroundImage(new Image("\\pictures\\logo.jpg", 1920, 1080, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true));
        grid.setBackground(new Background(myBI));
        ModifyDatabase lastUsedCharacter=new ModifyDatabase();
        Character character=lastUsedCharacter.getCharacter(id);

        Text scenetitle = new Text("Karakterlap");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 24));
        scenetitle.setFill(Color.WHITE);
        grid.add(scenetitle, 0, 0, 2, 1);

        Label charName=new Label("Karakter Neve: ");
        grid.add(charName, 0, 1);
        charName.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        charName.setAlignment(Pos.CENTER);
        Label CharName=new Label(character.getName());
        CharName.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        CharName.setAlignment(Pos.CENTER);
        grid.add(CharName, 1, 1);

        Label charClass=new Label("Karakter Osztálya: ");
        grid.add(charClass, 0, 2);
        charClass.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        charClass.setAlignment(Pos.CENTER);
        Label CharClass=new Label(character.getCharacterClass().toString());
        CharClass.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        CharClass.setAlignment(Pos.CENTER);
        grid.add(CharClass, 1, 2);


        Label charRace=new Label("Karakter Faja: ");
        grid.add(charRace, 0, 3);
        charRace.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        charRace.setAlignment(Pos.CENTER);
        Label CharRace=new Label(character.getRace().toString());
        CharRace.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        CharRace.setAlignment(Pos.CENTER);
        grid.add(CharRace, 1, 3);


        Label charLevel=new Label("Karakter Szintje: ");
        grid.add(charLevel, 0, 4);
        charLevel.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        charLevel.setAlignment(Pos.CENTER);
        Label CharLevel=new Label(String.valueOf(character.getLevel()));
        CharLevel.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        CharLevel.setAlignment(Pos.CENTER);
        grid.add(CharLevel, 1, 4);


        Label charExp=new Label("Tapasztalati pontok: ");
        grid.add(charExp, 0, 5);
        charExp.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        charExp.setAlignment(Pos.CENTER);
        Label CharExp=new Label(String.valueOf(character.getExperiencePoints()));
        CharExp.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        CharExp.setAlignment(Pos.CENTER);
        grid.add(CharExp, 1, 5);


        //5. Luck

        Label charStr=new Label("Erő: ");
        grid.add(charStr, 3, 1);
        charStr.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        charStr.setAlignment(Pos.CENTER);
        Label CharStr=new Label(String.valueOf(character.getStrength()));
        CharStr.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        CharStr.setAlignment(Pos.CENTER);
        grid.add(CharStr, 4, 1);

        Label charDex=new Label("Ügyesség: ");
        grid.add(charDex, 3, 2);
        charDex.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        charDex.setAlignment(Pos.CENTER);
        Label CharDex=new Label(String.valueOf(character.getDexterity()));
        CharDex.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        CharDex.setAlignment(Pos.CENTER);
        grid.add(CharDex, 4, 2);

        Label charInt=new Label("Értelem: ");
        grid.add(charInt, 3, 3);
        charInt.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        charInt.setAlignment(Pos.CENTER);
        Label CharInt=new Label(String.valueOf(character.getIntelligence()));
        CharInt.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        CharInt.setAlignment(Pos.CENTER);
        grid.add(CharInt, 4, 3);

        Label charCon=new Label("Kitartás: ");
        grid.add(charCon, 3, 4);
        charCon.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        charCon.setAlignment(Pos.CENTER);
        Label CharCon=new Label(String.valueOf(character.getConstitution()));
        CharCon.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        CharCon.setAlignment(Pos.CENTER);
        grid.add(CharCon, 4, 4);

        Label charLck=new Label("Szerencse: ");
        grid.add(charLck, 3, 5);
        charLck.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        charLck.setAlignment(Pos.CENTER);
        Label CharLck=new Label(String.valueOf(character.getLuck()));
        CharLck.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        CharLck.setAlignment(Pos.CENTER);
        grid.add(CharLck, 4, 5);

        Button create = new Button("Vissza");
        HBox hbCreate = new HBox(50);
        hbCreate.getChildren().add(create);
        grid.add(hbCreate, 4, 7);
        create.setOnAction(e-> menu(primaryStage));
        Scene scene = new Scene(grid, 1920, 1080);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public void increase(Label usedPoints, Label actualPoints){
        if((parseInt(usedPoints.getText())<maxPoints)&(parseInt(actualPoints.getText())<maxSkillPoints)) {
            usedPoints.setText(String.valueOf(parseInt(usedPoints.getText()) + 1));
            actualPoints.setText(String.valueOf(parseInt(actualPoints.getText()) + 1));
        }
    }
    public void decrease(Label usedPoints, Label actualPoints){
        if(parseInt(usedPoints.getText())>0&(parseInt(actualPoints.getText())>0)) {
            usedPoints.setText(String.valueOf(parseInt(usedPoints.getText()) - 1));
            actualPoints.setText(String.valueOf(parseInt(actualPoints.getText()) - 1));
        }
    }
    public int create(ArrayList<String> data, Label strengthPoints,Label dexterityPoints,Label intelligencePoints,Label constitutionPoints,Label luckPoints ){
        int characterId=0;
        ArrayList<String> charData=new ArrayList<>();
        charData.add(data.get(0));
        charData.add(data.get(1));
        charData.add(data.get(2));
        charData.add(strengthPoints.getText());
        charData.add(dexterityPoints.getText());
        charData.add(intelligencePoints.getText());
        charData.add(constitutionPoints.getText());
        charData.add(luckPoints.getText());
        ModifyDatabase creator=new ModifyDatabase();
        if((characterId=creator.newCharacter(charData))!=0){
        return characterId;}
        else return 0;
    }
}