package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.FastFourierTransform;



public class Main extends Application implements Mediator
{
    JavaSoundCapture soundCapture;
    FastFourierTransform fft = new FastFourierTransform();
    float[] data = new float[8192];
    float[] spec = new float[8192];
    Canvas canvas;
    public void start(Stage primaryStage)
    {
        BorderPane border = new BorderPane();
        border.setPrefWidth(800);
        Button btnStart = new Button("Start");
        //btnStart.setText("Start");
        btnStart.setPrefSize(100, 20);
        Button btnStop = new Button("Stop");
        //btnStop.setText("Stop");
        btnStop.setPrefSize(100, 20);

        //Events
        btnStart.setOnAction((ActionEvent event) -> {
                soundCapture  = new JavaSoundCapture(this::UpdateVector);
                Thread thread = new Thread(soundCapture);
                thread.start();
        });
        btnStop.setOnAction((ActionEvent event) -> {
                soundCapture.finish();
                soundCapture.cancel();
        });
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(15, 12, 15, 12));
        hBox.setSpacing(10);
        hBox.setStyle("-fx-background-color: #336699;");
        hBox.getChildren().addAll(btnStart, btnStop);
        border.setTop(hBox);

        canvas = new Canvas(800, 400);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        // Set stroke color, width, and global transparency
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2d);
        gc.setGlobalAlpha(0.5d);

        //
        drawFrequencyScale(gc);

        border.setCenter(canvas);
        //StackPane root = new StackPane();
        //root.getChildren().add(btnStart);
        //root.getChildren().add(btnStop);

        Scene scene = new Scene(border);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }

    public void drawFrequencyScale(GraphicsContext gc)
    {
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.DARKGREEN);
        gc.setLineWidth(1);
        // draw horizontal(frequency) scale
        gc.strokeLine(30,360,660,360);
        gc.strokeLine(30,360,30,355);
        gc.strokeText("16Hz",32,357);
        gc.strokeLine(90,360,90,355);
        gc.strokeText("32Hz",92,357);
        gc.strokeLine(150,360,150,355);
        gc.strokeText("64Hz",152,357);
        gc.strokeLine(210,360,210,355);
        gc.strokeText("128Hz",212,357);
        gc.strokeLine(270,360,270,355);
        gc.strokeText("256Hz",272,357);
        gc.strokeLine(330,360,330,355);
        gc.strokeText("512kHz",332,357);
        gc.strokeLine(390,360,390,355);
        gc.strokeText("1kHz",392,357);
        gc.strokeLine(450,360,450,355);
        gc.strokeText("2kHz",452,357);
        gc.strokeLine(510,360,510,355);
        gc.strokeText("4kHz",512,357);
        gc.strokeLine(570,360,570,355);
        gc.strokeText("8kHz",572,357);
        gc.strokeLine(630,360,630,355);
        gc.strokeText("16kHz",632,357);
        gc.strokeLine(660,360,660,355);

        gc.strokeLine(30,343,660,343);

        // y scale multiplier/divisor, size adjuster
        int mult = 16;

        // draw frequency information (slow!)
        gc.fillRect(50,340-(int)spec[3]/mult,20,3);
        gc.fillRect(30,340-(int)spec[2]/mult,20,3); //16Hz
        gc.fillRect(70,340-(int)spec[4]/mult,20,3);
        gc.fillRect(90,340-(int)spec[5]/mult,10,3); //32Hz
        gc.fillRect(100,340-(int)spec[6]/mult,10,3);
        gc.fillRect(110,340-(int)spec[7]/mult,10,3);
        gc.fillRect(120,340-(int)spec[8]/mult,10,3);
        gc.fillRect(130,340-(int)spec[9]/mult,10,3);
        gc.fillRect(140,340-(int)spec[10]/mult,10,3);
        gc.fillRect(150,340-(int)spec[11]/mult,10,3);
        gc.fillRect(160,340-(int)spec[12]/mult,10,3); //64Hz
        gc.fillRect(170,340-(int)spec[14]/mult,10,3);
        gc.fillRect(180,340-(int)spec[16]/mult,10,3);
        gc.fillRect(190,340-(int)spec[18]/mult,10,3);
        gc.fillRect(200,340-(int)spec[20]/mult,10,3);
        gc.fillRect(210,340-(int)spec[24]/mult,10,3);
        gc.fillRect(220,340-(int)spec[26]/mult,10,3); //128Hz
        gc.fillRect(230,340-(int)spec[30]/mult,10,3);
        gc.fillRect(240,340-(int)spec[34]/mult,10,3);
        gc.fillRect(250,340-(int)spec[38]/mult,10,3);
        gc.fillRect(260,340-(int)spec[42]/mult,10,3);
        gc.fillRect(270,340-(int)spec[48]/mult,10,3); //256Hz
        gc.fillRect(280,340-(int)spec[56]/mult,10,3);
        gc.fillRect(290,340-(int)spec[64]/mult,10,3);
        gc.fillRect(300,340-(int)spec[72]/mult,10,3);
        gc.fillRect(310,340-(int)spec[80]/mult,10,3);
        gc.fillRect(320,340-(int)spec[88]/mult,10,3);
        gc.fillRect(330,340-(int)spec[96]/mult,10,3); //512Hz
        gc.fillRect(340,340-(int)spec[112]/mult,10,3);
        gc.fillRect(350,340-(int)spec[128]/mult,10,3);
        gc.fillRect(360,340-(int)spec[144]/mult,10,3);
        gc.fillRect(370,340-(int)spec[160]/mult,10,3);
        gc.fillRect(380,340-(int)spec[176]/mult,10,3);
        gc.fillRect(390,340-(int)spec[192]/mult,10,3); //1kHz
        gc.fillRect(400,340-(int)spec[224]/mult,10,3);
        gc.fillRect(410,340-(int)spec[256]/mult,10,3);
        gc.fillRect(420,340-(int)spec[288]/mult,10,3);
        gc.fillRect(430,340-(int)spec[320]/mult,10,3);
        gc.fillRect(440,340-(int)spec[352]/mult,10,3);
        gc.fillRect(450,340-(int)spec[382]/mult,10,3); //2k
        gc.fillRect(460,340-(int)spec[446]/mult,10,3);
        gc.fillRect(470,340-(int)spec[510]/mult,10,3);
        gc.fillRect(480,340-(int)spec[574]/mult,10,3);
        gc.fillRect(490,340-(int)spec[638]/mult,10,3);
        gc.fillRect(500,340-(int)spec[702]/mult,10,3);
        gc.fillRect(510,340-(int)spec[766]/mult,10,3); //4k
        gc.fillRect(520,340-(int)spec[894]/mult,10,3);
        gc.fillRect(530,340-(int)spec[1022]/mult,10,3);
        gc.fillRect(540,340-(int)spec[1150]/mult,10,3);
        gc.fillRect(550,340-(int)spec[1278]/mult,10,3);
        gc.fillRect(560,340-(int)spec[1406]/mult,10,3);
        gc.fillRect(570,340-(int)spec[1534]/mult,10,3); //8k
        gc.fillRect(580,340-(int)spec[1790]/mult,10,3);
        gc.fillRect(590,340-(int)spec[2046]/mult,10,3);
        gc.fillRect(600,340-(int)spec[2302]/mult,10,3);
        gc.fillRect(610,340-(int)spec[2558]/mult,10,3);
        gc.fillRect(620,340-(int)spec[2814]/mult,10,3);
        gc.fillRect(630,340-(int)spec[3070]/mult,10,3); //16k
        gc.fillRect(640,340-(int)spec[3582]/mult,10,3);
        gc.fillRect(650,340-(int)spec[4094]/mult,10,3);
    }

    public void Redraw()
    {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.PAPAYAWHIP);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        drawFrequencyScale(gc);

        gc.strokeRect(10, 10, 50, 50);

    }
    public void UpdateVector(float[] vector)
    {
        data = vector;
        //Fourir Transform
        spec = fft.fftMag(vector);
        Platform.runLater(new Runnable() {
            @Override public void run() {
                Redraw();
            }
        });

    }
}
