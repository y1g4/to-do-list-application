package com.example.app;

import static android.content.Intent.getIntent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.LiveData;

import java.util.Date;

public class EditorActivity extends AppCompatActivity {


    // Constants for priority
    public static final int PRIORITY_HIGH = 1;
    public static final int PRIORITY_MEDIUM = 2;
    public static final int PRIORITY_LOW = 3;


    Button buttonAdd;
    EditText etTask;

    RadioButton radioButtonHigh;
    RadioButton radioButtonMedium;
    RadioButton radioButtonLow;


    AppDataBase mdb;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        buttonAdd = findViewById(R.id.saveButton);

        etTask = findViewById(R.id.editTextTaskDescription);


        radioButtonHigh = findViewById(R.id.radButton1);
        radioButtonMedium = findViewById(R.id.radButton2);
        radioButtonLow = findViewById(R.id.radButton3);


        mdb = AppDataBase.getsInstance(getApplicationContext());

        final Intent intent = getIntent();



        if (intent != null && intent.hasExtra("id")) {
            buttonAdd.setText("Update");
//
//            AppExecutor.getInstance().diskIO().execute(new Runnable() {
//                @Override
//                public void run() {

            final LiveData<Task> task = mdb.taskDao().getTaskById(intent.getIntExtra("id", 0));
//            Log.i("DetailActivity ", " Reading DataBAse");

            EditorScreenViewModel viewModel = new EditorScreenViewModel(mdb,intent.getIntExtra("id",0));

            viewModel.getTask().observe(EditorActivity.this, new Observer<Task>() {
                @Override
                public void onChanged(@Nullable Task task) {
                    final int priority = task.getPriority();

                    etTask.setText(task.getDescription());
                    Toast.makeText(EditorActivity.this, task.getDescription().toString(), Toast.LENGTH_SHORT).show();
                    setPriority(priority);

                }
            });




//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            etTask.setText(task.getDescription());
//
//                            Toast.makeText(EditorActivity.this,task.getDescription().toString(),Toast.LENGTH_SHORT).show();
//                            setPriority(priority);
//                        }
//                    });
//                }
//            });


        }


        buttonAdd.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                String text = etTask.getText().toString().trim();
                int priority = getPriorityFromViews();
                Date date = new Date();

                final Task task = new Task(text, priority, date);


                AppExecutor.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {

                        if (intent != null && intent.hasExtra("id")) {


                            task.setId(intent.getIntExtra("id", 0));
                            mdb.taskDao().updateTask(task);
                        } else {
                            mdb.taskDao().insertTask(task);


                        }

                        finish();


                    }
                });


            }
        });


    }


    /**
     * getPriority is called whenever the selected priority needs to be retrieved
     */
    public int getPriorityFromViews() {
        int priority = 1;
        int checkedId = ((RadioGroup) findViewById(R.id.radioGroup)).getCheckedRadioButtonId();
        switch (checkedId) {
            case R.id.radButton1:
                priority = PRIORITY_HIGH;
                break;
            case R.id.radButton2:
                priority = PRIORITY_MEDIUM;
                break;
            case R.id.radButton3:
                priority = PRIORITY_LOW;
        }
        return priority;
    }


    public void setPriority(int priority) {

        switch (priority) {

            case PRIORITY_HIGH:
                radioButtonHigh.setChecked(true);
                break;
            case PRIORITY_MEDIUM:
                radioButtonMedium.setChecked(true);
                break;
            case PRIORITY_LOW:
                radioButtonLow.setChecked(true);
                break;

        }

    }
}