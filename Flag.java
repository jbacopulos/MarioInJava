/*
 * John Bacopulos
 * June 10, 2022
 * Creates flag object
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

public class Flag
{
    public int x;
    public int y;
    public int width = 60;
    public int height = 480;
    public Image img;
    public Flag thisFlag = this;
    public Rectangle2D.Double rect;

    public Flag(int x, int y)
    {
        this.x = x;
        this.y = y;

        try
        {
            InputStream imgStream = Flag.class.getResourceAsStream("/img/flag1.png");
            img = ImageIO.read(imgStream);
        }

        catch (IOException e2)
        {
            System.out.println("Error thrown");
        }

        rect = new Rectangle2D.Double(x + 38, y, 5, 480);
        Mario.groundRectArr.add(rect);
        Mario.flagArr.add(thisFlag);
    }
}