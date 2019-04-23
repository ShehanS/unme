package unme.app.com.ume;


import android.content.SharedPreferences;

import android.support.annotation.NonNull;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;

import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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
    String sessionUserID, sessionUser, userID, serviceID;
    private ListView listView;
    private ServiceListAdapter serviceListAdapter;
    private List<ClientService> mServiceList;
    private Button btnClose, btnAdd;
    ArrayList<String> list = new ArrayList<>(); //create array list
    ArrayAdapter<String> adapter; //create array adapter
    private TextView viewCompany, viewCategory, viewMessage, viewContact, viewEmail, viewWebsite, viewPackge, viewName, viewAddress;

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
        GetCategory();
       // listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView serviceId = view.findViewById(R.id.txtServiceID);
                TextView userId = view.findViewById(R.id.txtUserID);
               userID = userId.getText().toString().trim();
               serviceID = serviceId.getText().toString().trim();
                showService();




            }
        });


    }

    public void GetCategory(){
        mServiceList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, list);
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

        mDatabase = FirebaseDatabase.getInstance().getReference("services");
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

    public void showService(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.view_service, null);
        viewCompany = view.findViewById(R.id.txtCompany);
        viewCategory = view.findViewById(R.id.txtCategory);
        viewMessage = view.findViewById(R.id.txtMessage);
        viewContact = view.findViewById(R.id.txtContact);
        viewEmail = view.findViewById(R.id.txtEmail);
        viewWebsite = view.findViewById(R.id.txtWeb);
        viewPackge = view.findViewById(R.id.txtPackge);
        viewName = view.findViewById(R.id.txtName);
        btnClose = view.findViewById(R.id.btnClose);
        viewAddress = view.findViewById(R.id.txtAddress);
        btnAdd = view.findViewById(R.id.btnAdd);
        builder.setView(view);
        final AlertDialog alert = builder.create();
        alert.show();

        mDatabase = FirebaseDatabase.getInstance().getReference("services").child(userID);
        Query query = mDatabase.orderByChild("serviceID").equalTo(serviceID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot childSnapshot: dataSnapshot.getChildren()) {

                        Service service = childSnapshot.getValue(Service.class);
                        viewCompany.setText("Company : "+service.getCompany());
                        viewCategory.setText("Category : "+service.getCategory());
                        viewMessage.setText("Messages : "+service.getMessage());
                        viewContact.setText("Contact : "+service.getContactNumber());
                        viewEmail.setText("Email : "+service.getEmail());
                        viewWebsite.setText("Website : "+service.getWebsite());
                        viewPackge.setText("Packeges "+String.valueOf(service.getPrice()));
                        viewName.setText("Person : "+service.getFirstName()+" "+service.getLastName());
                        viewAddress.setText("Address : "+service.getAddress());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





btnClose.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        alert.dismiss();
    }
});

btnAdd.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        showService();
    }
});


    }
}

