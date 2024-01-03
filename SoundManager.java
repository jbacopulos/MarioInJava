import javax.sound.sampled.AudioSystem;

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
 * Class: SoundManager
 * 
 * This class is the SoundManager class. It is simply used for the storage of the AudioPlayer objects
 * and their various sound files
 * 
 */
public class SoundManager
{ 

  public AudioPlayer music;
  public AudioPlayer flag;
  public AudioPlayer complete;
  public AudioPlayer jump;
  public AudioPlayer superjump;
  public AudioPlayer coin;
  public AudioPlayer powerup;
  public AudioPlayer powerdown;
  public AudioPlayer death;
  public AudioPlayer stomp;

  //audio objects and files
  public SoundManager()
  {
    music = new AudioPlayer("SuperMarioBros.wav");
    flag = new AudioPlayer("flagpole.wav");
    complete = new AudioPlayer("levelcomplete.wav");
    jump = new AudioPlayer("jump.wav");
    superjump = new AudioPlayer("superjump.wav");
    coin = new AudioPlayer("coin.wav");
    powerup = new AudioPlayer("powerup.wav");
    powerdown = new AudioPlayer("powerdown.wav");
    death = new AudioPlayer("death.wav");
    stomp = new AudioPlayer("stomp.wav");
  }
}