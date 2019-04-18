package unme.app.com.ume;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class LandingPage2Activity extends AppCompatActivity {
    String sessionUserID, sessionUser;
    private SharedPreferences sharedPreferences;
    private TextView userLogin;
    private ImageButton btnProfile, btnLogOut, btnEditProfile, addServices, myServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page2);

        userLogin = findViewById(R.id.txtUsername);
        btnProfile = findViewById(R.id.btnProfile);
        addServices = findViewById(R.id.btnAddService);
        myServices = findViewById(R.id.btnMyServices);


        sharedPreferences = getSharedPreferences("USER_LOGIN", MODE_PRIVATE);
        sessionUserID = sharedPreferences.getString("USER_ID", null);
        sessionUser = sharedPreferences.getString("USER", null);

        userLogin.setText("Hi.." + sessionUser); //Landing page header
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertModal();//call profile edit popup
            }
        });

        addServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingPage2Activity.this, AddServiceActivity.class);
                startActivity(intent);
            }
        });

        myServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingPage2Activity.this, MyAllServicesActivity.class);
                startActivity(intent);
            }
        });

    }

    public void alertModal() {
        //Create alert
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.profile_alert_box, null);
        builder.setView(view);
        btnLogOut = view.findViewById(R.id.btnLogout);
        btnEditProfile = view.findViewById(R.id.btnProEdit);

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingPage2Activity.this, EditUserActivity.class);
                intent.putExtra("USER_ID", sessionUserID);
                startActivity(intent);
            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }



    public void Logout() {
        //Clear session data and logout
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        Intent intent = new Intent(LandingPage2Activity.this, LoginActivity.class);
        finish();
        startActivity(intent);

    }
}
