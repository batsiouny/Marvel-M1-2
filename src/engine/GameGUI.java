package engine;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.abilities.Ability;
import model.abilities.AreaOfEffect;
import model.abilities.CrowdControlAbility;
import model.abilities.DamagingAbility;
import model.abilities.HealingAbility;
import model.world.Champion;
import model.world.Cover;
import model.world.Damageable;
import model.world.Direction;
import exceptions.GameActionException;

public class GameGUI extends JFrame implements ActionListener {
    JPanel board;
    JPanel leftMenu;
    JPanel rightMenu;
    Game game;
    JButton[][] tiles;
    JButton moveLeft;
    JButton moveRight;
    JButton moveUp;
    JButton moveDown;
    JButton attackLeft;
    JButton attackRight;
    JButton attackUp;
    JButton attackDown;
    JButton abilityOne;
    JButton abilityTwo;
    JButton abilityThree;
    JButton useLeaderAbility;
    JButton endTurn;
    boolean choosingSingleTarget;
    Ability a;
    Font customFont14px;
    Font customFont16px;

    public GameGUI(Game game) {
        this.game = game;
        this.tiles = new JButton[Game.getBoardheight()][Game.getBoardwidth()];

        this.setTitle("Marvel: Ultimate Wars");
        this.setResizable(true);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(true);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        try {
            customFont14px = Font.createFont(Font.TRUETYPE_FONT, new File("DeterminationSansWebRegular-369X.ttf")).deriveFont(16f);
            customFont16px = Font.createFont(Font.TRUETYPE_FONT, new File("DeterminationSansWebRegular-369X.ttf")).deriveFont(24f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont14px);
            ge.registerFont(customFont16px);
        } catch (IOException e) {
            e.printStackTrace();
        } catch(FontFormatException e) {
            e.printStackTrace();
        }

        //we are going to call this method every time the board changes
        updateGUI();
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == moveDown)
                game.move(Direction.DOWN);

            if (e.getSource() == moveUp)
                game.move(Direction.UP);

            if (e.getSource() == moveRight)
                game.move(Direction.RIGHT);

            if (e.getSource() == moveLeft)
                game.move(Direction.LEFT);

            if (e.getSource() == attackDown)
                game.attack(Direction.DOWN);

            if (e.getSource() == attackUp)
                game.attack(Direction.UP);

            if (e.getSource() == attackRight)
                game.attack(Direction.RIGHT);

            if (e.getSource() == attackLeft)
                game.attack(Direction.LEFT);

            if (e.getSource() == endTurn) {
                game.endTurn();
                choosingSingleTarget = true;
            }

            if (e.getSource() == useLeaderAbility)
                game.useLeaderAbility();

            if (e.getSource() == abilityOne) {
                a = game.getCurrentChampion().getAbilities().get(0);
                if (a.getCastArea() == AreaOfEffect.DIRECTIONAL) {
                    Object[] options = {"RIGHT", "LEFT", "DOWN", "UP"};
                    ImageIcon icon = new ImageIcon("src/resources/marvel-logo.png");
                    int n = JOptionPane.showOptionDialog(this, "Choose a direction to cast your champion's ability: ", a.getName(), JOptionPane.CLOSED_OPTION, JOptionPane.QUESTION_MESSAGE, icon, options, options[2]);
                    if (n == 0)
                        game.castAbility(a, Direction.RIGHT);
                    else if (n == 1)
                        game.castAbility(a, Direction.LEFT);
                    else if (n == 2)
                        game.castAbility(a, Direction.DOWN);
                    else if (n == 3)
                        game.castAbility(a, Direction.UP);
                }
                else if (a.getCastArea() == AreaOfEffect.SINGLETARGET)
                {
                    choosingSingleTarget = true;
                    JOptionPane.showMessageDialog(this,"Choose a champion tile to cast the ability on");
                }
                else if (a.getCastArea() == AreaOfEffect.SELFTARGET || a.getCastArea() == AreaOfEffect.SURROUND || a.getCastArea() == AreaOfEffect.TEAMTARGET){
                    game.castAbility(a);
                }
            }
            if (e.getSource() == abilityTwo) {
                a = game.getCurrentChampion().getAbilities().get(1);
                if (a.getCastArea() == AreaOfEffect.DIRECTIONAL) {
                    Object[] options = {"RIGHT", "LEFT", "DOWN", "UP"};
                    ImageIcon icon = new ImageIcon("src/resources/marvel-logo.png");
                    int n = JOptionPane.showOptionDialog(this, "Choose a direction to cast your champion's ability: ", a.getName(), JOptionPane.CLOSED_OPTION, JOptionPane.QUESTION_MESSAGE, icon, options, options[2]);
                    if (n == 0)
                        game.castAbility(a, Direction.RIGHT);
                    else if (n == 1)
                        game.castAbility(a, Direction.LEFT);
                    else if (n == 2)
                        game.castAbility(a, Direction.DOWN);
                    else if (n == 3)
                        game.castAbility(a, Direction.UP);
                }
                else if (a.getCastArea() == AreaOfEffect.SINGLETARGET)
                {
                    choosingSingleTarget = true;
                    JOptionPane.showMessageDialog(this,"Choose a champion tile to cast the ability on");
                }
                else if (a.getCastArea() == AreaOfEffect.SELFTARGET || a.getCastArea() == AreaOfEffect.SURROUND || a.getCastArea() == AreaOfEffect.TEAMTARGET){
                    game.castAbility(a);
                }


            }
            if (e.getSource() == abilityThree) {
                a = game.getCurrentChampion().getAbilities().get(2);
                if (a.getCastArea() == AreaOfEffect.DIRECTIONAL) {
                    Object[] options = {"RIGHT", "LEFT", "DOWN", "UP"};
                    ImageIcon icon = new ImageIcon("src/resources/marvel-logo.png");
                    int n = JOptionPane.showOptionDialog(this, "Choose a direction to cast your champion's ability: ", a.getName(), JOptionPane.CLOSED_OPTION, JOptionPane.QUESTION_MESSAGE, icon, options, options[2]);
                    if (n == 0)
                        game.castAbility(a, Direction.RIGHT);
                    else if (n == 1)
                        game.castAbility(a, Direction.LEFT);
                    else if (n == 2)
                        game.castAbility(a, Direction.DOWN);
                    else if (n == 3)
                        game.castAbility(a, Direction.UP);
                }
                else if (a.getCastArea() == AreaOfEffect.SINGLETARGET)
                {
                    choosingSingleTarget = true;
                    JOptionPane.showMessageDialog(this,"Choose a champion tile to cast the ability on");
                }
                else if (a.getCastArea() == AreaOfEffect.SELFTARGET || a.getCastArea() == AreaOfEffect.SURROUND || a.getCastArea() == AreaOfEffect.TEAMTARGET){
                    game.castAbility(a);
                }
            }
            for(int i = 0; i< tiles.length;i++){
                for(int j = 0; j< tiles[0].length;j++){
                    if(e.getSource()==tiles[i][j]){
                        if(choosingSingleTarget) {
                            choosingSingleTarget = false;
                            game.castAbility(a, i, j);
                        }
                    }
                }
            }
            Player player = game.checkGameOver();
            if (player != null)
            {
                int n = JOptionPane.showConfirmDialog(this,player.getName()+ "has won!!", "Game Over", JOptionPane.CLOSED_OPTION,JOptionPane.INFORMATION_MESSAGE,new ImageIcon("src/resources/deadp.jpg"));
                if (n == 0 || n == -1)
                {
                    this.dispose();
                    System.exit(0);
                }

            }

        } catch (GameActionException | CloneNotSupportedException exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage(),"Invalid move !",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("src/resources/warning.png"));
        }

        updateGUI();


    }

    //method containing what was previously in the constructor (we are going to need to call it multiple times)
    public void updateGUI() {
        if (board != null) {
            this.remove(board);
            this.remove(leftMenu);
            this.remove(rightMenu);
        }
        board = new JPanel();
        board.setLayout(new GridLayout(5, 5));
        board.setPreferredSize(new Dimension(this.getWidth() - 600, this.getHeight()));
        leftMenu = new JPanel();
        leftMenu.setLayout(new FlowLayout(FlowLayout.LEFT, 0, -5));
        leftMenu.setPreferredSize(new Dimension(300, this.getHeight()));
        rightMenu = new JPanel();
        rightMenu.setLayout(new FlowLayout(FlowLayout.LEFT, 0, -5));
        rightMenu.setPreferredSize(new Dimension(300, this.getHeight()));
        leftMenu.setBackground(Color.BLACK);
        rightMenu.setBackground(Color.BLACK);
        leftMenu.setOpaque(true);
        rightMenu.setOpaque(true);
        this.add(board, BorderLayout.CENTER);
        this.add(leftMenu, BorderLayout.WEST);
        this.add(rightMenu, BorderLayout.EAST);
        
        JPanel firstPlayerPanel = new JPanel();
        firstPlayerPanel.setPreferredSize(new Dimension(300, 128));
        firstPlayerPanel.setBackground(Color.BLACK);
        firstPlayerPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
        leftMenu.add(firstPlayerPanel);
        
        if(game.getFirstPlayer().getTeam().contains(game.getCurrentChampion()))
        	//firstPlayerPanel.setBorder(BorderFactory.createCompoundBorder(firstPlayerPanel.getBorder(),BorderFactory.createLineBorder(Color.YELLOW, 3)));
        	firstPlayerPanel.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 5));
        	
        //showing info about Players in the right menu
        JLabel firstPlayerName = new JLabel("  First Player: " + game.getFirstPlayer().getName());
        firstPlayerName.setFont(customFont16px);
        firstPlayerName.setForeground(Color.red);
        firstPlayerName.setPreferredSize(new Dimension(290, 50));
        firstPlayerPanel.add(firstPlayerName);
        
        JLabel firstLeaderAbility = new JLabel("  Leader Ability Used: " + game.isFirstLeaderAbilityUsed());
        firstLeaderAbility.setFont(customFont16px);
        firstLeaderAbility.setForeground(Color.red);
        firstLeaderAbility.setPreferredSize(new Dimension(290, 50));
        firstPlayerPanel.add(firstLeaderAbility);

        ImageIcon cap = new ImageIcon("src/resources/cap.gif");
        ImageIcon spidey = new ImageIcon("src/resources/spidey.gif");
        ImageIcon jacket = new ImageIcon("src/resources/jacket.png");
        ImageIcon wade = new ImageIcon("src/resources/wade.gif");
        ImageIcon hela = new ImageIcon("src/resources/hel.gif");
        ImageIcon tony = new ImageIcon("src/resources/ironman.gif");
        ImageIcon hulk = new ImageIcon("src/resources/hulk.gif");
        ImageIcon loki = new ImageIcon("src/resources/loki.gif");
        ImageIcon odinson = new ImageIcon("src/resources/thor.gif");
        ImageIcon rider = new ImageIcon("src/resources/rider2.gif");
        ImageIcon doctor = new ImageIcon("src/resources/strange.gif");
        ImageIcon silver = new ImageIcon("src/resources/silver.gif");
        ImageIcon ice = new ImageIcon("src/resources/ice.gif");
        ImageIcon electro = new ImageIcon("src/resources/electro.gif");
        ImageIcon venom = new ImageIcon("src/resources/venom.gif");
        ImageIcon cover = new ImageIcon("src/resources/cover2.jpeg");
        ImageIcon dead = new ImageIcon("src/resources/dead.jpeg");
        
        JPanel secondPlayerPanel = new JPanel();
        secondPlayerPanel.setPreferredSize(new Dimension(300, 128));
        secondPlayerPanel.setBackground(Color.BLACK);
        secondPlayerPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
        leftMenu.add(secondPlayerPanel);
        
        if(game.getSecondPlayer().getTeam().contains(game.getCurrentChampion()))
        	//secondPlayerPanel.setBorder(BorderFactory.createCompoundBorder(secondPlayerPanel.getBorder(),BorderFactory.createLineBorder(Color.YELLOW, 3)));
        	secondPlayerPanel.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 5));
        	
        //showing info about Players in the right menu
        JLabel secondPlayerName = new JLabel("  Second Player: " + game.getSecondPlayer().getName());
        secondPlayerName.setFont(customFont16px);
        secondPlayerName.setForeground(Color.blue);
        secondPlayerName.setPreferredSize(new Dimension(290, 50));
        secondPlayerPanel.add(secondPlayerName);
        
        JLabel secondLeaderAbility = new JLabel("  Leader Ability Used: " + game.isSecondLeaderAbilityUsed());
        secondLeaderAbility.setFont(customFont16px);
        secondLeaderAbility.setForeground(Color.blue);
        secondLeaderAbility.setPreferredSize(new Dimension(290, 50));
        secondPlayerPanel.add(secondLeaderAbility);
        
        //ImageIcon currChampionImageIcon = new ImageIcon(game.getCurrentChampion().getName()+"_icon.png");
        //JLabel currentTurnIcon = new JLabel();
        //currentTurnIcon.setIcon(currChampionImageIcon);
        
        JLabel currentTurn = new JLabel("  Current turn: " + game.getCurrentChampion().getName());
        currentTurn.setFont(customFont16px);
        currentTurn.setForeground(Color.yellow);
        currentTurn.setPreferredSize(new Dimension(300, 64));
        currentTurn.setBorder(BorderFactory.createLineBorder(Color.white,5));
        leftMenu.add(currentTurn);
        //leftMenu.add(currentTurnIcon);

        ArrayList<Champion> turnOrdr = new ArrayList<Champion>();

        while (!game.getTurnOrder().isEmpty()) {
            turnOrdr.add((Champion) game.getTurnOrder().remove());
        }
        for (int i = 0; i < turnOrdr.size(); i++) {
            game.getTurnOrder().insert(turnOrdr.get(i));
        }
        
        JPanel championsInTurn = new JPanel();
        championsInTurn.setPreferredSize(new Dimension(300,1600));
        championsInTurn.setBorder(BorderFactory.createLineBorder(Color.white,5));
        championsInTurn.setBackground(Color.black);
        championsInTurn.setLayout(new FlowLayout());
        leftMenu.add(championsInTurn);
        
        JLabel label = new JLabel("  Next champions in Turn: ");
        label.setFont(customFont16px);
        label.setForeground(Color.lightGray);
        label.setPreferredSize(new Dimension(290, 32));
        championsInTurn.add(label);
        
        for (int i = 1; i < game.getTurnOrder().size(); i++) {
            JLabel nextInTurn = new JLabel("         " + turnOrdr.get(i).getName());
            nextInTurn.setFont(customFont16px);
            nextInTurn.setForeground(Color.lightGray);
            nextInTurn.setPreferredSize(new Dimension(290, 25));
            championsInTurn.add(nextInTurn);
        }

        for (int i = 4; i >= 0; i--) {

            for (int j = 0; j < 5; j++) {
                Damageable d = (Damageable) game.getBoard()[i][j];
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(50, 50));
                button.setForeground(Color.black);
                button.setBackground(Color.lightGray);
                button.setOpaque(true);
                button.setVisible(true);
                button.addActionListener(this);
                tiles[i][j]=button;
                board.add(button);
                if (d != null && d.getClass() != Cover.class) {

                    button.setToolTipText("<html> Name: " + ((Champion) d).getName() + "<br>" + "Current HP: " + d.getCurrentHP() + "<br>" + "Mana: " + ((Champion) d).getMana() + "<br>" + "Speed: " + ((Champion) d).getSpeed() + "<br>" + "Action Points: " + ((Champion) d).getMaxActionPointsPerTurn() + "<br>" + "Attack Damage: " + ((Champion) d).getAttackDamage() + "<br>" + "Attack Range: " + ((Champion) d).getMaxActionPointsPerTurn() + "<br>" + "Type: " + d.getClass().getSimpleName() + "<br>" + "is Leader: " + game.checkLeader((Champion) d) + "<br>");
                    for (int h = 0; h < ((Champion) d).getAppliedEffects().size(); h++) {
                        button.setToolTipText(button.getToolTipText() + "Applied effect: " + ((Champion) d).getAppliedEffects().get(h).getName() + "<br>" + "Duration: " + ((Champion) d).getAppliedEffects().get(h).getDuration() + "<br>");
                    }
                    button.setToolTipText(button.getToolTipText() + "</html>");
                    if (((Champion) d).getName().equals("Captain America"))
                        button.setIcon(cap);
                    else if (((Champion) d).getName().equals("Spiderman"))
                        button.setIcon(spidey);
                    else if (((Champion) d).getName().equals("Yellow Jacket"))
                        button.setIcon(jacket);
                    else if (((Champion) d).getName().equals("Deadpool"))
                        button.setIcon(wade);
                    else if (((Champion) d).getName().equals("Quicksilver"))
                        button.setIcon(silver);
                    else if (((Champion) d).getName().equals("Hela"))
                        button.setIcon(hela);
                    else if (((Champion) d).getName().equals("Hulk"))
                        button.setIcon(hulk);
                    else if (((Champion) d).getName().equals("Iceman"))
                        button.setIcon(ice);
                    else if (((Champion) d).getName().equals("Loki"))
                        button.setIcon(loki);
                    else if (((Champion) d).getName().equals("Thor"))
                        button.setIcon(odinson);
                    else if (((Champion) d).getName().equals("Ghost Rider"))
                        button.setIcon(rider);
                    else if (((Champion) d).getName().equals("Dr Strange"))
                        button.setIcon(doctor);
                    else if (((Champion) d).getName().equals("Ironman"))
                        button.setIcon(tony);
                    else if (((Champion) d).getName().equals("Venom"))
                        button.setIcon(venom);
                    else if (((Champion) d).getName().equals("Electro"))
                        button.setIcon(electro);


                    //red border if player one, blue border if player two, and add a gold border if it is their current turn
                    if (game.getFirstPlayer().getTeam().contains(((Champion) d)))
                        button.setBorder(BorderFactory.createLineBorder(Color.RED, 4));
                    else if (game.getSecondPlayer().getTeam().contains(((Champion) d)))
                        button.setBorder(BorderFactory.createLineBorder(Color.BLUE, 4));
                    if (((Champion) d) == game.getCurrentChampion())
                        button.setBorder(BorderFactory.createCompoundBorder(button.getBorder(), BorderFactory.createLineBorder(new Color(0xFFD700), 4)));

                } else if (d != null && d.getClass() == Cover.class) {
                    button.setText("Current HP: " + d.getCurrentHP());
                    button.setIcon(cover);
                    button.setHorizontalTextPosition(JButton.CENTER);
                    button.setVerticalTextPosition(JButton.BOTTOM);
                    button.setToolTipText("Current HP: "+d.getCurrentHP());
                }
                else
                    button.setIcon(new ImageIcon("src/resources/grasso.jpeg"));
            }
        }

        JPanel currChampionNameAndType = new JPanel();
        currChampionNameAndType.setLayout(new FlowLayout());
        currChampionNameAndType.setPreferredSize(new Dimension(300, 96));
        currChampionNameAndType.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
        currChampionNameAndType.setBackground(Color.BLACK);
        
        //JLabel currChampionIcon = new JLabel();
        //currChampionIcon.setPreferredSize(new Dimension(30,30));
        //currChampionIcon.setIcon(currChampionImageIcon);

        JLabel currChampionName = new JLabel("  Current Champ: " + game.getCurrentChampion().getName());
        currChampionName.setPreferredSize(new Dimension(290, 40));
        currChampionName.setFont(customFont16px);
        currChampionName.setForeground(Color.lightGray);

        JLabel currChampionType = new JLabel("  Type: " + game.getCurrentChampion().getClass().getSimpleName());
        currChampionType.setPreferredSize(new Dimension(290, 16));
        currChampionType.setFont(customFont16px);
        currChampionType.setForeground(Color.lightGray);

        JLabel currChampionHP = new JLabel("  HP: " + game.getCurrentChampion().getCurrentHP() + "/" + game.getCurrentChampion().getMaxHP());
        currChampionHP.setPreferredSize(new Dimension(300, 48));
        currChampionHP.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
        currChampionHP.setFont(customFont16px);
        currChampionHP.setForeground(Color.lightGray);

        JLabel currChampionMana = new JLabel("  Mana: " + game.getCurrentChampion().getMana());
        currChampionMana.setPreferredSize(new Dimension(300, 48));
        currChampionMana.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
        currChampionMana.setFont(customFont16px);
        currChampionMana.setForeground(Color.lightGray);

        JLabel currChampionActionPoints = new JLabel("  Action Points: " + game.getCurrentChampion().getCurrentActionPoints() + "/" + game.getCurrentChampion().getMaxActionPointsPerTurn());
        currChampionActionPoints.setPreferredSize(new Dimension(300, 48));
        currChampionActionPoints.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
        currChampionActionPoints.setFont(customFont16px);
        currChampionActionPoints.setForeground(Color.lightGray);

        JPanel currChampionAttackDamageAndRange = new JPanel();
        currChampionAttackDamageAndRange.setLayout(new FlowLayout());
        currChampionAttackDamageAndRange.setPreferredSize(new Dimension(300, 96));
        currChampionAttackDamageAndRange.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
        currChampionAttackDamageAndRange.setBackground(Color.BLACK);

        JLabel currChampionAttackDamage = new JLabel("  Attack Damage: " + game.getCurrentChampion().getAttackDamage());
        currChampionAttackDamage.setPreferredSize(new Dimension(290, 40));
        currChampionAttackDamage.setFont(customFont16px);
        currChampionAttackDamage.setForeground(Color.lightGray);

        JLabel currChampionAttackRange = new JLabel("  Attack Range: " + game.getCurrentChampion().getAttackRange());
        currChampionAttackRange.setPreferredSize(new Dimension(290, 16));
        currChampionAttackRange.setFont(customFont16px);
        currChampionAttackRange.setForeground(Color.lightGray);

        rightMenu.add(currChampionNameAndType);
        currChampionNameAndType.add(currChampionName);
        //currChampionNameAndType.add(currChampionIcon);
        currChampionNameAndType.add(currChampionType);
        rightMenu.add(currChampionHP);
        rightMenu.add(currChampionMana);
        rightMenu.add(currChampionActionPoints);
        rightMenu.add(currChampionAttackDamageAndRange);
        currChampionAttackDamageAndRange.add(currChampionAttackDamage);
        currChampionAttackDamageAndRange.add(currChampionAttackRange);

        JPanel actionButtons = new JPanel();
        actionButtons.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 18));
        actionButtons.setPreferredSize(new Dimension(300, 1600));
        actionButtons.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
        actionButtons.setBackground(Color.BLACK);

        JLabel moveLabel = new JLabel("   Move: ");
        moveLabel.setPreferredSize(new Dimension(300, 16));
        moveLabel.setFont(customFont16px);
        moveLabel.setForeground(Color.lightGray);
        moveLabel.setHorizontalAlignment(JLabel.CENTER);

        moveUp = new JButton();
        moveUp.addActionListener(this);
        moveUp.setIcon(new ImageIcon("Up.png"));
        moveUp.setPreferredSize(new Dimension(35, 41));

        moveDown = new JButton();
        moveDown.addActionListener(this);
        moveDown.setIcon(new ImageIcon("Down.png"));
        moveDown.setPreferredSize(new Dimension(35, 41));

        moveRight = new JButton();
        moveRight.addActionListener(this);
        moveRight.setIcon(new ImageIcon("Right.png"));
        moveRight.setPreferredSize(new Dimension(35, 41));

        moveLeft = new JButton();
        moveLeft.addActionListener(this);
        moveLeft.setIcon(new ImageIcon("Left.png"));
        moveLeft.setPreferredSize(new Dimension(35, 41));

        JLabel attackLabel = new JLabel("   Attack: ");
        attackLabel.setPreferredSize(new Dimension(300, 16));
        attackLabel.setFont(customFont16px);
        attackLabel.setForeground(Color.lightGray);
        attackLabel.setHorizontalAlignment(JLabel.CENTER);

        attackUp = new JButton();
        attackUp.addActionListener(this);
        attackUp.setIcon(new ImageIcon("Up.png"));
        attackUp.setPreferredSize(new Dimension(35, 41));

        attackDown = new JButton();
        attackDown.addActionListener(this);
        attackDown.setIcon(new ImageIcon("Down.png"));
        attackDown.setPreferredSize(new Dimension(35, 41));

        attackRight = new JButton();
        attackRight.addActionListener(this);
        attackRight.setIcon(new ImageIcon("Right.png"));
        attackRight.setPreferredSize(new Dimension(35, 41));

        attackLeft = new JButton();
        attackLeft.addActionListener(this);
        attackLeft.setIcon(new ImageIcon("Left.png"));
        attackLeft.setPreferredSize(new Dimension(35, 41));

        JLabel emptySpace1 = new JLabel();
        emptySpace1.setPreferredSize(new Dimension(300, 90));

        JLabel emptySpace2 = new JLabel();
        emptySpace2.setPreferredSize(new Dimension(300, 18));

        rightMenu.add(actionButtons);
        actionButtons.add(moveLabel);
        actionButtons.add(moveUp);
        actionButtons.add(moveDown);
        actionButtons.add(moveRight);
        actionButtons.add(moveLeft);
        actionButtons.add(attackLabel);
        actionButtons.add(attackUp);
        actionButtons.add(attackDown);
        actionButtons.add(attackRight);
        actionButtons.add(attackLeft);
        actionButtons.add(emptySpace2);


        useLeaderAbility = new JButton("Use Leader Ability");
        useLeaderAbility.setPreferredSize(new Dimension(160, 41));
        useLeaderAbility.setIcon(new ImageIcon("Button160px.png"));
        useLeaderAbility.setHorizontalTextPosition(JButton.CENTER);
        useLeaderAbility.setFont(customFont14px);
        useLeaderAbility.setForeground(Color.WHITE);
        useLeaderAbility.addActionListener(this);

        Ability abOne = game.getCurrentChampion().getAbilities().get(0);
        abilityOne = new JButton("Ability: " + abOne.getName());
        if (abOne.getCurrentCooldown() != 0)
            abilityOne.setText(abilityOne.getText() + " (" + abOne.getCurrentCooldown() + " turns)");
        abilityOne.setPreferredSize(new Dimension(240, 41));
        abilityOne.setToolTipText("<html>" + "Ability Name: " + abOne.getName() + "<br>" + "Ability Type: " + abOne.getClass().getSimpleName() + " <br> AOE: " + abOne.getCastArea().name() + " <br> Cast range: " + abOne.getCastRange() + " <br> Mana cost: " + abOne.getManaCost() + "<br> Action Costs: " + abOne.getRequiredActionPoints() + "<br> Base cooldown: " + abOne.getBaseCooldown());
        abilityOne.setIcon(new ImageIcon("Button240px.png"));
        abilityOne.setHorizontalTextPosition(JButton.CENTER);
        abilityOne.setFont(customFont14px);
        abilityOne.setForeground(Color.WHITE);
        
        if (abOne.getClass() == DamagingAbility.class)
            abilityOne.setToolTipText(abilityOne.getToolTipText() + " <br> Damage amount: " + ((DamagingAbility) abOne).getDamageAmount() + "</html>");
        else if (abOne.getClass() == HealingAbility.class)
            abilityOne.setToolTipText(abilityOne.getToolTipText() + " <br> Heal amount: " + ((HealingAbility) abOne).getHealAmount() + "</html>");
        else if (abOne.getClass() == CrowdControlAbility.class)
            abilityOne.setToolTipText(abilityOne.getToolTipText() + " <br> Effect name: " + ((CrowdControlAbility) abOne).getEffect().getName() + " Effect duration: " + ((CrowdControlAbility) abOne).getEffect().getDuration() + "</html>");
        abilityOne.addActionListener(this);

        Ability abTwo = game.getCurrentChampion().getAbilities().get(1);
        abilityTwo = new JButton("Ability: " + abTwo.getName());
        if (abTwo.getCurrentCooldown() != 0)
            abilityTwo.setText(abilityTwo.getText() + " (" + abTwo.getCurrentCooldown() + " turns)");
        abilityTwo.setPreferredSize(new Dimension(240, 41));
        abilityTwo.setToolTipText("<html>" + "Ability Name: " + abTwo.getName() + "<br>" + "Ability Type: " + abTwo.getClass().getSimpleName() + " <br> AOE: " + abTwo.getCastArea().name() + " <br> Cast range: " + abTwo.getCastRange() + " <br> Mana cost: " + abTwo.getManaCost() + "<br> Action Costs: " + abTwo.getRequiredActionPoints() + "<br> Base cooldown: " + abTwo.getBaseCooldown());
        abilityTwo.setIcon(new ImageIcon("Button240px.png"));
        abilityTwo.setHorizontalTextPosition(JButton.CENTER);
        abilityTwo.setFont(customFont14px);
        abilityTwo.setForeground(Color.WHITE);
        
        if (abTwo.getClass() == DamagingAbility.class)
            abilityTwo.setToolTipText(abilityTwo.getToolTipText() + " <br> Damage amount: " + ((DamagingAbility) abTwo).getDamageAmount() + "</html>");
        else if (abTwo.getClass() == HealingAbility.class)
            abilityTwo.setToolTipText(abilityTwo.getToolTipText() + " <br> Heal amount: " + ((HealingAbility) abTwo).getHealAmount() + "</html>");
        else if (abTwo.getClass() == CrowdControlAbility.class)
            abilityTwo.setToolTipText(abilityTwo.getToolTipText() + " <br> Effect name: " + ((CrowdControlAbility) abTwo).getEffect().getName() + " Effect duration: " + ((CrowdControlAbility) abTwo).getEffect().getDuration() + "</html>");
        abilityTwo.addActionListener(this);

        Ability abThree = game.getCurrentChampion().getAbilities().get(2);
        abilityThree = new JButton("Ability: " + abThree.getName());
        if (abThree.getCurrentCooldown() != 0)
            abilityThree.setText(abilityThree.getText() + " (" + abThree.getCurrentCooldown() + " turns)");
        abilityThree.setPreferredSize(new Dimension(240, 41));
        abilityThree.setToolTipText("<html>" + "Ability Name: " + abThree.getName() + "<br>" + "Ability Type: " + abThree.getClass().getSimpleName() + " <br> AOE: " + abThree.getCastArea().name() + " <br> Cast range: " + abThree.getCastRange() + " <br> Mana cost: " + abThree.getManaCost() + "<br> Action Costs: " + abThree.getRequiredActionPoints() + "<br> Base cooldown: " + abThree.getBaseCooldown());
        abilityThree.setIcon(new ImageIcon("Button240px.png"));
        abilityThree.setHorizontalTextPosition(JButton.CENTER);
        abilityThree.setFont(customFont14px);
        abilityThree.setForeground(Color.WHITE);
        
        if (abThree.getClass() == DamagingAbility.class)
            abilityThree.setToolTipText(abilityThree.getToolTipText() + " <br> Damage amount: " + ((DamagingAbility) abThree).getDamageAmount() + "</html>");
        else if (abThree.getClass() == HealingAbility.class)
            abilityThree.setToolTipText(abilityThree.getToolTipText() + " <br> Heal amount: " + ((HealingAbility) abThree).getHealAmount() + "</html>");
        else if (abThree.getClass() == CrowdControlAbility.class)
            abilityThree.setToolTipText(abilityThree.getToolTipText() + " <br> Effect name: " + ((CrowdControlAbility) abThree).getEffect().getName() + " Effect duration: " + ((CrowdControlAbility) abThree).getEffect().getDuration() + "</html>");
        abilityThree.addActionListener(this);

        endTurn = new JButton("<html><p style=\"text-align:center\">End <br> This Turn</p></html>");
        endTurn.setPreferredSize(new Dimension(160, 80));
        endTurn.setForeground(Color.RED);
        endTurn.setFont(customFont16px);
        endTurn.addActionListener(this);
        endTurn.setIcon(new ImageIcon("Button160x80px.png"));
        endTurn.setHorizontalTextPosition(JButton.CENTER);
        endTurn.setForeground(Color.WHITE);

        actionButtons.add(useLeaderAbility);
        actionButtons.add(abilityOne);
        actionButtons.add(abilityTwo);
        actionButtons.add(abilityThree);
        actionButtons.add(endTurn);

        this.revalidate();
    }

    //test main method
    public static void main(String[] args) throws IOException {
        Game.loadAbilities("Abilities.csv");
        Game.loadChampions("Champions.csv");

        //creates player one with their name and adds random champions to their team
        Player playerOne = new Player("Player One");
        playerOne.getTeam().add(Game.getAvailableChampions().get(0));
        playerOne.getTeam().add(Game.getAvailableChampions().get(1));
        playerOne.getTeam().add(Game.getAvailableChampions().get(2));

        //creates player two with their name and adds random champions to their team
        Player playerTwo = new Player("Player Two");
        playerTwo.getTeam().add(Game.getAvailableChampions().get(3));
        playerTwo.getTeam().add(Game.getAvailableChampions().get(4));
        playerTwo.getTeam().add(Game.getAvailableChampions().get(5));

        //creates a new game with the two created players
        Game gameTest = new Game(playerOne, playerTwo);

        //launches a new window with the game
        new GameGUI(gameTest);
    }
}
