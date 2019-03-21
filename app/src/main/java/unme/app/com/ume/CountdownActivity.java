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

    String sessionUser, sessionUserID;
    Long eventMilis;
    private String EVENT_DATE_TIME;
    private String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private LinearLayout linear_layout_1, linear_layout_2;
    private TextView tv_days, tv_hour, tv_minute, tv_second, txtEventName, txtEventTime;
    private Handler handler = new Handler();
    private Runnable runnable;
    private Button btnStart, btnStop, btnShowDateTime, btnAddEvent;
    private SimpleDateFormat simpleDateFormat;
    private Calendar calendar;
    private DatabaseReference mDatabase;
    private SharedPreferences sharedPreferences;
    private EditText inputEventName;
    public static String LOG_APP = "[CountDown ] : ";
    private Countdown countdown;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);


        sharedPreferences = getSharedPreferences("USER_LOGIN", MODE_PRIVATE);
        sessionUserID = sharedPreferences.getString("USER_ID", null);
        sessionUser = sharedPreferences.getString("USER", null);

        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);
        txtEventName = findViewById(R.id.txtEventName);
        txtEventTime = findViewById(R.id.txtEventTime);
        inputEventName = findViewById(R.id.txtEvent);

        btnShowDateTime = findViewById(R.id.btnShowDateTime);
        btnAddEvent = findViewById(R.id.btnAddEvent);
        simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy h:mm a", Locale.getDefault());
        mDatabase = FirebaseDatabase.getInstance().getReference("event");

        btnAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countdown = new Countdown(sessionUserID,inputEventName.getText().toString(), Long.valueOf(eventMilis));
                mDatabase.child(sessionUserID).setValue(countdown);
                getData();
            }
        });


        System.out.println("========================================");

        System.out.println(sessionUserID);
        System.out.println(sessionUser);
        if ((sessionUser == null) || (sessionUser == null)) {
            Intent intent = new Intent(CountdownActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
btnStart.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        countDownStart();
        EVENT_DATE_TIME = String.valueOf(countdown.getEvent_time());
    }
});

        btnShowDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTime();
            }
        });

        getData();

    }


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
                        linear_layout_1.setVisibility(View.VISIBLE);
                        linear_layout_2.setVisibility(View.GONE);
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





    public void getData(){
        Query query = mDatabase.orderByChild("userId").equalTo(sessionUserID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    Countdown countdown = childSnapshot.getValue(Countdown.class);

                    txtEventName.setText(countdown.getEvent_name());
                   txtEventTime.setText(getDate(countdown.getEvent_time(),DATE_FORMAT));




                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(LOG_APP, databaseError.getDetails());
            }
        });

    }

    public String getDate(long milliSeconds, String dateFormat)
    {

        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);


        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
}



