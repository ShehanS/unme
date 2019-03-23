package unme.app.com.ume;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import java.util.HashMap;
import java.util.Map;

import unme.app.com.ume.model.UserModel;

public class EditUserActivity extends AppCompatActivity {
    public static String LOG_APP = "[EditUserActivity ] : ";
    String USER_ID, USER_TYPE;
    private DatabaseReference mDatabase;
    private Spinner userType;
    private Button btnSave;
    private SharedPreferences sharedPreferences;
    private String sessionUser, sessionUserID;
    private EditText txtWebAddress, txtLastName, txtFirstName, txtUserName, txtSurename, txtPhone, txtEmail, txtAddress, txtUsername, txtPassword, txtUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        USER_ID = getIntent().getStringExtra("USER_ID");

        txtUserName = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtFirstName = (EditText) findViewById(R.id.txtFirstName);
        txtLastName = (EditText) findViewById(R.id.txtLastName);
        txtPhone = (EditText) findViewById(R.id.txtPhone);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtWebAddress = (EditText) findViewById(R.id.txtWeb);
        userType = (Spinner) findViewById(R.id.select1);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateUser();
            }
        });
        mDatabase = FirebaseDatabase.getInstance().getReference("users");

        sharedPreferences = getSharedPreferences("USER_LOGIN", MODE_PRIVATE);
        sessionUserID = sharedPreferences.getString("USER_ID", null);
        sessionUser = sharedPreferences.getString("USER", null);

        Query query = mDatabase.orderByKey().equalTo(USER_ID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Load data to the UserModel

                if (dataSnapshot.exists()) {
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        UserModel userModel = childSnapshot.getValue(UserModel.class);
                        txtUserName.setText(userModel.getUsername());
                        txtPassword.setText(userModel.getPassword());
                        txtFirstName.setText(userModel.getFirstname());
                        txtLastName.setText((userModel.getLastname()));
                        txtPhone.setText(userModel.getContact());
                        txtEmail.setText(userModel.getEmail());
                        txtWebAddress.setText(userModel.getWeb());
                        USER_ID = userModel.getUserId();
                        USER_TYPE = userModel.getType();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(LOG_APP, "onCalled", databaseError.toException());
                Toast.makeText(EditUserActivity.this, "Database error !", Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void UpdateUser() {
        //Update user
        System.out.println("USER ID in EDIT "+sessionUserID);
        String UserName, Password, FirstName, LastName, Contact, Email, WebAddress, UserType;
        UserName = txtUserName.getText().toString().trim();
        Password = txtPassword.getText().toString().trim();
        FirstName = txtFirstName.getText().toString().trim();
        LastName = txtLastName.getText().toString().trim();
        Contact = txtPhone.getText().toString().trim();
        Email = txtEmail.getText().toString().trim();
        WebAddress = txtWebAddress.getText().toString().trim();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(USER_ID);
        Map<String, Object> updates = new HashMap<String, Object>();
        updates.put("contact", Contact);
        updates.put("email", Email);
        updates.put("firstname", FirstName);
        updates.put("lastname", LastName);
        updates.put("password", Password);
        updates.put("userId", sessionUserID);
        updates.put("username", UserName);
        updates.put("web", WebAddress);

        mDatabase.updateChildren(updates);
        Toast.makeText(getApplicationContext(), "Updated !", Toast.LENGTH_SHORT).show();
        finish();

    }
}
