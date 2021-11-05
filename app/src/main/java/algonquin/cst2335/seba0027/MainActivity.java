package algonquin.cst2335.seba0027;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.w( "MainActivity", "In onCreate() - Loading Widgets" );
        Log.d( TAG, "Message");

        TextView emailEditText = findViewById(R.id.editTextTextEmailAddress);
        String EmailText = emailEditText.getText().toString();

        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String emailAddress = prefs.getString("LoginName", "");
        EditText previousEmail = findViewById(R.id.editTextTextEmailAddress);
        previousEmail.setText(emailAddress);

        Button loginButton = findViewById(R.id.buttonlogin);
        loginButton.setOnClickListener( clk-> {
            Intent nextPage = new Intent( MainActivity.this, SecondActivity.class);
           //
            nextPage.putExtra( "EmailAddress", emailEditText.getText().toString() );

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("LoginName", emailEditText.getText().toString());
            editor.apply();
            startActivity( nextPage);
        } );




    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.w(TAG,"onStart() - The application is now visible on screen");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w(TAG,"onResume() - The application is now responding to user input");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w(TAG,"onPause()- The application no longer responds to user input");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.w(TAG,"onStop() - The application is no longer visible");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w(TAG,"onDestroy() - Any memory used by the application is freed");
    }
}