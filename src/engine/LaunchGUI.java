package engine;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.world.Champion;

public class LaunchGUI extends JFrame implements ActionListener {


    JTextField playerOneName;
    JTextField playerTwoName;
    JPanel playerOnePanel;
    JPanel playerTwoPanel;
    JPanel logo;
    JButton nameSubmit1;
    JButton nameSubmit2;
    JLabel textFieldTip1;
    JLabel textFieldTip2;
    ArrayList<JButton> selectionButtons1;
    ArrayList<JButton> selectionButtons2;
    boolean playerNameSubmitted;
    boolean championsSelected1;
    boolean championsSelected2;
    String pOneName = null;
    String pTwoName = null;
    ArrayList<Champion> playerOneChampions;
    ArrayList<Champion> playerTwoChampions;
    JLabel selectionText1 = null;
    JLabel selectionText2 = null;
    JLabel championsSelectedText1 = null;
    JLabel championsSelectedText2 = null;
    JButton selectLeader1 = null;
    JButton selectLeader2 = null;
    JButton selectLeader3 = null;
    JButton selectLeader4 = null;
    JButton selectLeader5 = null;
    JButton selectLeader6 = null;
    Champion playerOneLeader = null;
    Champion playerTwoLeader = null;
    JLabel teamOneLeader = null;
    JLabel teamTwoLeader = null;
    ArrayList<JButton> championInfo1 = new ArrayList<JButton>();
    ArrayList<JButton> championInfo2 = new ArrayList<JButton>();

    public LaunchGUI() throws IOException {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //loads the availableAbilities arraylist in the game class
        Game.loadAbilities("Abilities.csv");
        Game.loadChampions("Champions.csv");


        //initializes the selection buttons for player one and two
        selectionButtons1 = new ArrayList<JButton>();
        selectionButtons2 = new ArrayList<JButton>();

        //player one and two's names
        pOneName = null;
        pTwoName = null;

        //player one and two's champions
        playerOneChampions = new ArrayList<Champion>();
        playerTwoChampions = new ArrayList<Champion>();

        //boolean to check if any of the players submitted their name or not
        playerNameSubmitted = false;

        //boolean to check if any of the players selected all 3 of their champions or not
        championsSelected1 = false;
        championsSelected2 = false;

        //sets title, size and layout of frame
        this.setTitle("Marvel: Ultimate Wars");
        this.setPreferredSize(new Dimension(1600,900));
        this.setResizable(true);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(true);
        this.setLayout(new FlowLayout());


        //program should stop running after window close
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //creates a panel for player one's buttons
        playerOnePanel = new JPanel();
        playerOnePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        playerOnePanel.setPreferredSize(new Dimension(635, 720));
        playerOnePanel.setOpaque(true);

        //creates a panel for player two's buttons
        playerTwoPanel = new JPanel();
        playerTwoPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        playerTwoPanel.setPreferredSize(new Dimension(635, 720));
        playerTwoPanel.setOpaque(true);

        JPanel dividor = new JPanel();
        dividor.setPreferredSize(new Dimension(5, 720));
        //dividor.setOpaque(true);
        //dividor.setBackground(Color.BLACK);
        dividor.setMinimumSize(new Dimension(5, 720));

        //creates a label for the first text field
        textFieldTip1 = new JLabel();
        textFieldTip1.setText("First player name:");
        textFieldTip1.setFont(new Font("Arial", Font.BOLD, 14));
        textFieldTip1.setForeground(Color.RED);

        //creates a text field to enter first player's name
        playerOneName = new JTextField();
        playerOneName.setPreferredSize(new Dimension(300, 40));

        //creates a submission button for first player's name
        nameSubmit1 = new JButton();
        nameSubmit1.setText("Submit");
        nameSubmit1.setPreferredSize(new Dimension(75, 20));
        nameSubmit1.addActionListener(this); //this class should "listen" to this button

        textFieldTip2 = new JLabel();
        textFieldTip2.setText("Second player name:");
        textFieldTip2.setFont(new Font("Arial", Font.BOLD, 14));
        textFieldTip2.setForeground(Color.BLUE);

        playerTwoName = new JTextField();
        playerTwoName.setPreferredSize(new Dimension(300, 40));

        nameSubmit2 = new JButton();
        nameSubmit2.setText("Submit");
        nameSubmit2.setPreferredSize(new Dimension(75, 20));
        nameSubmit2.addActionListener(this);

        this.add(dividor, BorderLayout.CENTER);
        this.add(playerOnePanel, BorderLayout.WEST); //adds player one's panel to the west of the frame
        this.add(playerTwoPanel, BorderLayout.EAST); //adds player two's panel to the east of the frame

        //adds all components of player one's panel to it
        playerOnePanel.add(textFieldTip1);
        playerOnePanel.add(playerOneName);
        playerOnePanel.add(nameSubmit1);

        //adds all components of player two's panel to it
        playerTwoPanel.add(textFieldTip2);
        playerTwoPanel.add(playerTwoName);
        playerTwoPanel.add(nameSubmit2);

        this.setVisible(true); //sets the frame to be visible
    }

    //is called when a button is pressed
    public void actionPerformed(ActionEvent e) {


        //checks if the button pressed is the name submission button
        if (e.getSource() == nameSubmit1) {

            pOneName = playerOneName.getText(); //stores the text inside the text field

            //sets the text field to not be visible (to add more components)
            textFieldTip1.setVisible(false);
            playerOneName.setVisible(false);
            nameSubmit1.setVisible(false);

            //if the selection buttons weren't already prepared, prepare them
            if (playerNameSubmitted == false)
                prepareChampionSelectionButtons();

            //adds the selection buttons to the panel
            selectionText1 = new JLabel("Hello " + pOneName + ", please select your champions");
            selectionText1.setPreferredSize(new Dimension(620, 30));
            selectionText1.setFont(new Font("Arial", Font.BOLD, 14));
            selectionText1.setForeground(Color.RED);
            playerOnePanel.add(selectionText1);
            for (int i = 0; i < selectionButtons1.size(); i++) {

                ImageIcon icon = new ImageIcon(Game.getAvailableChampions().get(i).getName() + "_icon.png");
                JLabel image = new JLabel();
                image.setPreferredSize(new Dimension(30, 30));
                image.setIcon(icon);
                playerOnePanel.add(image);

                playerOnePanel.add(selectionButtons1.get(i));
                playerOnePanel.add(championInfo1.get(i));
            }

            //label to show which champions were selected
            championsSelectedText1 = new JLabel();
            championsSelectedText1.setPreferredSize(new Dimension(620, 30));
            championsSelectedText1.setFont(new Font("Arial", Font.BOLD, 14));
            championsSelectedText1.setForeground(Color.RED);
            championsSelectedText1.setText(pOneName + "'s selected champions: ");
            playerOnePanel.add(championsSelectedText1);

            playerNameSubmitted = true; //the selection buttons are prepared now, so condition is true
        }
        if (e.getSource() == nameSubmit2) {

            pTwoName = playerTwoName.getText();

            textFieldTip2.setVisible(false);
            playerTwoName.setVisible(false);
            nameSubmit2.setVisible(false);

            if (playerNameSubmitted == false)
                prepareChampionSelectionButtons();

            selectionText2 = new JLabel("Hello " + pTwoName + ", please select your champions");
            selectionText2.setPreferredSize(new Dimension(620, 30));
            selectionText2.setFont(new Font("Arial", Font.BOLD, 14));
            selectionText2.setForeground(Color.BLUE);
            playerTwoPanel.add(selectionText2);
            for (int i = 0; i < selectionButtons2.size(); i++) {

                ImageIcon icon = new ImageIcon(Game.getAvailableChampions().get(i).getName() + "_icon.png");
                JLabel image = new JLabel();
                image.setPreferredSize(new Dimension(30, 30));
                image.setIcon(icon);
                playerTwoPanel.add(image);
                playerTwoPanel.add(selectionButtons2.get(i));
                playerTwoPanel.add(championInfo2.get(i));
            }

            championsSelectedText2 = new JLabel();
            championsSelectedText2.setPreferredSize(new Dimension(620, 30));
            championsSelectedText2.setFont(new Font("Arial", Font.BOLD, 14));
            championsSelectedText2.setForeground(Color.BLUE);
            championsSelectedText2.setText(pTwoName + "'s selected champions: ");
            playerTwoPanel.add(championsSelectedText2);

            playerNameSubmitted = true;
        }


        //goes through the selectionButtons1 array, checking if they were pressed
        for (int i = 0; i < selectionButtons1.size(); i++) {
            JButton button = selectionButtons1.get(i);
            if (e.getSource() == button) {

                //if the button was pressed, disable it and its equivalent in the other array
                button.setEnabled(false);
                selectionButtons2.get(i).setEnabled(false);

                //adds the equivalent champion to the playerOneChampions array list
                Champion champion = Game.getAvailableChampions().get(i);
                playerOneChampions.add(champion);

                //shows that the champion was selected
                championsSelectedText1.setText(championsSelectedText1.getText() + champion.getName() + ", ");

                //if the list's size is equal to 3, wait for the other player or start the game
                if (playerOneChampions.size() == 3) {

                    //checks if the other player selected his team yet

                    //sets all selection buttons to be invisible and adds text
                    selectionText1.setVisible(false);

                    for (int j = 0; j < selectionButtons1.size(); j++) {
                        selectionButtons1.get(j).setVisible(false);
                        championInfo1.get(j).setVisible(false);
                    }
                    playerOnePanel.add(new JLabel("Select leader champion"));
                    selectLeader1 = new JButton(playerOneChampions.get(0).getName());
                    selectLeader2 = new JButton(playerOneChampions.get(1).getName());
                    selectLeader3 = new JButton(playerOneChampions.get(2).getName());
                    playerOnePanel.add(selectLeader1);
                    playerOnePanel.add(selectLeader2);
                    playerOnePanel.add(selectLeader3);
                    selectLeader1.addActionListener(this);
                    selectLeader2.addActionListener(this);
                    selectLeader3.addActionListener(this);
                    selectLeader1.setToolTipText("Selects leader");
                    selectLeader2.setToolTipText("Selects leader");
                    selectLeader3.setToolTipText("Selects leader");


                    //player one's champions were selected, so condition is now true
                }
            }
        }
        //goes through the selectionButtons2 array, checking if they were pressed
        for (int i = 0; i < selectionButtons2.size(); i++) {
            JButton button = selectionButtons2.get(i);
            if (e.getSource() == button) {

                //if the button was pressed, disable it and its equivalent in the other array
                button.setEnabled(false);
                selectionButtons1.get(i).setEnabled(false);

                //adds the equivalent champion to the playerOneChampions array list
                Champion champion = Game.getAvailableChampions().get(i);
                playerTwoChampions.add(champion);

                //shows that the champion was selected
                championsSelectedText2.setText(championsSelectedText2.getText() + champion.getName() + ", ");

                //if the list's size is equal to 3, wait for the other player or start the game
                if (playerTwoChampions.size() == 3) {

                    //checks if the other player selected his team yet
                    //sets all selection buttons to be invisible and adds text
                    selectionText2.setVisible(false);
                    //championsSelectedText2.setVisible(false);
                    for (int j = 0; j < selectionButtons2.size(); j++) {
                        selectionButtons2.get(j).setVisible(false);
                        championInfo2.get(j).setVisible(false);
                    }
                    playerTwoPanel.add(new JLabel("Select leader champion"));
                    selectLeader4 = new JButton(playerTwoChampions.get(0).getName());
                    selectLeader5 = new JButton(playerTwoChampions.get(1).getName());
                    selectLeader6 = new JButton(playerTwoChampions.get(2).getName());
                    playerTwoPanel.add(selectLeader4);
                    playerTwoPanel.add(selectLeader5);
                    playerTwoPanel.add(selectLeader6);
                    selectLeader4.addActionListener(this);
                    selectLeader5.addActionListener(this);
                    selectLeader6.addActionListener(this);
                    selectLeader4.setToolTipText("Selects leader");
                    selectLeader5.setToolTipText("Selects leader");
                    selectLeader6.setToolTipText("Selects leader");

                    //player two's champions were selected, so condition is now true
                }
            }
        }
        for (int i = 0; i < championInfo1.size(); i++) {
            if (e.getSource() == championInfo1.get(i)) {
                new ChampionInfoGUI(Game.getAvailableChampions().get(i));
            }
        }
        for (int i = 0; i < championInfo2.size(); i++) {
            if (e.getSource() == championInfo2.get(i)) {
                new ChampionInfoGUI(Game.getAvailableChampions().get(i));
            }
        }
        if (e.getSource() == selectLeader1) {
            selectLeader1.setVisible(false);
            selectLeader2.setVisible(false);
            selectLeader3.setVisible(false);
            playerOneLeader = playerOneChampions.get(0);
            teamOneLeader = new JLabel("Leader: " + playerOneLeader.getName());
            playerOnePanel.add(teamOneLeader);
            championsSelected1 = true;
            if (championsSelected1 && championsSelected2) {

                //creates player one with their name and adds all his champions to their team
                Player playerOne = new Player(pOneName);
                playerOne.getTeam().add(playerOneChampions.get(0));
                playerOne.getTeam().add(playerOneChampions.get(1));
                playerOne.getTeam().add(playerOneChampions.get(2));
                playerOne.setLeader(playerOneLeader);
                
                //creates player two with their name and adds all his champions to their team
                Player playerTwo = new Player(pTwoName);
                playerTwo.getTeam().add(playerTwoChampions.get(0));
                playerTwo.getTeam().add(playerTwoChampions.get(1));
                playerTwo.getTeam().add(playerTwoChampions.get(2));
                playerTwo.setLeader(playerTwoLeader);

                //creates a new game with the two created players
                Game game = new Game(playerOne, playerTwo);

                //launches a new window with the game
                new GameGUI(game);

                //closes this frame
                this.dispose();

            }
        }
        if (e.getSource() == selectLeader2) {
            selectLeader1.setVisible(false);
            selectLeader2.setVisible(false);
            selectLeader3.setVisible(false);
            playerOneLeader = playerOneChampions.get(1);
            teamOneLeader = new JLabel("Leader: " + playerOneLeader.getName());
            playerOnePanel.add(teamOneLeader);
            championsSelected1 = true;
            if (championsSelected1 && championsSelected2) {

                //creates player one with their name and adds all his champions to their team
                Player playerOne = new Player(pOneName);
                playerOne.getTeam().add(playerOneChampions.get(0));
                playerOne.getTeam().add(playerOneChampions.get(1));
                playerOne.getTeam().add(playerOneChampions.get(2));
                playerOne.setLeader(playerOneLeader);

                //creates player two with their name and adds all his champions to their team
                Player playerTwo = new Player(pTwoName);
                playerTwo.getTeam().add(playerTwoChampions.get(0));
                playerTwo.getTeam().add(playerTwoChampions.get(1));
                playerTwo.getTeam().add(playerTwoChampions.get(2));
                playerTwo.setLeader(playerTwoLeader);

                //creates a new game with the two created players
                Game game = new Game(playerOne, playerTwo);

                //launches a new window with the game
                new GameGUI(game);

                //closes this frame
                this.dispose();

            }
        }
        if (e.getSource() == selectLeader3) {
            selectLeader1.setVisible(false);
            selectLeader2.setVisible(false);
            selectLeader3.setVisible(false);
            playerOneLeader = playerOneChampions.get(2);
            teamOneLeader = new JLabel("Leader: " + playerOneLeader.getName());
            playerOnePanel.add(teamOneLeader);
            championsSelected1 = true;
            if (championsSelected1 && championsSelected2) {

                //creates player one with their name and adds all his champions to their team
                Player playerOne = new Player(pOneName);
                playerOne.getTeam().add(playerOneChampions.get(0));
                playerOne.getTeam().add(playerOneChampions.get(1));
                playerOne.getTeam().add(playerOneChampions.get(2));
                playerOne.setLeader(playerOneLeader);

                //creates player two with their name and adds all his champions to their team
                Player playerTwo = new Player(pTwoName);
                playerTwo.getTeam().add(playerTwoChampions.get(0));
                playerTwo.getTeam().add(playerTwoChampions.get(1));
                playerTwo.getTeam().add(playerTwoChampions.get(2));
                playerTwo.setLeader(playerTwoLeader);

                //creates a new game with the two created players
                Game game = new Game(playerOne, playerTwo);

                //launches a new window with the game
                new GameGUI(game);

                //closes this frame
                this.dispose();

            }
        }
        if (e.getSource() == selectLeader4) {
            selectLeader4.setVisible(false);
            selectLeader5.setVisible(false);
            selectLeader6.setVisible(false);
            playerTwoLeader = playerTwoChampions.get(0);
            teamTwoLeader = new JLabel("Leader: " + playerTwoLeader.getName());
            playerTwoPanel.add(teamTwoLeader);
            championsSelected2 = true;
            if (championsSelected1 && championsSelected2) {

                //creates player one with their name and adds all his champions to their team
                Player playerOne = new Player(pOneName);
                playerOne.getTeam().add(playerOneChampions.get(0));
                playerOne.getTeam().add(playerOneChampions.get(1));
                playerOne.getTeam().add(playerOneChampions.get(2));
                playerOne.setLeader(playerOneLeader);

                //creates player two with their name and adds all his champions to their team
                Player playerTwo = new Player(pTwoName);
                playerTwo.getTeam().add(playerTwoChampions.get(0));
                playerTwo.getTeam().add(playerTwoChampions.get(1));
                playerTwo.getTeam().add(playerTwoChampions.get(2));
                playerTwo.setLeader(playerTwoLeader);

                //creates a new game with the two created players
                Game game = new Game(playerOne, playerTwo);

                //launches a new window with the game
                new GameGUI(game);

                //closes this frame
                this.dispose();

            }
        }
        if (e.getSource() == selectLeader5) {
            selectLeader4.setVisible(false);
            selectLeader5.setVisible(false);
            selectLeader6.setVisible(false);
            playerTwoLeader = playerTwoChampions.get(1);
            teamTwoLeader = new JLabel("Leader: " + playerTwoLeader.getName());
            playerTwoPanel.add(teamTwoLeader);
            championsSelected2 = true;
            if (championsSelected1 && championsSelected2) {

                //creates player one with their name and adds all his champions to their team
                Player playerOne = new Player(pOneName);
                playerOne.getTeam().add(playerOneChampions.get(0));
                playerOne.getTeam().add(playerOneChampions.get(1));
                playerOne.getTeam().add(playerOneChampions.get(2));
                playerOne.setLeader(playerOneLeader);

                //creates player two with their name and adds all his champions to their team
                Player playerTwo = new Player(pTwoName);
                playerTwo.getTeam().add(playerTwoChampions.get(0));
                playerTwo.getTeam().add(playerTwoChampions.get(1));
                playerTwo.getTeam().add(playerTwoChampions.get(2));
                playerTwo.setLeader(playerTwoLeader);

                //creates a new game with the two created players
                Game game = new Game(playerOne, playerTwo);

                //launches a new window with the game
                new GameGUI(game);

                //closes this frame
                this.dispose();

            }
        }
        if (e.getSource() == selectLeader6) {
            selectLeader4.setVisible(false);
            selectLeader5.setVisible(false);
            selectLeader6.setVisible(false);
            playerTwoLeader = playerTwoChampions.get(2);
            teamTwoLeader = new JLabel("Leader: " + playerTwoLeader.getName());
            playerTwoPanel.add(teamTwoLeader);
            championsSelected2 = true;
            if (championsSelected1 && championsSelected2) {

                //creates player one with their name and adds all his champions to their team
                Player playerOne = new Player(pOneName);
                playerOne.getTeam().add(playerOneChampions.get(0));
                playerOne.getTeam().add(playerOneChampions.get(1));
                playerOne.getTeam().add(playerOneChampions.get(2));
                playerOne.setLeader(playerOneLeader);

                //creates player two with their name and adds all his champions to their team
                Player playerTwo = new Player(pTwoName);
                playerTwo.getTeam().add(playerTwoChampions.get(0));
                playerTwo.getTeam().add(playerTwoChampions.get(1));
                playerTwo.getTeam().add(playerTwoChampions.get(2));
                playerTwo.setLeader(playerTwoLeader);

                //creates a new game with the two created players
                Game game = new Game(playerOne, playerTwo);

                //launches a new window with the game
                new GameGUI(game);

                //closes this frame
                this.dispose();

            }
        }
        this.revalidate();
    }

    //loads the champion selection buttons in the selectionButtons1 and selectionButtons2 arrays
    public void prepareChampionSelectionButtons() {
        for (int i = 0; i < Game.getAvailableChampions().size(); i++) {

            //gets the champion from the avaialableChampion array list
            Champion champion = Game.getAvailableChampions().get(i);

            //creates a new button for his name
            JButton button1 = new JButton(champion.getName());
            button1.setPreferredSize(new Dimension(490, 30));
            button1.addActionListener(this);

            //the same as before but for player two
            JButton button2 = new JButton(champion.getName());
            button2.setPreferredSize(new Dimension(490, 30));
            button2.addActionListener(this);

            JButton championInfoP1 = new JButton("Info");
            championInfoP1.setPreferredSize(new Dimension(75, 20));
            championInfoP1.addActionListener(this);

            JButton championInfoP2 = new JButton("Info");
            championInfoP2.setPreferredSize(new Dimension(75, 20));
            championInfoP2.addActionListener(this);

            //adds the buttons created to their corresponding arrays
            selectionButtons1.add(button1);
            selectionButtons2.add(button2);
            championInfo1.add(championInfoP1);
            championInfo2.add(championInfoP2);
        }
    }

    public static void main(String[] args) throws IOException {
        new LaunchGUI(); //calls the constructor of the class to open the interface
    }

}
