/*
 * John Bacopulos
 * June 10, 2022
 * Creates hill object
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

public class Hill
{
    public int x;
    public int y;
    public int width = 250;
    public int height = 167;
    public Image img;
    public Hill thisHill = this;
    public Rectangle2D.Double rect;

    public Hill(int x, int y)
    {
        this.x = x;
        this.y = y;

        try
        {
            InputStream imgStream = Hill.class.getResourceAsStream("/img/hill.png");
            img = ImageIO.read(imgStream);
        }

        catch (IOException e2)
        {
            System.out.println("Error thrown");
        }

        Mario.hillArr.add(thisHill);
    }
}