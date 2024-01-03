/*
 * John Bacopulos
 * June 3, 2022
 * Creates object for ground
 * 
 */

import java.util.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.Timer;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;

public class Brick
{
    public int x;
    public int y;
    public int width = 128;
    public int height = 128;
    public Image img;
    public Brick thisBrick = this;
    public Rectangle2D.Double rect;

    public Brick(int x, int y)
    {
        this.x = x;
        this.y = y;

        try
        {
            InputStream imgStream = Brick.class.getResourceAsStream("/img/ground.jpg");
            img = ImageIO.read(imgStream);
        }

        catch (IOException e2)
        {
            System.out.println("Error thrown");
        }

        rect = new Rectangle2D.Double(x, y, 128, 128);
        Mario.groundRectArr.add(rect);
        Mario.groundArr.add(thisBrick);
    }
}