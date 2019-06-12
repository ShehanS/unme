package unme.app.com.ume;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import unme.app.com.ume.model.Guest;
import unme.app.com.ume.model.Service;

public class AddServiceActivity extends AppCompatActivity {
    private EditText companyName, firstName, lastName, contactNumber, email, address, website, amount, message;
    private Button btnSave;
    private Spinner category;
    private DatabaseReference mDatabase;
    private SharedPreferences sharedPreferences;
    String sessionUserID, sessionUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);
        companyName = findViewById(R.id.txtCompany);
        firstName = findViewById(R.id.txtFirstName);
        lastName = findViewById(R.id.txtLastName);
        contactNumber = findViewById(R.id.txtContact);
        email = findViewById(R.id.txtEmail);
        address = findViewById(R.id.txtAddress);
        website = findViewById(R.id.txtWeb);
        message = findViewById(R.id.txtMessage);
        amount = findViewById(R.id.txtAmount);
        btnSave = findViewById(R.id.btnSave);
        category = findViewById(R.id.SelectCategory);

        sharedPreferences = getSharedPreferences("USER_LOGIN", MODE_PRIVATE); //session save name
        sessionUserID = sharedPreferences.getString("USER_ID", null); //session save key user id
        sessionUser = sharedPreferences.getString("USER", null);    //session save key username

        //create array adapter and assing to the spinner
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.category, android.R.layout.simple_spinner_item);
        //set xml value to the adapter
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter1);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase = FirebaseDatabase.getInstance().getReference("services");
                String CompanyName, FirstName, LastName, ContactNumber, Email, Address, Website, Category, Message, AmountString;
                Double Amount = 0.00;
                CompanyName = companyName.getEditableText().toString().trim();
                FirstName = firstName.getEditableText().toString().trim();
                LastName = lastName.getEditableText().toString().trim();
                ContactNumber = contactNumber.getEditableText().toString().trim();
                Email = email.getEditableText().toString().trim();
                Address = address.getEditableText().toString().trim();
                Website = website.getEditableText().toString().trim();
                Category = category.getSelectedItem().toString();
                AmountString = amount.getEditableText().toString().trim();
                Message = message.getEditableText().toString().trim();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
                String currentDate = simpleDateFormat.format(new Date());

                String key = UUID.randomUUID().toString();



        if(TextUtils.isEmpty(CompanyName)){
                    Toast.makeText(AddServiceActivity.this, "Please enter company name !", Toast.LENGTH_SHORT).show();
                return;
                }

                if(TextUtils.isEmpty(FirstName)){
                    Toast.makeText(AddServiceActivity.this, "Please first name !", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(LastName)){
                    Toast.makeText(AddServiceActivity.this, "Please last name !", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(Address)){
                    Toast.makeText(AddServiceActivity.this, "Please address !", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(AmountString)){

                }else{
                    Amount = Double.parseDouble(AmountString);
                }

                Service service = new Service(sessionUserID,key,CompanyName, FirstName, LastName, ContactNumber, Email, Address, Website, Category, currentDate, Amount, Message, false, false);
                mDatabase.child(sessionUserID).child(key).setValue(service);

                Toast.makeText(getApplicationContext(), "Adding service !", Toast.LENGTH_SHORT).show();



            }
        });






    }
}
