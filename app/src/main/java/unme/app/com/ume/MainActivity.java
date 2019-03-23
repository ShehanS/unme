package unme.app.com.ume;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Random;

import unme.app.com.ume.model.UserModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
public static String LOG_APP = "U&ME : ";
private Spinner userType;
private Button btnSave;
private DatabaseReference mDatabase;
    private EditText txtWebAddress, txtLastName, txtFirstName, txtUserName, txtPhone, txtEmail, txtPassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Random random = new Random();
        final String user_uuid =  String.format("%04d", random.nextInt(10000));
        mDatabase = FirebaseDatabase.getInstance().getReference();

        txtUserName = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtFirstName = (EditText) findViewById(R.id.txtFirstName);
        txtLastName = (EditText) findViewById(R.id.txtLastName);
        txtPhone = (EditText) findViewById(R.id.txtPhone);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtWebAddress = (EditText) findViewById(R.id.txtWeb);
        userType = (Spinner) findViewById(R.id.select1);
        btnSave = (Button) findViewById(R.id.btnSave);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.select1, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userType.setAdapter(adapter1);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String UserID, UserName, Password, FirstName, LastName, Contact, Email, WebAddress, UserType;

                UserID = user_uuid;
                UserName = txtUserName.getEditableText().toString().trim();
                Password = txtPassword.getEditableText().toString().trim();
                FirstName = txtFirstName.getEditableText().toString().trim();
                LastName = txtLastName.getEditableText().toString().trim();
                Contact = txtPhone.getEditableText().toString().trim();
                Email = txtEmail.getEditableText().toString().trim();
                WebAddress = txtWebAddress.getEditableText().toString().trim();
                UserType = userType.getSelectedItem().toString();


                if (TextUtils.isEmpty(UserName)) {
                    Toast.makeText(getApplicationContext(), "Enter your Username !", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(Password)) {
                    Toast.makeText(getApplicationContext(), "Enter your Password !", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (Password.length()<6) {
                    Toast.makeText(getApplicationContext(), "Password is short !", Toast.LENGTH_SHORT).show();
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

                if (Contact.length()<10){
                    Toast.makeText(getApplicationContext(), "Invalid number !", Toast.LENGTH_SHORT).show();
                    return;
                }



                if (TextUtils.isEmpty(Email)) {
                    Toast.makeText(getApplicationContext(), "Enter Email address !", Toast.LENGTH_SHORT).show();
                    return;
                }

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if (Email.matches(emailPattern)) {
                }else{
                    Toast.makeText(getApplicationContext(),"Invalid email address !", Toast.LENGTH_SHORT).show();
                    return;
                }


                UserModel userModel = new UserModel(UserID, UserType, UserName, Password, FirstName, LastName, Email, Contact, WebAddress);
                mDatabase.child("users").child(user_uuid).setValue(userModel);
                Toast.makeText(MainActivity.this, "Create Profile !", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                finish();
                startActivity(intent);



            }


        });




    }

}
