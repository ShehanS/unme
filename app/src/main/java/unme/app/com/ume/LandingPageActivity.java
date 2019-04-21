package unme.app.com.ume;


import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.content.SharedPreferences;



public class LandingPageActivity extends AppCompatActivity {
    //create object from xml
    private TextView userLogin;
    private SharedPreferences sharedPreferences;
    String sessionUserID, sessionUser;
    private ImageButton btnProfile, btnLogOut, btnEditProfile, btnCountDown, btnToDo, btnGuest, btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        //Get login user id
        btnGuest = findViewById(R.id.btnGust);
        userLogin = findViewById(R.id.txtUsername);
        btnProfile = findViewById(R.id.btnProfile);
        btnCountDown = findViewById(R.id.btnCountDown);
        btnSearch = findViewById(R.id.btnSearch);
        btnToDo =  findViewById(R.id.btnTodo);
        sharedPreferences = getSharedPreferences("USER_LOGIN", MODE_PRIVATE); //session save name
        sessionUserID = sharedPreferences.getString("USER_ID", null);//session save key user id
        sessionUser = sharedPreferences.getString("USER", null); //session save key username

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingPageActivity.this, CategoryActivity.class);
                startActivity(intent);
            }
        });
        userLogin.setText("Hi.." + sessionUser); //Landing page header
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertModal();//call profile edit popup
            }
        });
        btnToDo.setOnClickListener(new View.OnClickListener() {
            @Override
            // call todo list page
            public void onClick(View v) {
                Intent intent = new Intent(LandingPageActivity.this, TodoList.class);
                startActivity(intent);
            }
        });
        btnCountDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // call countdown page
                Intent intent = new Intent(LandingPageActivity.this, CountdownActivity.class);
                startActivity(intent);
            }
        });


        btnGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingPageActivity.this, GuestViewActivity.class);
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
                Intent intent = new Intent(LandingPageActivity.this, EditUserActivity.class);
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
        Intent intent = new Intent(LandingPageActivity.this, LoginActivity.class);
        finish();
        startActivity(intent);

    }
}
