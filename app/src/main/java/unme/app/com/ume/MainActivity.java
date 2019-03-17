package unme.app.com.ume;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Random;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import unme.app.com.ume.UserModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
public static String LOG_APP = "U&ME : ";
    private Spinner userType;
private Button btnSave;
private DatabaseReference mDatabase;
    private EditText txtWebAddress, txtLastName, txtFirstName, txtUserName, txtSurename, txtPhone, txtEmail, txtAddress, txtUsername, txtPassword, txtUserID;
private RadioGroup radioGroup;
private RadioButton radioButton;
private FirebaseAuth auth;


private FirebaseAuth.AuthStateListener  authStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Random random = new Random();
        final String user_uuid =  String.format("%04d", random.nextInt(10000));
        mDatabase = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        txtUserID = (EditText) findViewById(R.id.userID);
        txtUserName = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtFirstName = (EditText) findViewById(R.id.txtFirstName);
        txtLastName = (EditText) findViewById(R.id.txtLastName);
        txtPhone = (EditText) findViewById(R.id.txtPhone);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtWebAddress = (EditText) findViewById(R.id.txtWeb);
        userType = (Spinner) findViewById(R.id.select1);
        btnSave = (Button) findViewById(R.id.btnSave);
        txtUserID.setText(user_uuid);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.select1, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userType.setAdapter(adapter1);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String UserID, UserName, Password, FirstName, LastName, Contact, Email, WebAddress, UserType;

                UserID = txtUserID.getText().toString().trim();
                UserName = txtUserName.getText().toString().trim();
                Password = txtPassword.getText().toString().trim();
                FirstName = txtFirstName.getText().toString().trim();
                LastName = txtLastName.getText().toString().trim();
                Contact = txtPhone.getText().toString().trim();
                Email = txtEmail.getText().toString().trim();
                WebAddress = txtWebAddress.getText().toString().trim();
                UserType = userType.getSelectedItem().toString();


                if (TextUtils.isEmpty(UserName)) {
                    Toast.makeText(getApplicationContext(), "Enter your Username !", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(Password)) {
                    Toast.makeText(getApplicationContext(), "Enter your Password !", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(FirstName)) {
                    Toast.makeText(getApplicationContext(), "Enter your First name !", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(LastName)) {
                    Toast.makeText(getApplicationContext(), "Enter your Last name !", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(Contact)) {
                    Toast.makeText(getApplicationContext(), "Enter your Contact number !", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(Email)) {
                    Toast.makeText(getApplicationContext(), "Enter Email address !", Toast.LENGTH_SHORT).show();
                    return;
                }

                /*

                auth.createUserWithEmailAndPassword(UserName, Password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                           auth.getCurrentUser().getUid();
                        if (!task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Authentication failed !" + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        }


                    }
                });

                */
                UserModel userModel = new UserModel(UserID, UserType, UserName, Password, FirstName, LastName, Contact, Email, WebAddress);
                mDatabase.child("users").child(user_uuid).setValue(userModel);
                Toast.makeText(MainActivity.this, "Create Profile !", Toast.LENGTH_LONG).show();


            }


        });




    }

}
