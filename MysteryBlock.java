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

public class MysteryBlock
{
    public int x;
    public int y;
    public int width = 40;
    public int height = 40;
    public Image img;
    public MysteryBlock thisMysteryBlock = this;
    public Rectangle2D.Double rect;

    public MysteryBlock(int x, int y)
    {
        this.x = x;
        this.y = y;

        try
        {
            InputStream imgStream = MysteryBlock.class.getResourceAsStream("/img/mysteryblock.png");
            img = ImageIO.read(imgStream);
        }

        catch (IOException e2)
        {
            System.out.println("Error thrown");
        }

        rect = new Rectangle2D.Double(x, y, 40, 40);
        Mario.groundRectArr.add(rect);
        Mario.mysteryBlockArr.add(thisMysteryBlock);
        Mario.mysteryBlockUsableArr.add(true);
    }
}