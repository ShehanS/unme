package unme.app.com.ume;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import unme.app.com.ume.model.Budget;
import unme.app.com.ume.model.MyBudgetList;
import unme.app.com.ume.model.MyServiceList;

public class BudgetActivity extends AppCompatActivity {
    private Button addBudget,btnViewAdd, btnViewClose;
   private EditText myBudget;
    private SharedPreferences sharedPreferences;
    private DatabaseReference mDatabase;
    private Button btnAdd,btnClose,btnAddClose;
    private String sessionUserID, sessionUser, userID, serviceID;
    private TextView txtOverFlow, serviceName, serviceBudget, servicContact, serviceNote, txtExpetedBudget, setMyBudget,  MycurrentBudget;
    private String uuid,ServiceName, ServiceContact,ServiceNote, ServiceUserId = "";
    private Double ServiceBudget;
    private Double myBudgetVal,currentBudget = 0.00;
    private ListView listView;
    private MyBudgetAdapter myBudgetAdapter;
    private List<MyBudgetList> myBudgetLists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);
        sharedPreferences = getSharedPreferences("USER_LOGIN", MODE_PRIVATE); //session save name
        sessionUserID = sharedPreferences.getString("USER_ID", null); //session save key user id
        sessionUser = sharedPreferences.getString("USER", null);    //session save key username
       // mDatabase=FirebaseDatabase.getInstance().getReference("my-budget").child(sessionUserID);
        txtExpetedBudget = findViewById(R.id.txtExpetedBudget);
        setMyBudget = findViewById(R.id.MyBudget);
        MycurrentBudget = findViewById(R.id.txtCurrentBudget);
        listView = findViewById(R.id.listView);
        txtOverFlow = findViewById(R.id.txtOverFlow);
        getMyExpectedBudget();
        getMyListBudget();




        System.out.println("+++BUDGETS+++");
        System.out.println("My Budget "+myBudgetVal);
        System.out.println("Current Budget "+currentBudget);

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                }else{
                   showAddBudget();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        setMyBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddBudget();
            }
        });
        addBudget = findViewById(R.id.btnAddBudget);
        addBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAddBudget();
            }
        });



    }




    public void showAddBudget(){

        mDatabase = FirebaseDatabase.getInstance().getReference("my-budget");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.alert_add_budget, null);
        btnAdd = view.findViewById(R.id.btnAdd);
        myBudget = view.findViewById(R.id.txtMyBudget);
        btnClose = view.findViewById(R.id.btnClose);
        builder.setView(view);
        final AlertDialog alert = builder.create();
       // myBudget.setText(myBudgetVal.toString());
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uuid = UUID.randomUUID().toString();
                SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(System.currentTimeMillis());
                String currentDate = (formatter.format(date));
                String checkEmptyBuget =myBudget.getText().toString().trim();
                if(TextUtils.isEmpty(checkEmptyBuget)){
                   Toast.makeText(getApplicationContext(),"Please enter your budget !", Toast.LENGTH_LONG).show();
                   return;
                }

                myBudgetVal = Double.parseDouble(myBudget.getText().toString().trim());
                Budget budget = new Budget(sessionUserID, myBudgetVal,currentDate);
                mDatabase.child(sessionUserID).setValue(budget);
                Toast.makeText(getApplicationContext(),"Budget added !", Toast.LENGTH_LONG).show();
                getMyExpectedBudget();
                alert.dismiss();

            }
        });
btnClose.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        alert.dismiss();
    }
});

        alert.show();
    }

    public void addAddBudget(){

        mDatabase = FirebaseDatabase.getInstance().getReference("my-services");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.add_budget_popup, null);
        serviceName = view.findViewById(R.id.txtServiceName);
        servicContact = view.findViewById(R.id.txtServiceContact);
        serviceBudget = view.findViewById(R.id.txtServiceBudget);
        serviceNote = view.findViewById(R.id.txtServiceNote);

        btnAdd = view.findViewById(R.id.btnAdd);
        btnAddClose = view.findViewById(R.id.btnClose);
         builder.setView(view);
        final AlertDialog alert = builder.create();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
                Date date = new Date(System.currentTimeMillis());
                String currentDate = (formatter.format(date));
                uuid = UUID.randomUUID().toString();
               ServiceName = serviceName.getEditableText().toString().trim();
               ServiceContact = servicContact.getEditableText().toString().trim();
               ServiceBudget =Double.valueOf(serviceBudget.getEditableText().toString());
               ServiceNote = serviceNote.getEditableText().toString().trim();

               if (TextUtils.isEmpty(ServiceName)) {
                   Toast.makeText(getApplicationContext(),"Please enter service !", Toast.LENGTH_LONG).show();
                   return;
               }


               if (currentBudget == 0.0){
                   currentBudget = ServiceBudget;
               }

                System.out.println("+++BUDGETS+++");
                System.out.println("My Budget "+myBudgetVal);
                System.out.println("Current Budget "+currentBudget);



               MyServiceList myServiceList = new MyServiceList(uuid, uuid,sessionUserID, "additional service", ServiceName, ServiceNote, ServiceContact, "Unkown","Unkown",ServiceBudget,"Unkown","Unkown",currentDate,true,"Active",ServiceBudget,"Unkown");
                mDatabase.child(sessionUserID).child(uuid).setValue(myServiceList);
                //Toast.makeText(getApplicationContext(),"Add additiona service !", Toast.LENGTH_LONG).show();
                currentBudget = 0.00;
                getMyListBudget();
                serviceName.setText("");
                servicContact.setText("");
                servicContact.setText("");
                serviceBudget.setText("00.00");
                serviceNote.setText("");
alert.dismiss();

            }
        });
        btnAddClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });
        alert.show();
    }


public void getMyExpectedBudget(){
    System.out.println("SESSION USER ID"+sessionUserID);
    mDatabase = FirebaseDatabase.getInstance().getReference("my-budget");
    Query query = mDatabase.orderByChild("userID").equalTo(sessionUserID);
    query.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
           for(DataSnapshot budgetSnapshot : dataSnapshot.getChildren()){
               System.out.println(budgetSnapshot);
               Budget budget = budgetSnapshot.getValue(Budget.class);
               txtExpetedBudget.setText("My Expected Budget "+budget.getBudget().toString() +" Rs");
               myBudgetVal = budget.getBudget();
           }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });
}

public void getMyListBudget(){
    myBudgetLists = new ArrayList<>();
    mDatabase = FirebaseDatabase.getInstance().getReference("my-services").child(sessionUserID);
    mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for(DataSnapshot serviceSnapshot : dataSnapshot.getChildren()){
                MyServiceList myServiceList = serviceSnapshot.getValue(MyServiceList.class);

                currentBudget += myServiceList.getBudget();
                myBudgetLists.add(new MyBudgetList(myServiceList.getCategory(),myServiceList.getBudget(),sessionUserID,myServiceList.getServiceID()));
                myBudgetAdapter = new MyBudgetAdapter(getApplicationContext(), myBudgetLists);
                listView.setAdapter(myBudgetAdapter);
                myBudgetAdapter.notifyDataSetChanged();
            }
            if (myBudgetVal<currentBudget){
                Toast.makeText(getApplicationContext(),"Your budget is overflow !", Toast.LENGTH_LONG).show();
                txtOverFlow.setText("Budget is overflow !");

            }else{
                txtOverFlow.setText("");
            }

            MycurrentBudget.setText("Current Budget "+currentBudget.toString()+" Rs");
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }


    });





}



}
