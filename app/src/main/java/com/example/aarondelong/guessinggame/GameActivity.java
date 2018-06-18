package com.example.aarondelong.guessinggame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {

    private Button guessButton;
    private TextView clue;
    private EditText guess;

    private int generateNumber;
    private int numberOfGuesses = 0;
    public static final String winningNumber = "WINNING_NUMBER";

//    Fix restarting game!!!
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        guessButton = findViewById(R.id.submit_guessbutton);
        clue = findViewById(R.id.clue_textview);
        guess = findViewById(R.id.guess_edittext);


//        Toast.makeText(this, Integer.toString(generateNumber), Toast.LENGTH_SHORT).show();

        setListener();
    }

    @Override
    protected void onStart() {
        super.onStart();

        //This generates a random number between 1 and 100
        generateNumber = (int) Math.ceil(Math.random() * 100);

        numberOfGuesses = 0;
        clue.setVisibility(View.INVISIBLE);
        guess.setText("");
    }

    private void setListener() {
        guessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateGuess();
            }
        });
    }

    //Check to make sure that the user has put in a valid number
    private void validateGuess() {

        try {

            int userGuess = Integer.parseInt(guess.getText().toString());
            if (userGuess > 100 || userGuess <= 0) {
                clue.setText(R.string.choose_number);
                clue.setVisibility(View.VISIBLE);
                guess.setText("");
            } else {

                checkGuess(userGuess);
            }

        } catch (NumberFormatException nfe) {
            clue.setText(R.string.enter_number);
            clue.setVisibility(View.VISIBLE);
        }
    }

    //This method will take the input and check it against the generateNumber. Depending on the outcome it will change the view accordingly or take us to the ResultsActivity;
    private void checkGuess(int userGuess) {

        int MAX_GUESS_COUNT = 4;
        if (userGuess == generateNumber) {
//            Goes to ResultsActivity. User has guessed correctly
            Intent winner = new Intent(this, ResultsActivity.class);
            startActivity(winner);

        } else if (numberOfGuesses == MAX_GUESS_COUNT) {
//                Goes to ResultsActivity. User has ran out of chances
                Intent loser = new Intent(this, ResultsActivity.class);
                loser.putExtra(winningNumber, generateNumber);
                startActivity(loser);

            } else if (userGuess < generateNumber) {
//                Update clue TextView to say higher. Set visibility to VISIBLE, set guess EditText to "" and increment numberOfGuesses by 1

                clue.setText(R.string.higher);
                clue.setVisibility(View.VISIBLE);
                guess.setText("");
                numberOfGuesses++;
                Toast.makeText(this, getString(R.string.chances_left, (5 - numberOfGuesses)), Toast.LENGTH_SHORT).show();


            } else if (userGuess > generateNumber) {
                //Update clue TextView to say lower. Set visibility to VISIBLE, set guess EditText to "" and increment numberOfGuesses by 1

                clue.setText(R.string.lower);
                clue.setVisibility(View.VISIBLE);
                guess.setText("");
                numberOfGuesses++;
            Toast.makeText(this, getString(R.string.chances_left, (5 - numberOfGuesses)), Toast.LENGTH_SHORT).show();

            }
        }



    @Override
    public void onBackPressed() {
//    Removed super.onBackPressed to make sure that if the back button is pressed nothing will happen.
    }
}

