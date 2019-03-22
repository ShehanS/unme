package unme.app.com.ume;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;

import java.util.ArrayList;

import java.util.Date;
import java.util.Random;


import unme.app.com.ume.model.Todo;

public class TodoList extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private String sessionUser, sessionUserID;
    private Button btnAddTask, btnSaveTask;
    private EditText taskName, task;
    private CheckedTextView checkedTextView;
    private DatabaseReference mDatabase;
   private ListView listView;
    public static String LOG_APP = "[ToDo ] : ";
    ArrayList<String> list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        sharedPreferences = getSharedPreferences("USER_LOGIN", MODE_PRIVATE);
        sessionUserID = sharedPreferences.getString("USER_ID", null);
        sessionUser = sharedPreferences.getString("USER", null);
        btnAddTask = findViewById(R.id.btnAddTask);
        listView = findViewById(R.id.listView);

        getData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                String taskName =(String) parent.getItemAtPosition(position);

            }
        });

        if ((sessionUser == null) || (sessionUser == null)) {
            Intent intent = new Intent(TodoList.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTodo();
            }
        });
    }




    public void addTodo() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.todo, null);
        builder.setView(view);
        btnSaveTask = view.findViewById(R.id.btnSave);
        taskName = view.findViewById(R.id.txtTaskName);
        task = view.findViewById(R.id.txtTask);
        checkedTextView = view.findViewById(R.id.taskStatus);
        mDatabase = FirebaseDatabase.getInstance().getReference("todo");



        btnSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                String key =  String.format("%08d", random.nextInt(10000));
                String MyTaskName, MyTask;
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = new Date();
                String currentDate = (formatter.format(date));
                MyTaskName = taskName.getText().toString();
                MyTask = task.getText().toString();

                Todo todo = new Todo(key,sessionUserID, MyTaskName , MyTask,"Ongoing", currentDate );
                mDatabase.child(sessionUserID).child(key).setValue(todo);

            }
        });






        AlertDialog alert = builder.create();
        alert.show();
    }


    public void getData(){
        mDatabase = FirebaseDatabase.getInstance().getReference("todo").child(sessionUserID);
        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,list);
        listView.setAdapter(adapter);
        mDatabase.orderByChild(sessionUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    Todo todo = childSnapshot.getValue(Todo.class);
                    list.add(todo.getTaskName());
                    adapter.notifyDataSetChanged();

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println(databaseError.getMessage());
            }
        });


    }

}








