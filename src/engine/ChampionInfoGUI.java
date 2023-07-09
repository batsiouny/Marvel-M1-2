package engine;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;

import javax.swing.*;

import model.abilities.*;
import model.world.Champion;

//work in progress
public class ChampionInfoGUI extends JFrame {

    public ChampionInfoGUI(Champion champion) {
        this.setTitle(champion.getName());
        this.setResizable(false);
        this.setSize(500, 650);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.addFocusListener(new FocusListener() {
            boolean gained = false;

            public void focusGained(FocusEvent e) {
                gained = true;


            }

            @Override
            public void focusLost(FocusEvent e) {
                gained = false;

            }
        });
        this.setLayout(new FlowLayout());
        JPanel midPanel = new JPanel();
        midPanel.setPreferredSize(new Dimension(250, 700));

        JLabel championInfo1 = new JLabel("<html> Maximum health points: " + champion.getMaxHP() + "</html>");
        championInfo1.setFont(new Font(null, 0, 12));
        championInfo1.setPreferredSize(new Dimension(200, 14));

        JLabel championInfo2 = new JLabel("<html> Mana: " + champion.getMana() + "</html>");
        championInfo2.setFont(new Font(null, 0, 12));
        championInfo2.setPreferredSize(new Dimension(200, 14));

        JLabel championInfo3 = new JLabel("<html> Maximum action points: " + champion.getMaxActionPointsPerTurn() + "</html>");
        championInfo3.setFont(new Font(null, 0, 12));
        championInfo3.setPreferredSize(new Dimension(200, 14));

        JLabel championInfo4 = new JLabel("<html> Attack range: " + champion.getAttackRange() + "</html>");
        championInfo4.setFont(new Font(null, 0, 12));
        championInfo4.setPreferredSize(new Dimension(200, 14));

        JLabel championInfo5 = new JLabel("<html> Attack damage: " + champion.getAttackDamage() + "</html>");
        championInfo5.setFont(new Font(null, 0, 12));
        championInfo5.setPreferredSize(new Dimension(200, 14));

        JLabel championInfo6 = new JLabel("<html> Speed: " + champion.getSpeed() + "</html>");
        championInfo6.setFont(new Font(null, 0, 12));


        Ability abilityOne = champion.getAbilities().get(0);
        championInfo6.setPreferredSize(new Dimension(200, 14));
        JLabel championAbilityOne = new JLabel("<html>" + "Ability Name: " + abilityOne.getName() + "<br>" + "Ability Type: " + abilityOne.getClass().getSimpleName() + " <br> AOE: " + abilityOne.getCastArea().name() + " <br> Cast range: " + abilityOne.getCastRange() + " <br> Mana cost: " + abilityOne.getManaCost() + "<br> Action Costs: " + abilityOne.getRequiredActionPoints() + "<br> Base cooldown: " + abilityOne.getBaseCooldown());
        championAbilityOne.setFont(new Font(null, 0, 12));
        championAbilityOne.setPreferredSize(new Dimension(200, 170));

        if (abilityOne.getClass() == DamagingAbility.class)
            championAbilityOne.setText(championAbilityOne.getText() + " <br> Damage amount: " + ((DamagingAbility) abilityOne).getDamageAmount() + "</html>");
        else if (abilityOne.getClass() == HealingAbility.class)
            championAbilityOne.setText(championAbilityOne.getText() + " <br> Heal amount: " + ((HealingAbility) abilityOne).getHealAmount() + "</html>");
        else if (abilityOne.getClass() == CrowdControlAbility.class)
            championAbilityOne.setText(championAbilityOne.getText() + " <br> Effect name: " + ((CrowdControlAbility) abilityOne).getEffect().getName() + ", Effect duration: " + ((CrowdControlAbility) abilityOne).getEffect().getDuration() + "</html>");

        Ability abilityTwo = champion.getAbilities().get(1);
        JLabel championAbilityTwo = new JLabel("<html>" + "Ability Name: " + abilityTwo.getName() + "<br>" + "Ability Type: " + abilityTwo.getClass().getSimpleName() + " <br> AOE: " + abilityTwo.getCastArea().name() + " <br> Cast range: " + abilityTwo.getCastRange() + " <br> Mana cost: " + abilityTwo.getManaCost() + "<br> Action Costs: " + abilityTwo.getRequiredActionPoints() + "<br> Base cooldown: " + abilityTwo.getBaseCooldown());
        championAbilityTwo.setFont(new Font(null, 0, 13));
        championAbilityTwo.setPreferredSize(new Dimension(200, 170));

        if (abilityTwo.getClass() == DamagingAbility.class)
            championAbilityTwo.setText(championAbilityTwo.getText() + " <br> Damage amount: " + ((DamagingAbility) abilityTwo).getDamageAmount() + "</html>");
        else if (abilityTwo.getClass() == HealingAbility.class)
            championAbilityTwo.setText(championAbilityTwo.getText() + " <br> Heal amount: " + ((HealingAbility) abilityTwo).getHealAmount() + "</html>");
        else if (abilityTwo.getClass() == CrowdControlAbility.class)
            championAbilityTwo.setText(championAbilityTwo.getText() + " <br> Effect name: " + ((CrowdControlAbility) abilityTwo).getEffect().getName() + " Effect duration: " + ((CrowdControlAbility) abilityTwo).getEffect().getDuration() + "</html>");

        Ability abilityThree = champion.getAbilities().get(2);
        JLabel championAbilityThree = new JLabel("<html>" + "Ability Name: " + abilityThree.getName() + "<br>" + "Ability Type: " + abilityThree.getClass().getSimpleName() + " <br> AOE: " + abilityThree.getCastArea().name() + " <br> Cast range: " + abilityThree.getCastRange() + " <br> Mana cost: " + abilityThree.getManaCost() + "<br> Action Costs: " + abilityThree.getRequiredActionPoints() + "<br> Base cooldown: " + abilityThree.getBaseCooldown());
        championAbilityThree.setFont(new Font(null, 0, 13));
        championAbilityThree.setPreferredSize(new Dimension(200, 170));

        if (abilityThree.getClass() == DamagingAbility.class)
            championAbilityThree.setText(championAbilityThree.getText() + " <br> Damage amount: " + ((DamagingAbility) abilityThree).getDamageAmount() + "</html>");
        else if (abilityThree.getClass() == HealingAbility.class)
            championAbilityThree.setText(championAbilityThree.getText() + " <br> Heal amount: " + ((HealingAbility) abilityThree).getHealAmount() + "</html>");
        else if (abilityThree.getClass() == CrowdControlAbility.class)
            championAbilityThree.setText(championAbilityThree.getText() + " <br> Effect name: " + ((CrowdControlAbility) abilityThree).getEffect().getName() + " Effect duration: " + ((CrowdControlAbility) abilityThree).getEffect().getDuration() + "</html>");


        midPanel.add(championInfo1, 0);
        midPanel.add(championInfo2, 1);
        midPanel.add(championInfo3, 2);
        midPanel.add(championInfo4, 3);
        midPanel.add(championInfo5, 4);
        midPanel.add(championInfo6, 5);
        midPanel.add(championAbilityOne, 6);
        midPanel.add(championAbilityTwo, 7);
        midPanel.add(championAbilityThree, 8);
        this.add(midPanel);


        this.setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        Game.loadAbilities("Abilities.csv");
        Game.loadChampions("Champions.csv");
        Champion champion = Game.getAvailableChampions().get(0);
        new ChampionInfoGUI(champion);
    }
}
