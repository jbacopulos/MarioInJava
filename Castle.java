/*
 * John Bacopulos
 * June 3, 2022
 * Creates castle object
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

public class Castle
{
    public int x;
    public int y;
    public int width = 40;
    public int height = 40;
    public Image img;
    public Castle thisCastle = this;
    public Rectangle2D.Double rect;

    public Castle(int x, int y)
    {
        this.x = x;
        this.y = y;

        try
        {
            InputStream imgStream = Castle.class.getResourceAsStream("/img/castle.png");
            img = ImageIO.read(imgStream);
        }

        catch (IOException e2)
        {
            System.out.println("Error thrown");
        }

        rect = new Rectangle2D.Double(x, y, 40, 40);
        Mario.castleArr.add(thisCastle);
    }
}