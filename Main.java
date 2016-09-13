


import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.Timer;


public class Main extends Application {



Timer timer;


    public String CurrentTrackGUID;

    Media media;
    MediaPlayer mediaPlayer;
    MediaView mediaView;

   public Slider progressSlider;



private boolean isPaused=false;


    @Override
    public void start(Stage primaryStage)
    {
        //Add a scene
        Group root = new Group();
        Scene scene = new Scene(root, 500, 200);

        Button PlayPauseButton=new Button("Play/Pause");
        Button NextButton=new Button("Next");
         progressSlider=new Slider(0,1,0);
        progressSlider.setLayoutY(50);

        NextButton.setLayoutX(100);

        PlayPauseButton.setOnAction(new javafx.event.EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            PlayPause();
            }
        });

        NextButton.setOnAction(new javafx.event.EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            Next();
            }
        });


        progressSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                double  sliderValue=   progressSlider.getValue();

                Duration MediaDuration= media.getDuration();
                Double Mediasecs=MediaDuration.toSeconds();
                Duration CurrentDuration= mediaPlayer.getCurrentTime();
                Double Currentsecs=CurrentDuration.toSeconds();


                Double progressSecs=Mediasecs*sliderValue;


                Duration newpos=Duration.seconds(progressSecs);

                Double TimeDiff=progressSecs-Currentsecs;

                if(TimeDiff<0)TimeDiff*=-1;

                if(TimeDiff>1)
                mediaPlayer.seek(newpos);
            }
        });






        //Add a mediaView, to display the media. Its necessary !
        //This mediaView is added to a Pane
         mediaView = new MediaView();
        Next();

        ((Group)scene.getRoot()).getChildren().add(mediaView);
        ((Group)scene.getRoot()).getChildren().add(PlayPauseButton);
        ((Group)scene.getRoot()).getChildren().add(NextButton);
        ((Group)scene.getRoot()).getChildren().add(progressSlider);



        //show the stage
        primaryStage.setTitle("OwnRadio");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }









public void Next(){


new ResponseTask(this).run();


}

    public void PlayPause(){
if(isPaused){
    Play();
}else {
    Pause();
}
    }




    public void Play(){
        mediaPlayer.play();
        isPaused=false;


        ResumeTimer();

    }





    public void Pause(){
        mediaPlayer.pause();
        isPaused=true;

        PauseTimer();
    }





    public void SetMedia(String MediaPath){

        media = new Media(MediaPath);
        if(mediaPlayer!=null) {
            mediaPlayer.dispose();
        }
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setOnEndOfMedia(new ResponseTask(this));
         mediaView.setMediaPlayer(mediaPlayer);

    }






    public void PauseTimer(){

        if(timer!=null)
        timer.cancel();

    }






    public void ResumeTimer(){

        if(timer!=null)
        timer.cancel();
        timer = new Timer();
        timer.scheduleAtFixedRate(new ProgressSliderUpdateTimerTask(this), 0, 1000);
    }

}
