package com.akhil.wordle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    var wordToGuess: String = ""
    var count = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        wordToGuess = FourLetterWordList.getRandomFourLetterWord();
        count = 1

        val inputField: TextView = findViewById(R.id.guess_input_field)
        val guess1TextValue: TextView = findViewById(R.id.guess1_value)
        val guess2TextValue: TextView = findViewById(R.id.guess2_value)
        val guess3TextValue: TextView = findViewById(R.id.guess3_value)
        val guess1TextValidation: TextView = findViewById(R.id.guess1_validation)
        val guess2TextValidation: TextView = findViewById(R.id.guess2_validation)
        val guess3TextValidation: TextView = findViewById(R.id.guess3_validation)
        val correctWord: TextView = findViewById(R.id.correct_word)

        val guessButton: Button = findViewById<Button>(R.id.guess_reset_button)
        guessButton.setOnClickListener {
            //restart the activity if the count is 4
            if (count == 4) {
                finish()
                startActivity(intent)
            }
            //show message on invalid input
            if (inputField.text.trim().isNullOrBlank() || inputField.text.trim().length != 4) {
                Toast.makeText(this, "Enter a valid input of 4 characters", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val inputValue = inputField.text.trim().toString().uppercase()
                val checkGuess = checkGuess(inputValue)
                when (count) {
                    1 -> {
                        updateViewsOnGuess(
                            guess1TextValue,
                            inputValue,
                            guess1TextValidation,
                            checkGuess,
                            inputField,
                            correctWord,
                            guessButton
                        )

                    }
                    2 -> {
                        updateViewsOnGuess(
                            guess2TextValue,
                            inputValue,
                            guess2TextValidation,
                            checkGuess,
                            inputField,
                            correctWord,
                            guessButton
                        )
                    }
                    3 -> {
                        if (!updateViewsOnGuess(
                                guess3TextValue,
                                inputValue,
                                guess3TextValidation,
                                checkGuess,
                                inputField,
                                correctWord,
                                guessButton
                            )
                        ) {
                            correctWord.text = "Correct Word: " + wordToGuess
                            guessButton.text = getString(R.string.reset_button_val)
                            count = 4
                        }
                    }
                }

            }
        }
    }

    /**
     * Updates the views once the user hits the guess button.
     * @param guessTextValidation: Text view showing the guess.
     *
     * Returns True if the word was guessed correctly, otherwise false.
     */
    private fun updateViewsOnGuess(
        guessTextValue: TextView,
        inputValue: String,
        guessTextValidation: TextView,
        checkGuess: String,
        inputField: TextView,
        correctWord: TextView,
        guessButton: Button
    ): Boolean {
        guessTextValue.visibility = View.VISIBLE
        guessTextValue.text = inputValue
        guessTextValidation.visibility = View.VISIBLE
        guessTextValidation.text = checkGuess
        inputField.text = ""
        count += 1
        if (checkGuess == "OOOO") {
            correctWord.text = "You WIN!"
            correctWord.setTextColor(getColor(R.color.green))
            count = 4
            guessButton.text = getString(R.string.reset_button_val)

            return true
        }

        return false
    }


    /**
     * Parameters / Fields:
     *   wordToGuess : String - the target word the user is trying to guess
     *   guess : String - what the user entered as their guess
     *
     * Returns a String of 'O', '+', and 'X', where:
     *   'O' represents the right letter in the right place
     *   '+' represents the right letter in the wrong place
     *   'X' represents a letter not in the target word
     */
    private fun checkGuess(guess: String): String {
        var result = ""
        for (i in 0..3) {
            if (guess[i] == wordToGuess[i]) {
                result += "O"
            } else if (guess[i] in wordToGuess) {
                result += "+"
            } else {
                result += "X"
            }
        }
        return result
    }
}