/*
 * John Bacopulos
 * June 3, 2022
 * Creates object for tube
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

public class Tube
{
    public int x;
    public int y;
    public int width = 100;
    public int height;
    public Image img;
    public Tube thisTube = this;
    public Rectangle2D.Double rect;

    public Tube(int x, int y, int h)
    {
        this.x = x;
        this.y = y;
        this.height = h;

        try
        {
            InputStream imgStream = Tube.class.getResourceAsStream("/img/tube.png");
            img = ImageIO.read(imgStream);
        }

        catch (IOException e2)
        {
            System.out.println("Error thrown");
        }

        BufferedImage img2 = (BufferedImage)img;
        BufferedImage img3 = img2.getSubimage(0, 0, 100, h);
        this.img = img3;

        rect = new Rectangle2D.Double(x, y, this.width, h);
        Mario.groundRectArr.add(rect);
        Mario.tubeArr.add(thisTube);
    }
}