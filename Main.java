


import javafx.application.Application;

import javafx.event.ActionEvent;
import javafx.scene.Group;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.media.Media;

import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Main extends Application {






    public String CurrentTrackGUID;

    Media media;
    MediaPlayer mediaPlayer;
    MediaView mediaView;


private boolean isPaused=false;


    @Override
    public void start(Stage primaryStage)
    {
        //Add a scene
        Group root = new Group();
        Scene scene = new Scene(root, 500, 200);

        Button PlayPauseButton=new Button("Play/Pause");
        Button NextButton=new Button("Next");

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






        //Add a mediaView, to display the media. Its necessary !
        //This mediaView is added to a Pane
         mediaView = new MediaView();
        Next();

        ((Group)scene.getRoot()).getChildren().add(mediaView);
        ((Group)scene.getRoot()).getChildren().add(PlayPauseButton);
        ((Group)scene.getRoot()).getChildren().add(NextButton);



        //show the stage
        primaryStage.setTitle("Media Player");
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
        Duration dur=mediaPlayer.getCurrentTime();

        System.out.println(dur.toString());
    }

    public void Pause(){
        mediaPlayer.pause();
        isPaused=true;
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

}
