package unme.app.com.ume;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import unme.app.com.ume.model.Event;
import unme.app.com.ume.model.MyServiceList;
import unme.app.com.ume.model.RequestClientList;
import unme.app.com.ume.model.Service;

public class ClientActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private SharedPreferences sharedPreferences;
    String sessionUserID, sessionUser;
    private RequestClientAdapter requestClientAdapter;
    private List<RequestClientList> mClientList;
    private ListView listView;
    private String ServiceID;
    private TextView txtClintName, txtClientContact, txtClientEvetDate, txtStatus, txtService, txtUserID, txtServiceID;
    private String clientName, clientContact, clientEventDate, clientStatus, clientUserID, clientServiceID, clientRequestService;
    private Button btnClose, btnAccept, btnCancel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        sharedPreferences = getSharedPreferences("USER_LOGIN", MODE_PRIVATE); //session save name
        sessionUserID = sharedPreferences.getString("USER_ID", null); //session save key user id
        sessionUser = sharedPreferences.getString("USER", null);    //session save key username
        listView = findViewById(R.id.listView);


        LoadList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView serviceId = view.findViewById(R.id.txtService);
                ServiceID = serviceId.getText().toString();
                System.out.println("OnItemClicking "+ServiceID);

                clientConfirmDialog();


            }
        });
    }


    public void LoadList(){
        mClientList = new ArrayList<>();
        System.out.println("SESSION USER "+sessionUserID);
        mDatabase = FirebaseDatabase.getInstance().getReference("client-request");
        ValueEventListener roomsValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userDataSnapshot : dataSnapshot.getChildren()) {

                    for(DataSnapshot childSnapshot : userDataSnapshot.getChildren()) {
                        ClientRequest client = childSnapshot.getValue(ClientRequest.class);
                        System.out.println("Client Userid "+client.getServiceUserID());
                        if (client.getServiceUserID().equals(sessionUserID)) {
                            mClientList.add(new RequestClientList(client.getUserId(), client.getName(), client.getContact(), client.getService(), client.getServiceId()));
                            requestClientAdapter = new RequestClientAdapter(getApplicationContext(), mClientList);
                            clientName = client.getName();
                            clientContact = client.getContact();
                            clientEventDate = client.getEventDate();
                            clientStatus = client.getStatus();
                            clientUserID = client.getUserId();
                            clientServiceID = client.getServiceId();
                            clientRequestService = client.getService();
                            listView.setAdapter(requestClientAdapter);
                            requestClientAdapter.notifyDataSetChanged();
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



 public void clientConfirmDialog() {
        System.out.println(sessionUserID+"/"+ServiceID);
        System.out.println("Dialog");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.client_response, null);
        builder.setView(view);
        txtClintName = view.findViewById(R.id.txtName);
        txtClientContact = view.findViewById(R.id.txtContact);
        txtClientEvetDate = view.findViewById(R.id.txtEventDate);
        txtStatus = view.findViewById(R.id.txtStatus);
        txtService = view.findViewById(R.id.txtService);
        txtUserID = view.findViewById(R.id.txtUserID);
        txtServiceID = view.findViewById(R.id.txtServiceID);
        btnAccept = view.findViewById(R.id.btnAccept);
        btnCancel =  view.findViewById(R.id.btnCancel);
        txtClintName.setText(clientName);
        txtClientContact.setText(clientContact);
        txtClientEvetDate.setText(clientEventDate);
        txtStatus.setText(clientStatus);
        txtUserID.setText(clientUserID);
        txtServiceID.setText(clientServiceID);
        txtService.setText(clientRequestService);

        final AlertDialog alert = builder.create();
        btnClose = view.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase = FirebaseDatabase.getInstance().getReference().child("client-request").child(clientUserID).child(clientServiceID);
                Map<String, Object> updatesClient = new HashMap<String, Object>();
                updatesClient.put("status", "Accepted");
                mDatabase.updateChildren(updatesClient); //force to the update

                mDatabase = FirebaseDatabase.getInstance().getReference().child("my-services").child(clientUserID).child(clientServiceID);
                Map<String, Object> updatesMyService = new HashMap<String, Object>();
                updatesMyService.put("status", "Accepted");
                mDatabase.updateChildren(updatesMyService); //force to the update
                Toast.makeText(getApplicationContext(), "Accept user request !", Toast.LENGTH_SHORT).show();

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase = FirebaseDatabase.getInstance().getReference().child("client-request").child(clientUserID).child(clientServiceID);
                Map<String, Object> updatesClient = new HashMap<String, Object>();
                updatesClient.put("status", "Cancel");
                mDatabase.updateChildren(updatesClient); //force to the update

                mDatabase = FirebaseDatabase.getInstance().getReference().child("my-services").child(clientUserID).child(clientServiceID);
                Map<String, Object> updatesMyService = new HashMap<String, Object>();
                updatesMyService.put("status", "Cancel");
                mDatabase.updateChildren(updatesMyService); //force to the update
                Toast.makeText(getApplicationContext(), "Cancel user request !", Toast.LENGTH_SHORT).show();
            }
        });



        alert.show();



    }


}
