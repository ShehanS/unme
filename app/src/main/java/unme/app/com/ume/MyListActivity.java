package unme.app.com.ume;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import unme.app.com.ume.model.ClientService;
import unme.app.com.ume.model.Service;

public class MyListActivity extends AppCompatActivity {
    private ServiceListAdapter serviceListAdapter;
    private List<ClientService> mServiceList;
    private DatabaseReference mDatabase;
    private ListView listView;
    private SharedPreferences sharedPreferences;
    private String sessionUserID, sessionUser, userID, serviceID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);
        listView = findViewById(R.id.listView);
        sharedPreferences = getSharedPreferences("USER_LOGIN", MODE_PRIVATE); //session save name
        sessionUserID = sharedPreferences.getString("USER_ID", null); //session save key user id
        sessionUser = sharedPreferences.getString("USER", null);    //session save key username
        mServiceList = new ArrayList<>();


        mDatabase = FirebaseDatabase.getInstance().getReference("my-services");
        ValueEventListener roomsValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for ( DataSnapshot userDataSnapshot : dataSnapshot.getChildren() ) {
                    Object UserKey = userDataSnapshot.getKey();
                    if (userDataSnapshot != null) {
                        for ( DataSnapshot childSnapshot : userDataSnapshot.getChildren() ) {
                            Service service = childSnapshot.getValue(Service.class);
                            String serviceKey = childSnapshot.getKey();
                            System.out.println(serviceKey);
                            //list.add(service.getCompany());
                            mServiceList.add(new ClientService(1,String.valueOf(UserKey),service.getCompany(),service.getCategory(),serviceKey));
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
