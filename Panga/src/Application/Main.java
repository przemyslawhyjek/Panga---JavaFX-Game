package Application;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;


public class Main extends Application {
   private static final int wysokosc = 500;
   private static final int szerokosc = 900;
   private double vaktual = 3;
    private double difficulty = 0.7;
    private double vx = 3;
    private double vy = 3;
    private double pilkax = szerokosc / 2;
    private double pilkay = wysokosc / 2;
   private int pilkarozmiar = 7;
   private int player1posx = 25;
   private int player1posy = 50;
    private int player2posx = szerokosc - 35;
   private  int player2posy = 50;
    private int wysokoscg = 60;
   private double ve1 = 0;
    private double vel2 = 0;
   private int player1score = 0;
 private int player2score = 0;
    private double velplayer2 = 6;

    public void punkt() {
        vaktual = 3;
        vx = 3;
        vy = 3;
        pilkax = szerokosc / 2;
        pilkay = wysokosc / 2;

    }

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Scene scene = new Scene( root, szerokosc, wysokosc );
        scene.setFill( Color.WHITESMOKE );
        primaryStage.setTitle( "Panga" );
        primaryStage.setScene( scene );
        primaryStage.show();


        Rectangle pilka = new Rectangle( pilkax, pilkay, 7, 7 );
        pilka.setFill( Color.BLACK );
        Rectangle player1 = new Rectangle( player1score, player1posy, 10, wysokoscg );
        player1.setFill( Color.BLACK );
        Rectangle player2 = new Rectangle( player2posx, player1posy, 10, wysokoscg );
        player2.setFill( Color.BLACK );
        //punktacja
        Text texto = new Text();
        texto.setText( "0" + "                                " + "0" );
        texto.setTranslateX( (szerokosc / 2) - 100 );
        texto.setTranslateY( 20 );
        texto.setTextAlignment( TextAlignment.CENTER );
        texto.setWrappingWidth( 200 );
        texto.setFill( Color.BLACK );
        root.getChildren().addAll( pilka, player1, player2, texto );

        AnimationTimer anima = new AnimationTimer() {
            @Override
            public void handle(long now) {
                //movement p1
                player1posy += ve1;
                player1.setY( player1posy );
                if (player1posy < 0) {
                    player1posy = 0;
                } else {
                    if (player1posy > wysokosc - wysokoscg) {
                        player1posy = wysokosc - wysokoscg;
                    }
                }
                //movement p2
                if (vaktual <= velplayer2) {
                    player2posy = (int) pilka.getY() - wysokoscg / 2;
                }
                if (vaktual > velplayer2) {
                    if (pilkay < player2posy- 3 + wysokoscg / 2) {
                        vel2 = -velplayer2;
                    } else {
                        if (pilkay > player2posy + 3 + wysokoscg / 2) {
                            vel2 = velplayer2;
                        }
                    }
                }
                if (player2posy < 0) {
                    player2posy = 0;
                } else {
                    if (player2posy > wysokosc - wysokoscg) {
                        player2posy = wysokosc - wysokoscg;
                    }
                }
                player2posy += vel2;
                player2.setY( player2posy );
                // kolizja gracz 1
                Shape collisionplayer1 = Shape.intersect( player1, pilka );
                boolean player1col = collisionplayer1.getBoundsInLocal().isEmpty();
                if (player1col == false) {
                    vx = vaktual;
                }
                // kolizja gracz2
                Shape collisionplayer2 = Shape.intersect( player2, pilka );
                boolean player2col = collisionplayer2.getBoundsInLocal().isEmpty();
                if (player2col == false) {
                    vx = vaktual * -1;
                }

                //ruch i odbicie pilki
               pilkax += vx;
                pilkay += vy;
                if (pilkax >= szerokosc - pilkarozmiar) {
                    player1score += 1;
                    System.out.println( "Player1: " + player1score + " CPU: " + player2score );
                    punkt();
                    texto.setText( player1score + "                                " + player2score );
                    vx = vaktual * -1;
                }
                if (pilkax < 0) {
                    player2score += 1;
                    System.out.println( "Player1: " + player1score + " CPU: " + player2score );
                    punkt();
                    texto.setText( player1score + "                                " + player2score );

                }
                if (pilkay >= wysokosc - pilkarozmiar) {
                    vy = -vaktual;

                    vaktual += difficulty;

                }
                if (pilkay < 0) {
                    vy = vaktual;
                    vaktual += +difficulty;
                }

                pilka.setX( pilkax );
                pilka.setY( pilkay );


            }

            ;
        };
        //klawisze, movement
        scene.setOnKeyPressed( (KeyEvent event) -> {
            switch (event.getCode()) {
                case UP:
                    ve1 = -9;
                    break;
                case DOWN:
                    ve1 = 9;
                    break;
            }
        } );
        scene.setOnKeyReleased( (KeyEvent event) -> {
            ve1 = 0;
        } );

        anima.start();
    }

}
   
    


