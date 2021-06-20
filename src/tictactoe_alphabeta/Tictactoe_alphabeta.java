package tictactoe_alphabeta;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class Tictactoe_alphabeta extends Application {
    
    @Override
    public void start(Stage primaryStage) {        
        Group root = new Group();        
        Scene scene = new Scene(root, 256, 256);
        
        Button btn1 = new Button("Easy");
        btn1.setLayoutX(110);
        btn1.setLayoutY(80);
        
        Button btn2 = new Button("Hard");
        btn2.setLayoutX(110);
        btn2.setLayoutY(120);
        
        btn1.setOnMousePressed((MouseEvent event) -> {
            primaryStage.close();
            board K = new board(1);
            K.start(primaryStage);
        });
        
        btn2.setOnMousePressed((MouseEvent event) -> {
            primaryStage.close();
            board K = new board(7);
            K.start(primaryStage);
        });
        
        ImageView im = new ImageView(getClass().getResource("t.png").toExternalForm());
        root.getChildren().addAll(im, btn1, btn2);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
 
}
