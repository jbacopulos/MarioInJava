/*
 * John Bacopulos
 * June 3, 2022
 * Main class for creating the game Mario
 * Contains the main method and the drawing of the graphics
 * 
 */

// Import required libraries
import java.util.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.Font.*;
import javax.swing.Timer;
import javax.swing.*; 
import java.io.*;
import javax.imageio.*;

public class Mario extends JFrame implements ActionListener, KeyListener
{
    // Name-constants for the various dimensions
    public static final int CANVAS_WIDTH = 1280;
    public static final int CANVAS_HEIGHT = 720;
    public static final Color CANVAS_BACKGROUND = new Color(92, 148, 252);

    private DrawCanvas canvas; // The custom drawing canvas (extends JPanel)

    // Buttons for start and quit
    JButton btnStart;
    JButton quitBtn;

    // Sound manager for playing sound effects
    SoundManager sm = new SoundManager();

    // Initializes camera position
    public int camerax = 0;
    public int cameray = 0;

    Timer timer; // Initializes timer object
    Image myImg; // Initializes icon image
    Image titleImg; // Initializes image for title screen

    // Initializes booleans for game state
    boolean started = false;
    boolean finish = false;
    boolean isGameOver = false;
    boolean gameWon = false;

    // Initializes booleans for character state
    boolean isJumping = false;
    boolean isGoingRight = true;
    boolean isHeld = false;
    boolean fallingFromJump = false;
    boolean isJumpPossible = true;
    boolean jumpWait = false;
    boolean sliding = false;

    // Initializes booleans for state of powerups and coins
    boolean mushroomGenerated = false;
    boolean mushroomGoingRight = true;
    boolean coinGenerated = false;
    boolean isSuperMario = false;
    boolean powerupAnimation = false;

    // Initializes ints for character movement
    int jumpI = 0;
    int jumpWaitCounter = 0;
    int cycle = 0;
    int counter = 1;

    // Initializes ints for state of powerup, coins, death, and win
    int coinCounter = 0;
    int deathAnimationCounter = 0;
    int timeToPointsCounter = 0;
    int pointsAtATime = 0;
    int powerupAnimationCounter = 0;
    static int goombaKilledCounter = 0;

    // Initializes ints for state of game
    int finishCounter = 0;
    int timeLeft = 12060;
    int points = 0;
    int livesLeft = 3;

    // Initializes ints for positions of character, mushroom and coin
    int mariox = 50;
    int marioy = CANVAS_HEIGHT - 168;
    int mushroomx = 0;
    int mushroomy = 0;
    int coinx = 0;
    int coiny = 0;

    // Initalizes objects for different mario icons
    CharacterObj marioContainer;
    CharacterObj mario1;
    CharacterObj mario2;
    CharacterObj mario3;
    CharacterObj mario4;
    CharacterObj mario5;
    CharacterObj mario6;
    CharacterObj mario7;
    CharacterObj mario8;
    CharacterObj mario9;
    CharacterObj mario10;
    CharacterObj transparentMario;

    // Initializes objects for mushroom and coin icons
    CharacterObj mushroomObj;
    CharacterObj coinObj;

    // Initalizes three lone goombas
    Goomba g1;
    Goomba g2;
    Goomba g3;

    // Initializes flag and castle object
    Flag f;
    Castle c;

    // Initializes the fonts used in the game
    Font btnFont;
    Font endFont;
    Font gameFont;

    // Creates all of the array lists used for characters, bricks, platforms, rectangles, goombas etc.
    public static ArrayList keysPressed = new ArrayList<Character>();
    public static ArrayList<Brick> groundArr = new ArrayList<Brick>();
    public static ArrayList<Platform> platformArr = new ArrayList<Platform>();
    public static ArrayList<MysteryBlock> mysteryBlockArr = new ArrayList<MysteryBlock>();
    public static ArrayList<Boolean> mysteryBlockUsableArr = new ArrayList<Boolean>();
    public static ArrayList<Rectangle2D.Double> groundRectArr = new ArrayList<Rectangle2D.Double>();
    public static ArrayList<Goomba> goombaArr = new ArrayList<Goomba>();
    public static ArrayList<Tube> tubeArr = new ArrayList<Tube>();
    public static ArrayList<Stair> stairArr = new ArrayList<Stair>();
    public static ArrayList<Flag> flagArr = new ArrayList<Flag>();
    public static ArrayList<Castle> castleArr = new ArrayList<Castle>();
    public static ArrayList<Hill> hillArr = new ArrayList<Hill>();
    public static ArrayList<Cloud> cloudArr = new ArrayList<Cloud>();

    // Mario constructor
    public Mario() 
    {
        // Creates timer with tick of 15 ms
        timer =  new Timer(15, this);        

        // Set up a custom drawing JPanel
        canvas = new DrawCanvas();
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        canvas.setLayout(null);

        // Add both panels to this JFrame
        Container cp = getContentPane();

        // Try to create custom font for button
        try
        {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            btnFont = Font.createFont(Font.TRUETYPE_FONT, new File("font.ttf"));
            btnFont = btnFont.deriveFont(Font.PLAIN, 30);
            ge.registerFont(btnFont);
        }

        catch (IOException|FontFormatException e)
        {
            btnFont = new Font("serif", 1, 45);
        }

        // Try to create custom font for end screen
        try
        {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            endFont = Font.createFont(Font.TRUETYPE_FONT, new File("font.ttf"));
            endFont = endFont.deriveFont(Font.PLAIN, 72);
            ge.registerFont(endFont);
        }

        catch (IOException|FontFormatException e)
        {
            endFont = new Font("serif", 1, 45);
        }

        // Try to create custom font for text in game
        try
        {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            gameFont = Font.createFont(Font.TRUETYPE_FONT, new File("font.ttf"));
            gameFont = gameFont.deriveFont(Font.PLAIN, 32);
            ge.registerFont(gameFont);
        }

        catch (IOException|FontFormatException e)
        {
            gameFont = new Font("serif", 1, 45);
        }

        // Create start button, position it and set size and font
        btnStart = new JButton("Start");
        canvas.add(btnStart);
        btnStart.addActionListener(this);
        btnStart.setSize(300, 150);
        btnStart.setLocation(CANVAS_WIDTH/2 - 150, 325);
        btnStart.setContentAreaFilled(false);
        btnStart.setBorderPainted(false);
        btnStart.setForeground(Color.WHITE);
        btnStart.setFont(btnFont);

        // Create quit button, position it and set size and font
        quitBtn = new JButton("Quit");
        canvas.add(quitBtn);
        quitBtn.addActionListener(this);
        quitBtn.setSize(300, 150);
        quitBtn.setLocation(CANVAS_WIDTH/2 - 150, 425);
        quitBtn.setContentAreaFilled(false);
        quitBtn.setBorderPainted(false);
        quitBtn.setForeground(Color.WHITE);
        quitBtn.setFont(btnFont);

        // Set attributes of JFrame
        setResizable(false);
        setTitle("Mario");

        // Create icon for game
        try
        {
            InputStream imgStream = Mario.class.getResourceAsStream("/img/icon.png");
            BufferedImage myImg = ImageIO.read(imgStream);
            setIconImage(myImg);
        }

        catch (IOException e)
        {

        }

        // Create image for start screen
        try
        {
            InputStream imgStream = Mario.class.getResourceAsStream("/img/title.png");
            titleImg = ImageIO.read(imgStream);
        }

        catch (IOException e)
        {

        }

        // Calls method to generate the map
        generateMap();

        // Creates the different icons for mario
        mario1 = new CharacterObj(1);
        mario2 = new CharacterObj(2);
        mario3 = new CharacterObj(3);
        mario4 = new CharacterObj(4);
        mario5 = new CharacterObj(5);
        mario6 = new CharacterObj(6);
        mario7 = new CharacterObj(7);
        mario8 = new CharacterObj(8);
        mario9 = new CharacterObj(14);
        mario10 = new CharacterObj(16);
        transparentMario = new CharacterObj(15);

        // Starting mario is mario1
        marioContainer = mario1;

        // Add everything to the container
        cp.setLayout(new BorderLayout());
        cp.add(canvas, BorderLayout.CENTER);

        addKeyListener(this); // "this" JFrame fires KeyEvent
        requestFocus(); // Set the focus to JFrame to receive KeyEvent    
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Handle the CLOSE button  
        pack(); // Pack all the components in the JFrame
        setVisible(true); // Show it
    }

    public void actionPerformed(ActionEvent e)
    {
        // Every timer tick
        if (e.getSource()== timer)
        {   
            requestFocus();

            // If the game has started
            if (started)
            {
                // If it is not over, time decreases and check if no time left
                if (!finish)
                {
                    timeLeft--;
                    checkTime();
                }

                // If mario is dead, set icon and do death animation
                if (marioContainer.dead)
                {
                    marioContainer = mario9;
                    deathAnimation();
                    canvas.repaint();
                }

                // If game is finished, do the finish level animation
                else if (finish)
                {
                    if (sliding)
                        marioContainer = mario10;

                    flagAnimation();
                    canvas.repaint();
                }

                // If game is going
                else
                {
                    // If "D" is pressed
                    if (keysPressed.contains(KeyEvent.VK_D))
                    {
                        // If movement right is possible
                        if (!collision("right"))
                        {
                            isGoingRight = true;

                            // Move mario or move camera
                            if (mariox > CANVAS_WIDTH/2)
                            {
                                camerax += 4;
                            }

                            else
                            {
                                mariox += 4;
                            }

                            // Change mario icon to make it look like he's running
                            if (counter % 10 == 0)
                            {
                                if (marioContainer == mario1)
                                {
                                    if (cycle == 0)
                                    {
                                        marioContainer = mario2;
                                    }

                                    else
                                    {
                                        marioContainer = mario3;
                                    }
                                }

                                else
                                {
                                    marioContainer = mario1;
                                }

                                counter++;
                            }

                            else
                            {
                                counter++;
                            }

                            // If he is jumping, set the icon
                            if (isJumping)
                            {
                                marioContainer = mario7;
                            }
                        }
                    }

                    // If "A" is pressed
                    else if (keysPressed.contains(KeyEvent.VK_A))
                    {
                        // If mario is not on the left side of the screen, and he can move left
                        if (mariox > 0 && !collision("left"))
                        {
                            // Move mario
                            isGoingRight = false;
                            mariox -= 4;

                            // Change icon to make it look like he's running
                            if (counter % 10 == 0)
                            {
                                if (marioContainer == mario4)
                                {
                                    if (cycle == 0)
                                    {
                                        marioContainer = mario5;
                                    }

                                    else
                                    {
                                        marioContainer = mario6;
                                    }
                                }

                                else
                                {
                                    marioContainer = mario4;
                                }

                                counter++;
                            }

                            else
                            {
                                counter++;
                            }

                            // Set icon to jumping
                            if (isJumping)
                            {
                                marioContainer = mario8;
                            }
                        }
                    }

                    // Check to see if goomba's have been killed
                    for (int i = 0; i < goombaArr.size(); i++)
                    {
                        if (goombaArr.get(i).justKilled)
                        {
                            goombaJustKilled(i);
                        }
                    }

                    // If "W" is pressed and Mario can jump
                    if (keysPressed.contains(KeyEvent.VK_W) && !jumpWait && isJumpPossible && isJumping == false && !fallingFromJump || keysPressed.contains(KeyEvent.VK_SPACE) && !jumpWait && isJumpPossible && isJumping == false && !fallingFromJump)
                    {
                        // Set correct mario icon
                        if (isGoingRight == true)
                        {
                            marioContainer = mario7;
                        }

                        else
                        {
                            marioContainer = mario8;
                        }

                        // Sound effect depending on mario size
                        if (!isSuperMario)
                            sm.jump.startMusic();

                        else
                            sm.superjump.startMusic();

                        // Move mario and start the jump
                        marioy += 6;
                        isJumping = true;
                        fallingFromJump = false;
                        isHeld = true;

                    }

                    // If jump has already started, and is continued
                    else if (isJumping && isHeld && !collision("up") && (keysPressed.contains(KeyEvent.VK_W) || keysPressed.contains(KeyEvent.VK_SPACE)))
                    {
                        // If mario still can jump more
                        if (jumpI <= 28)
                        {
                            marioy -= 6;
                        }

                        // Slow ascension
                        else if (jumpI > 28 && jumpI <= 33)
                        {
                            marioy -= 3;
                        }

                        // Mario starts falling
                        else 
                        {
                            isJumping = false;
                            jumpWait = true;
                            fallingFromJump = true;
                            jumpI = 0;
                        }

                        jumpI++;
                    }

                    // If user lets go of "W", jump ends
                    else if (isJumping && (!(keysPressed.contains(KeyEvent.VK_W) || keysPressed.contains(KeyEvent.VK_SPACE)) || isHeld == false))
                    {
                        isHeld = false;
                        isJumping = false;
                        fallingFromJump = true;
                    }

                    // Check if mario has touched the flag or if he has fallen off the map
                    marioFlagCollision();
                    fallOffMap();

                    // If the game has been beaten, stop main music, start flag music
                    if (finish)
                    {
                        sm.music.stopMusic();
                        sm.flag.startMusic();
                    }

                    // If mario size is changing
                    if (powerupAnimation)
                    {
                        if (powerupAnimationCounter < 12)
                        {
                            powerupAnimationCounter++;
                        }

                        else
                        {
                            powerupAnimationCounter = 0;
                            powerupAnimation = false;
                        }
                    }

                    // If not jumping, check if mario is falling
                    if (!isJumping)
                    {
                        isFalling();
                    }

                    // Make it so that you cannot jump right after you land
                    if (jumpWait)
                    {
                        if (jumpWaitCounter == 10)
                        {
                            jumpWait = false;
                            jumpWaitCounter = 0;
                        }

                        else
                        {
                            jumpWaitCounter++;
                        }
                    }

                    // If the mushroom is generated, check if it is falling, its collisions and move it
                    if (mushroomGenerated)
                    {
                        isMushroomFalling();
                        mushroomWallCollision();
                        moveMushroom();
                    }

                    // If the coin is generated, move it
                    if (coinGenerated)
                    {
                        moveCoin();
                    }

                    // If not super mario, check if Mario touches mushroom
                    if (!isSuperMario)
                    {
                        superMario();
                    }

                    // For goombas that are alive, move them, check if their falling, check their collision's, check if they collide with Mario
                    for (int i = 0; i < goombaArr.size(); i++)
                    {
                        if (goombaArr.get(i).dead == true)
                        {
                            
                        }

                        else
                        {
                            if (goombaArr.get(i).x - camerax >= 0 && goombaArr.get(i).x - camerax <= CANVAS_WIDTH && !goombaArr.get(i).isActive)
                            {
                                goombaArr.get(i).isActive = true;
                            }
                            

                            if (goombaArr.get(i).isActive == true)
                            {
                                isGoombaFalling(i);
                                goombaWallColision(i);
                                moveGoomba(i);
                                goombaMarioCollision(i);
                            }
                        }
                    }

                    canvas.repaint();
                }
            }  

            // If game is at win or lose screen
            else if (gameWon || isGameOver)
            {
                // Show start button
                btnStart.setText("Play again");
                btnStart.setSize(700, 150);
                btnStart.setLocation(CANVAS_WIDTH/2 - 345, 450);
                btnStart.setVisible(true);

                // Show quit button
                quitBtn.setLocation(CANVAS_WIDTH/2 - 150, 550);
                quitBtn.setVisible(true);
            }
            
            // If game is not started, repaint menu
            else if (!gameWon && !started)
            {
                canvas.repaint();
            }
        }

        // If the start button is pressed
        if (e.getSource() == btnStart)
        {
            // Reset ints and booleans to initial values
            mariox = 50;
            marioy = CANVAS_HEIGHT - 168;
            marioContainer = mario1;
            marioContainer.dead = false;
            camerax = 0;
            cameray = 0;
            finish = false;
            started = true;
            isGameOver = false;
            gameWon = false;
            isJumping = false;
            isGoingRight = true;
            isHeld = false;
            fallingFromJump = false;
            isJumpPossible = true;
            jumpWait = false;
            mushroomGenerated = false;
            mushroomGoingRight = true;
            coinGenerated = false;
            isSuperMario = false;
            sliding = false;
            powerupAnimation = false;
            jumpI = 0;
            jumpWaitCounter = 0;
            cycle = 0;
            counter = 1;
            coinCounter = 0;
            deathAnimationCounter = 0;
            finishCounter = 0;
            timeLeft = 12060;
            points = 0;
            livesLeft = 3;
            timeToPointsCounter = 0;
            pointsAtATime = 0;
            powerupAnimationCounter = 0;
            goombaKilledCounter = 0;
            mario1.dead = false;
            mario2.dead = false;
            mario3.dead = false;
            mario4.dead = false;
            mario5.dead = false;
            mario6.dead = false;
            mario7.dead = false;
            mario8.dead = false;
            mario9.dead = false;
            mario10.dead = false;
            transparentMario.dead = false;
            
            btnStart.setVisible(false);
            quitBtn.setVisible(false);

            generateMap(); // Regen the map
            sm.music.startMusic(); // Start the music
        }

        // If quit button is clicked, close the window
        if (e.getSource() == quitBtn)
        {
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
    }

    // Method to generate the map
    public void generateMap()
    {
        // Clear all the arraylists
        groundArr.clear();
        platformArr.clear();
        mysteryBlockArr.clear();
        mysteryBlockUsableArr.clear();
        groundRectArr.clear();
        goombaArr.clear();
        tubeArr.clear();
        stairArr.clear();
        flagArr.clear();
        castleArr.clear();
        hillArr.clear();
        cloudArr.clear();

        // Create all the objects at specific positions and add them to their respective array lists
        for (int i = 0; i < goombaArr.size(); i++)
        {
            for (int j = 0; j < goombaArr.size(); j++)
            {
                if (goombaArr.get(i).x == goombaArr.get(j).x && goombaArr.get(i).y == goombaArr.get(j).y && i != j)
                {
                    goombaArr.remove(i);
                }
            }
        }
        for (int i = 0; i < 23; i++)
        {
            int x = i * 128;
            Brick b = new Brick(x, CANVAS_HEIGHT - 128);
        }

        for (int i = 0; i < 4; i++)
        {
            int x = i * 128;
            Brick b = new Brick(3100 + x, CANVAS_HEIGHT - 128);
        }

        for (int i = 0; i < 19; i++)
        {
            int x = i * 128;
            Brick b = new Brick(3768 + x, CANVAS_HEIGHT - 128);
        }

        for (int i = 0; i < 20; i++)
        {
            int x = i * 128;
            Brick b = new Brick(6280 + x, CANVAS_HEIGHT - 128);
        }

        for (int i = 0; i < 3; i++)
        {
            int x = i * 80;
            Platform p = new Platform(x + 800, CANVAS_HEIGHT - 288);
        }

        for (int i = 1; i < 5; i += 2)
        {
            int x = i * 40;
            MysteryBlock m = new MysteryBlock(x + 800, CANVAS_HEIGHT - 288);
        }

        for (int i = 0; i < 4; i++)
        {
            int x = i * 350;
            int h = i * 30;

            if (i == 3)
            {
                Tube t = new Tube(1350 + x, CANVAS_HEIGHT - 208 - h + 30, 80 + h - 30);
            }

            else 
            {
                Tube t = new Tube(1350 + x, CANVAS_HEIGHT - 208 - h, 80 + h);
            }
        }

        for (int i = 0; i < 2; i++)
        {
            int x = i * 80;
            Platform p = new Platform(3296 + x, CANVAS_HEIGHT - 288);
        }

        for (int i = 0; i < 7; i++)
        {
            int x = i * 40;
            Platform p = new Platform(3416 + x, CANVAS_HEIGHT - 440);
        }

        for (int i = 0; i < 2; i++)
        {
            int x = i * 60;
            Goomba g = new Goomba(3450 + x, CANVAS_HEIGHT - 470, 1);
        }

        for (int i = 0; i < 3; i++)
        {
            int x = i * 40;
            Platform p = new Platform(3816 + x, CANVAS_HEIGHT - 440);
        }

        for (int i = 0; i < 2; i++)
        {
            int x = i * 40;
            Platform p = new Platform(4096 + x, CANVAS_HEIGHT - 288);
        }

        for (int i = 0; i < 3; i++)
        {
            int x = i * 120;
            MysteryBlock m = new MysteryBlock(4440 + x, CANVAS_HEIGHT - 288);
        }

        for (int i = 0; i < 3; i++)
        {
            int x = i * 40;
            Platform p = new Platform(5020 + x, CANVAS_HEIGHT - 440);
        }

        for (int i = 0; i < 2; i++)
        {
            int x = i * 120;
            Platform p = new Platform(5260 + x, CANVAS_HEIGHT - 440);
        }

        for (int i = 0; i < 2; i++)
        {
            int x = i * 40;
            MysteryBlock m = new MysteryBlock(5300 + x, CANVAS_HEIGHT - 440);
        }

        for (int i = 0; i < 2; i++)
        {
            int x = i * 40;
            Platform p = new Platform(5300 + x, CANVAS_HEIGHT - 288);
        }

        for (int i = 0; i < 4; i++)
        {
            int x = i * 40;

            for (int j = 0; j < i + 1; j++)
            {
                Stair s = new Stair(5500 + x, CANVAS_HEIGHT - 168 - (j * 40));
            }
        }

        for (int i = 4; i > 0; i--)
        {
            int x = Math.abs(i - 4) * 40;

            for (int j = 0; j < i; j++)
            {
                Stair s = new Stair(5760 + x, CANVAS_HEIGHT - 168 - (j * 40));
            }
        }

        for (int i = 0; i < 4; i++)
        {
            int x = i * 40;

            for (int j = 0; j < i + 1; j++)
            {
                Stair s = new Stair(6040 + x, CANVAS_HEIGHT - 168 - (j * 40));
            }
        }

        for (int i = 4; i > 0; i--)
        {
            int x = Math.abs(i - 4) * 40;

            for (int j = 0; j < i; j++)
            {
                Stair s = new Stair(6280 + x, CANVAS_HEIGHT - 168 - (j * 40));
            }
        }

        for (int i = 0; i < 8; i++)
        {
            int x = i * 40;

            for (int j = 0; j < i + 1; j++)
            {
                Stair s = new Stair(7410 + x, CANVAS_HEIGHT - 168 - (j * 40));
            }
        }

        for (int i = 0; i < 2; i++)
        {
            int x = i * 40;
            Platform p = new Platform(6840 + x, CANVAS_HEIGHT - 288);
        }

        for (int i = 0; i < 2; i++)
        {
            int x = i * 60;
            Goomba g = new Goomba(7100 + x, CANVAS_HEIGHT - 158, 1);
        }
        
        for (int i = 0; i < 2; i++)
        {
            int x = i * 60;
            Goomba g = new Goomba(4200 + x, CANVAS_HEIGHT - 158, 1);
        }

        for (int i = 0; i < 2; i++)
        {
            int x = i * 60;
            Goomba g = new Goomba(4700 + x, CANVAS_HEIGHT - 158, 1);
        }

        for (int i = 0; i < 2; i++)
        {
            int x = i * 60;
            Goomba g = new Goomba(5050 + x, CANVAS_HEIGHT - 158, 1);
        }

        for (int i = 0; i < 2; i++)
        {
            int x = i * 60;
            Goomba g = new Goomba(5200 + x, CANVAS_HEIGHT - 158, 1);
        }

        for (int i = 0; i < 2; i++)
        {
            int x = i * 60;
            Goomba g = new Goomba(7100 + x, CANVAS_HEIGHT - 158, 1);
        }

        MysteryBlock m = new MysteryBlock(2750, CANVAS_HEIGHT - 330);
        MysteryBlock m2 = new MysteryBlock(3336, CANVAS_HEIGHT - 288);
        MysteryBlock m3 = new MysteryBlock(3936, CANVAS_HEIGHT - 440);
        MysteryBlock m4 = new MysteryBlock(4560, CANVAS_HEIGHT - 440);
        MysteryBlock m5 = new MysteryBlock(6920, CANVAS_HEIGHT - 288);
        Platform p = new Platform(3936, CANVAS_HEIGHT - 288);
        Platform p2 = new Platform(4900, CANVAS_HEIGHT - 288);
        Platform p3 = new Platform(6960, CANVAS_HEIGHT - 288);
        Tube t = new Tube(6600, CANVAS_HEIGHT - 208, 80);
        Tube t2 = new Tube(7300, CANVAS_HEIGHT - 208, 80);
        Stair s = new Stair(8000, CANVAS_HEIGHT - 168);

        f = new Flag(7980, CANVAS_HEIGHT - 648);
        c = new Castle(8150, CANVAS_HEIGHT - 328);

        g1 = new Goomba(950, CANVAS_HEIGHT - 158, 1);
        g2 = new Goomba(1900, CANVAS_HEIGHT - 158, 1);
        g3 = new Goomba(2280, CANVAS_HEIGHT - 158, 1);

        // Clear duplicate generation of goombas
        for (int i = 0; i < goombaArr.size(); i++)
        {
            for (int j = 0; j < goombaArr.size(); j++)
            {
                if (goombaArr.get(i).x == goombaArr.get(j).x && goombaArr.get(i).y == goombaArr.get(j).y && i != j)
                {
                    goombaArr.remove(i);
                }
            }
        }

        for (int i = 0; i < 10; i++)
        {
            Cloud c = new Cloud(100 + (800 * i), 75, 1);
        }

        for (int i = 0; i < 10; i++)
        {
            Cloud c = new Cloud(350 + (825 * i), 150, 2);
        }

        Hill h1 = new Hill(0, CANVAS_HEIGHT - 295);
        Hill h2 = new Hill(2100, CANVAS_HEIGHT - 295);
        Hill h3 = new Hill(4200, CANVAS_HEIGHT - 295);
        Hill h4 = new Hill(5900, CANVAS_HEIGHT - 295);
    }

    // Method to check if Mario is falling
    public void isFalling()
    {
        // For regular mario
        if (!isSuperMario)
        {
            // Create a rectangle for mario
            Rectangle2D.Double marioRect = new Rectangle2D.Double(mariox + camerax + 5, marioy + cameray + 30, 20, 15);
            boolean falling = true;

            // For all the rectangles, check if mario is standing on any of them
            for (int i = 0; i < groundRectArr.size(); i++)
            {
                // If mario is standing on one
                if (marioRect.intersects(groundRectArr.get(i)))
                {
                    if (isJumpPossible == false)
                    {
                        marioContainer = mario1;
                    }

                    isJumpPossible = true; // Jump is possible
                    marioy = (int)groundRectArr.get(i).getY() - 40; // Set mario height to standing on rectangle
                    falling = false; // Not falling
                    jumpI = 0;

                    // If just landed from a jump
                    if (fallingFromJump)
                    {
                        jumpWait = true; // Wait before allowing next jump

                        // Set icons
                        if (isGoingRight)
                        {
                            marioContainer = mario1;
                        }

                        else
                        {
                            marioContainer = mario4;
                        }
                    }

                    fallingFromJump = false;

                    break;
                }

                else
                {
                    falling = true;
                }
            }

            // If mario is falling, move him down and set icon
            if (falling)
            {
                marioy += 6;
                
                if (isGoingRight)
                {
                    marioContainer = mario7;
                }

                else
                {
                    marioContainer = mario8;
                }

                isJumpPossible = false; // Jump not possible when falling
            }
        }

        // For super mario
        else
        {
            // Create a rectangle for mario
            Rectangle2D.Double marioRect = new Rectangle2D.Double(mariox + camerax + 5, marioy + cameray + 70, 35, 15);
            boolean falling = true;

            // For all the rectangles, check if mario is standing on any of them
            for (int i = 0; i < groundRectArr.size(); i++)
            {
                // If mario is standing on one
                if (marioRect.intersects(groundRectArr.get(i)))
                {
                    if (isJumpPossible == false)
                    {
                        marioContainer = mario1;
                    }

                    isJumpPossible = true; // Jump is possible
                    marioy = (int)groundRectArr.get(i).getY() - 80; // Set mario's height to standing on platform
                    falling = false; // Not falling
                    jumpI = 0;

                    // If just landed from a jumnp
                    if (fallingFromJump)
                    {
                        jumpWait = true; // Wait before allowing next jump

                        // Set icons
                        if (isGoingRight)
                        {
                            marioContainer = mario1;
                        }

                        else
                        {
                            marioContainer = mario4;
                        }
                    }

                    fallingFromJump = false;

                    break;
                }

                else
                {
                    falling = true;
                }
            }

            // If mario is falling, move him down and set icon
            if (falling)
            {
                marioy += 6;
                
                if (isGoingRight)
                {
                    marioContainer = mario7;
                }

                else
                {
                    marioContainer = mario8;
                }

                isJumpPossible = false; // Jump not possible when falling
            }
        }
    }

    // Method to check if mario collides with objects
    public boolean collision(String s)
    {
        // If checking if mario is colliding upwards
        if (s.equals("up"))
        {
            // For regular mario
            if (!isSuperMario)
            {
                // Create rectangle for mario's top
                Rectangle2D.Double marioRect = new Rectangle2D.Double(mariox + camerax + 2, marioy + cameray - 6, 26, 12);

                // Check every rectangle
                for (int i = 0; i < groundRectArr.size(); i++)
                {
                    // If mario collides with the rectangle
                    if (marioRect.intersects(groundRectArr.get(i)))
                    {
                        // Check if he collides with a mystery block
                        for (int j = 0; j < mysteryBlockArr.size(); j++)
                        {
                            if (mysteryBlockArr.get(j).x == groundRectArr.get(i).getX() && mysteryBlockArr.get(j).y == groundRectArr.get(i).getY() && mysteryBlockUsableArr.get(j) == true)
                            {
                                // Randomize between coin and mushroom
                                double n = Math.random();

                                // 50/50 coin/mushroom, unless mushroom has already been generated and not used
                                // If generates mushroom
                                if (n >= 0.5 && !mushroomGenerated)
                                {
                                    // Create mushroom
                                    mushroomObj = new CharacterObj(9);

                                    // Set position of mushroom
                                    mushroomx = (int)groundRectArr.get(i).getX();
                                    mushroomy = (int)groundRectArr.get(i).getY() - 30;
                                    mushroomGenerated = true;
                                }

                                // If coin is generated
                                else
                                {
                                    // Create coin
                                    coinObj = new CharacterObj(10);

                                    // Set position of coin
                                    coinx = (int)groundRectArr.get(i).getX() + 5;
                                    coiny = (int)groundRectArr.get(i).getY() - 30;
                                    coinGenerated = true;

                                    // Start coin sound effect and add points to the score
                                    sm.coin.startMusic();
                                    points += 200;
                                }

                                // Change the mystery block's image to being used
                                try
                                {
                                    InputStream imgStream = MysteryBlock.class.getResourceAsStream("/img/usedblock.png");
                                    mysteryBlockArr.get(j).img = ImageIO.read(imgStream);
                                }

                                catch (IOException e2)
                                {
                                    System.out.println("Error thrown");
                                }

                                // Make it so that you cannot use a mystery block twice
                                mysteryBlockUsableArr.set(j, false);
                            }
                        }

                        // Mario is falling
                        isJumping = false;
                        fallingFromJump = true;
                        jumpI = 0;
                        return true;
                    }
                }

                return false;
            }

            // Same as above but for super mario
            else
            {
                Rectangle2D.Double marioRect = new Rectangle2D.Double(mariox + camerax + 10, marioy + cameray - 6, 25, 12);

                for (int i = 0; i < groundRectArr.size(); i++)
                {
                    if (marioRect.intersects(groundRectArr.get(i)))
                    {
                        for (int j = 0; j < mysteryBlockArr.size(); j++)
                        {
                            if (mysteryBlockArr.get(j).x == groundRectArr.get(i).getX() && mysteryBlockArr.get(j).y == groundRectArr.get(i).getY() && mysteryBlockUsableArr.get(j) == true)
                            {
                                double n = Math.random();

                                if (n >= 0.5 && !mushroomGenerated && !isSuperMario)
                                {
                                    mushroomObj = new CharacterObj(9);
                                    mushroomx = (int)groundRectArr.get(i).getX();
                                    mushroomy = (int)groundRectArr.get(i).getY() - 30;
                                    mushroomGenerated = true;
                                }

                                else
                                {
                                    coinObj = new CharacterObj(10);
                                    coinx = (int)groundRectArr.get(i).getX() + 5;
                                    coiny = (int)groundRectArr.get(i).getY() - 30;
                                    coinGenerated = true;
                                    sm.coin.startMusic();
                                    points += 200;
                                }

                                try
                                {
                                    InputStream imgStream = MysteryBlock.class.getResourceAsStream("/img/usedblock.png");
                                    mysteryBlockArr.get(j).img = ImageIO.read(imgStream);
                                }

                                catch (IOException e2)
                                {
                                    System.out.println("Error thrown");
                                }

                                mysteryBlockUsableArr.set(j, false);
                            }
                        }

                        isJumping = false;
                        fallingFromJump = true;
                        jumpI = 0;
                        return true;
                    }
                }

                return false;
            }
        }

        // If checking left collision
        else if (s.equals("left"))
        {
            // For regular mario
            if (!isSuperMario)
            {
                // Create rectangle for mario's left side
                Rectangle2D.Double marioRect = new Rectangle2D.Double(mariox + camerax - 1, marioy + cameray, 5, 39);
                
                // Check if the rectangle collides with any rectangles
                for (int i = 0; i < groundRectArr.size(); i++)
                {
                    if (marioRect.intersects(groundRectArr.get(i)))
                    {
                        return true;
                    }
                }

                return false;
            }

            // Do the same for super mario
            else
            {
                Rectangle2D.Double marioRect = new Rectangle2D.Double(mariox + camerax - 1, marioy + cameray, 5, 79);
                
                for (int i = 0; i < groundRectArr.size(); i++)
                {
                    if (marioRect.intersects(groundRectArr.get(i)))
                    {
                        return true;
                    }
                }

                return false;
            }
        }

        // If checking right collision
        else 
        {
            // For regular mario
            if (!isSuperMario)
            {
                // Create rectangle for mario's right side
                Rectangle2D.Double marioRect = new Rectangle2D.Double(mariox + camerax + 26, marioy + cameray, 5, 39);
                
                // Check if that rectangle collides with any objects
                for (int i = 0; i < groundRectArr.size(); i++)
                {
                    if (marioRect.intersects(groundRectArr.get(i)))
                    {
                        return true;
                    }
                }

                return false;
            }

            // Do the same for super mario
            else
            {
                Rectangle2D.Double marioRect = new Rectangle2D.Double(mariox + camerax + 41, marioy + cameray, 5, 79);
                
                for (int i = 0; i < groundRectArr.size(); i++)
                {
                    if (marioRect.intersects(groundRectArr.get(i)))
                    {
                        return true;
                    }
                }

                return false;
            }
        }
    }

    // Method to move the mushroom if generated
    public void moveMushroom()
    {
        if (mushroomGenerated)
        {
            // If the mushroom is going right, move it right 
            if (mushroomGoingRight)
            {
                mushroomx += 2;
            }

            else
            {
                mushroomx -= 2;
            }
        }
    }

    public void mushroomWallCollision()
    {
        if (mushroomGoingRight)
        {
            Rectangle2D.Double mushRect = new Rectangle2D.Double(mushroomx + 26, mushroomy, 5, 29);

            for (int j = 0; j < groundRectArr.size(); j++)
            {
                if (mushRect.intersects(groundRectArr.get(j)))
                {
                    mushroomGoingRight = false;
                }
            }
        }

        else
        {
            Rectangle2D.Double mushRect = new Rectangle2D.Double(mushroomx - 1, mushroomy, 5, 29);

            for (int j = 0; j < groundRectArr.size(); j++)
            {
                if (mushRect.intersects(groundRectArr.get(j)))
                {
                    mushroomGoingRight = true;
                }
            }
        }
    }

    public void isMushroomFalling()
    {
        Rectangle2D.Double mushRect = new Rectangle2D.Double(mushroomx, mushroomy + cameray, 30, 40);
        boolean falling = true;

        for (int i = 0; i < groundRectArr.size(); i++)
        {
            if (mushRect.intersects(groundRectArr.get(i)) && (mushroomy <= (int)groundRectArr.get(i).getY() - 30))
            {
                mushroomy = (int)groundRectArr.get(i).getY() - 30;
                falling = false;
                break;
            }

            else
            {
                falling = true;
            }
        }

        if (falling)
        {
            mushroomy += 6;
            mushRect = new Rectangle2D.Double(mushroomx, mushroomy + cameray, 30, 30);

            for (int i = 0; i < groundRectArr.size(); i++)
            {
                if (mushRect.intersects(groundRectArr.get(i)) && (mushroomy <= (int)groundRectArr.get(i).getY() - 30))
                {
                    mushroomy = (int)groundRectArr.get(i).getY() - 30;
                }
            }
        }
    }

    public void moveCoin()
    {
        if (coinCounter < 16)
        {
            coiny -= 6;
            coinCounter++;
        }

        else if (coinCounter >= 16 && coinCounter < 32)
        {
            coiny += 6;
            coinCounter++;
        }

        else
        {
            coinGenerated = false;
            coinCounter = 0;
        }
    }

    public void superMario()
    {
        if (mushroomGenerated)
        {
            Rectangle2D.Double marioRect = new Rectangle2D.Double(mariox + camerax, marioy + cameray, 30, 40);
            Rectangle2D.Double mushRect = new Rectangle2D.Double(mushroomx, mushroomy + cameray, 30, 30);

            if (marioRect.intersects(mushRect))
            {
                isSuperMario = true;
                powerupAnimation = true;
                powerupAnimationCounter = 0;
                sm.powerup.startMusic();
                points += 1000;
                mushroomGenerated = false;
            }
        }
    }

    public void moveGoomba(int i)
    {
        if (goombaArr.get(i).goingRight)
        {
            goombaArr.get(i).x += 1;
        }

        else
        {
            goombaArr.get(i).x -= 1;
        }

        if (goombaArr.get(i).counter % 10 == 0)
        {
            if (goombaArr.get(i).index == 1)
            {
                goombaArr.get(i).index = 2;
                goombaArr.get(i).refreshImage();
            }

            else
            {
                goombaArr.get(i).index = 1;
                goombaArr.get(i).refreshImage();
            }
            goombaArr.get(i).counter++;
        }

        else
        {
            goombaArr.get(i).counter++;
        }
    }

    public void isGoombaFalling(int i)
    {
        Rectangle2D.Double goombaRect = new Rectangle2D.Double(goombaArr.get(i).x, goombaArr.get(i).y + cameray, 30, 40);
        boolean falling = true;

        for (int j = 0; j < groundRectArr.size(); j++)
        {
            if (goombaRect.intersects(groundRectArr.get(j)) && (goombaArr.get(i).y <= (int)groundRectArr.get(j).getY() - 30))
            {
                goombaArr.get(i).y = (int)groundRectArr.get(j).getY() - 30;
                falling = false;
                break;
            }

            else
            {
                falling = true;
            }
        }

        if (falling)
        {
            goombaArr.get(i).y += 6;
            goombaRect = new Rectangle2D.Double(goombaArr.get(i).x, goombaArr.get(i).y + cameray, 30, 30);

            for (int j = 0; j < groundRectArr.size(); j++)
            {
                if (goombaRect.intersects(groundRectArr.get(j)) && (goombaArr.get(i).y <= (int)groundRectArr.get(j).getY() - 30))
                {
                    goombaArr.get(i).y = (int)groundRectArr.get(j).getY() - 30;
                }
            }
        }
    }

    public void deathAnimation()
    {
        marioContainer.dead = true;

        if (deathAnimationCounter == 0)
        {
            sm.music.stopMusic();
            livesLeft--;
        }

        if (deathAnimationCounter >= 0 && deathAnimationCounter < 30)
        {
            marioy -= 6;
            deathAnimationCounter++;
        }

        else if (deathAnimationCounter >= 30 && deathAnimationCounter <= 150)
        {
            marioy += 6;
            deathAnimationCounter++;
        }

        else
        {
            if (livesLeft > 0)
            {
                mariox = 50;
                marioy = CANVAS_HEIGHT - 168;
                marioContainer = mario1;
                marioContainer.dead = false;
                camerax = 0;
                cameray = 0;
                finish = false;
                started = true; // If the game has started
                isGameOver = false; // If the game is over
                gameWon = false;
                isJumping = false;
                isGoingRight = true;
                isHeld = false;
                fallingFromJump = false;
                isJumpPossible = true;
                jumpWait = false;
                mushroomGenerated = false;
                mushroomGoingRight = true;
                coinGenerated = false;
                isSuperMario = false;
                sliding = false;
                powerupAnimation = false;
                jumpI = 0;
                jumpWaitCounter = 0;
                cycle = 0;
                counter = 1;
                coinCounter = 0;
                deathAnimationCounter = 0;
                finishCounter = 0;
                timeLeft = 12060;
                points = 0;
                pointsAtATime = 0;
                powerupAnimationCounter = 0;
                timeToPointsCounter = 0;
                goombaKilledCounter = 0;
                mario1.dead = false;
                mario2.dead = false;
                mario3.dead = false;
                mario4.dead = false;
                mario5.dead = false;
                mario6.dead = false;
                mario7.dead = false;
                mario8.dead = false;
                mario9.dead = false;
                mario10.dead = false;
                transparentMario.dead = false;

                generateMap();
                sm.music.startMusic();
            }

            else
            {
                started = false;
                isGameOver = true;
            }
        }
    }

    public void flagAnimation()
    {
        if (!isSuperMario)
        {
            if (marioy < CANVAS_HEIGHT - 208)
            {
                sliding = true;
                marioy += 3;
            }

            if (marioy >= CANVAS_HEIGHT - 208 && finishCounter == 0)
            {
                sliding = false;
                marioy = CANVAS_HEIGHT - 208;
                mariox += 35;
                finishCounter++;
                pointsAtATime = (int)((timeLeft/140)/67);
            }

            if (finishCounter > 0 && (mariox + camerax) < 8235)
            {
                isGoingRight = true;

                if (mariox > CANVAS_WIDTH/2)
                {
                    camerax += 3;
                }

                else
                {
                    mariox += 3;
                }

                if (counter % 10 == 0)
                {
                    if (marioContainer == mario1)
                    {
                        if (cycle == 0)
                        {
                            marioContainer = mario2;
                        }

                        else
                        {
                            marioContainer = mario3;
                        }
                    }

                    else
                    {
                        marioContainer = mario1;
                    }

                    counter++;
                }

                else
                {
                    counter++;
                }

                if ((mariox + camerax) >= 8235)
                {
                    sm.flag.stopMusic();
                    sm.complete.startMusic();
                }

                finishCounter++;
                isFalling();
            }

            if ((mariox + camerax) >= 8235)
            {
                if (timeToPointsCounter < 140)
                {
                    marioContainer = transparentMario;
                    points += pointsAtATime * 10;
                    timeLeft -= pointsAtATime * 67;
                    timeToPointsCounter++;
                    return;
                }

                if (timeToPointsCounter == 140)
                {
                    points += timeLeft * 10;
                    timeLeft = 0;
                    timeToPointsCounter++;
                    return;
                }

                else
                {
                    started = false;
                    gameWon = true;
                }
            }
        }

        else
        {
            if (marioy < CANVAS_HEIGHT - 248)
            {
                sliding = true;
                marioy += 3;
            }

            if (marioy >= CANVAS_HEIGHT - 248 && finishCounter == 0)
            {
                sliding = false;
                marioy = CANVAS_HEIGHT - 248;
                mariox += 50;
                finishCounter++;
                pointsAtATime = (int)((timeLeft/140)/67);
            }

            if (finishCounter > 0 && (mariox + camerax) < 8235)
            {
                isGoingRight = true;

                if (mariox > CANVAS_WIDTH/2)
                {
                    camerax += 3;
                }

                else
                {
                    mariox += 3;
                }

                if (counter % 10 == 0)
                {
                    if (marioContainer == mario1)
                    {
                        if (cycle == 0)
                        {
                            marioContainer = mario2;
                        }

                        else
                        {
                            marioContainer = mario3;
                        }
                    }

                    else
                    {
                        marioContainer = mario1;
                    }

                    counter++;
                }

                else
                {
                    counter++;
                }

                if ((mariox + camerax) >= 8235)
                {
                    sm.flag.stopMusic();
                    sm.complete.startMusic();
                }

                finishCounter++;
                isFalling();
            }

            if ((mariox + camerax) >= 8235)
            {
                if (timeToPointsCounter < 140)
                {
                    marioContainer = transparentMario;
                    points += pointsAtATime * 10;
                    timeLeft -= pointsAtATime * 67;
                    timeToPointsCounter++;
                    return;
                }

                if (timeToPointsCounter == 140)
                {
                    points += timeLeft * 10;
                    timeLeft = 0;
                    timeToPointsCounter++;
                    return;
                }

                else
                {
                    started = false;
                    gameWon = true;
                }
            }
        }
    }

    public void goombaMarioCollision(int i)
    {
        if (goombaArr.get(i).index != 3)
        {
            if (!isSuperMario)
            {
                Rectangle2D.Double marioRect = new Rectangle2D.Double(mariox + camerax, marioy + cameray, 30, 40);
                Rectangle2D.Double goombaRect = new Rectangle2D.Double(goombaArr.get(i).x, goombaArr.get(i).y + cameray, 30, 30);
                Rectangle2D.Double mariobRect = new Rectangle2D.Double(mariox + camerax + 5, marioy + cameray + 35, 20, 10);
                Rectangle2D.Double goombabRect = new Rectangle2D.Double(goombaArr.get(i).x, goombaArr.get(i).y + cameray - 5, 30, 10);

                if (marioRect.intersects(goombaRect))
                {
                    if (mariobRect.intersects(goombabRect))
                    {
                        goombaArr.get(i).dead = true;
                        goombaArr.get(i).justKilled = true;
                        sm.stomp.startMusic();
                        points += 100;
                    }

                    else 
                    {
                        marioContainer.dead = true;
                        sm.death.startMusic();
                    }
                }
            }

            else
            {
                Rectangle2D.Double marioRect = new Rectangle2D.Double(mariox + camerax, marioy + cameray, 45, 80);
                Rectangle2D.Double goombaRect = new Rectangle2D.Double(goombaArr.get(i).x, goombaArr.get(i).y + cameray, 30, 30);
                Rectangle2D.Double mariobRect = new Rectangle2D.Double(mariox + camerax + 5, marioy + cameray + 75, 350, 10);
                Rectangle2D.Double goombabRect = new Rectangle2D.Double(goombaArr.get(i).x, goombaArr.get(i).y + cameray, 20, 5);

                if (marioRect.intersects(goombaRect))
                {
                    if (mariobRect.intersects(goombabRect))
                    {
                        goombaArr.get(i).dead = true;
                        goombaArr.get(i).justKilled = true;
                        sm.stomp.startMusic();
                        points += 100;
                    }

                    else 
                    {
                        goombaArr.get(i).dead = true;
                        isSuperMario = false;
                        sm.powerdown.startMusic();
                        powerupAnimation = true;
                        powerupAnimationCounter = 0;
                    }
                }
            }
        }
    }

    public void marioFlagCollision()
    {
        if (!isSuperMario)
        {
            Rectangle2D.Double marioRect = new Rectangle2D.Double(mariox + camerax, marioy + cameray, 31, 40);

            if (marioRect.intersects(f.rect))
            {
                finish = true;
            }
        }

        else
        {
            Rectangle2D.Double marioRect = new Rectangle2D.Double(mariox + camerax, marioy + cameray, 46, 80);

            if (marioRect.intersects(f.rect))
            {
                finish = true;
            }
        }
    }

    public void goombaWallColision(int i)
    {
        if (goombaArr.get(i).goingRight)
        {
            Rectangle2D.Double goombaRect = new Rectangle2D.Double(goombaArr.get(i).x + 26, goombaArr.get(i).y, 5, 29);

            for (int j = 0; j < groundRectArr.size(); j++)
            {
                if (goombaRect.intersects(groundRectArr.get(j)))
                {
                    goombaArr.get(i).goingRight = false;
                }
            }
        }

        else
        {
            Rectangle2D.Double goombaRect = new Rectangle2D.Double(goombaArr.get(i).x - 1, goombaArr.get(i).y, 5, 29);

            for (int j = 0; j < groundRectArr.size(); j++)
            {
                if (goombaRect.intersects(groundRectArr.get(j)))
                {
                    goombaArr.get(i).goingRight = true;
                }
            }
        }
    }

    public void goombaJustKilled(int i)
    {
        isJumpPossible = false;
        isJumping = false;
        goombaArr.get(i).index = 3;
        goombaArr.get(i).refreshImage();

        if (goombaKilledCounter >= 0 && goombaKilledCounter < 10)
        {
            marioy -= 12;
            goombaKilledCounter++;
        }

        else 
        {
            goombaKilledCounter = 0;
            goombaArr.get(i).justKilled = false;
        }
    }

    public void checkTime()
    {
        if (timeLeft <= 0)
        {
            marioContainer.dead = true;
            sm.death.startMusic();
        }
    }

    public void fallOffMap()
    {
        if (marioy > 1500)
        {
            marioContainer.dead = true;
        }
    }

    // Method to check if key is pressed
    public void keyPressed(KeyEvent evt) 
    {
        // If new key pressed add it to keysPressed array
        if (!keysPressed.contains(evt.getKeyCode())){
            keysPressed.add(evt.getKeyCode());
        }    
    }

    // Method to check if key was released
    public void keyReleased(KeyEvent evt) 
    {
        if (!(keysPressed.indexOf(evt.getKeyCode()) < 0))
        {
            // Remove the key from the keysPressed array
            keysPressed.remove(keysPressed.indexOf(evt.getKeyCode())); 
        }
    }

    public void keyTyped(KeyEvent evt) 
    {

    }

    // Main method for entry
    public static void main(String[] args) 
    {
        Mario myProg = new Mario(); // Let the constructor do the job
        myProg.timer.start(); // Start the timer
    }

    // DrawCanvas (inner class) is a JPanel used for custom drawing
    class DrawCanvas extends JPanel 
    {

        // Paints canvas
        public void paintComponent(Graphics g) 
        {
            super.paintComponent(g);
            setBackground(CANVAS_BACKGROUND); // Set the background
            Graphics2D g2d = (Graphics2D)g; // Create g2d object

            if (started && !gameWon)
            {
                for (int i = 0; i < cloudArr.size(); i++)
                {
                    g2d.drawImage(cloudArr.get(i).img, cloudArr.get(i).x - camerax, cloudArr.get(i).y - cameray, cloudArr.get(i).width, cloudArr.get(i).height, null);
                }

                for (int i = 0; i < hillArr.size(); i++)
                {
                    g2d.drawImage(hillArr.get(i).img, hillArr.get(i).x - camerax, hillArr.get(i).y - cameray, hillArr.get(i).width, hillArr.get(i).height, null);
                }
                
                for (int i = 0; i < tubeArr.size(); i++)
                {
                    g2d.drawImage(tubeArr.get(i).img, tubeArr.get(i).x - camerax, tubeArr.get(i).y - cameray, 100, tubeArr.get(i).height, null);
                }

                g2d.setFont(gameFont);
                g2d.setColor(Color.WHITE);
                g2d.drawString("Time: " + Integer.toString((int)timeLeft/67), 50, 50);
                g2d.drawString("Lives: " + Integer.toString(livesLeft), CANVAS_WIDTH/2 - 190, 50);
                g2d.drawString("Points: " + Integer.toString(points), CANVAS_WIDTH - 450, 50);

                for (int i = 0; i < groundArr.size(); i++)
                {
                    if (groundArr.get(i).x - camerax >= -200 && groundArr.get(i).x - camerax <= 1480)
                    {
                        g2d.drawImage(groundArr.get(i).img, groundArr.get(i).x - camerax, groundArr.get(i).y - cameray, 128, 128, null);
                    }
                }

                for (int i = 0; i < platformArr.size(); i++)
                {
                    if (platformArr.get(i).x - camerax >= -200 && platformArr.get(i).x - camerax <= 1480)
                    {
                        g2d.drawImage(platformArr.get(i).img, platformArr.get(i).x - camerax, platformArr.get(i).y - cameray, 40, 40, null);
                    }
                }

                for (int i = 0; i < castleArr.size(); i++)
                {
                    g2d.drawImage(castleArr.get(i).img, castleArr.get(i).x - camerax, castleArr.get(i).y - cameray, 200, 200, null);
                }

                for (int i = 0; i < stairArr.size(); i++)
                {
                    if (stairArr.get(i).x - camerax >= -200 && stairArr.get(i).x - camerax <= 1480)
                    {
                        g2d.drawImage(stairArr.get(i).img, stairArr.get(i).x - camerax, stairArr.get(i).y - cameray, 40, 40, null);
                    }
                }

                for (int i = 0; i < mysteryBlockArr.size(); i++)
                {
                    if (mysteryBlockArr.get(i).x - camerax >= -200 && mysteryBlockArr.get(i).x - camerax <= 1480)
                    {
                        g2d.drawImage(mysteryBlockArr.get(i).img, mysteryBlockArr.get(i).x - camerax, mysteryBlockArr.get(i).y - cameray, 40, 40, null);
                    }
                }

                for (int i = 0; i < flagArr.size(); i++)
                {
                    g2d.drawImage(flagArr.get(i).img, flagArr.get(i).x - camerax, flagArr.get(i).y - cameray, 60, 480, null);
                }

                if (mushroomGenerated)
                {
                    g2d.drawImage(mushroomObj.img, mushroomx - camerax, mushroomy - cameray, 30, 30, null);
                }

                if (coinGenerated)
                {
                    g2d.drawImage(coinObj.img, coinx - camerax, coiny - cameray, 30, 30, null);
                }

                if (!isSuperMario && !powerupAnimation)
                {
                    g2d.drawImage(marioContainer.img, mariox, marioy, 30, 40, null);
                }

                if (!isSuperMario && powerupAnimation)
                {
                    g2d.drawImage(marioContainer.img, mariox, marioy - 20, 37, 60, null);
                }

                for (int i = 0; i < goombaArr.size(); i++)
                {
                    if (!(goombaArr.get(i).dead && !goombaArr.get(i).justKilled))
                    {
                        if (goombaArr.get(i).index == 3)
                        {
                            g2d.drawImage(goombaArr.get(i).img, goombaArr.get(i).x - camerax, goombaArr.get(i).y - cameray, goombaArr.get(i).width, goombaArr.get(i).height, null);
                        }

                        else
                        {
                            if (goombaArr.get(i).x - camerax >= -200 && goombaArr.get(i).x - camerax <= 1480)
                            {
                                g2d.drawImage(goombaArr.get(i).img, goombaArr.get(i).x - camerax, goombaArr.get(i).y - cameray, goombaArr.get(i).width, goombaArr.get(i).height, null);
                            }
                        }
                    }
                }

                if (isSuperMario && powerupAnimation)
                {
                    g2d.drawImage(marioContainer.img, mariox, marioy + 20, 37, 60, null);
                }

                if (isSuperMario && !powerupAnimation)
                {
                    g2d.drawImage(marioContainer.img, mariox, marioy, 45, 80, null);
                }
            }

            else if (!started && isGameOver)
            {
                g2d.setFont(endFont);
                g2d.setColor(Color.WHITE);
                g2d.drawString("GAME OVER", (CANVAS_WIDTH/2) - 350, 150);
                
                try
                {
                    InputStream imgStream = CharacterObj.class.getResourceAsStream("/img/gameovermario.png");
                    Image img = ImageIO.read(imgStream);
                    g2d.drawImage(img, 400, 200, 440, 200, null);
                }

                catch (IOException e2)
                {
                    System.out.println("Error thrown");
                }
            }

            else if (!started && !gameWon)
            {
                g2d.drawImage(titleImg, (CANVAS_WIDTH/2) - 250, 50, 500, 250, null);
            }

            else if (gameWon)
            {
                g2d.setFont(endFont);
                g2d.setColor(Color.WHITE);
                g2d.drawString("YOU WIN", (CANVAS_WIDTH/2) - 250, 150);

                try
                {
                    InputStream imgStream = CharacterObj.class.getResourceAsStream("/img/winmario.png");
                    Image img = ImageIO.read(imgStream);
                    g2d.drawImage(img, 580, 300, 125, 172, null);
                }

                catch (IOException e2)
                {
                    System.out.println("Error thrown");
                }

                g2d.setFont(gameFont);
                g2d.drawString("Points: " + Integer.toString(points), (CANVAS_WIDTH/2) - 180, 225);
            }
        }
    }
}