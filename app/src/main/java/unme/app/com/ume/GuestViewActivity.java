package unme.app.com.ume;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import unme.app.com.ume.model.Guest;
import unme.app.com.ume.model.GuestCount;
import unme.app.com.ume.model.Todo;

public class GuestViewActivity extends AppCompatActivity {
private Button addCount, btnAddGuest, btnAddCount,btnAdd, btnGuestDelete, btnGuestUpdate;
private EditText guestName, guestContact, guestCount;
private TextView totalGuesView, CurrentGuests,GuestVariance, title;
private ListView listView;
private String selected,selectedGuest;
private int count, totMembers, currentMembers, currentVariance;;
    ArrayList<String> list = new ArrayList<>(); //create array list
    ArrayAdapter<String> adapter; //create array adapter


    private SharedPreferences sharedPreferences;
    String sessionUserID, sessionUser;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_view);

        sharedPreferences = getSharedPreferences("USER_LOGIN", MODE_PRIVATE); //session save name
        sessionUserID = sharedPreferences.getString("USER_ID", null);//session save key user id
        sessionUser = sharedPreferences.getString("USER", null); //session save key username

        title = findViewById(R.id.Guest);
        btnAdd = findViewById(R.id.btnAdd);
        listView = findViewById(R.id.listView);
        totalGuesView = findViewById(R.id.totalGuest);
        GuestVariance = findViewById(R.id.variance);
        CurrentGuests = findViewById(R.id.currentGuests);
        loadData();










        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //adapter.clear();
                //currentMembers = 0;
                loadData();
                addGuestCount();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               addGuest();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) { //listen list view position
                selectedGuest = (String) parent.getItemAtPosition(position); //get position value
                guestEditDelete();



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

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.guest_count, null);
        builder.setView(view);
        final AlertDialog alert = builder.create();
        alert.show();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        final String currentDate = simpleDateFormat.format(new Date());
        btnAddCount = view.findViewById(R.id.btnAddCount);
        guestCount = view.findViewById(R.id.guestCount);

        btnAddCount.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mDatabase = FirebaseDatabase.getInstance().getReference("guest-count");
               String totGuests = guestCount.getEditableText().toString().trim();


                if (TextUtils.isEmpty(totGuests)) {
                    Toast.makeText(getApplicationContext(), "Please enter guest count!", Toast.LENGTH_SHORT).show();
                    return;
                }

                int totalGuest = Integer.parseInt(totGuests);

               GuestCount guestCount = new GuestCount(totalGuest,sessionUserID,currentDate);
               mDatabase.child(sessionUserID).setValue(guestCount);
                Toast.makeText(getApplicationContext(), "Adding guest count !", Toast.LENGTH_SHORT).show();
                alert.dismiss();
               // adapter.clear();
                currentMembers = 0;
               loadData();
              //  guestContact.setText(String.valueOf(guestCount.getCount()));
                adapter.clear();
                currentMembers=0;

            }
        });



   }


    public void addGuest() {
        //show alert











        mDatabase = FirebaseDatabase.getInstance().getReference("guest");


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_add_guest, null);
        builder.setView(view);
        final AlertDialog alert = builder.create();
        alert.show();

        guestName = view.findViewById(R.id.guestName);
        guestContact = view.findViewById(R.id.guestContact);
        guestCount = view.findViewById(R.id.guestCount);
        btnAddGuest = view.findViewById(R.id.addGuest);
        btnAddGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get current date
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
                String currentDate = simpleDateFormat.format(new Date());

                Random random = new Random();
                String key = String.format("%08d", random.nextInt(100000000));

                //check empty
                String GuestName, GuestContact;

                GuestName = guestName.getEditableText().toString().trim();
                GuestContact = guestContact.getEditableText().toString().trim();
                count = Integer.parseInt(guestCount.getEditableText().toString().trim());
                if (TextUtils.isEmpty(GuestName)) {
                    Toast.makeText(getApplicationContext(), "Please enter guest name!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (count==0) {
                    Toast.makeText(getApplicationContext(), "Please enter guest count!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // add guest count
               adapter.clear();
                currentMembers=0;

                Guest guest = new Guest(sessionUserID, currentDate, GuestName, GuestContact, count);
                mDatabase.child(sessionUserID).child(key).setValue(guest);

                Toast.makeText(getApplicationContext(), "Adding guest !", Toast.LENGTH_SHORT).show();
                alert.dismiss();
            }
        });




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
                    totMembers = guestCount.getCount();
                   //gust_count.setText(String.valueOf(count));
                    totalGuesView.setText(String.valueOf(guestCount.getCount()));
                    System.out.println(guestCount.getCount());



                }
              loadListData();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println(databaseError.getMessage());
            }
        });

    }


    public void loadListData(){

        mDatabase = FirebaseDatabase.getInstance().getReference("guest").child(sessionUserID);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, list);
        listView.setAdapter(adapter);

        mDatabase.orderByChild("userId").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        if(dataSnapshot.exists()) {
               for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    Guest guest = childSnapshot.getValue(Guest.class);
                    System.out.println(guest.getName()+guest.getCount());
                    list.add(guest.getName());
                   currentMembers += (guest.getCount());
                   adapter.notifyDataSetChanged();


    }
}
                CurrentGuests.setText(String.valueOf(currentMembers));
                currentVariance = totMembers-currentMembers;
                GuestVariance.setText(String.valueOf(currentVariance));
                System.out.println(currentVariance);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }



        });



    }


    public void guestEditDelete(){

        //show alert

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.guest_changes, null);
        builder.setView(view);
        final AlertDialog alert = builder.create();
        alert.show();

        guestName = view.findViewById(R.id.guestName);
        guestContact = view.findViewById(R.id.guestContact);
        btnAddGuest = view.findViewById(R.id.addGuest);
        btnGuestDelete = view.findViewById(R.id.btnGuestDelete);
        btnGuestUpdate = view.findViewById(R.id.btnUpdateGuest);
        guestName = view.findViewById(R.id.guestName);
        guestCount = view.findViewById(R.id.guestCount);

        mDatabase = FirebaseDatabase.getInstance().getReference("guest").child(sessionUserID);
        Query query = mDatabase.orderByChild("name").equalTo(selectedGuest);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    Guest guest = childSnapshot.getValue(Guest.class);
                    guestName.setText(guest.getName());
                    guestCount.setText(String.valueOf(guest.getCount()));
                    guestContact.setText(guest.getContact());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println(databaseError.getMessage());
            }
        });







        btnGuestDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mDatabase = FirebaseDatabase.getInstance().getReference("guest").child(sessionUserID);
                Query query = mDatabase.orderByChild("name").equalTo(selectedGuest);
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                            dataSnapshot1.getRef().removeValue();
                            adapter.clear();
                            currentMembers = 0;
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        System.out.println(databaseError.getMessage());
                    }
                });
// Show remove task
                Toast.makeText(getApplicationContext(),"Remove guest !", Toast.LENGTH_LONG).show();
                alert.dismiss();



            }
        });

btnGuestUpdate.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        //check empty
        final String GuestName, GuestContact;
        mDatabase = FirebaseDatabase.getInstance().getReference("guest").child(sessionUserID);
        Query query = mDatabase.orderByChild("name").equalTo(selectedGuest);


        GuestName = guestName.getEditableText().toString().trim();
        GuestContact = guestContact.getEditableText().toString().trim();
        count = Integer.parseInt(guestCount.getEditableText().toString().trim());
        if (TextUtils.isEmpty(GuestName)) {
            Toast.makeText(getApplicationContext(), "Please enter guest name!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (count==0) {
            Toast.makeText(getApplicationContext(), "Please enter guest count!", Toast.LENGTH_SHORT).show();
            return;
        }

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) { // database updte by edit text box values


                    dataSnapshot1.getRef().child("name").setValue(GuestName);
                    dataSnapshot1.getRef().child("contact").setValue(GuestContact);
                    dataSnapshot1.getRef().child("count").setValue(count);

                    adapter.clear();
                    currentMembers = 0;

                   adapter.notifyDataSetChanged();
                }

                alert.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println(databaseError.getMessage());
            }
        });
// show update message
        Toast.makeText(getApplicationContext(), "Guest updated !", Toast.LENGTH_LONG).show();

    }



});

    }





}
