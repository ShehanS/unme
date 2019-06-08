package unme.app.com.ume;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import unme.app.com.ume.model.Service;
import unme.app.com.ume.model.Todo;

public class EditServiceActivity extends AppCompatActivity {
    private EditText companyName, firstName, lastName, contactNumber, email, address, website, amount, category, message;
    private Button btnUpdate, btnDelete;
    private DatabaseReference mDatabase;
    private SharedPreferences sharedPreferences;
    private Double Amount = 0.00;
    String sessionUserID, sessionUser, selectedItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_service);
        selectedItem = getIntent().getStringExtra("service");
        companyName = findViewById(R.id.txtCompany);
        firstName = findViewById(R.id.txtFirstName);
        lastName = findViewById(R.id.txtLastName);
        contactNumber = findViewById(R.id.txtContact);
        email = findViewById(R.id.txtEmail);
        address = findViewById(R.id.txtAddress);
        website = findViewById(R.id.txtWeb);
        message = findViewById(R.id.txtMessage);
        amount = findViewById(R.id.txtAmount);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        category = findViewById(R.id.txtCategory);
        sharedPreferences = getSharedPreferences("USER_LOGIN", MODE_PRIVATE); //session save name
        sessionUserID = sharedPreferences.getString("USER_ID", null);//session save key user id
        sessionUser = sharedPreferences.getString("USER", null); //session save key username
        System.out.println(getIntent().getStringExtra("service"));
        mDatabase = FirebaseDatabase.getInstance().getReference("services").child(sessionUserID);
        mDatabase.orderByChild(sessionUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                   Service service = childSnapshot.getValue(Service.class);
                    companyName.setText(service.getCompany());
                    firstName.setText(service.getFirstName());
                    lastName.setText(service.getLastName());
                    contactNumber.setText(service.getContactNumber());
                    email.setText(service.getEmail());
                    address.setText(service.getAddress());
                    website.setText(service.getWebsite());
                    message.setText(service.getMessage());
                    amount.setText(String.valueOf(service.getPrice()));
                    category.setText(service.getCategory());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println(databaseError.getMessage());
            }
        });


btnUpdate.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        UpdateService();
    }
});

btnDelete.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
     DeleteService();
    }
});

    }

    public void UpdateService() {
        mDatabase = FirebaseDatabase.getInstance().getReference("services").child(sessionUserID);
        Query query = mDatabase.orderByChild("category").equalTo(selectedItem);
        final String CompanyName, FirstName, LastName, ContactNumber, Email, Address, Website, Category, Message, AmountString;
        CompanyName = companyName.getEditableText().toString().trim();
        FirstName = firstName.getEditableText().toString().trim();
        LastName = lastName.getEditableText().toString().trim();
        ContactNumber = contactNumber.getEditableText().toString().trim();
        Email = email.getEditableText().toString().trim();
        Address = address.getEditableText().toString().trim();
        Website = website.getEditableText().toString().trim();
        AmountString = amount.getEditableText().toString().trim();
        Message = message.getEditableText().toString().trim();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        Category = category.getEditableText().toString().trim();
        final String currentDate = simpleDateFormat.format(new Date());



        if (TextUtils.isEmpty(CompanyName)) {
            Toast.makeText(EditServiceActivity.this, "Please enter company name !", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(FirstName)) {
            Toast.makeText(EditServiceActivity.this, "Please first name !", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(LastName)) {
            Toast.makeText(EditServiceActivity.this, "Please last name !", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(Address)) {
            Toast.makeText(EditServiceActivity.this, "Please address !", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(AmountString)) {

        } else {
            Amount = Double.parseDouble(AmountString);
        }

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) { // database updte by edit text box values


                    dataSnapshot1.getRef().child("address").setValue(Address);
                    dataSnapshot1.getRef().child("category").setValue(Category);
                    dataSnapshot1.getRef().child("company").setValue(CompanyName);
                    dataSnapshot1.getRef().child("contactNumber").setValue(ContactNumber);
                    dataSnapshot1.getRef().child("date").setValue(currentDate);
                    dataSnapshot1.getRef().child("email").setValue(Email);
                    dataSnapshot1.getRef().child("firstName").setValue(FirstName);
                    dataSnapshot1.getRef().child("lastName").setValue(LastName);
                    dataSnapshot1.getRef().child("message").setValue(Message);
                    dataSnapshot1.getRef().child("price").setValue(Amount);
                    dataSnapshot1.getRef().child("website").setValue(Website);


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println(databaseError.getMessage());
            }
        });



        Toast.makeText(getApplicationContext(), "Updated service !", Toast.LENGTH_SHORT).show();


    }

    public void DeleteService(){
        mDatabase = FirebaseDatabase.getInstance().getReference("services").child(sessionUserID);
        Query query = mDatabase.orderByChild("category").equalTo(selectedItem);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    dataSnapshot1.getRef().removeValue();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println(databaseError.getMessage());
            }
        });

        Toast.makeText(getApplicationContext(),"Remove Service !", Toast.LENGTH_LONG).show();
       finish();
       Intent intent = new Intent(EditServiceActivity.this, MyAllServicesActivity.class);
       startActivity(intent);


    }


}
