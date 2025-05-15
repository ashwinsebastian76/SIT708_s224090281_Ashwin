package com.example.taskmanagerapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import android.graphics.Color;
import android.graphics.Typeface;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button myButton;
    AlertDialog theDialog;
    LinearLayout theLayout;
    Database myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myButton = findViewById(R.id.add);
        theLayout = findViewById(R.id.container);
        myDB = new Database(this);

        loadAllTasks();
        makeDialog();

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                theDialog.show();
            }
        });
    }

    public void makeDialog() {
        AlertDialog.Builder box = new AlertDialog.Builder(this);
        View popup = getLayoutInflater().inflate(R.layout.newtask, null);

        EditText nameInput = popup.findViewById(R.id.nameEdit);
        EditText descInput = popup.findViewById(R.id.descriptionEdit);
        EditText dateInput = popup.findViewById(R.id.dueDateEdit);

        TextView title = new TextView(this);
        title.setText("Add the task");
        title.setTextSize(20);
        title.setPadding(40, 30, 40, 10);
        title.setTypeface(null, Typeface.BOLD);

        box.setCustomTitle(title);
        box.setView(popup);
        box.setPositiveButton("Save", (dialog, i) -> {
            String taskN = nameInput.getText().toString().trim();
            String taskD = descInput.getText().toString().trim();
            String taskDate = dateInput.getText().toString().trim();

            if (checkData(taskN, taskD, taskDate)) {
                myDB.addTask(taskN, taskD, taskDate);
                loadAllTasks();
                Toast.makeText(MainActivity.this, "Task added!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Something's wrong. Fill all & use right date!", Toast.LENGTH_SHORT).show();
            }
        });

        box.setNegativeButton("Cancel", (dialog, i) -> { });

        theDialog = box.create();
    }

    public void loadAllTasks() {
        theLayout.removeAllViews();
        List<Task> taskList = myDB.getAllTasks();

        for (Task t : taskList) {
            putCard(t.getId(), t.getName(), t.getDescription(), t.getDueDate());
        }
    }

    public void putCard(int id, String n, String d, String date) {
        View view = getLayoutInflater().inflate(R.layout.taskadded, null);

        TextView nText = view.findViewById(R.id.name);
        TextView dText = view.findViewById(R.id.description);
        TextView dateText = view.findViewById(R.id.dueDate);
        Button delBtn = view.findViewById(R.id.delete);
        Button editBtn = view.findViewById(R.id.edit);

        nText.setText(n);
        dText.setText(d);
        dateText.setText(date);

        delBtn.setOnClickListener(v -> {
            myDB.deleteTask(id);
            loadAllTasks();
            Toast.makeText(MainActivity.this, "Deleted!", Toast.LENGTH_SHORT).show();
        });

        editBtn.setOnClickListener(v -> editBox(id, nText, dText, dateText));

        theLayout.addView(view);
    }

    public void editBox(int id, TextView nText, TextView dText, TextView dateText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View popup = getLayoutInflater().inflate(R.layout.newtask, null);

        EditText nEdit = popup.findViewById(R.id.nameEdit);
        EditText dEdit = popup.findViewById(R.id.descriptionEdit);
        EditText dateEdit = popup.findViewById(R.id.dueDateEdit);

        nEdit.setText(nText.getText().toString());
        dEdit.setText(dText.getText().toString());
        dateEdit.setText(dateText.getText().toString());

        TextView title = new TextView(this);
        title.setText("Edit");
        title.setTextSize(20);
        title.setPadding(40, 30, 40, 10);
        title.setTypeface(null, Typeface.BOLD);

        builder.setCustomTitle(title);
        builder.setView(popup);
        builder.setPositiveButton("Update", (dialog, i) -> {
            String newN = nEdit.getText().toString().trim();
            String newD = dEdit.getText().toString().trim();
            String newDate = dateEdit.getText().toString().trim();

            if (checkData(newN, newD, newDate)) {
                myDB.updateTask(id, newN, newD, newDate);
                loadAllTasks();
                Toast.makeText(MainActivity.this, "Updated!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Check fields & format!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, i) -> { });

        builder.create().show();
    }

    public boolean checkData(String n, String d, String date) {
        Log.d("Check", "Inputs - " + n + ", " + d + ", " + date);

        n = n.trim();
        d = d.trim();
        date = date.trim();

        if (n.isEmpty() || d.isEmpty() || date.isEmpty()) {
            return false;
        }

        String pattern = "^\\d{2}-\\d{2}-\\d{4}$";
        return date.matches(pattern);
    }
}
