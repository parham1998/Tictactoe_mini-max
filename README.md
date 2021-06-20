# Tictactoe_alphabeta (warm-up project!)
Implementation of Tic tac toe game using mini-max algorithm and alpha-beta pruning with JavaFX

There are 2 java files in src/tictactoe_alphabeta. actually mini-max algorithm with alpha-beta pruning implemented in board.java.
you play as X and the computer plays as O, in fact, after your turn, the computer will consider all possible scenarios and makes the most optimal move.

    mini-max algorithm tries to find the best move in every step by evaluates all the available moves. (see image below)
    
    alpha-Beta pruning is not actually a new algorithm, rather an optimization technique for minimax algorithm. 
    It reduces the computation time by a huge factor. This allows us to search much faster and even go into deeper levels in the game tree. 
    It cuts off branches in the game tree which need not be searched because there already exists a better move available. 
    It is called Alpha-Beta pruning because it passes 2 extra parameters in the minimax function, namely alpha and beta.
    
    Alpha is the best value that the maximizer currently can guarantee at that level or above. 
    Beta is the best value that the minimizer currently can guarantee at that level or above.

![mini-max](https://user-images.githubusercontent.com/85555218/122682694-41ca0380-d210-11eb-91ba-16ec0c983268.jpg)

I considered two modes, easy and hard! (to make the game easy, I reduced the depth of the mini-max tree to 1, and in the hard scenario, I considered all possible depths of the mini-max tree)

the following animation shows the performance of the mini-max algorithm in the tic tac toe game:
![tic-tac-toe](https://user-images.githubusercontent.com/85555218/122684629-b6ef0600-d21b-11eb-8857-c51abea60894.gif)
