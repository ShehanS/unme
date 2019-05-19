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
import android.widget.Toast;

import com.google.android.gms.common.util.CollectionUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.jdo.PersistenceManager;


import unme.app.com.ume.model.ClientService;
import unme.app.com.ume.model.MyServiceList;
import unme.app.com.ume.model.Service;



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
    private String company, mycategory, message, contact, email, website, person, address;
    private Double mypackage;
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
                            System.out.println("++++++++++++++++++++++++++++FLITER ACTIVE++++++++++++");
                            System.out.println(category);
                            Service service = childSnapshot.getValue(Service.class);

                            String serviceKey = childSnapshot.getKey();

                            //list.add(service.getCompany());

                            if(service.getCategory().equals(category)) {


                                mServiceList.add(new ClientService(1, String.valueOf(UserKey), service.getCompany(), service.getCategory(), serviceKey));
                                //adapter.notifyDataSetChanged();
                                serviceListAdapter = new ServiceListAdapter(getApplicationContext(), mServiceList);

                                listView.setAdapter(serviceListAdapter);
                                serviceListAdapter.notifyDataSetChanged();
                            }

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
                        company = service.getCompany();
                        viewCategory.setText("Category : "+service.getCategory());
                        mycategory = service.getCategory();
                        viewMessage.setText("Messages : "+service.getMessage());
                        message = service.getMessage();
                        viewContact.setText("Contact : "+service.getContactNumber());
                        contact = service.getContactNumber();
                        viewEmail.setText("Email : "+service.getEmail());
                        email = service.getEmail();
                        viewWebsite.setText("Website : "+service.getWebsite());
                        website = service.getWebsite();
                        viewPackge.setText("Packeges "+String.valueOf(service.getPrice()));
                        mypackage = service.getPrice();
                        viewName.setText("Person : "+service.getFirstName()+" "+service.getLastName());
                        person = service.getFirstName()+" "+service.getLastName();
                        viewAddress.setText("Address : "+service.getAddress());
                        address = service.getAddress();
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
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        String currentDate = simpleDateFormat.format(new Date());
        Random random = new Random();
        String id = String.format("%08d", random.nextInt(100000000));
        mDatabase = FirebaseDatabase.getInstance().getReference("my-services");
        MyServiceList myServiceList = new MyServiceList(id,id,company,mycategory,message,contact,email,website,mypackage,person,address,currentDate,false);
        mDatabase.child(sessionUserID).child(id).setValue(myServiceList);
        Toast.makeText(getApplicationContext(), "Service added !", Toast.LENGTH_SHORT).show();





    }
});


    }
}

