package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GameBoard extends AppCompatActivity implements View.OnClickListener{


    //Creating 2D array to represent our board for our buttons
    private Button[][] buttonPos = new Button[3][3];

    //Creating variables for the Textviews
    private TextView p1Txt, p2Txt;

    //Creating variable for resetting the board and new game session button
    private Button resetBoard, newSession;

    //Creating variable to represent the turn of players
    private boolean turnPlayer = true;

    //Creating variable to check the number of rounds have passed, if value of variable comes up to 9 then we declare a draw
    private int numRounds = 0;

    //Creating variable to keep track of player points
    private int p1Points;
    private int p2Points;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_board);

        //linking all our variables to our id's
        p1Txt = findViewById(R.id.p1Txt);
        p2Txt = findViewById(R.id.p2Txt);
        resetBoard = findViewById(R.id.resetBoard);
        resetBoard.setOnClickListener(this);
        newSession = findViewById(R.id.resetGame);
        newSession.setOnClickListener(this);

        //looping through buttons to link them dynamically
        for(int a = 0; a < 3; a++){
            for(int b= 0; b < 3; b++){
                String idButton = "btn_" + a + b;
                int idButtonsInBoard = getResources().getIdentifier(idButton,"id",getPackageName());
                buttonPos[a][b] = findViewById(idButtonsInBoard);
                buttonPos[a][b].setOnClickListener(this);
            }
        }

    }

    @Override
    public void onClick(View view) {
        //checking which button is pressed.
        switch (view.getId()){
            case R.id.resetBoard://resets the board
                bdRes();
                break;
            case R.id.resetGame://resets the game session
                sesRes();
                break;
        }

        //checking if button has already been filled with an X or O
        if(!((Button) view).getText().toString().equals("")){
            return;//if it has been filled we don't do anything
        }

        //if it's player 1's turns we set the button to X else we set button for O
        if(turnPlayer){
            ((Button) view).setText("X");
        }else{
            ((Button) view).setText("O");
        }

        //increments number of rounds
        numRounds++;

        //checking who won the game
        if(gameWinner()){
            //if player 1 wins
            if(turnPlayer){
                p1Points++;//increment player 1 points
                Toast.makeText(this, "Winner is player 1!", Toast.LENGTH_SHORT).show();//show toast message player 1 wins
            }else{//if player 2 wins
                p2Points++;//increments player 2 points
                Toast.makeText(this, "Winner is player 2!", Toast.LENGTH_SHORT).show();//show toast message player 2 wins
            }
            ptDisplay();//displays points
            bdRes();//resets board
        }else if(numRounds == 9){//if the number of rounds go to 9
            Toast.makeText(this, "It's a draw!", Toast.LENGTH_SHORT).show();//shows message that it's a draw
            bdRes();//resets board
        }else{
            turnPlayer = !turnPlayer;//switches player turns
        }

    }

    //checking if there is a winner or not
    private boolean gameWinner(){
        /*saving button in a 2 dimensional string array
         * which basically has the same shape as our buttonPos
         * array.*/
        String[][] val = new String[3][3];
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                val[i][j] = buttonPos[i][j].getText().toString();
            }
        }
        //going through columns to check for winner
        for(int i = 0; i < 3; i++){
            if(val[i][0].equals(val[i][1]) && val[i][0].equals(val[i][2]) && !val[i][0].equals("")){
                return true;
            }
        }

        //going through rows to check for winner
        for(int i = 0; i < 3; i++){
            if(val[0][i].equals(val[1][i]) && val[0][i].equals(val[2][i]) && !val[0][i].equals("")){
                return true;
            }
        }


        //checking diagonals to check for a win
        if(val[0][0].equals(val[1][1]) && val[0][0].equals(val[2][2]) && !val[0][0].equals("")){
            return true;
        }

        if(val[0][2].equals(val[1][1]) && val[0][2].equals(val[2][0]) && !val[0][2].equals("")){
            return true;
        }

        //if no one wins
        return false;
    }

    //displays points
    private void ptDisplay(){
        p1Txt.setText("Player 1: " + p1Points);
        p2Txt.setText("Player 2: " + p2Points);
    }

    //resets board
    private void bdRes(){
        for(int a = 0; a < 3; a++){
            for(int b= 0; b < 3; b++){
                buttonPos[a][b].setText("");
            }
        }
        numRounds = 0;
        turnPlayer = true;
    }

    //resets session
    private void sesRes(){
        p1Points = 0;
        p2Points = 0;
        ptDisplay();
        bdRes();
    }
}