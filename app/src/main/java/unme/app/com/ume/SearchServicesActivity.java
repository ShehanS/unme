package unme.app.com.ume;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import unme.app.com.ume.model.ClientService;
import unme.app.com.ume.model.Service;
import unme.app.com.ume.model.Todo;

public class SearchServicesActivity extends AppCompatActivity {
    private String category;
    private SharedPreferences sharedPreferences;
    private DatabaseReference mDatabase;
    String sessionUserID, sessionUser;
    private ListView listView;
    private ServiceListAdapter serviceListAdapter;
    private List<ClientService> mServiceList;
    ArrayList<String> list = new ArrayList<>(); //create array list
    ArrayAdapter<String> adapter; //create array adapter
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        sharedPreferences = getSharedPreferences("USER_LOGIN", MODE_PRIVATE); //session save name
        sessionUserID = sharedPreferences.getString("USER_ID", null); //session save key user id
        sessionUser = sharedPreferences.getString("USER", null);    //session save key username
        category = getIntent().getStringExtra("category");
        listView = findViewById(R.id.listView);
        listView = findViewById(R.id.listView);
        System.out.println(category);
        GetCategory();

    }

    public void GetCategory(){
        mServiceList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, list);
        listView.setAdapter(adapter);
        mDatabase = FirebaseDatabase.getInstance().getReference("services");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = view.findViewById(R.id.txtServiceID);
                System.out.println(textView.getText());
            }
        });



/*
        Query query = FirebaseDatabase.getInstance().getReference("services").orderByChild("category").equalTo(category);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for ( DataSnapshot userDataSnapshot : dataSnapshot.getChildren() ) {
                    if (userDataSnapshot != null) {
                        for ( DataSnapshot roomDataSnapshot : userDataSnapshot.getChildren() ) {
                            for(DataSnapshot childSnapshot: roomDataSnapshot.getChildren()) {
                                Service service = childSnapshot.getValue(Service.class);
                                System.out.println(service);
                                list.add(service.getCompany());
                                adapter.notifyDataSetChanged();
                            }

                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println(databaseError.getMessage());
            }
        });

*/


        ValueEventListener roomsValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for ( DataSnapshot userDataSnapshot : dataSnapshot.getChildren() ) {
                    if (userDataSnapshot != null) {
                        for ( DataSnapshot childSnapshot : userDataSnapshot.getChildren() ) {
                            Service service = childSnapshot.getValue(Service.class);
                            String serviceKey = childSnapshot.getKey();
                            //list.add(service.getCompany());
                           mServiceList.add(new ClientService(1,serviceKey,service.getCompany(),service.getCategory(),0));
                            //adapter.notifyDataSetChanged();
                            serviceListAdapter = new ServiceListAdapter(getApplicationContext(),mServiceList);
                            listView.setAdapter(serviceListAdapter);
                            serviceListAdapter.notifyDataSetChanged();

                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(databaseError.getMessage());
            }
        };

        mDatabase.addListenerForSingleValueEvent(roomsValueEventListener);



    }
}
