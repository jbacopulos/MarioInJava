import java.util.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.Timer;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;

public class CharacterObj
{
    public Image img;
    public boolean dead = false;

    public CharacterObj (int i)
    {
        if (i == 1)
        {
            try
            {
                InputStream imgStream = CharacterObj.class.getResourceAsStream("/img/mario_still.png");
                img = ImageIO.read(imgStream);
            }

            catch (IOException e2)
            {
                System.out.println("Error thrown");
            }
        }  

        else if (i == 2)
        {
            try
            {
                InputStream imgStream = CharacterObj.class.getResourceAsStream("/img/mario_run1.png");
                img = ImageIO.read(imgStream);
            }

            catch (IOException e2)
            {
                System.out.println("Error thrown");
            }
        }

        else if (i == 3)
        {
            try
            {
                InputStream imgStream = CharacterObj.class.getResourceAsStream("/img/mario_run2.png");
                img = ImageIO.read(imgStream);
            }

            catch (IOException e2)
            {
                System.out.println("Error thrown");
            }
        }

        else if (i == 4)
        {
            try
            {
                InputStream imgStream = CharacterObj.class.getResourceAsStream("/img/mario_still_left.png");
                img = ImageIO.read(imgStream);
            }

            catch (IOException e2)
            {
                System.out.println("Error thrown");
            }
        }  

        else if (i == 5)
        {
            try
            {
                InputStream imgStream = CharacterObj.class.getResourceAsStream("/img/mario_run1_left.png");
                img = ImageIO.read(imgStream);
            }

            catch (IOException e2)
            {
                System.out.println("Error thrown");
            }
        }

        else if (i == 6)
        {
            try
            {
                InputStream imgStream = CharacterObj.class.getResourceAsStream("/img/mario_run2_left.png");
                img = ImageIO.read(imgStream);
            }

            catch (IOException e2)
            {
                System.out.println("Error thrown");
            }
        }

        else if (i == 7)
        {
            try
            {
                InputStream imgStream = CharacterObj.class.getResourceAsStream("/img/mario_jump.png");
                img = ImageIO.read(imgStream);
            }

            catch (IOException e2)
            {
                System.out.println("Error thrown");
            }
        }

        else if (i == 8)
        {
            try
            {
                InputStream imgStream = CharacterObj.class.getResourceAsStream("/img/mario_jump_left.png");
                img = ImageIO.read(imgStream);
            }

            catch (IOException e2)
            {
                System.out.println("Error thrown");
            }
        }

        else if (i == 9)
        {
            try
            {
                InputStream imgStream = CharacterObj.class.getResourceAsStream("/img/mushroom.png");
                img = ImageIO.read(imgStream);
            }

            catch (IOException e2)
            {
                System.out.println("Error thrown");
            }
        }

        else if (i == 10)
        {
            try
            {
                InputStream imgStream = CharacterObj.class.getResourceAsStream("/img/coin.png");
                img = ImageIO.read(imgStream);
            }

            catch (IOException e2)
            {
                System.out.println("Error thrown");
            }
        }

        else if (i == 11)
        {
            try
            {
                InputStream imgStream = CharacterObj.class.getResourceAsStream("/img/goomba1.png");
                img = ImageIO.read(imgStream);
            }

            catch (IOException e2)
            {
                System.out.println("Error thrown");
            }
        }

        else if (i == 12)
        {
            try
            {
                InputStream imgStream = CharacterObj.class.getResourceAsStream("/img/goomba2.png");
                img = ImageIO.read(imgStream);
            }

            catch (IOException e2)
            {
                System.out.println("Error thrown");
            }
        }

        else if (i == 13)
        {
            try
            {
                InputStream imgStream = CharacterObj.class.getResourceAsStream("/img/smushedgoomba.png");
                img = ImageIO.read(imgStream);
            }

            catch (IOException e2)
            {
                System.out.println("Error thrown");
            }
        }

        else if (i == 14)
        {
            try
            {
                InputStream imgStream = CharacterObj.class.getResourceAsStream("/img/mariodead.png");
                img = ImageIO.read(imgStream);
            }

            catch (IOException e2)
            {
                System.out.println("Error thrown");
            }
        }

        else if (i == 15)
        {
            try
            {
                InputStream imgStream = CharacterObj.class.getResourceAsStream("/img/transparent.png");
                img = ImageIO.read(imgStream);
            }

            catch (IOException e2)
            {
                System.out.println("Error thrown");
            }
        }

        else if (i == 16)
        {
            try
            {
                InputStream imgStream = CharacterObj.class.getResourceAsStream("/img/mario_slide.png");
                img = ImageIO.read(imgStream);
            }

            catch (IOException e2)
            {
                System.out.println("Error thrown");
            }
        }

        else
        {
        }
    }
}