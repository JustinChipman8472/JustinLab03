package com.example.simpleviews1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.content.DialogInterface;
import android.provider.Settings;
import android.content.Intent;
import android.widget.ImageButton;
import android.net.Uri;
import android.widget.Switch;
import com.google.android.material.snackbar.Snackbar;
import android.widget.CompoundButton;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private Toast currentToast;
    private int currentImageIndex = 0;
    private final int[] imageIds = {
            R.drawable.ichigo,
            R.drawable.light,
            R.drawable.naruto,
            R.drawable.asta
    };
    private final String[] imageNames = {"ichigo", "light", "naruto", "asta"};
    public void btnSaved_clicked (View view) {
        DisplayToast(getString(R.string.save_msg));
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //---Switch Button---
        Switch switchButton = findViewById(R.id.switchButton);
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Determine the message based on the switch state
                int messageId = isChecked ? R.string.switch_on : R.string.switch_off;

                // Create and display a Snackbar with the message
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                        getString(messageId),
                        Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        });

        //---imageButton view---
        ImageButton imageButton = findViewById(R.id.btnImg1);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update the image index
                currentImageIndex = (currentImageIndex + 1) % imageIds.length;

                // Set the new image
                imageButton.setImageResource(imageIds[currentImageIndex]);

                // Show the toast
                String toastMessage = getString(R.string.Name) + imageNames[currentImageIndex];
                DisplayToast(toastMessage);
            }
        });

        //---Button view---
        Button btnOpen = (Button) findViewById(R.id.btnOpen);
        //register the button with an event listener
        btnOpen.setOnClickListener(new View.OnClickListener()
        { //anonymous class
            //implement your event handler method
            public void onClick(View v) {
                // if there is a toast cancel it
                if (currentToast != null) {
                    currentToast.cancel();
                }
                // Launch the device settings screen
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                startActivity(intent);
            }
        });


		//---Button view---
		Button btnSave = (Button) findViewById(R.id.btnSave);
		btnSave.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v) {
                String url = "https://www.netflix.com";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
			}
		});


        //---CheckBox---
        CheckBox checkBox = (CheckBox) findViewById(R.id.chkAutosave);
        checkBox.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                if (((CheckBox)v).isChecked())
                    DisplayToast(getString(R.string.checked_true));
                else
                    DisplayToast(getString(R.string.checked_false));
            }
        });

        //---RadioButton---
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rdbGp1);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb1 = (RadioButton) findViewById(R.id.rdb1);
                if (rb1.isChecked()) {
                    DisplayToast(getString(R.string.opt1_checked));
                } else {
                    DisplayToast(getString(R.string.opt2_checked));
                }
            }
        });

        //---ToggleButton---
        ToggleButton toggleButton =
                (ToggleButton) findViewById(R.id.toggle1);
        toggleButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                if (((ToggleButton)v).isChecked())
                    DisplayToast(getString(R.string.toggle_on));
                else
                    DisplayToast(getString(R.string.toggle_off));
            }
        });
    }

    private void DisplayToast(String msg)
    {
        // if there is already a toast display close it
        if(currentToast != null) {
            currentToast.cancel();
        }
        // create toast
        currentToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        // display toast
        currentToast.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Log the message when the app goes into the background
        Log.d(getString(R.string.tag), getString(R.string.name_studentnum));
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // create logo
        builder.setIcon(R.drawable.home_logo);
        // create title
        builder.setTitle(getString(R.string.app_name));
        // set message
        builder.setMessage(getString(R.string.exit_msg));
        // add functionality for yes
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked Yes button
                finish(); // This will close the current activity and exit the app
            }
        });
        // add functionality for no
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked No button
                dialog.dismiss();
            }
        });

        builder.setCancelable(false);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
