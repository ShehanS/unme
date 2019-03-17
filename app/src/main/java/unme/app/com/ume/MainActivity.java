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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
public static String LOG_APP = "U&ME : ";
private Spinner spinner1, spinner2;
private Button btnSave;
private DatabaseReference mDatabase;
private EditText txtName, txtSurename, txtPhone, txtEmil, txtAddress, txtUsername, txtPassword, txtUserID;
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
        txtName = (EditText) findViewById(R.id.txtName);
        txtSurename = (EditText) findViewById(R.id.txtSurename);
        txtPhone = (EditText) findViewById(R.id.txtPhone);
        txtEmil = (EditText) findViewById(R.id.txtEmail);
        txtAddress = (EditText) findViewById(R.id.txtAddress);
        spinner1 = (Spinner) findViewById(R.id.select1);
        spinner2 = (Spinner) findViewById(R.id.select2);
        radioGroup = (RadioGroup) findViewById(R.id.opt);
        btnSave = (Button) findViewById(R.id.btnSave);
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtUserID.setText(user_uuid);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.select1, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.select2, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name, surename, phone, email, address, select1, select2, select3, username, password;
                name = txtName.getText().toString().trim();
                surename = txtSurename.getText().toString().trim();
                phone = txtPhone.getText().toString().trim();
                email = txtEmil.getText().toString().trim();
                address = txtAddress.getText().toString().trim();
                select1 = spinner1.getSelectedItem().toString();
                select2 = spinner2.getSelectedItem().toString();


                username = txtUsername.getText().toString().trim();
                password = txtPassword.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getApplicationContext(), "Enter name!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(surename)) {
                    Toast.makeText(getApplicationContext(), "Enter surename!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(getApplicationContext(), "Enter contact number!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(address)) {
                    Toast.makeText(getApplicationContext(), "Enter address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (radioGroup.getCheckedRadioButtonId()==-1){
                    Toast.makeText(getApplicationContext(), "Please select category!", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    radioButton = (RadioButton) findViewById(selectedId);
                    select3 = radioButton.getText().toString();

                }

                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(getApplicationContext(), "Enter username!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }




                auth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        }


                    }
                });
                UserModel userModel = new UserModel(name, surename, phone, email, address, select1, select2, select3);
                mDatabase.child("users").child(user_uuid).setValue(userModel);
                Toast.makeText(MainActivity.this, "Create Profile", Toast.LENGTH_LONG).show();


            }




        });




    }

}
