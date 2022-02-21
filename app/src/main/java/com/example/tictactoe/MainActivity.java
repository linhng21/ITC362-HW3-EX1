package com.example.tictactoe;
//In Version 0 of our TicTacToe app, we use the empty activity template and only setup the GUI.
//We use a 3x3 two-dimensional array of Buttons, in order to mirror the 3x3 to-dimensioanl array game in our Model
//the TicTacToe class.
//In order to keep things simple, we first place the View inside the Activity class,
//so the View and the Controller are in the same class. Later in the chapter,
//we separate the View from the Controller and place them in two different classes

//In Version 1, we add code to capture a click on any button, identify what button was clicked,
// and we place an X inside the button that was clicked.
// At that point, we are not concerned about playing the game or enforcing its rules.
// We will do that in Version 2.

//We are assuming that two users will be playing on the same device against each other.
// Enabling game play does not just mean placing an X or an O on the grid of buttons at each turn.
// It also means enforcing the rules, such as not allowing someone to play twice at the same position on the grid,
// checking if one player won, indicating if the game is over.
// Our Model, the TicTacToe class, provides that functionality.
// In order to enable play, we add a TicTacToe object as an instance variable of our Activity class,
// and we call the methods of the TicTacToe class as and when needed.
// Play is happening inside the update method so we have to modify it.
// We also need to check if the game is over and, in that case, disable all the buttons.

//We want to improve Version 2
// by showing the current state of the game at the bottom of the screen,

//In Version 4, we enable the player to play another game after the current one is over.
// When the game is over, we want a dialog box asking the user if he or she wants to play again to pop up.
// If the answer is yes, he or she can play again.
// If the answer is no,
// we exit the activity (in this case the app since there is only one activity).

//In Version 5, we split the View and the Controller.
// In this way, we make the View reusable.
// The Controller is the middleman between the View and the Model,
// so we keep the View independent from the Model.

import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
//import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
//import android.widget.GridLayout;
//import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TicTacToe game;
    private ButtonGridAndTextView tttView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        game = new TicTacToe();
        //setContentView(R.layout.activity_main);
        Point size = new Point();
        //getWindowManager().getCurrentWindowMetrics().getBounds();
        getDisplay().getRealSize(size);
        int w = size.x / TicTacToe.SIDE;
        ButtonHandler bh = new ButtonHandler();
        tttView = new ButtonGridAndTextView(this, w, TicTacToe.SIDE, bh);
        tttView.setStatusText(game.result());
        setContentView(tttView);

        //buildGUIByCode();

    }

    //public void buildGUIByCode() {

    //get width of the screen
    // Point size = new Point();
    //getWindowManager().getCurrentWindowMetrics().getBounds();
    //int w = size.x / TicTacToe.SIDE;

    public void showNewGameDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("This is fun");
        alert.setMessage("Play again?");
        PlayDialog playAgain = new PlayDialog();
        alert.setPositiveButton("YES", playAgain);
        alert.setNegativeButton("NO", playAgain);
        alert.show();
    }


    private class ButtonHandler implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            for (int row = 0; row < TicTacToe.SIDE; row++)
                for (int column = 0; column < TicTacToe.SIDE; column++)
                    if (tttView.isButton((Button) v, row, column)) {
                        int play = game.play(row, column);
                        if (play == 1)
                            tttView.setButtonText(row, column, "X");
                        else if (play == 2)
                            tttView.setButtonText(row, column, "O");
                        if (game.isGameOver()) {
                            tttView.setStatusBackgroundColor(Color.RED);
                            tttView.enableButtons(false);
                            tttView.setStatusText(game.result());
                            showNewGameDialog();    // offer to play again
                        }
                    }
                }

            }
        


            private class PlayDialog implements DialogInterface.OnClickListener {
                public void onClick(DialogInterface dialog, int id) {
                    if (id == -1) /* YES button */ {
                        game.resetGame();
                        tttView.enableButtons(true);
                        tttView.resetButtons();
                        tttView.setStatusBackgroundColor(Color.GREEN);
                        tttView.setStatusText(game.result());
                    } else if (id == -2) // NO button
                        MainActivity.this.finish();
                }
            }
        }



