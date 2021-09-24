package algonquin.cst2335.seba0027;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView mytext = findViewById(R.id.textview);


        EditText myedit = findViewById(R.id.myedittext);

        Button btn = findViewById(R.id.mybutton);
        if(btn != null)btn.setOnClickListener(v -> mytext.setText("Your edit text has: " + myedit.getText().toString()));

        RadioButton radioB = findViewById(R.id.rb);

        CheckBox checkB = findViewById(R.id.cb);
        checkB.setOnCheckedChangeListener( (b, c) -> {
            radioB.setChecked(c);
            Toast.makeText(MainActivity.this, "You clicked on CheckBox", Toast.LENGTH_LONG).show();
        } );

        Switch switchButton = findViewById(R.id.sw);
        switchButton.setOnCheckedChangeListener(  ( x , y ) -> {
            radioB.setChecked(y);
            Toast.makeText(MainActivity.this, "You clicked on Switch", Toast.LENGTH_SHORT).show();
        } );

        radioB.setOnCheckedChangeListener( ( s,t) -> {
            Toast.makeText(MainActivity.this, "You clicked on RadioButton", Toast.LENGTH_SHORT).show();
        });


        ImageView myimage = findViewById(R.id.logo_algonquin);



        ImageButton imgbtn = findViewById( R.id.myimagebutton );



        imgbtn.setOnClickListener( w  -> {
            int width = imgbtn.getWidth();
            int height = imgbtn.getHeight();
            Toast.makeText(MainActivity.this, "The width = " + width + " and height = " + height + ".", Toast.LENGTH_SHORT).show();
        });


    }
}