package edu.iu.c212.places.games.blackjack;
import edu.iu.c212.Arcade;
import edu.iu.c212.models.User;
import edu.iu.c212.places.games.Game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

public class BlackjackGame extends Game {
    private static JFrame frame;
    private static JLabel totalsLabel;
    private static JLabel dealerLabel;
    private static JButton hit;
    private static JButton stay;
    private static JLabel result;
    static BlackjackPlayer player;
    static BlackjackDealer dealer;
    public static boolean isRunning;
    public User user;


    public BlackjackGame(Arcade arcade) {

        setArcade(arcade);
        setEntryFee(10);
        setPlaceName("Blackjack");
    }

    public void bust() {

    }



    public void onEnter(User user) {
        player = new BlackjackPlayer();
        dealer = new BlackjackDealer();
        this.user = user;


        frame = new JFrame();
        frame.setTitle("BlackJack");
        frame.setSize(420, 420);
        JPanel mainPanel = new JPanel();
        JPanel statusPanel = new JPanel();

        totalsLabel = new JLabel("Your hand total: " + player.getBestTotal());
        statusPanel.add(totalsLabel);
        JPanel buttonsPanel = new JPanel();
        dealerLabel = new JLabel("Dealer's hand: " + dealer.getPartialHand());

        hit = new JButton("Hit!");
        stay = new JButton("stay");
        result = new JLabel("");
        buttonsPanel.add(hit);
        buttonsPanel.add(stay);
        statusPanel.add(dealerLabel);
        mainPanel.add(statusPanel);
        mainPanel.add(buttonsPanel);
        statusPanel.add(result);
        hit.addActionListener(new hitButtonListener());
        stay.addActionListener(new stayButtonListener());
        frame.addWindowListener(new WindowClosedListener() {});


        frame.add(mainPanel);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.toFront();

        isRunning = true;


    }
    public class WindowClosedListener implements WindowListener {

        @Override
        public void windowOpened(WindowEvent e) {}

        @Override
        public void windowClosing(WindowEvent e) {}

        @Override
        public void windowClosed(WindowEvent e) {
            if (isRunning) {
                System.out.println("================================================");
                System.out.println("\n By exiting mid-game, you've choose to forfeit");
                System.out.println("\n Goodbye!");
                System.out.println("================================================\n");

            }
            else {
                if (player.getBust()) {
                    System.out.println("================================================");
                    System.out.println("\n You lost! Hope to see you again soon! \n");
                    System.out.println("================================================\n");
                }
                else if (player.getBestTotal() > dealer.getBestTotal()) {
                    user.addBalance(50.00);
                    getArcade().saveUsersToFile();
                    System.out.println("================================================");
                    System.out.println("\n Congratulations! you won $50!!");
                    //give player some money
                    System.out.println("\n Hope to see you again soon!");
                    System.out.println("================================================ \n");
                }
                else if (player.getBestTotal() == dealer.getBestTotal()) {
                    System.out.println("================================================");
                    System.out.println("\n Whoops! Nobody won... ");
                    System.out.println("\n Hope to see you again soon!! ");
                    System.out.println("================================================ \n");
                }
                else {
                    System.out.println("================================================\n");
                    System.out.println("\n You lost! Hope to see you again soon!");
                    System.out.println("================================================\n");
                }

                }

            try {
                getArcade().transitionArcadeState("Lobby");
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void windowIconified(WindowEvent e) {}

        @Override
        public void windowDeiconified(WindowEvent e) {}

        @Override
        public void windowActivated(WindowEvent e) {}

        @Override
        public void windowDeactivated(WindowEvent e) {}
    }

    public static class stayButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            dealer.play();
            if (dealer.getBust()) {
                dealerLabel.setText("The Dealer BUSTED!!");
                result.setText("You won!");

            }
            else {
                dealerLabel.setText("Dealer's hand: " + dealer.getBestTotal());
                if (dealer.getBestTotal()> player.getBestTotal()) {
                    result.setText("You Lost!");
                }
                else if (dealer.getBestTotal() == player.getBestTotal()) {
                    result.setText("It's a tie!");
                }
                else {
                    result.setText("You won!");
                }
            }

            hit.setEnabled(false);
            stay.setEnabled(false);
            isRunning = false;
        }
    }

    public static class hitButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            player.hit();
            if (player.getBust()) {
                hit.setEnabled(false);
                stay.setEnabled(false);
                totalsLabel.setText("You BUSTED");
                isRunning = false;
                result.setText("You Lost!");
                dealerLabel.setText("Dealer's hand: " + dealer.getBestTotal());
            } else {
                totalsLabel.setText("Your hand total: " + player.getBestTotal());
            }
        }
    }
}