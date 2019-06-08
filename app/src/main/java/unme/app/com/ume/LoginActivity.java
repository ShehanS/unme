package unme.app.com.ume;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import unme.app.com.ume.model.UserModel;


public class LoginActivity extends AppCompatActivity {
    public static String LOG_APP = "[LoginActivity ] : ";
    private EditText txtUsername, txtPassword;
    private Button btnLogin;
    private TextView txtSingup;
    private DatabaseReference mDatabase;
    private SharedPreferences sharedPreferences;
    String sessionUserID, sessionUser, appSwitch;
    //App create app  switch intent(Customer and Service)
    private  Intent customer_intent, service_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Create object from the xml
        txtSingup = findViewById(R.id.txtSingup);
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        //Create session save keys
        sharedPreferences = getSharedPreferences("USER_LOGIN", MODE_PRIVATE); //session save name
        sessionUserID = sharedPreferences.getString("USER_ID", null); //session save key user id
        sessionUser = sharedPreferences.getString("USER", null);    //session save key username
        appSwitch = sharedPreferences.getString("APP_TYPE", null);  //session save key app switch (customer or service)

        System.out.println("======APP TYPE==========");
        System.out.println("======Session status ==========");
        System.out.println(appSwitch);
        System.out.println("sessionUserID-" + sessionUserID);
        System.out.println("sessionUser-" + sessionUser);

        customer_intent = new Intent(LoginActivity.this, LandingPageActivity.class); //initialize customer intenat
        service_intent = new Intent(LoginActivity.this, LandingPage2Activity.class); //initialize service intenat



        if (((sessionUserID != null) || (sessionUser != null))){  //check onetime login
        if (appSwitch.equals("Service")){                         //check appSwitch value
            finish();
            startActivity(service_intent);                      //goto service LandingPage2Activity
        }
        if(appSwitch.equals("Customer")){                       //goto service LandingPageActivity
            finish();
            startActivity(customer_intent);


        }
        }



        txtSingup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {   //goto singup page  MainActivity

                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        });

        btnLogin = findViewById(R.id.btnLogin);         //call singin method
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });


    }






    private void login() {
        Log.d(LOG_APP, "Start Login");
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        final String username, password;
        username = txtUsername.getText().toString();
        password = txtPassword.getText().toString();
//check input box value
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(getApplicationContext(), "Please enter username!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_SHORT).show();
            return;
        }

        mDatabase = FirebaseDatabase.getInstance().getReference("users");       //Get firebase table path
        Log.d(LOG_APP, "[Check user]");
        Query query = mDatabase.orderByChild("username").equalTo(username);          //Check entered username in the User collection
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {// check database availability
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) { // check all the data in the database one by one
                        UserModel userModel = childSnapshot.getValue(UserModel.class);

                        if (userModel.getUsername().equals(username) && userModel.getPassword().equals(password)) {  //set to data usermodel
                            //create session
                            if(userModel.isActive()==true) {
                                editor.putString("USER_ID", userModel.getUserId());//assign value for session
                                editor.putString("USER", userModel.getUsername());//assign value for session
                                editor.commit(); //save session

                                System.out.println(userModel.getType());
                                if (userModel.getType().equals("Customer")) {
                                    editor.putString("APP_TYPE", userModel.getType()); //assign value for session
                                    editor.commit();//save session
                                    startActivity(customer_intent); //start customer intenat
                                    finish();//close login window

                                } else if (userModel.getType().equals("Service")) {
                                    editor.putString("APP_TYPE", userModel.getType());//assign value for session
                                    editor.commit();//save session
                                    startActivity(service_intent);//start service intenat
                                    finish();//close login window
                                }
                            }else{
                                Toast.makeText(LoginActivity.this, "Please active your account !", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            // if password is wrong show toast
                            Toast.makeText(LoginActivity.this, "Password is wrong !", Toast.LENGTH_LONG).show();
                        }

                    }
                } else {
                    // if no user in the database show toast
                    Toast.makeText(LoginActivity.this, "Cannot find user !", Toast.LENGTH_LONG).show();
                }
            }
//show database error
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println(databaseError.toException());
            }
        });

    }





}







