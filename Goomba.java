import java.util.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.Timer;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;
import javax.lang.model.util.ElementScanner6;

public class Goomba 
{ 
    public boolean dead = false;
    public boolean justKilled = false;
    public boolean isActive = false;
    public int x;
    public int y;
    public int width;
    public int height;
    public int index;
    public Goomba thisGoomba = this;
    public Rectangle2D.Double rect;
    public Image img;
    public int counter = 0;
    public boolean goingRight = false;

    public Goomba (int x, int y, int index)
    {
        this.x = x;
        this.y = y;
        this.index = index;

        if (index == 1)
        {
            width = 30;
            height = 30;

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

        else if (index == 2)
        {
            width = 30;
            height = 30;

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

        else
        {
            width = 30;
            height = 10;

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

        rect = new Rectangle2D.Double(this.x, this.y, width, height);
        Mario.goombaArr.add(thisGoomba);
    }

    public void refreshImage()
    {
        if (index == 1)
        {
            width = 30;
            height = 30;

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

        else if (index == 2)
        {
            width = 30;
            height = 30;

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

        else
        {
            width = 30;
            height = 15;

            if (Mario.goombaKilledCounter == 0)
            {
                this.y += 15;
            }

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
    }
}