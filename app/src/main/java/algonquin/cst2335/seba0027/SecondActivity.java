package algonquin.cst2335.seba0027;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SecondActivity extends AppCompatActivity {
    SharedPreferences prefs;
    ImageButton profileImage;
    EditText previousPhoneNo;

    ActivityResultLauncher<Intent> cameraResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        //File whereAmI = getFilesDir();
                        Bitmap thumbnail = data.getParcelableExtra("data");

                        try {
                            FileOutputStream file = openFileOutput("Picture.png", Context.MODE_PRIVATE);
                            thumbnail.compress(Bitmap.CompressFormat.PNG, 100, file);
                            file.flush();
                            file.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        catch(IOException ioe) {
                            Log.w("SecondActivity","IOException");
                        }
                        ImageView profPic = findViewById(R.id.imageView);
                        profPic.setImageBitmap( thumbnail );



                    }
                    else if (result.getResultCode() == Activity.RESULT_CANCELED){
                        Log.w("Got bitmap", "User refused the image");

                    }
                }
            });


    @Override
    protected void onPause() {
        super.onPause();
        prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        previousPhoneNo = findViewById(R.id.editTextPhone);
        editor.putString("PhoneNo", previousPhoneNo.getText().toString());
        editor.apply();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent fromPrevious = getIntent();
        String emailAddress = fromPrevious.getStringExtra("EmailAddress");
        prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        TextView textVeiwTop = findViewById(R.id.textView3);
        textVeiwTop.setText("Welcome" + " " +emailAddress);
        String phNo = prefs.getString("PhoneNo", "");
        previousPhoneNo = findViewById(R.id.editTextPhone);
        previousPhoneNo.setText(phNo);

        Button callButton = findViewById(R.id.button2);
        callButton.setOnClickListener( clk-> {
            TextView getPhone = findViewById(R.id.editTextPhone);
            String phoneNumber =getPhone.getText().toString();
            Intent call = new Intent(Intent.ACTION_DIAL);
            call.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(call);
        } );
    Button camButton = findViewById(R.id.button3);
    camButton.setOnClickListener( clk-> {
       Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        cameraResult.launch(cameraIntent);
   });
        ImageView icon = findViewById(R.id.imageView);
        File file = new File( getFilesDir(), "Picture.png");
        if(file.exists()) {
            Bitmap theImage = BitmapFactory.decodeFile(file.getAbsolutePath());
            icon.setImageBitmap( theImage );
            }



    }
}