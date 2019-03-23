package unme.app.com.ume;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import unme.app.com.ume.model.Countdown;
import unme.app.com.ume.model.UserModel;

public class CountdownActivity extends AppCompatActivity {

    private String sessionUser, sessionUserID;
    private Long eventMilis;
    private String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private String EVENT_DATE_TIME;
    private LinearLayout linear_layout_2;
    private TextView tv_days, tv_hour, tv_minute, tv_second, txtEventName, txtEventTime;
    private Handler handler = new Handler();
    private Runnable runnable;
    private Button btnShowDateTime, btnAddEvent;
    private DatabaseReference mDatabase;
    private SharedPreferences sharedPreferences;
    private EditText inputEventName;
    public static String LOG_APP = "[CountDown ] : ";
    private Countdown countdown;
    private long timeMils = 0;
    private String eventName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);

    //Get session data
        sharedPreferences = getSharedPreferences("USER_LOGIN", MODE_PRIVATE);
        sessionUserID = sharedPreferences.getString("USER_ID", null);
        sessionUser = sharedPreferences.getString("USER", null);

        //Get xml components
        txtEventName = findViewById(R.id.txtEventName);
        txtEventTime = findViewById(R.id.txtEventTime);
        inputEventName = findViewById(R.id.txtEvent);

        btnShowDateTime = findViewById(R.id.btnShowDateTime);
        btnAddEvent = findViewById(R.id.btnAddEvent);
        mDatabase = FirebaseDatabase.getInstance().getReference("event");
        // Load database data
        getData();

        //Create countdown
        btnAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventName = inputEventName.getText().toString();
            //check inputs values
                if (TextUtils.isEmpty(eventName)) {
                    Toast.makeText(getApplicationContext(), "Please enter event name!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (eventMilis.equals(0)) {
                    Toast.makeText(getApplicationContext(), "Please pick time !", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Refresh data, save and start timer
                getData();
                countdown = new Countdown(sessionUserID,inputEventName.getText().toString(), eventMilis);
                mDatabase.child(sessionUserID).setValue(countdown);
                countDownStart();

            }
        });

        linear_layout_2 = findViewById(R.id.linear_layout_2);
        tv_days = findViewById(R.id.tv_days);
        tv_hour = findViewById(R.id.tv_hour);
        tv_minute = findViewById(R.id.tv_minute);
        tv_second = findViewById(R.id.tv_second);

        if ((sessionUser == null) || (sessionUser == null)) {
            Intent intent = new Intent(CountdownActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        countDownStart();


       btnShowDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTime();
            }
        });



    }

//Show alert
    public void showDateTime() {
        final View dialogView = View.inflate(this, R.layout.date_time, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        dialogView.findViewById(R.id.date_time_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.date_picker);
                TimePicker timePicker = (TimePicker) dialogView.findViewById(R.id.time_picker);

                Calendar calendar = new GregorianCalendar(datePicker.getYear(),
                        datePicker.getMonth(),
                        datePicker.getDayOfMonth(),
                        timePicker.getCurrentHour(),
                        timePicker.getCurrentMinute());
               eventMilis = calendar.getTimeInMillis();
                alertDialog.dismiss();

            }
        });
        alertDialog.setView(dialogView);
        alertDialog.show();
    }





    private void countDownStart() {
        runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    handler.postDelayed(this, 1000);
                    SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
                    Date event_date = dateFormat.parse(EVENT_DATE_TIME);
                    Date current_date = new Date();
                    if (!current_date.after(event_date)) {
                        long diff = event_date.getTime() - current_date.getTime();
                        long Days = diff / (24 * 60 * 60 * 1000);
                        long Hours = diff / (60 * 60 * 1000) % 24;
                        long Minutes = diff / (60 * 1000) % 60;
                        long Seconds = diff / 1000 % 60;
                        //
                        tv_days.setText(String.format("%02d", Days));
                        tv_hour.setText(String.format("%02d", Hours));
                        tv_minute.setText(String.format("%02d", Minutes));
                        tv_second.setText(String.format("%02d", Seconds));
                    } else {

                        linear_layout_2.setVisibility(View.VISIBLE);
                        handler.removeCallbacks(runnable);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 0);

    }

    protected void onStop() {
        super.onStop();
        // handler.removeCallbacks(runnable);
    }




//Get data from firebase using session user id
    public void getData(){
        Query query = mDatabase.orderByChild("userId").equalTo(sessionUserID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    Countdown countdown = childSnapshot.getValue(Countdown.class);
                    txtEventName.setText("Event Name: "+countdown.getEvent_name()); //get event name in the database
                    timeMils = countdown.getEvent_time(); //get event name  in the database
                    SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(timeMils);
                    EVENT_DATE_TIME = formatter.format(calendar.getTime());
                    txtEventTime.setText("Event Time: "+EVENT_DATE_TIME);

               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(LOG_APP, databaseError.getDetails());
            }
        });

    }

}



