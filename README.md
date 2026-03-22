Worked alongside Max Dustin to create a two-player tank game with the following features:
- timer
- bullet physics
- interactive enviormment
- movement physics

Two players are placed in set positions on the map, with a timer counting down and a health bar. If one player shoots the other, 
the score shifts accordingly. The game ends when either one player's score (or the timer) reaches zero.

This game utilizes many features of Object Oriented Programming, including inheritance, overloading, and overriding. 

Collision between objects is the primary focus of this game, and is handled in Tank.java.
- **tank** and **bullet** objects, opposing bullet object hitting tank resets map and timer, and updates score
-  **tank** and **block** objects, detects every few pixels of a block's border and disallows tank movement if collision detected.
Able to happen because movement is kept to boolean 'allowMovemenet'. Paired with another feature, that moves the tank backwards
if movement does try to occur if collision detected, to prevent getting stuck in object.
- **bullet** and **block** objects, if collision between bullet and block, removes the bullet object entirely.


