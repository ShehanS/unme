package unme.app.com.ume;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import unme.app.com.ume.model.ClientRequest;
import unme.app.com.ume.model.ClientService;
import unme.app.com.ume.model.MyServiceList;
import unme.app.com.ume.model.UserModel;

public class MyConfirmList extends AppCompatActivity {

    private ServiceListAdapter serviceListAdapter;
    private List<ClientService> mServiceList;
    private DatabaseReference mDatabase;
    private ListView listView;
    private SharedPreferences sharedPreferences;
    private String sessionUserID, sessionUser;
    private Button btnClose, btnRemove, btnSendRequest;
    private String ServiceID;
    private TextView title, company, name, contact, packge;

    String userId,fullName,Contact,Email,Web,Service,ServiceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_confirm_list);
        listView = findViewById(R.id.listView);
        sharedPreferences = getSharedPreferences("USER_LOGIN", MODE_PRIVATE); //session save name
        sessionUserID = sharedPreferences.getString("USER_ID", null); //session save key user id
        sessionUser = sharedPreferences.getString("USER", null);    //session save key username
        mServiceList = new ArrayList<>();
        listLoad();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView serviceId = view.findViewById(R.id.txtServiceID);
                ServiceID = serviceId.getText().toString();
                confirmDialog();


            }
        });


    }

    public void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.activity_service_confirm_remove, null);
        builder.setView(view);
        title = view.findViewById(R.id.txtTitle);
        company = view.findViewById(R.id.txtCompany);
        name = view.findViewById(R.id.txtName);
        contact = view.findViewById(R.id.txtContact);
        packge = view.findViewById(R.id.txtPackge);
        final AlertDialog alert = builder.create();
        mDatabase = FirebaseDatabase.getInstance().getReference("my-services").child(sessionUserID);
        Query query = mDatabase.orderByChild("serviceID").equalTo(ServiceID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {

                    MyServiceList myServiceList = userSnapshot.getValue(MyServiceList.class);
                    System.out.println(myServiceList.getCategory());
                    title.setText(myServiceList.getCategory());
                    company.setText(myServiceList.getCompany());
                    name.setText(myServiceList.getName());
                    contact.setText(myServiceList.getContact());
                    Service = myServiceList.getCategory();
                    ServiceId = myServiceList.getServiceID();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        btnClose = view.findViewById(R.id.btnClose);
        btnRemove = view.findViewById(R.id.btnRemove);
        btnSendRequest = view.findViewById(R.id.btnRequest);

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemoveService();
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });

        btnSendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest();
            }
        });


        alert.show();
    }

    public void listLoad() {
        mDatabase = FirebaseDatabase.getInstance().getReference("my-services");
        ValueEventListener roomsValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot userDataSnapshot : dataSnapshot.getChildren()) {
                    Object UserKey = userDataSnapshot.getKey();
                    if (userDataSnapshot != null) {
                        for (DataSnapshot childSnapshot : userDataSnapshot.getChildren()) {
                            MyServiceList myServiceList = childSnapshot.getValue(MyServiceList.class);
                            String serviceKey = childSnapshot.getKey();
                            //list.add(service.getCompany());

                            if (myServiceList.isConfirm() == true) {
                                mServiceList.add(new ClientService(1, String.valueOf(UserKey), myServiceList.getCompany(), myServiceList.getCategory(), serviceKey));
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

    public void RemoveService() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("my-services").child(sessionUserID).child(ServiceID);
        Map<String, Object> updates = new HashMap<String, Object>();
        updates.put("confirm", false);
        mDatabase.updateChildren(updates); //force to the update


        serviceListAdapter.notifyDataSetChanged();
        Toast.makeText(getApplicationContext(), "Remove !", Toast.LENGTH_SHORT).show();

        finish();
        startActivity(getIntent());


    }


    public void sendRequest() {


        System.out.println(sessionUserID);
        mDatabase = FirebaseDatabase.getInstance().getReference("users").child(sessionUserID);       //Get firebase table path
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserModel user = dataSnapshot.getValue(UserModel.class);
                userId = user.getUserId();
                fullName = user.getFirstname()+ " "+user.getLastname();
                Contact = user.getContact();
                Email = user.getEmail();
                Web = user.getWeb();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference("client-request");
        ClientRequest clientRequest = new ClientRequest(userId,fullName,Contact,Email,Web,Service,ServiceId,false);
        mDatabase.child(sessionUserID).setValue(clientRequest);

    }
}



