package unme.app.com.ume;

import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import unme.app.com.ume.model.Service;

public class ViewServiceActivity extends AppCompatActivity {
    private TextView viewCompany, viewCategory,  viewMessage, viewContact, viewEmail, viewWebsite, viewPackge, viewName, viewAddress, txtUser;
    private Button btnClose, btnAdd;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_service);
        String userID = getIntent().getStringExtra("userid").trim();
        String serviceID = getIntent().getStringExtra("serviceid").trim();

        System.out.println("+++++++++++++++++++++++++++++++++++++");
        System.out.println(userID);
        System.out.println(serviceID);
        viewCompany = findViewById(R.id.txtCompany);
        viewCategory = findViewById(R.id.txtCategory);
        viewMessage = findViewById(R.id.txtMessage);
        viewContact =findViewById(R.id.txtContact);
        viewEmail = findViewById(R.id.txtEmail);
        viewWebsite = findViewById(R.id.txtWeb);
        viewPackge = findViewById(R.id.txtPackge);
        viewName = findViewById(R.id.txtName);
        btnClose = findViewById(R.id.btnClose);
        viewAddress =findViewById(R.id.txtAddress);
        btnAdd = findViewById(R.id.btnAdd);




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
              finish();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });





    }
}
