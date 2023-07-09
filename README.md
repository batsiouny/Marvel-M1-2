Marvel: Ultimate War
====================
Marvel: Ultimate War is a 2 player battle game that I've made to test what I've learned in object-oriented programming using JavaFX. 

Each player picks 3 champions to form his team and fight the other player’s team.

The players take turns to fight the other player’s champions.

The turns will keep going back and forth until a player is able to defeat all of the other player’s champions, which will make them the winner of the battle.

During the battle, each player will use their champions to attack the opponent's champions either by using normal attacks or using special attacks/abilities.

The battle takes place on a 5x5 grid. Each cell in the grid can either be empty or contain a champion or obstacle/cover.

At the beginning of the battle, each team will stand at one of the sides/edges of the grid as a starting position.



Champions
=========
Champions are the fighters that each player will form his team. Each champion will have a certain type which influences how the champion deals damage to other types as well as how much damage it will receive from them. The available types are: 

- **Heroes**: deal extra damage when attacking villains.
- **Villains**: deal extra damage when attacking heroes.
- **Anti-Heroes**: when being attacked or attacking a hero or villain, the antihero will always act as the opposite type. If attacking an antihero, damage is calculated normally.



Gameplay Flow
=============
Each player will select his three champions to form his team. The champions will take turns based on their speed. 

The champion with the highest speed (from all selected champions) will begin acting first, followed by the champion with the second highest speed, and so on. 

When the turn goes to a champion, the player controlling the champion can use them to carry out any action as long as the champion has enough action points needed for the action and also enough mana in case of using any of their abilities. 

After that, the champion can end their turn, and the turn will go to the next champion.

The turns will keep passing over the living champions until a player is able to defeat all three champions of the opposing player.

In this case, the game ends, and the player with the remaining living champion will be declared the winner.
