import javafx.util.Duration;

import java.util.TimerTask;

/**
 * Created by xander on 9/13/16.
 */
public class ProgressSliderUpdateTimerTask extends TimerTask {

    //каждую секунду или другой установленный интервал, устанавливает прогресс в слайдер.


    private Main main;

    public ProgressSliderUpdateTimerTask(Main main){
        this.main=main;
    }

    @Override
    public void run() {

//тут в общем текущая_временая_позиция / на медиа_время_полное, и получается позиция от 0...1 и после величину в слайдер

      Duration MediaDuration= main.media.getDuration();
      Duration CurrentDuration= main.mediaPlayer.getCurrentTime();
        Double Mediasecs=MediaDuration.toSeconds();
        Double Currentsecs=CurrentDuration.toSeconds();

        Double progress= Currentsecs/Mediasecs;


        main.progressSlider.setValue(progress);


    }






}
