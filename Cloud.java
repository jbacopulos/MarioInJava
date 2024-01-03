/*
 * John Bacopulos
 * June 10, 2022
 * Creates cloud object
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

public class Cloud
{
    public int x;
    public int y;
    public int width = 133;
    public int height = 97;
    public Image img;
    public Cloud thisCloud = this;

    public Cloud(int x, int y, int index)
    {
        this.x = x;
        this.y = y;

        if (index == 1)
        {
            try
            {
                InputStream imgStream = Cloud.class.getResourceAsStream("/img/cloud.png");
                img = ImageIO.read(imgStream);
            }

            catch (IOException e2)
            {
                System.out.println("Error thrown");
            }
        }

        else
        {

            width = 200;
            height = 100;
            try
            {
                InputStream imgStream = Cloud.class.getResourceAsStream("/img/cloud2.png");
                img = ImageIO.read(imgStream);
            }

            catch (IOException e2)
            {
                System.out.println("Error thrown");
            }
        }

        Mario.cloudArr.add(thisCloud);
    }
}