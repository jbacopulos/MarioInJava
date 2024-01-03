/*Program: ICS Culminating 2018 - Portal 2D Game
 * 
 * Created by: Peter Meijer
 * 
 * Date Started: April 27, 2018
 * 
 * Date Finished: June, 2018
 * 
 * For: Donald Corea
 * 
 * Class: Audio Player
 * 
 * This class is the audio player class. It handles the playing, looping, and stopping of audio clips
 * used for music and sound effects. An object of this class is created with a wav file string name inputed
 * the audio is then loaded and the instance methods can be called on the object to update the audio.
 * 
 */

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class AudioPlayer
{
  private Clip c;
  
  //constructor to create audioplayer based on file name
  public AudioPlayer(String fileName) 
  {
    try
    {
      File file = new File(fileName);
      if (file.exists())
      {
        AudioInputStream sound = AudioSystem.getAudioInputStream(file);
        c = AudioSystem.getClip();
        c.open(sound);
      }
    } catch (Exception e)
    {
      
    }
    
  }

  public void startMusic()
  {
    c.setFramePosition(0);
    c.start();
  }
  
  //method to set looping of audio to forever
  public void loop()
  {
    c.loop(Clip.LOOP_CONTINUOUSLY);
  }
  
  //method to stop the playing of the clip
  public void stopMusic()
  {
    c.stop();
  }
}