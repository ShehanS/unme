package unme.app.com.ume;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageButton;
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

import java.util.Calendar;
import java.util.Date;
import java.util.Random;


import unme.app.com.ume.model.Countdown;
import unme.app.com.ume.model.Todo;

public class TodoList extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private String sessionUser, sessionUserID;
    private Button btnAddTask, btnSaveTask, btnDelete;
    private EditText taskName, task, editTaskName, editTask, editDate;
    private CheckedTextView checkedTextView;
    private DatabaseReference mDatabase;
    private ListView listView;
    private String selected;
    public static String LOG_APP = "[ToDo ] : ";
    ArrayList<String> list = new ArrayList<>();
    ArrayAdapter<String> adapter;

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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected = (String) parent.getItemAtPosition(position);
                System.out.println("Press alert");
                alertModal();

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
        final AlertDialog alert = builder.create();
        alert.show();

        btnSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Random random = new Random();
                String key = String.format("%08d", random.nextInt(10000));
                String MyTaskName, MyTask;
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = new Date();
                String currentDate = (formatter.format(date));
                MyTaskName = taskName.getText().toString();
                MyTask = task.getText().toString();


                if (TextUtils.isEmpty(MyTaskName)) {
                    Toast.makeText(getApplicationContext(), "Enter your task name !", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(MyTask)) {
                    Toast.makeText(getApplicationContext(), "Enter your task !", Toast.LENGTH_SHORT).show();
                    return;
                }



                Todo todo = new Todo(key, sessionUserID, MyTaskName, MyTask, "Ongoing", currentDate);
                mDatabase.child(sessionUserID).child(key).setValue(todo);



                    Toast.makeText(getApplicationContext(), "Add task !", Toast.LENGTH_SHORT).show();
                adapter.clear();
                alert.dismiss();



            }
        });



    }


    public void getData() {

        mDatabase = FirebaseDatabase.getInstance().getReference("todo").child(sessionUserID);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, list);
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

    public void alertModal() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.task_view, null);
        editTaskName = view.findViewById(R.id.editTaskName);
        editTask = view.findViewById(R.id.editTask);
        editDate = view.findViewById(R.id.editDate);
        btnDelete = view.findViewById(R.id.btnDelete);
        builder.setView(view);


        System.out.println("selected-"+selected);
        mDatabase = FirebaseDatabase.getInstance().getReference("todo").child(sessionUserID);

        Query query = mDatabase.orderByChild("taskName").equalTo(selected);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    Todo todo = childSnapshot.getValue(Todo.class);


                    editTaskName.setText(todo.getTaskName());
                    editTask.setText(todo.getTask());
                    editDate.setText(todo.getDate());


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println(databaseError.getMessage());
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase = FirebaseDatabase.getInstance().getReference("todo").child(sessionUserID).child(selected);
                 mDatabase.removeValue();

            }
        });



        AlertDialog alert = builder.create();
        alert.show();
    }

}








