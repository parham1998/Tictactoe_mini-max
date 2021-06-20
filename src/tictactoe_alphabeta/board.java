package tictactoe_alphabeta;

import java.util.Random;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class board {
    
    public String[] shuffle;
    
    public int[][] m;
    public int[][] oflag;
    public int[][] xflag;
    
    boolean turnX;
    boolean end;
    boolean flag;
    
    int level;
    int v, a, b;
    int ihold, jhold;

    public board(int le) {        
        this.m = new int[3][3];//2 = O, 1 = X
        this.oflag = new int[3][3];
        this.xflag = new int[3][3];
                
        this.turnX = true;
        this.end = false;
        this.flag = true;
        
        this.level = le;
    }

    
    public void start(Stage stage) {
        Group g = new Group();
        Scene s = new Scene(g);

        Canvas c = new Canvas(600, 700);
        g.getChildren().add(c);
        GraphicsContext gc = c.getGraphicsContext2D();
        drawboard(gc);
        
        Button btn = new Button("Restart");
        btn.setLayoutX(10);
        btn.setLayoutY(640);
        g.getChildren().add(btn);
        
        btn.setOnMousePressed((MouseEvent event) -> {
            stage.close();
            Tictactoe_alphabeta t = new Tictactoe_alphabeta();
            t.start(stage);
        });
        
        Text text = new Text();
        text.setX(270); 
        text.setY(660);
        text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        
        s.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                if (!end & turnX) {
                    if (getcoordinate(gc, (int) e.getX(), (int) e.getY())) {
                        if (winX()) {
                            text.setText("X Win!");
                            g.getChildren().add(text);
                            end = true;
                        } else if (noWin()) {
                            text.setText("Draw");
                            g.getChildren().add(text);
                            end = true;
                        }
                        turnX = false;
                    }
                    //O turn
                    if (!turnX & !end) {
                        
                        String[] ss = {"00", "10", "20", "01", "11", "21", "02", "12", "22"};
                        shuffle = ss;
                        
                        for (int i = 0; i < 3; i++) {
                            for (int j = 0; j < 3; j++) {
                                if (m[i][j] != 0) {
                                    removeElement(String.valueOf(i)+String.valueOf(j), shuffle.length);
                                }
                            }
                        }
                        a = Integer.MIN_VALUE;
                        b = Integer.MAX_VALUE;
                        while (shuffle != null) {
                            
                            String str = randomElement();
                            int i = Character.getNumericValue(str.charAt(0));
                            int j = Character.getNumericValue(str.charAt(1));
                            removeElement(String.valueOf(i)+String.valueOf(j), shuffle.length);
                            
                            m[i][j] = 2;
                            turnX = true;
                            v = mini_max_algo_alpha_beta(0, a, b, level);
                            if (v < b) {
                                b = v;
                                ihold = i;
                                jhold = j;
                            }
                            m[i][j] = 0;
                            if (a >= b) {
                                break;
                            }
                        }
                        m[ihold][jhold] = 2;
                        drawO(gc, ihold * 200 + 36, jhold * 200 + 36);
                        if (winO()) {
                            text.setText("O Win!");
                            g.getChildren().add(text);
                            end = true;
                        }
                        turnX = true;
                    }
                }
            }
        });

        stage.setScene(s);
        stage.show();
    }
    
    
    public String randomElement() {
        int rnd = new Random().nextInt(shuffle.length);
        return shuffle[rnd];
    }
    
    
    public void removeElement(String element, int length) {
        int x = 0;
        for (int i = 0; i < length; i++) {
            if (shuffle[i].equals(element)) {
                x = i;
                break;
            }
        }
        if (length == 1) {
            shuffle = null;
        } else {
            String[] copy = new String[length - 1];
            for (int i = 0, k = 0; i < length; i++) {
  
                // if the index is the removal element index
                if (i == x) {
                    continue;
                }
  
                // if the index is not the removal element index
                copy[k++] = shuffle[i];
            }
            shuffle = copy;
        }
    }

    
    int mini_max_algo_alpha_beta(int depth, int a, int b, int level) {

        if (winX()) {
            return 1;
        } 
        if (winO()) {
            return -1;
        } 
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (m[i][j] == 0) {
                    flag = false;
                }
            }
        }
        if (flag) {
            return 0;
        }
        flag = true;   
        
        if(depth == level)
            return 0;

        if (turnX) {
            turnX = false;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (m[i][j] == 0 & xflag[i][j] == 0) {
                        m[i][j] = 1;
                        xflag[i][j] = 1;
                        turnX = false;
                        v = mini_max_algo_alpha_beta(depth + 1, a, b, level);
                        if (v > a) {
                            a = v;
                        }
                        m[i][j] = 0;
                        if (a >= b) {
                            break;
                        }
                    }
                }
                if (a >= b) {
                    break;
                }
            }
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    xflag[i][j] = 0;
                }
            }
            return a;
        } else {
            turnX = true;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (m[i][j] == 0 & oflag[i][j] == 0) {
                        m[i][j] = 2;
                        oflag[i][j] = 1;
                        turnX = true;
                        v = mini_max_algo_alpha_beta(depth + 1, a, b, level);
                        if (v < b) {
                            b = v;
                        }
                        m[i][j] = 0;
                        if (a >= b) {
                            break;
                        }
                    }
                }
                if (a >= b) {
                    break;
                }
            }
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    oflag[i][j] = 0;
                }
            }
            return b;
        }

    }

    //draw tic tac toe board
    public void drawboard(GraphicsContext gc) {
        gc.clearRect(0, 0, 600, 600);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                gc.setFill(Color.WHITE);
                gc.fillRect(i * 200, j * 200, 200, 200);
                gc.strokeRect(i * 200, j * 200, 200, 200);
                gc.setStroke(Color.BLACK);
            }
        }
    }

    //draw X on board
    public void drawX(GraphicsContext gc, int x, int y) {
        Image im = new Image(getClass().getResource("x.png").toExternalForm());
        gc.drawImage(im, x, y, 128, 128);
    }

    //draw O on board
    public void drawO(GraphicsContext gc, int x, int y) {
        Image im = new Image(getClass().getResource("o.png").toExternalForm());
        gc.drawImage(im, x, y, 128, 128);
    }

    //check if X win
    public boolean winX() {
        for (int i = 0; i < 3; i++) {
            if (m[i][0] == 1 & m[i][1] == 1 & m[i][2] == 1) {
                return true;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (m[0][i] == 1 & m[1][i] == 1 & m[2][i] == 1) {
                return true;
            }
        }
        if (m[0][0] == 1 & m[1][1] == 1 & m[2][2] == 1) {
            return true;
        }
        return m[2][0] == 1 & m[1][1] == 1 & m[0][2] == 1;
    }

    //check if O win
    public boolean winO() {
        for (int i = 0; i < 3; i++) {
            if (m[i][0] == 2 & m[i][1] == 2 & m[i][2] == 2) {
                return true;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (m[0][i] == 2 & m[1][i] == 2 & m[2][i] == 2) {
                return true;
            }
        }
        if (m[0][0] == 2 & m[1][1] == 2 & m[2][2] == 2) {
            return true;
        }
        return m[2][0] == 2 & m[1][1] == 2 & m[0][2] == 2;
    }
    
    //check if draw
    public boolean noWin() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (m[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    } 

    //max
    public int max(int a, int b) {
        if (a >= b) {
            return a;
        } else {
            return b;
        }
    }

    //min
    public int min(int a, int b) {
        if (a <= b) {
            return a;
        } else {
            return b;
        }
    }
    
    //find X coordinate on board
    public boolean getcoordinate(GraphicsContext gc, int X, int Y) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (X > i * 200 & X < (i + 1) * 200 & Y < (j + 1) * 200 & Y > j * 200) {
                    if (m[i][j] == 0) {
                        drawX(gc, i * 200 + 36, j * 200 + 36);
                        m[i][j] = 1;
                        return true;
                    } 
                }
            }
        }
        return false;
    }

}
