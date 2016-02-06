package edu.berkeley.cs160.crunch_time;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class Main2Activity extends AppCompatActivity {

    HashMap<String, Integer> ex_to_cal = new HashMap<String, Integer>();
    String exercise_type;
    int cals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        set_cal_values();

        set_spinner_values();
        final Spinner spinner = (Spinner) findViewById(R.id.Exercises);
        final EditText txt_field = (EditText) findViewById(R.id.Input);
        final TextView cal_field = (TextView) findViewById(R.id.BurnedCal);
        final TextView alt1 = (TextView) findViewById(R.id.Alt1);
        final TextView alt2 = (TextView) findViewById(R.id.Alt2);
        final TextView alt3 = (TextView) findViewById(R.id.Alt3);
        final TextView units_field = (TextView) findViewById(R.id.Units);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                //final TextView cal_field = (TextView) findViewById(R.id.BurnedCal);
                cal_field.setText(""); // The resetting mechanism when you start with another exercise
                txt_field.setText("");
                txt_field.setVisibility(View.VISIBLE);
                alt1.setText("");
                alt2.setText("");
                alt3.setText("");
                units_field.setVisibility(View.VISIBLE);
                set_units_values(spinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });


        txt_field.addTextChangedListener(new TextWatcher() {
                 public void afterTextChanged(Editable s)
                 {
                     if (!(txt_field.getText().toString().equals(""))) {
                         display_burned(spinner.getSelectedItem().toString(), txt_field.getText().toString()); // Does this work here??
                     }
                     else
                     {
                         cal_field.setText("");
                         alt1.setText("");
                         alt2.setText("");
                         alt3.setText("");
                     }

                 }
                 public void beforeTextChanged(CharSequence s, int start,
                                               int count, int after)
                 {

                 }
                 public void onTextChanged(CharSequence s, int start,
                                           int before, int count)
                 {

                 }
        });




    }

    private void set_spinner_values()
    {
        Spinner spinner = (Spinner) findViewById(R.id.Exercises);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.exercise_list, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    private void set_units_values(String exercise_type)
    {
        TextView units_field = (TextView) findViewById(R.id.Units);

        switch (exercise_type)
        {
            case "Pushup":
            case "Situp":units_field.setText("Reps"); System.out.println("In the zone!"); break;
            case "Jumping Jacks":
            case "Jogging":units_field.setText("Minutes"); break;
        }
    }


    private void display_burned(String exercise_type, String num_str)
    {
        TextView cal_field = (TextView) findViewById(R.id.BurnedCal);
        int cal = 0;
        int num;
        num = Integer.parseInt(num_str); // Should it be an integer?? Should it be signed??
        switch(exercise_type)
        {
            case "Pushup": get_burned("Pushup", num); break;
            case "Situp": get_burned("Situp", num);break;
            case "Jumping Jacks": get_burned("Jumping Jacks", num);break;
            case "Jogging": get_burned("Jogging", num);break;
        }

    }

    private void get_burned(String exercise_key, int num)
    {
        int factor = ex_to_cal.get(exercise_key);
        int ans = (num*100)/factor;//Should i display the decimal value??
        final TextView cal_field = (TextView) findViewById(R.id.BurnedCal);
        if (ans!=1) {
            cal_field.setText("You have burned " + ans + " calories.");
        }
        else{ //For the "1 calorie" case.
            cal_field.setText("You have burned " + ans + " calorie.");
        }
        cals = ans;
        exercise_type = exercise_key;
        //equivalent(exercise_key, ans);
    }

    public void display_equivalent(View w)
    {
        equivalent(exercise_type, cals);
    }

    private void equivalent(String curr_exercise, int cals) // Shift to a better data structure that allows a flexible number of labels.
    {
        TextView alt1 = (TextView) findViewById(R.id.Alt1);
        TextView alt2 = (TextView) findViewById(R.id.Alt2);
        TextView alt3 = (TextView) findViewById(R.id.Alt3);
        switch (curr_exercise)
        {
            case "Pushup":alt1.setText(((cals*ex_to_cal.get("Situp"))/100)+" Reps of Situps");
                alt2.setText(((cals*ex_to_cal.get("Jumping Jacks"))/100)+" Minutes of Jumping Jacks");
                alt3.setText(((cals*ex_to_cal.get("Jogging"))/100)+" Minutes of Jogging");
                break;
            case "Situp":alt1.setText(((cals*ex_to_cal.get("Pushup"))/100)+" Reps of Pushups");
                alt2.setText(((cals*ex_to_cal.get("Jumping Jacks"))/100)+" Minutes of Jumping Jacks");
                alt3.setText(((cals*ex_to_cal.get("Jogging"))/100)+" Minutes of Jogging");
                break;
            case "Jumping Jacks":alt1.setText(((cals*ex_to_cal.get("Pushup"))/100)+" Reps of Pushups");
                alt2.setText(((cals*ex_to_cal.get("Situp"))/100)+" Reps of Situps");
                alt3.setText(((cals*ex_to_cal.get("Jogging"))/100)+" Minutes of Jogging");
                break;
            case "Jogging":alt1.setText(((cals*ex_to_cal.get("Pushup"))/100)+" Reps of Pushups");
                alt2.setText(((cals*ex_to_cal.get("Situp"))/100)+" Reps of Situps");
                alt3.setText(((cals*ex_to_cal.get("Jumping Jacks"))/100)+" Minutes of Jumping Jacks");
                break;
        }
    }


    private void set_cal_values()
    {
       ex_to_cal.put("Pushup", 350);
       ex_to_cal.put("Situp",200);
       ex_to_cal.put("Jumping Jacks",10);
       ex_to_cal.put("Jogging",12);
    }
}
