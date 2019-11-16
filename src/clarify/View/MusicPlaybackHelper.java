/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clarify.View;

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 *
 * @author sharayubakal
 */
class MusicPlaybackHelper {

    public static MediaPlayer globalMediaPlayer;

    public static boolean playMusic(String filename) {
        boolean isSuccessful = false;
        try {
            Media media = new Media(new File(filename).toURI().toString());
            MusicPlaybackHelper.globalMediaPlayer = new MediaPlayer(media);
            MusicPlaybackHelper.globalMediaPlayer.play();
            isSuccessful = true;
        } catch (Exception e) {
            isSuccessful = false;
        }

        return isSuccessful;
    }

    public static boolean pauseMusic() {
        boolean isSuccessful = false;
        try {
            MusicPlaybackHelper.globalMediaPlayer.pause();
            isSuccessful = true;
        } catch (Exception e) {
            isSuccessful = false;
        }

        return isSuccessful;
    }
    
    public static boolean resumeMusic() {
        boolean isSuccessful = false;
        try {
            if(globalMediaPlayer != null){
                MusicPlaybackHelper.globalMediaPlayer.play();
                isSuccessful = true;
            } else{
                //DO NOTHING
            }
        } catch (Exception e) {
            isSuccessful = false;
        }

        return isSuccessful;        
    }
    
    //MusicPlaybackHelper.globalMediaPlayer.pause();
    
}