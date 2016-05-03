package com.forkthecode.piggame;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    static int playerOverallScore = 0;
    static int playerTurnScore = 0;
    static int computerOverallScore = 0;
    static int computerTurnScore = 0;
    static int TIMER = 1500;
    ImageView imageView;
    TextView turnTextView;
    TextView turnTotalTextView;
    TextView overallTotalTextView;
    Button rollButton;
    Button holdButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView)findViewById(R.id.imageView);
        turnTextView = (TextView)findViewById(R.id.turnTextView);
        turnTotalTextView = (TextView)findViewById(R.id.turnTotalTextView);
        overallTotalTextView = (TextView)findViewById(R.id.overallTotalTextView);
        rollButton = (Button)findViewById(R.id.rollButton);
        holdButton = (Button)findViewById(R.id.holdButton);
    }

    public void controlPlayerTurn(int diceRoll){
        if(diceRoll == 1){
            changeToComputerTurn();
            Toast.makeText(this,"You got 1 :(",Toast.LENGTH_SHORT).show();
        }
        else {
            playerTurnScore = playerTurnScore + diceRoll;
            displayTurnTotal(playerTurnScore);
        }
    }

    public void changeToComputerTurn() {
        if(playerOverallScore >= 100){
            Toast.makeText(this,"Yeah!! You Won :)",Toast.LENGTH_SHORT).show();
            reset();
        }
        else {
            playerTurnScore = 0;
            turnTextView.setText("Computer's Turn");
            displayTurnTotal(0);
            displayOverallScore();
            rollButton.setEnabled(false);
            holdButton.setEnabled(false);
            changeImage(0);
            computerTurnScore = 0;
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    computerTurn();
                }
            }, TIMER);
        }

    }
    public void displayOverallScore(){
        overallTotalTextView.setText("Your total : " + playerOverallScore + " Computer's total : " + computerOverallScore);
    }
    public void displayTurnTotal(int turnTotal){
        turnTotalTextView.setText("Turn Total : " + turnTotal);
    }
    public int getRandomDiceRoll(){
        Random random = new Random();
        return random.nextInt(6) + 1;
    }
    public void changeImage(int diceRoll){
        switch (diceRoll){
            case 1:
                imageView.setImageResource(R.drawable.dice1);
                break;
            case 2:
                imageView.setImageResource(R.drawable.dice2);
                break;
            case 3:
                imageView.setImageResource(R.drawable.dice3);
                break;
            case 4:
                imageView.setImageResource(R.drawable.dice4);
                break;
            case 5:
                imageView.setImageResource(R.drawable.dice5);
                break;
            case 6:
                imageView.setImageResource(R.drawable.dice6);
                break;
            default:imageView.setImageResource(R.drawable.defaultimage);
        }
    }

    public void onRollClick(View view){
        int diceRoll = getRandomDiceRoll();
        changeImage(diceRoll);
        controlPlayerTurn(diceRoll);
    }
    public void onHoldClick(View view){
        playerOverallScore = playerOverallScore + playerTurnScore;
        changeToComputerTurn();
   }
    public void computerTurn(){
        if(computerTurnScore <= 20){
            int diceRoll = getRandomDiceRoll();
            changeImage(diceRoll);
            controlComputerTurn(diceRoll);
        }
        else{
            computerOverallScore = computerOverallScore + computerTurnScore;
            changeToPlayerTurn();
        }
    }
    private void controlComputerTurn(int diceRoll) {
        if(diceRoll == 1){
            changeToPlayerTurn();
            Toast.makeText(this,"Computer got 1 :)",Toast.LENGTH_SHORT).show();
        }
        else{
            computerTurnScore = computerTurnScore + diceRoll;
            displayTurnTotal(computerTurnScore);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    computerTurn();
                }
            }, TIMER);
        }
    }
    private void changeToPlayerTurn() {
        if(computerOverallScore >= 100){
            Toast.makeText(this,"Oh No!! Computer won :(. Try Again",Toast.LENGTH_SHORT).show();
            reset();
        }
        else {
            computerTurnScore = 0;
            turnTextView.setText("Your Turn");
            displayTurnTotal(0);
            displayOverallScore();
            changeImage(0);
            rollButton.setEnabled(true);
            holdButton.setEnabled(true);
            playerTurnScore = 0;
        }
    }
    public void onResetClick(View view){
        reset();
    }
    public void reset(){
        computerTurnScore = 0;
        computerOverallScore = 0;
        playerTurnScore = 0;
        playerOverallScore = 0;
        imageView.setImageResource(R.drawable.dice1);
        turnTextView.setText("Your Turn");
        displayOverallScore();
        displayTurnTotal(0);
        rollButton.setEnabled(true);
        holdButton.setEnabled(true);
        changeImage(0);
    }
}
