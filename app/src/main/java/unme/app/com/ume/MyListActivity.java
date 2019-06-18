package unme.app.com.ume;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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

import unme.app.com.ume.model.ClientService;
import unme.app.com.ume.model.MyServiceList;
import unme.app.com.ume.model.Service;

public class MyListActivity extends AppCompatActivity {
    private ServiceListAdapter serviceListAdapter;
    private List<ClientService> mServiceList;
    private DatabaseReference mDatabase;
    private ListView listView;
    private SharedPreferences sharedPreferences;
    private String sessionUserID, sessionUser, userID, serviceID;
    private Button btnClose,btnRemove,btnConfirm;
    private String ServiceID;
    private  TextView title, company, name, contact, packge, notice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);
        listView = findViewById(R.id.listView);
        sharedPreferences = getSharedPreferences("USER_LOGIN", MODE_PRIVATE); //session save name
        sessionUserID = sharedPreferences.getString("USER_ID", null); //session save key user id
        sessionUser = sharedPreferences.getString("USER", null);    //session save key username
        mServiceList = new ArrayList<>();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView serviceId = view.findViewById(R.id.txtServiceID);
                ServiceID = serviceId.getText().toString();
                System.out.println("Service ID" + ServiceID);
                confirmDialog();


            }
        });

listLoad();

    }
    public void confirmDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.activity_service_confirm, null);
        builder.setView(view);
        title = view.findViewById(R.id.txtTitle);
        company = view.findViewById(R.id.txtCompany);
        name = view.findViewById(R.id.txtName);
        contact = view.findViewById(R.id.txtContact);
        packge = view.findViewById(R.id.txtPackge);
        notice = view.findViewById(R.id.txtNotice);
        final AlertDialog alert = builder.create();
        mDatabase = FirebaseDatabase.getInstance().getReference("my-services").child(sessionUserID);
        Query query = mDatabase.orderByChild("serviceID").equalTo(ServiceID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot userSnapshot : dataSnapshot.getChildren()){

                        MyServiceList myServiceList = userSnapshot.getValue(MyServiceList.class);
                        System.out.println(myServiceList.getCategory());
                        title.setText(myServiceList.getCategory());
                        company.setText(myServiceList.getCompany());
                        name.setText(myServiceList.getName());
                        contact.setText(myServiceList.getContact());
                        notice.setText(myServiceList.getMessage());

                    }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnClose = view.findViewById(R.id.btnClose);
        btnRemove = view.findViewById(R.id.btnRemove);
        btnClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });
        btnConfirm = view.findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(new OnClickListener() {

         @Override
        public void onClick(View v) {
         System.out.println("Service id "+ServiceID);
         mDatabase = FirebaseDatabase.getInstance().getReference().child("my-services").child(sessionUserID).child(ServiceID);
          Map<String, Object> updates = new HashMap<String, Object>();
         updates.put("confirm", true);
        mDatabase.updateChildren(updates); //force to the update

        Toast.makeText(getApplicationContext(), "Added !", Toast.LENGTH_SHORT).show();

       // finish();

        }});


        btnRemove.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                mDatabase = FirebaseDatabase.getInstance().getReference("my-services").child(sessionUserID);
                Query query = mDatabase.orderByChild("serviceID").equalTo(ServiceID);
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                            dataSnapshot1.getRef().removeValue();

                            serviceListAdapter.notifyDataSetChanged();
                            listLoad();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        System.out.println(databaseError.getMessage());
                    }
                });

                Toast.makeText(getApplicationContext(),"Remove service!", Toast.LENGTH_LONG).show();
                mServiceList.clear();



alert.dismiss();
            }
        });

        alert.show();
    }
    public void listLoad () {

        mServiceList.clear();

        mDatabase = FirebaseDatabase.getInstance().getReference("my-services");
        ValueEventListener roomsValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userDataSnapshot : dataSnapshot.getChildren()) {
                    Object UserKey = userDataSnapshot.getKey();
                    if (userDataSnapshot != null) {
                        for (DataSnapshot childSnapshot : userDataSnapshot.getChildren()) {
                            Service service = childSnapshot.getValue(Service.class);
                            String serviceKey = childSnapshot.getKey();
                            System.out.println(serviceKey);
                            //list.add(service.getCompany());
                            mServiceList.add(new ClientService(1, String.valueOf(UserKey), service.getCompany(), service.getCategory(), serviceKey));
                            //adapter.notifyDataSetChanged();
                            serviceListAdapter = new ServiceListAdapter(getApplicationContext(), mServiceList);

                            listView.setAdapter(serviceListAdapter);
                            serviceListAdapter.notifyDataSetChanged();

                        }
                    }else{
                       mServiceList.clear();
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
