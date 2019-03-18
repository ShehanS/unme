package unme.app.com.ume;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import unme.app.com.ume.model.UserModel;

public class EditUserActivity extends AppCompatActivity {
    public static String LOG_APP = "[EditUserActivity ] : ";
    private DatabaseReference mDatabase;
    private Spinner userType;
    private Button btnSave;
    private EditText txtWebAddress, txtLastName, txtFirstName, txtUserName, txtSurename, txtPhone, txtEmail, txtAddress, txtUsername, txtPassword, txtUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        String USER_ID = getIntent().getStringExtra("USER_ID");
        System.out.println(USER_ID);
        txtUserName = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtFirstName = (EditText) findViewById(R.id.txtFirstName);
        txtLastName = (EditText) findViewById(R.id.txtLastName);
        txtPhone = (EditText) findViewById(R.id.txtPhone);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtWebAddress = (EditText) findViewById(R.id.txtWeb);
        userType = (Spinner) findViewById(R.id.select1);
        btnSave = (Button) findViewById(R.id.btnSave);

        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        Query query = mDatabase.orderByKey().equalTo(USER_ID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
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


    }
}
