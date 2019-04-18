package unme.app.com.ume;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import unme.app.com.ume.model.Guest;
import unme.app.com.ume.model.Service;

public class MyAllServicesActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private ListView listView;
    ArrayList<String> list = new ArrayList<>(); //create array list
    ArrayAdapter<String> adapter; //create array adapter
    private SharedPreferences sharedPreferences;
    String sessionUserID, sessionUser, selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_all_services);
        sharedPreferences = getSharedPreferences("USER_LOGIN", MODE_PRIVATE); //session save name
        sessionUserID = sharedPreferences.getString("USER_ID", null);//session save key user id
        sessionUser = sharedPreferences.getString("USER", null); //session save key username
        listView = findViewById(R.id.listView);
        loadListData();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) { //listen list view position
                selectedItem = (String) parent.getItemAtPosition(position); //get position value
                System.out.println(selectedItem);
                Intent intent = new Intent(MyAllServicesActivity.this, EditServiceActivity.class);
                intent.putExtra("service",selectedItem);
                startActivity(intent);
                finish();


            }
        });
    }


    public void loadListData(){

        mDatabase = FirebaseDatabase.getInstance().getReference("services").child(sessionUserID);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, list);
        listView.setAdapter(adapter);

        mDatabase.orderByChild("userId").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        Service service = childSnapshot.getValue(Service.class);
                        list.add(service.getCategory());
                        adapter.notifyDataSetChanged();


                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }



        });



    }
}
