package unme.app.com.ume;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import unme.app.com.ume.model.GuestCount;
import unme.app.com.ume.model.Todo;

public class GuestViewActivity extends AppCompatActivity {
private Button addCount, addGuest;
private EditText gust_count;
private TextView totalGuesView;
private  int count = 0;
    private SharedPreferences sharedPreferences;
    String sessionUserID, sessionUser, guestCount;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_view);
        sharedPreferences = getSharedPreferences("USER_LOGIN", MODE_PRIVATE); //session save name
        sessionUserID = sharedPreferences.getString("USER_ID", null);//session save key user id
        sessionUser = sharedPreferences.getString("USER", null); //session save key username
        addGuest = findViewById(R.id.btnAddGuest);
        totalGuesView = findViewById(R.id.totalGuest);
        loadData();
        addGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGuest();
            }
        });


        mDatabase = FirebaseDatabase.getInstance().getReference("guest-count");       //Get firebase table path

        Query query = mDatabase.orderByChild("userId").equalTo(sessionUserID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){


                }else{
                    addGuestCount();
                    loadData(); //get guest count in db
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void addGuestCount() {
        //show alert
        mDatabase = FirebaseDatabase.getInstance().getReference("guest-count");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.guest_count, null);
        builder.setView(view);
        final AlertDialog alert = builder.create();
        alert.show();
        addCount = view.findViewById(R.id.btnAddCount);
        gust_count = view.findViewById(R.id.GuestCount);
        addCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get current date
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
                String currentDate = simpleDateFormat.format(new Date());

                //check empty
                String count = gust_count.getEditableText().toString().trim();
                if (TextUtils.isEmpty(count)) {
                    Toast.makeText(getApplicationContext(), "Please guest count!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // add guest count
                GuestCount guestCount = new GuestCount(Integer.parseInt(count),sessionUserID,currentDate);
                mDatabase.child(sessionUserID).setValue(guestCount);
                loadData();
                Toast.makeText(getApplicationContext(), "Adding guest count!", Toast.LENGTH_SHORT).show();
                alert.dismiss();

            }
        });













    }


    public void addGuest() {
        //show alert
        mDatabase = FirebaseDatabase.getInstance().getReference("guest-count");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_add_guest, null);
        builder.setView(view);
        final AlertDialog alert = builder.create();
        alert.show();

                //get current date
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
                String currentDate = simpleDateFormat.format(new Date());
    }



    public void loadData(){

        mDatabase = FirebaseDatabase.getInstance().getReference("guest-count");
        Query query = mDatabase.orderByChild("userId").equalTo(sessionUserID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    GuestCount guestCount = childSnapshot.getValue(GuestCount.class);
                    count = guestCount.getCount();
                   // gust_count.setText(String.valueOf(count));
                    totalGuesView.setText(String.valueOf(guestCount.getCount()));
                    System.out.println(guestCount.getCount());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println(databaseError.getMessage());
            }
        });
    }


}
