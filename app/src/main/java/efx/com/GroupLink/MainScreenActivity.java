package efx.com.GroupLink;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainScreenActivity extends AppCompatActivity {

    RecyclerView mainRecyclerView;
    RecycleViewAdapter mainAdapter;
    UserInfo mainUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        //Initializing the UserInfo class
        mainUser = new UserInfo();
        initRecycler();
        //createData("EventName","Description", "06/10/2018", "STARTTIME", "ENDTIME");
    }


    //Called when this activity receives the result code of an activity
    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED){
            Log.i("Cancelled EventData", "Nothing new was added");

        } else {
            /*String title = data.getStringExtra("title");
            String description = data.getStringExtra("description");
            String date = data.getStringExtra("date");*/
            String start = data.getStringExtra("start");
            String end = data.getStringExtra("end");
            //createData(title, description, date, start, end);

            String time = start + " - " + end;

            mainUser.addEventName(data.getStringExtra("title"));
            mainUser.addEventDesc(data.getStringExtra("description"));
            mainUser.addEventDate(data.getStringExtra("date"));
            mainUser.addEventFlavor("9");
            mainUser.addEventTime(time);
            mainUser.addNumberOfEvents(1);

            //mainAdapter.setEqual(mainUser);

            mainAdapter.notifyDataSetChanged();
        }
    }

    private void createEntry(UserInfo user){
        mainUser.addNewEvent(
                "My First Event Title",
                "8:00 AM - 12:00 PM",
                "A generic event",
                "DEBUG: SETUP FLAVOR TEXT METHODS",
                false);

    }

    //This will initialize our custom recyclerView by telling it which RecyclerView to reference [The one in Main Activity]
    private void initRecycler(){
        //References the RecyclerView in the MainActivity
        mainRecyclerView = findViewById(R.id.mainRecycler);

        //Creates a new class object from our custom RecycleViewAdapter.Java class
        //This is calling the constructor
        //mainAdapter = new RecycleViewAdapter(hour, eventName, time, description,this);
        mainAdapter = new RecycleViewAdapter(mainUser);

        //Connects our recycler and our adapter
        mainRecyclerView.setAdapter(mainAdapter);

        //This will order the items correctly in a linear fashion
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //DEBUG Statement: Called to ensure that the recycler was created without any fatal errors
        Log.i("init called:", "Recycler created successfully");
    }

    public void openActivity(View v){
        Intent intent = new Intent(MainScreenActivity.this, EventData.class);
        startActivityForResult(intent, 123);
        //Log.i("Event1:", mainUser.getEventName(0));
    }
}
