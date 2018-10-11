package efx.com.GroupLink;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class EventData extends AppCompatActivity {

    private EditText[] input;
    private TextView alertTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_data);
        Toolbar topToolBar = findViewById(R.id.toolbar);
        //setSupportActionBar(topToolBar);
        //ActionBar actionBar = getSupportActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true);

        input = new EditText[]{
                findViewById(R.id.eventTitle),
                findViewById(R.id.fragDesc),
                findViewById(R.id.eventDate),
                findViewById(R.id.eventStart),
                findViewById(R.id.eventEnd)
        };

        input[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDateDialog(input[3]).show();
            }
        });

        input[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDateDialog(input[4]).show();
            }
        });

    }

    //Create the alert box
    public Dialog createDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("You must fill every text slot.");
        builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                emptyField = false;
                dialog.dismiss();
            }
        });

        return builder.create();

    }

    public Dialog createDateDialog(final TextView myTextView){
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);
        final TimePickerDialog.OnTimeSetListener myListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String mAMPM;
                if (hourOfDay > 13) {
                    hourOfDay -= 12;
                    mAMPM = "PM";
                } else {
                    mAMPM = "AM";
                }

                myTextView.setText(String.format("%2d:%02d " + mAMPM, hourOfDay, minute));

               //myTextView.setText("h:mm a");
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                android.R.style.Theme_Holo_Dialog_NoActionBar,
                myListener,
                hour,
                min,
                false);

        timePickerDialog.setTitle("Choose time of event:");
        timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        return timePickerDialog;

    }


    boolean emptyField = false;
    String assignStringData(int pos){
        if (input[pos].getText().toString().isEmpty()){
            emptyField = true;
            return null;

        } else {
            String inputString = input[pos].getText().toString();
            Log.i("Value:", inputString);
            return inputString;
        }

    }

    public void saveData(View v){
        //String title, description, date, start, end, sAMPM, eAMPM;

        /*title = assignStringData(0);
        description= assignStringData(1);
        date = assignStringData(2);
        start = assignStringData(3);
        sAMPM = assignStringData(4);
        end = assignStringData(5);
        eAMPM = assignStringData(6);*/


        //If any of these fields are empty, emptyField will be set to true, causing an error dialog
        String arr[] = new String[5];

        for (int i = 0; i < 5; i++){
            arr[i] = assignStringData(i);
        }

        //If any of the fields were empty, show  error message
        if (emptyField){
            //When error message is closed, emptyField is set to false again
            createDialog().show();


        //Otherwise, put all of the userInput into an intent bundle to be transferred to the MainActivity
        } else {
            Intent mIntent = new Intent(EventData.this, MainScreenActivity.class);
            mIntent.putExtra("title", arr[0]);
            mIntent.putExtra("description", arr[1]);
            mIntent.putExtra("date", arr[2]);
            mIntent.putExtra("start", arr[3]);
            mIntent.putExtra("end", arr[4]);
            setResult(123, mIntent);
            finish();
        }

    }
}