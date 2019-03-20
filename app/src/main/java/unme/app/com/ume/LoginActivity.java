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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtSingup = (TextView) findViewById(R.id.txtSingup);
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        sharedPreferences = getSharedPreferences("USER_LOGIN", MODE_PRIVATE);
        sessionUserID = sharedPreferences.getString("USER_ID", null);
        sessionUser = sharedPreferences.getString("USER", null);
        appSwitch = sharedPreferences.getString("APP_TYPE", null);

        System.out.println("======APP TYPE==========");
        System.out.println(appSwitch);
        System.out.println("======Session status ==========");
        System.out.println("sessionUserID-" + sessionUserID);
        System.out.println("sessionUser-" + sessionUser);

        if (((sessionUserID != null) || (sessionUser != null))){
            if (appSwitch =="SERVICE"){
                Intent intent = new Intent(LoginActivity.this, LandingPage2Activity.class);
                startActivity(intent);
                finish();

            }

            if(appSwitch=="CUSTOMER"){
                Intent intent = new Intent(LoginActivity.this, LandingPageActivity.class);
                startActivity(intent);
                finish();

            }

        }



        txtSingup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });

        btnLogin = findViewById(R.id.btnLogin);
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

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(getApplicationContext(), "Please enter username!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_SHORT).show();
            return;
        }

        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        Log.d(LOG_APP, "[Check user]");
        Query query = mDatabase.orderByChild("username").equalTo(username);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        UserModel userModel = childSnapshot.getValue(UserModel.class);

                        if (userModel.getUsername().equals(username) && userModel.getPassword().equals(password)) {
                            Intent customer_intent = new Intent(LoginActivity.this, LandingPageActivity.class);
                            Intent service_intent = new Intent(LoginActivity.this, LandingPage2Activity.class);
                            //create session
                            editor.putString("USER_ID", userModel.getUserId());
                            editor.putString("USER", userModel.getUsername());
                            editor.commit();

                            System.out.println(userModel.getType());
                            if (userModel.getType().equals("A customer")) {
                                editor.putString("APP_TYPE", "CUSTOMER");
                                editor.commit();
                                startActivity(customer_intent);
                                finish();

                            }else if(userModel.getType().equals("A service provider")){
                                editor.putString("APP_TYPE", "SERVICE");
                                editor.commit();
                                startActivity(service_intent);
                                finish();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "Password is wrong !", Toast.LENGTH_LONG).show();
                        }

                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Cannot find user !", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(LOG_APP, "loadPost:onCancelled", databaseError.toException());
            }
        });

    }
}







