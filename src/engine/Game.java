package engine;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import exceptions.*;
import model.abilities.*;
import model.effects.*;
import model.world.*;

public class Game {
    private static ArrayList<Champion> availableChampions = new ArrayList<Champion>();
    private static ArrayList<Ability> availableAbilities = new ArrayList<Ability>();
    private Player firstPlayer;
    private Player secondPlayer;
    private Object[][] board;
    private PriorityQueue turnOrder;
    private boolean firstLeaderAbilityUsed;
    private boolean secondLeaderAbilityUsed;
    private final static int BOARDWIDTH = 5;
    private final static int BOARDHEIGHT = 5;

    public Game(Player first, Player second) {
        firstPlayer = first;

        secondPlayer = second;
        availableChampions = new ArrayList<Champion>();
        availableAbilities = new ArrayList<Ability>();
        board = new Object[BOARDWIDTH][BOARDHEIGHT];
        turnOrder = new PriorityQueue(6);
        placeChampions();
        placeCovers();
        prepareChampionTurns();
    }

    public static void loadAbilities(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line = br.readLine();
        while (line != null) {
            String[] content = line.split(",");
            Ability a = null;
            AreaOfEffect ar = null;
            switch (content[5]) {
                case "SINGLETARGET":
                    ar = AreaOfEffect.SINGLETARGET;
                    break;
                case "TEAMTARGET":
                    ar = AreaOfEffect.TEAMTARGET;
                    break;
                case "SURROUND":
                    ar = AreaOfEffect.SURROUND;
                    break;
                case "DIRECTIONAL":
                    ar = AreaOfEffect.DIRECTIONAL;
                    break;
                case "SELFTARGET":
                    ar = AreaOfEffect.SELFTARGET;
                    break;

            }
            Effect e = null;
            if (content[0].equals("CC")) {
                switch (content[7]) {
                    case "Disarm":
                        e = new Disarm(Integer.parseInt(content[8]));
                        break;
                    case "Dodge":
                        e = new Dodge(Integer.parseInt(content[8]));
                        break;
                    case "Embrace":
                        e = new Embrace(Integer.parseInt(content[8]));
                        break;
                    case "PowerUp":
                        e = new PowerUp(Integer.parseInt(content[8]));
                        break;
                    case "Root":
                        e = new Root(Integer.parseInt(content[8]));
                        break;
                    case "Shield":
                        e = new Shield(Integer.parseInt(content[8]));
                        break;
                    case "Shock":
                        e = new Shock(Integer.parseInt(content[8]));
                        break;
                    case "Silence":
                        e = new Silence(Integer.parseInt(content[8]));
                        break;
                    case "SpeedUp":
                        e = new SpeedUp(Integer.parseInt(content[8]));
                        break;
                    case "Stun":
                        e = new Stun(Integer.parseInt(content[8]));
                        break;
                }
            }
            switch (content[0]) {
                case "CC":
                    a = new CrowdControlAbility(content[1], Integer.parseInt(content[2]), Integer.parseInt(content[4]),
                            Integer.parseInt(content[3]), ar, Integer.parseInt(content[6]), e);
                    break;
                case "DMG":
                    a = new DamagingAbility(content[1], Integer.parseInt(content[2]), Integer.parseInt(content[4]),
                            Integer.parseInt(content[3]), ar, Integer.parseInt(content[6]), Integer.parseInt(content[7]));
                    break;
                case "HEL":
                    a = new HealingAbility(content[1], Integer.parseInt(content[2]), Integer.parseInt(content[4]),
                            Integer.parseInt(content[3]), ar, Integer.parseInt(content[6]), Integer.parseInt(content[7]));
                    break;
            }
            availableAbilities.add(a);
            line = br.readLine();
        }
        br.close();
    }

    public static void loadChampions(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line = br.readLine();
        while (line != null) {
            String[] content = line.split(",");
            Champion c = null;
            switch (content[0]) {
                case "A":
                    c = new AntiHero(content[1], Integer.parseInt(content[2]), Integer.parseInt(content[3]),
                            Integer.parseInt(content[4]), Integer.parseInt(content[5]), Integer.parseInt(content[6]),
                            Integer.parseInt(content[7]));
                    break;

                case "H":
                    c = new Hero(content[1], Integer.parseInt(content[2]), Integer.parseInt(content[3]),
                            Integer.parseInt(content[4]), Integer.parseInt(content[5]), Integer.parseInt(content[6]),
                            Integer.parseInt(content[7]));
                    break;
                case "V":
                    c = new Villain(content[1], Integer.parseInt(content[2]), Integer.parseInt(content[3]),
                            Integer.parseInt(content[4]), Integer.parseInt(content[5]), Integer.parseInt(content[6]),
                            Integer.parseInt(content[7]));
                    break;
            }

            c.getAbilities().add(findAbilityByName(content[8]));
            c.getAbilities().add(findAbilityByName(content[9]));
            c.getAbilities().add(findAbilityByName(content[10]));
            availableChampions.add(c);
            line = br.readLine();
        }
        br.close();
    }

    private static Ability findAbilityByName(String name) {
        for (Ability a : availableAbilities) {
            if (a.getName().equals(name))
                return a;
        }
        return null;
    }

    public void placeCovers() {
        int i = 0;
        while (i < 5) {
            int x = ((int) (Math.random() * (BOARDWIDTH - 2))) + 1;
            int y = (int) (Math.random() * BOARDHEIGHT);

            if (board[x][y] == null) {
                board[x][y] = new Cover(x, y);
                i++;
            }
        }

    }

    public void placeChampions() {
        int i = 1;
        for (Champion c : firstPlayer.getTeam()) {
            board[0][i] = c;
            c.setLocation(new Point(0, i));
            i++;
        }
        i = 1;
        for (Champion c : secondPlayer.getTeam()) {
            board[BOARDHEIGHT - 1][i] = c;
            c.setLocation(new Point(BOARDHEIGHT - 1, i));
            i++;
        }

    }

    public static ArrayList<Champion> getAvailableChampions() {
        return availableChampions;
    }

    public static ArrayList<Ability> getAvailableAbilities() {
        return availableAbilities;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }

    public Object[][] getBoard() {
        return board;
    }

    public PriorityQueue getTurnOrder() {
        return turnOrder;
    }

    public boolean isFirstLeaderAbilityUsed() {
        return firstLeaderAbilityUsed;
    }

    public boolean isSecondLeaderAbilityUsed() {
        return secondLeaderAbilityUsed;
    }

    public static int getBoardwidth() {
        return BOARDWIDTH;
    }

    public static int getBoardheight() {
        return BOARDHEIGHT;
    }

    public Champion getCurrentChampion() {
        if (turnOrder.isEmpty())
            prepareChampionTurns();
        return (Champion) (turnOrder.peekMin());
    }

    public boolean checkLeader(Champion c) {
        if (firstPlayer.getLeader() == c)
            return true;
        else if (secondPlayer.getLeader() == c)
            return true;
        else
            return false;

    }

    public Player checkGameOver() {
        ArrayList<Champion> firstTeam = firstPlayer.getTeam();
        ArrayList<Champion> secondTeam = secondPlayer.getTeam();
        int sum1 = 0;
        int sum2 = 0;
        for (int i = 0; i < firstTeam.size(); i++) {
            sum1 += firstTeam.get(i).getCurrentHP();
        }
        for (int i = 0; i < secondTeam.size(); i++) {
            sum2 += secondTeam.get(i).getCurrentHP();
        }

        if (sum1 == 0)
            return secondPlayer;
        else if (sum2 == 0)
            return firstPlayer;
        else
            return null;
    }

    public void move(Direction d) throws UnallowedMovementException, NotEnoughResourcesException {
        Champion c = getCurrentChampion();
        if (c.getCurrentActionPoints() == 0)
            throw new NotEnoughResourcesException("This champion does not have enough action points");

        else if (c.getCondition() == Condition.INACTIVE || c.getCondition() == Condition.ROOTED)
            throw new UnallowedMovementException("This champion is rooted");

        if (d == Direction.UP) {
            if (c.getLocation().x == 4)
                throw new UnallowedMovementException("You can't move outside the board");
            else if (board[c.getLocation().x + 1][c.getLocation().y] != null) {
                throw new UnallowedMovementException("That tile is not empty");
            }
            board[c.getLocation().x][c.getLocation().y] = null;
            c.setLocation(new Point(c.getLocation().x + 1, c.getLocation().y));
            board[c.getLocation().x][c.getLocation().y] = c;
        } else if (d == Direction.DOWN) {
            if (c.getLocation().x == 0)
                throw new UnallowedMovementException("You can't move outside the board");
            else if (board[c.getLocation().x - 1][c.getLocation().y] != null) {
                throw new UnallowedMovementException("That tile is not empty");
            }
            board[c.getLocation().x][c.getLocation().y] = null;
            c.setLocation(new Point(c.getLocation().x - 1, c.getLocation().y));
            board[c.getLocation().x][c.getLocation().y] = c;
        } else if (d == Direction.RIGHT) {
            if (c.getLocation().y == 4)
                throw new UnallowedMovementException("You can't move outside the board");
            else if (board[c.getLocation().x][c.getLocation().y + 1] != null) {
                throw new UnallowedMovementException("That tile is not empty");
            }
            board[c.getLocation().x][c.getLocation().y] = null;
            c.setLocation(new Point(c.getLocation().x, c.getLocation().y + 1));
            board[c.getLocation().x][c.getLocation().y] = c;
        } else if (d == Direction.LEFT) {
            if (c.getLocation().y == 0)
                throw new UnallowedMovementException("You can't move outside the board");
            else if (board[c.getLocation().x][c.getLocation().y - 1] != null) {
                throw new UnallowedMovementException("That tile is not empty");
            }
            board[c.getLocation().x][c.getLocation().y] = null;
            c.setLocation(new Point(c.getLocation().x, c.getLocation().y - 1));
            board[c.getLocation().x][c.getLocation().y] = c;
        }
        c.setCurrentActionPoints(c.getCurrentActionPoints() - 1);

    }

    //checks if two champions are allied or not
    public boolean isAlly(Champion c1, Champion c2) {
        if (firstPlayer.getTeam().contains(c1) && firstPlayer.getTeam().contains(c2))
            return true;
        if (secondPlayer.getTeam().contains(c1) && secondPlayer.getTeam().contains(c2))
            return true;
        return false;
    }

    //sets a champion as knocked out and removes them from the board
    public void kill(Damageable d) {
        d.setCurrentHP(0);
        board[d.getLocation().x][d.getLocation().y] = null;
        if (d.getClass() != Cover.class) {
            Champion c = (Champion) d;
            c.setCondition(Condition.KNOCKEDOUT);
            getAllyTeam(c).remove(c);
            ArrayList<Champion> stack = new ArrayList<Champion>();
            while (!turnOrder.isEmpty()) {
                Champion current = (Champion) turnOrder.remove();
                if (!current.equals(c))
                    stack.add(current);
            }
            for (int i = 0; i < stack.size(); i++) {
                turnOrder.insert(stack.get(i));
            }
        }
    }

    //damage the champion or cover according to the attacker's type
    public void damage(Champion c, Damageable d) {
        if (c.getClass() == Hero.class && (d.getClass() == Villain.class || d.getClass() == AntiHero.class))
            d.setCurrentHP(d.getCurrentHP() - (int) (c.getAttackDamage() * 1.5));
        else if (c.getClass() == Villain.class && (d.getClass() == Hero.class || d.getClass() == AntiHero.class))
            d.setCurrentHP(d.getCurrentHP() - (int) (c.getAttackDamage() * 1.5));
        else if (c.getClass() == AntiHero.class && (d.getClass() == Hero.class || d.getClass() == Villain.class))
            d.setCurrentHP(d.getCurrentHP() - (int) (c.getAttackDamage() * 1.5));
        else
            d.setCurrentHP(d.getCurrentHP() - c.getAttackDamage());
    }

    //
    public ArrayList<Champion> getAllyTeam(Champion c) {
        if (firstPlayer.getTeam().contains(c))
            return firstPlayer.getTeam();
        else
            return secondPlayer.getTeam();
    }

    public ArrayList<Champion> getEnemyTeam(Champion c) {
        if (firstPlayer.getTeam().contains(c))
            return secondPlayer.getTeam();
        else
            return firstPlayer.getTeam();
    }

    public boolean checkDodge(Champion c) {
        for (int i = 0; i < c.getAppliedEffects().size(); i++) {
            if (c.getAppliedEffects().get(i).getClass() == Dodge.class)
                return true;
        }
        return false;
    }

    //
    public boolean checkShield(Champion c) {
        for (int i = 0; i < c.getAppliedEffects().size(); i++) {
            if (c.getAppliedEffects().get(i).getClass() == Shield.class)
                return true;
        }
        return false;
    }

    //
    public void removeShield(Champion c) {
        for (int i = 0; i < c.getAppliedEffects().size(); i++) {
            if (c.getAppliedEffects().get(i).getClass() == Shield.class) {
                c.getAppliedEffects().get(i).remove(c);
                c.getAppliedEffects().remove(i);
                break;
            }
        }
    }

    public void attack(Direction d) throws InvalidTargetException, NotEnoughResourcesException, ChampionDisarmedException {
        Champion c = getCurrentChampion();
        for (int i = 0; i < c.getAppliedEffects().size(); i++) {
            if (c.getAppliedEffects().get(i).getClass() == Disarm.class)
                throw new ChampionDisarmedException();
        }
        if (c.getCurrentActionPoints() < 2)
            throw new NotEnoughResourcesException("This champion does not have enough action points");

        if (d == Direction.UP) {
            int x = c.getLocation().x + 1;
            for (int i = 0; i < c.getAttackRange() && x >= 0 && x <= 4; i++) {
                Damageable target = (Damageable) board[x][c.getLocation().y];

                if (target != null) {

                    if (target.getClass() != Cover.class && isAlly(c, (Champion) target))
                        throw new InvalidTargetException("That target is an ally");
                    if (target.getClass() != Cover.class && checkShield((Champion) target)) {
                        removeShield((Champion) target);
                        break;
                    }
                    if (target.getClass() != Cover.class && checkDodge((Champion) target)) {
                        int rng = (int) ((Math.random() * 2));
                        if (rng == 1)
                            damage(c, target);
                    } else
                        damage(c, target);
                    if (target.getCurrentHP() == 0)
                        kill(target);
                    break;

                }
                x++;
            }
        } else if (d == Direction.DOWN) {
            int x = c.getLocation().x - 1;
            for (int i = 0; i < c.getAttackRange() && x >= 0 && x <= 4; i++) {
                Damageable target = (Damageable) board[x][c.getLocation().y];

                if (target != null) {

                    if (target.getClass() != Cover.class && isAlly(c, (Champion) target))
                        throw new InvalidTargetException("That target is an ally");
                    if (target.getClass() != Cover.class && checkShield((Champion) target)) {
                        removeShield((Champion) target);
                        break;
                    }
                    if (target.getClass() != Cover.class && checkDodge((Champion) target)) {
                        int rng = (int) ((Math.random() * 2));
                        if (rng == 1)
                            damage(c, target);
                    } else
                        damage(c, target);
                    if (target.getCurrentHP() == 0)
                        kill(target);
                    break;

                }
                x--;
            }
        } else if (d == Direction.RIGHT) {
            int y = c.getLocation().y + 1;
            for (int i = 0; i < c.getAttackRange() && y >= 0 && y <= 4; i++) {
                Damageable target = (Damageable) board[c.getLocation().x][y];

                if (target != null) {

                    if (target.getClass() != Cover.class && isAlly(c, (Champion) target))
                        throw new InvalidTargetException("That target is an ally");
                    if (target.getClass() != Cover.class && checkShield((Champion) target)) {
                        removeShield((Champion) target);
                        break;
                    }
                    if (target.getClass() != Cover.class && checkDodge((Champion) target)) {
                        int rng = (int) ((Math.random() * 2));
                        if (rng == 1)
                            damage(c, target);
                    } else
                        damage(c, target);
                    if (target.getCurrentHP() == 0)
                        kill(target);
                    break;

                }
                y++;
            }
        } else if (d == Direction.LEFT) {
            int y = c.getLocation().y - 1;
            for (int i = 0; i < c.getAttackRange() && y >= 0 && y <= 4; i++) {
                Damageable target = (Damageable) board[c.getLocation().x][y];

                if (target != null) {

                    if (target.getClass() != Cover.class && isAlly(c, (Champion) target)) {
                    }
                    //throw new InvalidTargetException("That target is an ally");
                    if (target.getClass() != Cover.class && checkShield((Champion) target)) {
                        removeShield((Champion) target);
                        break;
                    }
                    if (target.getClass() != Cover.class && checkDodge((Champion) target)) {
                        int rng = (int) ((Math.random() * 2));
                        if (rng == 1)
                            damage(c, target);
                    } else
                        damage(c, target);
                    if (target.getCurrentHP() == 0)
                        kill(target);
                    break;

                }
                y--;
            }
        }
        c.setCurrentActionPoints(c.getCurrentActionPoints() - 2);
    }

    public int manhattan(Champion c1, Damageable c2) {
        return Math.abs(c1.getLocation().x - c2.getLocation().x) + Math.abs(c1.getLocation().y - c2.getLocation().y);
    }

    public void castAbility(Ability a) throws AbilityUseException, NotEnoughResourcesException, CloneNotSupportedException {
        Champion c = getCurrentChampion();
        ArrayList<Damageable> targets = new ArrayList<Damageable>();
        for (int i = 0; i < c.getAppliedEffects().size(); i++) {
            if (c.getAppliedEffects().get(i).getClass() == Silence.class)
                throw new AbilityUseException("This champion is silenced");
        }
        if (a.getCurrentCooldown() > 0)
            throw new AbilityUseException("This ability is on cooldown");
        if (c.getMana() < a.getManaCost())
            throw new NotEnoughResourcesException("This champion does not have enough mana");
        if (c.getCurrentActionPoints() < a.getRequiredActionPoints())
            throw new NotEnoughResourcesException("This champion does not have enough action points");


        if (a.getCastArea() == AreaOfEffect.SELFTARGET) {
            targets.add(c);
        } else if (a.getCastArea() == AreaOfEffect.TEAMTARGET) {
            if (a.getClass() == DamagingAbility.class) {
                for (int i = 0; i < getEnemyTeam(c).size(); i++) {
                    Champion target = getEnemyTeam(c).get(i);
                    if (manhattan(c, target) <= a.getCastRange())
                        if (checkShield(getEnemyTeam(c).get(i))) {
                            removeShield(getEnemyTeam(c).get(i));
                        } else
                            targets.add(getEnemyTeam(c).get(i));
                }
            } else if (a.getClass() == HealingAbility.class) {
                for (int i = 0; i < getAllyTeam(c).size(); i++) {
                    Champion target = getAllyTeam(c).get(i);
                    if (manhattan(c, target) <= a.getCastRange())
                        targets.add(getAllyTeam(c).get(i));
                }
            } else if (a.getClass() == CrowdControlAbility.class) {
                CrowdControlAbility cc = (CrowdControlAbility) a;
                if (cc.getEffect().getType() == EffectType.BUFF) {
                    for (int i = 0; i < getAllyTeam(c).size(); i++) {
                        Champion target = getAllyTeam(c).get(i);
                        if (manhattan(c, target) <= a.getCastRange())
                            targets.add(getAllyTeam(c).get(i));
                    }
                } else if (cc.getEffect().getType() == EffectType.DEBUFF) {
                    for (int i = 0; i < getEnemyTeam(c).size(); i++) {
                        Champion target = getEnemyTeam(c).get(i);
                        if (manhattan(c, target) <= a.getCastRange())
                            targets.add(getEnemyTeam(c).get(i));
                    }
                }
            }
        } else if (a.getCastArea() == AreaOfEffect.SURROUND) {
            ArrayList<Point> directions = new ArrayList<Point>();
            if ((c.getLocation().x + 1) <= 4) {
                directions.add(new Point(c.getLocation().x + 1, c.getLocation().y));
                if ((c.getLocation().y + 1 <= 4))
                    directions.add(new Point(c.getLocation().x + 1, c.getLocation().y + 1));
                if ((c.getLocation().y - 1 >= 0))
                    directions.add(new Point(c.getLocation().x + 1, c.getLocation().y - 1));
            }
            if ((c.getLocation().x - 1) >= 0) {
                directions.add(new Point(c.getLocation().x - 1, c.getLocation().y));
                if ((c.getLocation().y + 1 <= 4))
                    directions.add(new Point(c.getLocation().x - 1, c.getLocation().y + 1));
                if ((c.getLocation().y - 1 >= 0))
                    directions.add(new Point(c.getLocation().x - 1, c.getLocation().y - 1));
            }
            if ((c.getLocation().y + 1) <= 4)
                directions.add(new Point(c.getLocation().x, c.getLocation().y + 1));
            if ((c.getLocation().y - 1) >= 0)
                directions.add(new Point(c.getLocation().x, c.getLocation().y - 1));

            if (a.getClass() == DamagingAbility.class) {
                for (int i = 0; i < directions.size(); i++) {
                    Damageable d = (Damageable) board[directions.get(i).x][directions.get(i).y];
                    if (d == null) {
                    } else if (d.getClass() != Cover.class && !isAlly(c, (Champion) d)) {
                        if (checkShield((Champion) d)) {
                            removeShield((Champion) d);
                        } else
                            targets.add(d);
                    } else if (d.getClass() == Cover.class) {
                        targets.add(d);
                    }
                }

            }

            if (a.getClass() == HealingAbility.class) {
                for (int i = 0; i < directions.size(); i++) {
                    Damageable d = (Damageable) board[directions.get(i).x][directions.get(i).y];
                    if (d == null) {
                    } else if (d.getClass() != Cover.class && isAlly(c, (Champion) d)) {
                        targets.add(d);
                    }
                }

            }
            if (a.getClass() == CrowdControlAbility.class) {
                CrowdControlAbility cc = (CrowdControlAbility) a;
                if (cc.getEffect().getType() == EffectType.BUFF) {
                    for (int i = 0; i < directions.size(); i++) {
                        Damageable d = (Damageable) board[directions.get(i).x][directions.get(i).y];
                        if (d == null) {
                        } else if (d.getClass() != Cover.class && isAlly(c, (Champion) d)) {
                            targets.add(d);
                        }
                    }
                }
                if (cc.getEffect().getType() == EffectType.DEBUFF) {
                    for (int i = 0; i < directions.size(); i++) {
                        Damageable d = (Damageable) board[directions.get(i).x][directions.get(i).y];
                        if (d == null) {
                        } else if (d.getClass() != Cover.class && !isAlly(c, (Champion) d)) {
                            targets.add(d);
                        }
                    }
                }
            }
        }
        a.execute(targets);
        c.setCurrentActionPoints(c.getCurrentActionPoints() - a.getRequiredActionPoints());
        c.setMana(c.getMana() - a.getManaCost());
        a.setCurrentCooldown(a.getBaseCooldown());
        for (int i = 0; i < targets.size(); i++) {
            if (targets.get(i).getCurrentHP() == 0) {
                kill(targets.get(i));
            }
        }
    }

    public void castAbility(Ability a, Direction d) throws NotEnoughResourcesException, AbilityUseException, CloneNotSupportedException {
        Champion c = getCurrentChampion();
        ArrayList<Damageable> targets = new ArrayList<Damageable>();
        for (int i = 0; i < c.getAppliedEffects().size(); i++) {
            if (c.getAppliedEffects().get(i).getClass() == Silence.class)
                throw new AbilityUseException("This champion is silenced");
        }
        if (a.getCurrentCooldown() > 0)
            throw new AbilityUseException("This ability is on cooldown");
        if (c.getMana() < a.getManaCost())
            throw new NotEnoughResourcesException("This champion does not have enough mana");
        if (c.getCurrentActionPoints() < a.getRequiredActionPoints())
            throw new NotEnoughResourcesException("This champion does not have enough action points");

        if (a.getClass() == DamagingAbility.class) {
            if (d == Direction.UP) {
                int x = c.getLocation().x + 1;
                for (int i = 0; i < a.getCastRange() && x >= 0 && x <= 4; i++) {
                    Damageable target = (Damageable) board[x][c.getLocation().y];

                    if (target != null) {
                        if (target.getClass() != Cover.class && !isAlly(c, (Champion) target))
                            if (checkShield((Champion) target)) {
                                removeShield((Champion) target);
                            } else
                                targets.add(target);
                        else if (target.getClass() == Cover.class)
                            targets.add(target);
                    }
                    x++;
                }
            }
            if (d == Direction.DOWN) {
                int x = c.getLocation().x - 1;
                for (int i = 0; i < a.getCastRange() && x >= 0 && x <= 4; i++) {
                    Damageable target = (Damageable) board[x][c.getLocation().y];

                    if (target != null) {
                        if (target.getClass() != Cover.class && !isAlly(c, (Champion) target))
                            if (checkShield((Champion) target)) {
                                removeShield((Champion) target);
                            } else
                                targets.add(target);
                        else if (target.getClass() == Cover.class)
                            targets.add(target);
                    }
                    x--;
                }
            }
            if (d == Direction.RIGHT) {
                int y = c.getLocation().y + 1;
                for (int i = 0; i < a.getCastRange() && y >= 0 && y <= 4; i++) {
                    Damageable target = (Damageable) board[c.getLocation().x][y];

                    if (target != null) {
                        if (target.getClass() != Cover.class && !isAlly(c, (Champion) target))
                            if (checkShield((Champion) target)) {
                                removeShield((Champion) target);
                            } else
                                targets.add(target);
                        else if (target.getClass() == Cover.class)
                            targets.add(target);
                    }
                    y++;
                }
            }
            if (d == Direction.LEFT) {
                int y = c.getLocation().y - 1;
                for (int i = 0; i < a.getCastRange() && y >= 0 && y <= 4; i++) {
                    Damageable target = (Damageable) board[c.getLocation().x][y];

                    if (target != null) {
                        if (target.getClass() != Cover.class && !isAlly(c, (Champion) target))
                            if (checkShield((Champion) target)) {
                                removeShield((Champion) target);
                            } else
                                targets.add(target);
                        else if (target.getClass() == Cover.class)
                            targets.add(target);
                    }
                    y--;
                }
            }
        }
        if (a.getClass() == HealingAbility.class) {
            if (d == Direction.UP) {
                int x = c.getLocation().x + 1;
                for (int i = 0; i < a.getCastRange() && x >= 0 && x <= 4; i++) {
                    Damageable target = (Damageable) board[x][c.getLocation().y];

                    if (target != null) {
                        if (target.getClass() != Cover.class && isAlly(c, (Champion) target))
                            targets.add(target);
                    }
                    x++;
                }
            }
            if (d == Direction.DOWN) {
                int x = c.getLocation().x - 1;
                for (int i = 0; i < a.getCastRange() && x >= 0 && x <= 4; i++) {
                    Damageable target = (Damageable) board[x][c.getLocation().y];

                    if (target != null) {
                        if (target.getClass() != Cover.class && isAlly(c, (Champion) target))
                            targets.add(target);
                    }
                    x--;
                }
            }
            if (d == Direction.RIGHT) {
                int y = c.getLocation().y + 1;
                for (int i = 0; i < a.getCastRange() && y >= 0 && y <= 4; i++) {
                    Damageable target = (Damageable) board[c.getLocation().x][y];

                    if (target != null) {
                        if (target.getClass() != Cover.class && isAlly(c, (Champion) target))
                            targets.add(target);
                    }
                    y++;
                }
            }
            if (d == Direction.LEFT) {
                int y = c.getLocation().y - 1;
                for (int i = 0; i < a.getCastRange() && y >= 0 && y <= 4; i++) {
                    Damageable target = (Damageable) board[c.getLocation().x][y];

                    if (target != null) {
                        if (target.getClass() != Cover.class && isAlly(c, (Champion) target))
                            targets.add(target);
                    }
                    y--;
                }
            }
        }
        if (a.getClass() == CrowdControlAbility.class) {
            CrowdControlAbility cc = (CrowdControlAbility) a;
            boolean bool = false;

            if (d == Direction.UP) {
                int x = c.getLocation().x + 1;
                for (int i = 0; i < a.getCastRange() && x >= 0 && x <= 4; i++) {
                    Damageable target = (Damageable) board[x][c.getLocation().y];
                    if (target != null) {
                        if (target.getClass() != Cover.class && cc.getEffect().getType() == EffectType.BUFF)
                            bool = isAlly(c, (Champion) target);
                        else if (target.getClass() != Cover.class && cc.getEffect().getType() == EffectType.DEBUFF)
                            bool = !isAlly(c, (Champion) target);

                        if (target.getClass() != Cover.class && bool)
                            targets.add(target);
                    }
                    x++;
                }
            }
            if (d == Direction.DOWN) {
                int x = c.getLocation().x - 1;
                for (int i = 0; i < a.getCastRange() && x >= 0 && x <= 4; i++) {
                    Damageable target = (Damageable) board[x][c.getLocation().y];
                    if (target != null) {
                        if (target.getClass() != Cover.class && cc.getEffect().getType() == EffectType.BUFF)
                            bool = isAlly(c, (Champion) target);
                        else if (target.getClass() != Cover.class && cc.getEffect().getType() == EffectType.DEBUFF)
                            bool = !isAlly(c, (Champion) target);

                        if (target.getClass() != Cover.class && bool)
                            targets.add(target);
                    }
                    x--;
                }
            }
            if (d == Direction.RIGHT) {
                int y = c.getLocation().y + 1;
                for (int i = 0; i < a.getCastRange() && y >= 0 && y <= 4; i++) {
                    Damageable target = (Damageable) board[c.getLocation().x][y];
                    if (target != null) {
                        if (target.getClass() != Cover.class && cc.getEffect().getType() == EffectType.BUFF)
                            bool = isAlly(c, (Champion) target);
                        else if (target.getClass() != Cover.class && cc.getEffect().getType() == EffectType.DEBUFF)
                            bool = !isAlly(c, (Champion) target);
                        if (target.getClass() != Cover.class && bool)
                            targets.add(target);
                    }
                    y++;
                }
            }
            if (d == Direction.LEFT) {
                int y = c.getLocation().y - 1;
                for (int i = 0; i < a.getCastRange() && y >= 0 && y <= 4; i++) {
                    Damageable target = (Damageable) board[c.getLocation().x][y];
                    if (target != null) {
                        if (target.getClass() != Cover.class && cc.getEffect().getType() == EffectType.BUFF)
                            bool = isAlly(c, (Champion) target);
                        else if (target.getClass() != Cover.class && cc.getEffect().getType() == EffectType.DEBUFF)
                            bool = !isAlly(c, (Champion) target);

                        if (target.getClass() != Cover.class && bool)
                            targets.add(target);
                    }
                    y--;
                }
            }
        }

        a.execute(targets);
        c.setCurrentActionPoints(c.getCurrentActionPoints() - a.getRequiredActionPoints());
        c.setMana(c.getMana() - a.getManaCost());
        a.setCurrentCooldown(a.getBaseCooldown());
        for (int i = 0; i < targets.size(); i++) {
            if (targets.get(i).getCurrentHP() == 0) {
                kill(targets.get(i));
            }
        }
    }

    public void castAbility(Ability a, int x, int y) throws NotEnoughResourcesException, AbilityUseException, InvalidTargetException, CloneNotSupportedException {
        Champion c = getCurrentChampion();
        ArrayList<Damageable> targets = new ArrayList<Damageable>();
        for (int i = 0; i < c.getAppliedEffects().size(); i++) {
            if (c.getAppliedEffects().get(i).getClass() == Silence.class)
                throw new AbilityUseException("This champion is silenced");
        }
        if (a.getCurrentCooldown() > 0)
            throw new AbilityUseException("This ability is on cooldown");
        if (x > 4 || x < 0 || y > 4 || y < 0)
            throw new InvalidTargetException("You can't use this outside the board");
        if (c.getMana() < a.getManaCost())
            throw new NotEnoughResourcesException("This champion does not have enough mana");
        if (c.getCurrentActionPoints() < a.getRequiredActionPoints())
            throw new NotEnoughResourcesException("This champion does not have enough action points");
        if (board[x][y] == null)
            throw new InvalidTargetException("You can't use this on an empty cell");


        Damageable d = (Damageable) board[x][y];
        if (manhattan(c, d) > a.getCastRange())
            throw new AbilityUseException("The target is outside of the ability's cast range");
        if (a.getClass() == DamagingAbility.class) {
            if (d.getClass() != Cover.class && !isAlly(c, (Champion) d))
                if (checkShield((Champion) d)) {
                    removeShield((Champion) d);
                } else
                    targets.add(d);
            else if (d.getClass() == Cover.class)
                targets.add(d);
            else
                throw new InvalidTargetException("That target is an ally");
        } else if (a.getClass() == HealingAbility.class) {
            if (d.getClass() != Cover.class && isAlly(c, (Champion) d))
                targets.add(d);
            else
                throw new InvalidTargetException("That target is an enemy or cover");
        } else if (a.getClass() == CrowdControlAbility.class) {
            CrowdControlAbility cc = (CrowdControlAbility) a;
            if (cc.getEffect().getType() == EffectType.BUFF) {
                if (d.getClass() != Cover.class && isAlly(c, (Champion) d))
                    targets.add(d);
                else
                    throw new InvalidTargetException("That target is an enemy or cover");
            } else {
                if (d.getClass() != Cover.class && !isAlly(c, (Champion) d))
                    targets.add(d);
                else
                    throw new InvalidTargetException("That target is an ally or cover");
            }

        }

        a.execute(targets);
        c.setCurrentActionPoints(c.getCurrentActionPoints() - a.getRequiredActionPoints());
        c.setMana(c.getMana() - a.getManaCost());
        a.setCurrentCooldown(a.getBaseCooldown());
        for (int i = 0; i < targets.size(); i++) {
            if (targets.get(i).getCurrentHP() == 0) {
                kill(targets.get(i));
            }
        }
    }

    public void useLeaderAbility() throws AbilityUseException, LeaderAbilityAlreadyUsedException, LeaderNotCurrentException, CloneNotSupportedException {

        Champion c = getCurrentChampion();
        ArrayList<Champion> targets = new ArrayList<Champion>();
        if (firstPlayer.getTeam().contains(c)) {
            if (!firstPlayer.getLeader().equals(c))
                throw new LeaderNotCurrentException("Current champion is not the leader");
            if (firstLeaderAbilityUsed == true)
                throw new LeaderAbilityAlreadyUsedException("The leader ability is already used");
            firstLeaderAbilityUsed = true;
        } else if (secondPlayer.getTeam().contains(c)) {
            if (!secondPlayer.getLeader().equals(c))
                throw new LeaderNotCurrentException("Current champion is not the leader");
            if (secondLeaderAbilityUsed == true)
                throw new LeaderAbilityAlreadyUsedException("The leader ability is already used");
            secondLeaderAbilityUsed = true;
        }
        if (c.getClass() == Hero.class) {
            for (int i = 0; i < getAllyTeam(c).size(); i++) {
                if (getAllyTeam(c).get(i).getCondition() != Condition.KNOCKEDOUT)
                    targets.add(getAllyTeam(c).get(i));
            }
        } else if (c.getClass() == Villain.class) {
            for (int i = 0; i < getEnemyTeam(c).size(); i++) {
                if (getEnemyTeam(c).get(i).getCondition() != Condition.KNOCKEDOUT)
                    targets.add(getEnemyTeam(c).get(i));
            }
        } else if (c.getClass() == AntiHero.class) {
            for (int i = 0; i < getAllyTeam(c).size(); i++) {
                if (getAllyTeam(c).get(i).getCondition() != Condition.KNOCKEDOUT) {
                    if (!firstPlayer.getLeader().equals(getAllyTeam(c).get(i)) && !secondPlayer.getLeader().equals(getAllyTeam(c).get(i)))
                        targets.add(getAllyTeam(c).get(i));
                }
            }
            for (int i = 0; i < getEnemyTeam(c).size(); i++) {
                if (getEnemyTeam(c).get(i).getCondition() != Condition.KNOCKEDOUT) {
                    if (!firstPlayer.getLeader().equals(getAllyTeam(c).get(i)) && !secondPlayer.getLeader().equals(getAllyTeam(c).get(i)))
                        targets.add(getEnemyTeam(c).get(i));
                }
            }
        }
        c.useLeaderAbility(targets);
        for (int i = 0; i < targets.size(); i++) {
            if (targets.get(i).getCurrentHP() == 0) {
                kill(targets.get(i));
            }
        }
    }

    public void updateCooldowns(Champion c) {
        ArrayList<Effect> effects = c.getAppliedEffects();
        ArrayList<Ability> abilities = c.getAbilities();
        for (int i = 0; i < effects.size(); i++) {
            effects.get(i).setDuration(effects.get(i).getDuration() - 1);
            if (effects.get(i).getDuration() <= 0) {
                Effect e = effects.remove(i);
                e.remove(c);
            }

        }
        for (int i = 0; i < abilities.size(); i++) {
            abilities.get(i).setCurrentCooldown(abilities.get(i).getCurrentCooldown() - 1);
        }
        c.setCurrentActionPoints(c.getMaxActionPointsPerTurn());
    }

    public void endTurn() {
        Champion c = (Champion) turnOrder.remove();
        if (turnOrder.size() == 0)
            prepareChampionTurns();
        while (((Champion) turnOrder.peekMin()).getCondition() == Condition.INACTIVE) {
            c = (Champion) turnOrder.remove();
            if (turnOrder.size() == 0)
                prepareChampionTurns();
            updateCooldowns(c);
        }
        if (turnOrder.size() == 0)
            prepareChampionTurns();
        Champion current = getCurrentChampion();
        updateCooldowns(current);

    }

    private void prepareChampionTurns() {
        for (int i = 0; i < firstPlayer.getTeam().size(); i++) {
            if (firstPlayer.getTeam().get(i).getCondition() != Condition.KNOCKEDOUT) {
                turnOrder.insert(firstPlayer.getTeam().get(i));
            }
        }
        for (int i = 0; i < secondPlayer.getTeam().size(); i++) {
            if (secondPlayer.getTeam().get(i).getCondition() != Condition.KNOCKEDOUT) {
                turnOrder.insert(secondPlayer.getTeam().get(i));
            }
        }
    }

    public static void main(String[] args) {
        Game game = new Game(new Player("p1"), new Player("p2"));
        Hero c1 = new Hero("c1", 0, 0, 0, 0, 0, 0);
        AntiHero c2 = new AntiHero("c2", 0, 0, 0, 0, 0, 0);
        Villain c3 = new Villain("c3", 0, 0, 0, 0, 0, 0);
        game.turnOrder.insert(c1);
        game.turnOrder.insert(c2);
        game.turnOrder.insert(c3);
        while (!game.turnOrder.isEmpty())
            System.out.println(game.turnOrder.remove());
        game.turnOrder.insert(c1);
        game.turnOrder.insert(c2);
        game.turnOrder.insert(c3);
        ArrayList<Champion> stack = new ArrayList<Champion>();
        while (!game.turnOrder.isEmpty()) {
            Champion current = (Champion) (game.turnOrder.remove());
            if (!current.equals(c3))
                stack.add(current);
        }
        for (int i = 0; i < stack.size(); i++) {
            game.turnOrder.insert(stack.get(i));
        }
        while (!game.turnOrder.isEmpty())
            System.out.println(game.turnOrder.remove());
    }

}
