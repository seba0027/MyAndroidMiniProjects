package algonquin.cst2335.seba0027;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;


/**
 * This page lets the user enter a password and the password is checked for its complexity
 * The page lets the user enter a password and this password is checked for complexity. If the
 * password meets the minimum requirement then the user gets a pass message else the user
 * would not be getting any message.
 *
 * @author Jules Sebastian
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {


    private String stringURL;


//
//    /**  Declaring and initializing a TextView object */
//    private TextView tv = null;
//    /** Declaring and  initializing a button object */
//    private Button bt = null;
//    /** Declaring and initializing a EditText object */
//    private EditText et = null;
//
//
//
//    /** This function checks if the String is complex or not
//     *  This function checks if the password string that is provided by the user is complex,
//     *  containing upper case, lowercase, special character and also a number. If the password
//     *  contains all the following then this function would return a true value and would let the
//     *  user set that as a password.
//     * @param pw String character to be checked, which is entered by the user.
//     * @return true is all conditions are met, otherwise false.
//     */
//    private boolean checkPasswordComplexity (String pw) {
//        boolean foundUpperCase, foundLowerCase, foundNumber, foundSpecial;
//        foundUpperCase = foundLowerCase = foundNumber = foundSpecial = false;
//
//        for (int i = 0; i < pw.length(); i++) {
//            char c = pw.charAt(i);
//
//            if (Character.isLowerCase(c))
//                foundLowerCase = true;
//
//            if (Character.isUpperCase(c))
//                foundUpperCase = true;
//
//            if (Character.isDigit(c))
//                foundNumber = true;
//
//            if (isSpecialCharacter(c))
//                foundSpecial = true;
//        }
//
//
//        if(!foundUpperCase)
//        {
//            Context context = getApplicationContext();
//            CharSequence text = "Password missing upper case character!";
//            int duration = Toast.LENGTH_SHORT;
//
//            Toast toast = Toast.makeText(context, text, duration);
//            toast.show();
//            return false;
//        }
//        else if( ! foundLowerCase)
//        {
//            Context context = getApplicationContext();
//            CharSequence text = "Password missing lower case character!";
//            int duration = Toast.LENGTH_SHORT;
//
//            Toast toast = Toast.makeText(context, text, duration);
//            toast.show();
//            return false;
//        }
//        else if( ! foundNumber) {
//            Context context = getApplicationContext();
//            CharSequence text = "Password missing a number!";
//            int duration = Toast.LENGTH_SHORT;
//
//            Toast toast = Toast.makeText(context, text, duration);
//            toast.show();
//            return false;
//        }
//        else if (! foundSpecial) {
//            Context context = getApplicationContext();
//            CharSequence text = "Password missing a special character!";
//            int duration = Toast.LENGTH_SHORT;
//
//            Toast toast = Toast.makeText(context, text, duration);
//            toast.show();
//            return false;
//        }
//        else
//            return foundUpperCase && foundLowerCase && foundNumber && foundSpecial;
//
//
//    }
//
//    /** Returns true if contains { #$%^&*!@? }, false otherwise
//     * This function tests if a character form the password provided by the user contains a special
//     * character form the following list of special characters { #$%^&*!@? }. This function would
//     * retun true if any of the following were found, else it woudl return false
//     * @param c value of the character form the password being checked for
//     * @return true if contians { #$%^&*!@? }, false otherwise
//     */
//    private boolean isSpecialCharacter(char c){
//        switch(c)
//        {
//            case '#':
//            case '$':
//            case '%':
//            case '^':
//            case '&':
//            case '*':
//            case '!':
//            case '@':
//            case '?':
//                return true;
//            default:
//                return false;
//        }
//    }

    private String iconName;
    private Bitmap image = null;
    private Double current;
    private double min;
    private double max;
    private int humidity;
    private String description;
    private String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button forecastBtn = findViewById(R.id.forecastButton);
        EditText citytext = findViewById(R.id.cityTextField);


        forecastBtn.setOnClickListener((click) -> {
            // Week 9 start, URL connection Executor
            Executor newThread = Executors.newSingleThreadExecutor();
            newThread.execute(() -> {
                try {

                    String cityName = citytext.getText().toString();
                    stringURL = "https://api.openweathermap.org/data/2.5/weather?q="
                            + URLEncoder.encode(cityName, "UTF-8")
                            + "&appid=97e79ba6a035a5b918f9c86d3dc930be&units=metric";


                    URL url = new URL(stringURL);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                    String text = (new BufferedReader(
                            new InputStreamReader(in, StandardCharsets.UTF_8)))
                            .lines()
                            .collect(Collectors.joining("\n"));

                    JSONObject theDocument = new JSONObject(text);

                    //JSONArray theArray = new JSONArray( text );


                    // Getting Coord
                    JSONObject coord = theDocument.getJSONObject("coord");
                    // Getting weather
                    JSONArray weatherArray = theDocument.getJSONArray("weather");
                    // Visibility
                    int vis = theDocument.getInt("visibility");
                    // Name
                    name = theDocument.getString("name");
                    JSONObject position0 = weatherArray.getJSONObject(0);
                    description = position0.getString("description");
                    iconName = position0.getString("icon");
                    JSONObject mainObject = theDocument.getJSONObject("main");
                    current = mainObject.getDouble("temp");
                    min = mainObject.getDouble("temp_min");
                    max = mainObject.getDouble("temp_max");
                    humidity = mainObject.getInt("humidity");


                } catch (JSONException je) {
                    Log.e("JSONException", je.getMessage());
                } catch (IOException ioe) {
                    Log.e("IOException", ioe.getMessage());
                }


                File file = new File(getFilesDir(), iconName + ".png");
                if(file.exists()){
                    image = BitmapFactory.decodeFile(getFilesDir() + "/" + iconName + ".png");
                }else{
                    try {
                        URL imgUrl = new URL("https://openweathermap.org/img/w/" + iconName + ".png");
                        HttpURLConnection connection = (HttpURLConnection) imgUrl.openConnection();
                        connection.connect();
                        int responseCode = connection.getResponseCode();
                        if (responseCode == 200) {
                            image = BitmapFactory.decodeStream(connection.getInputStream());

                        }
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }




                FileOutputStream fOut = null;

                try {
                    fOut = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);
                    image.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                    fOut.flush();
                    fOut.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                runOnUiThread(() -> {
                    TextView tv = findViewById(R.id.temp);
                    tv.setText("Current temperature is " + current + "°C");
                    tv.setVisibility(View.VISIBLE);

                    tv = findViewById(R.id.minTemp);
                    tv.setText("The min temperature is " + min + "°C");
                    tv.setVisibility(View.VISIBLE);

                    tv = findViewById(R.id.maxTemp);
                    tv.setText("The max temperature is " + max +"°C");
                    tv.setVisibility(View.VISIBLE);

                    tv = findViewById(R.id.humidity);
                    tv.setText("The humidity is " + humidity +"%");
                    tv.setVisibility(View.VISIBLE);

                    tv = findViewById(R.id.description);
                    tv.setText(description);
                    tv.setVisibility(View.VISIBLE);


                    ImageView iv = findViewById(R.id.icon);
                    iv.setImageBitmap(image);
                    iv.setVisibility(View.VISIBLE);
                });


            });


        });


        //tv = findViewById(R.id.textView2);
        //bt = findViewById(R.id.loginbutton);
        //et = findViewById(R.id.editTextTextPassword);

//        bt.setOnClickListener( clk ->{
//            String password = et.getText().toString();
//
//            if(checkPasswordComplexity( password ))
//                tv.setText("Your password meets the requirements");
//            else
//                tv.setText("You shall not pass!");
//        });


    }
}