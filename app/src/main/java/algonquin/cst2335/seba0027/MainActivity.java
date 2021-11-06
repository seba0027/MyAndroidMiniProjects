package algonquin.cst2335.seba0027;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/** This page lets the user enter a password and the password is checked for its complexity
 *  The page lets the user enter a password and this password is checked for complexity. If the
 *  password meets the minimum requirement then the user gets a pass message else the user
 *  would not be getting any message.
 * @author Jules Sebastian
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {


    /**  Declaring and initializing a TextView object */
    private TextView tv = null;
    /** Declaring and  initializing a button object */
    private Button bt = null;
    /** Declaring and initializing a EditText object */
    private EditText et = null;



    /** This function checks if the String is complex or not
     *  This function checks if the password string that is provided by the user is complex,
     *  containing upper case, lowercase, special character and also a number. If the password
     *  contains all the following then this function would return a true value and would let the
     *  user set that as a password.
     * @param pw String character to be checked, which is entered by the user.
     * @return true is all conditions are met, otherwise false.
     */
    private boolean checkPasswordComplexity (String pw) {
        boolean foundUpperCase, foundLowerCase, foundNumber, foundSpecial;
        foundUpperCase = foundLowerCase = foundNumber = foundSpecial = false;

        for (int i = 0; i < pw.length(); i++) {
            char c = pw.charAt(i);

            if (Character.isLowerCase(c))
                foundLowerCase = true;

            if (Character.isUpperCase(c))
                foundUpperCase = true;

            if (Character.isDigit(c))
                foundNumber = true;

            if (isSpecialCharacter(c))
                foundSpecial = true;
        }


        if(!foundUpperCase)
        {
            Context context = getApplicationContext();
            CharSequence text = "Password missing upper case character!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return false;
        }
        else if( ! foundLowerCase)
        {
            Context context = getApplicationContext();
            CharSequence text = "Password missing lower case character!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return false;
        }
        else if( ! foundNumber) {
            Context context = getApplicationContext();
            CharSequence text = "Password missing a number!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return false;
        }
        else if (! foundSpecial) {
            Context context = getApplicationContext();
            CharSequence text = "Password missing a special character!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return false;
        }
        else
            return foundUpperCase && foundLowerCase && foundNumber && foundSpecial;


    }

    /** Returns true if contains { #$%^&*!@? }, false otherwise
     * This function tests if a character form the password provided by the user contains a special
     * character form the following list of special characters { #$%^&*!@? }. This function would
     * retun true if any of the following were found, else it woudl return false
     * @param c value of the character form the password being checked for
     * @return true if contians { #$%^&*!@? }, false otherwise
     */
    private boolean isSpecialCharacter(char c){
        switch(c)
        {
            case '#':
            case '$':
            case '%':
            case '^':
            case '&':
            case '*':
            case '!':
            case '@':
            case '?':
                return true;
            default:
                return false;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.textView2);
        bt = findViewById(R.id.loginbutton);
        et = findViewById(R.id.editTextTextPassword);

        bt.setOnClickListener( clk ->{
            String password = et.getText().toString();

            if(checkPasswordComplexity( password ))
                tv.setText("Your password meets the requirements");
            else
                tv.setText("You shall not pass!");
        });




    }
}