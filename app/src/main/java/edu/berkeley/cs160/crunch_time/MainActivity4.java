package edu.berkeley.cs160.crunch_time;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity4 extends AppCompatActivity {

    String exercise_type;
    HashMap<String, Integer> ex_to_cal = new HashMap<String, Integer>();
    int curr_burned;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        set_cal_values();
        curr_burned = 0;
    }

    public void chosen_exercise(View w)
    {
        int intID = w.getId();
        Button button = (Button) findViewById(intID);
        TextView units_field = (TextView) findViewById(R.id.Units);
        curr_burned = 0;
        switch (intID)
        {
            case R.id.pushup_bttn: units_field.setText("Reps");
                exercise_type = "Pushup";
                break;
            case R.id.situp_bttn:exercise_type = "Situp";
                units_field.setText("Reps");
                break;
            case R.id.jumping_bttn:exercise_type = "Jumping Jack";
                units_field.setText("Minutes");
                break;
            case R.id.jogging_bttn:exercise_type = "Jogging";
                units_field.setText("Minutes");
                break;
        }
        units_field.setVisibility(View.VISIBLE);
        final EditText times = (EditText) findViewById(R.id.Times);
        final Button equivalent = (Button) findViewById(R.id.Equivalent);
        times.setText("");
        times.setVisibility(View.VISIBLE);

        final TextView alt1 = (TextView) findViewById(R.id.Alt1);
        final TextView alt2 = (TextView) findViewById(R.id.Alt2);
        final TextView alt3 = (TextView) findViewById(R.id.Alt3);
        final TextView burned_cal = (TextView) findViewById(R.id.Burned);

//        alt1.setText("");
//        alt2.setText("");
//        alt3.setText("");
        burned_cal.setText("");
        burned_cal.setVisibility(View.VISIBLE);


        times.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (!(times.getText().toString().equals(""))) {
                    display_burned(exercise_type, times.getText().toString()); // Does this work here??
                    equivalent.setVisibility(View.VISIBLE);
                } else {
                    burned_cal.setText("");
                    //alt1.setText("");
                    //alt2.setText("");
                    //alt3.setText("");
                }

            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

            }
        });

    }

    private void display_burned(String type, String num_str)
    {
        TextView cal_field = (TextView) findViewById(R.id.Burned);
        int cal = 0;
        int num;
        num = Integer.parseInt(num_str); // Should it be an integer?? Should it be signed??
        switch(type)
        {
            case "Pushup": get_burned("Pushup", num); break;
            case "Situp": get_burned("Situp", num);break;
            case "Jumping Jack": get_burned("Jumping Jack", num);break;
            case "Jogging": get_burned("Jogging", num);break;
        }

    }

    private void get_burned(String exercise_key, int num)
    {
        int factor = ex_to_cal.get(exercise_key);
        int ans = (num*100)/factor;//Should i display the decimal value??
        final TextView cal_field = (TextView) findViewById(R.id.Burned);
        if (ans!=1) {
            cal_field.setText("You have burned " + ans + " calories.");
        }
        else{ //For the "1 calorie" case.
            cal_field.setText("You have burned " + ans + " calorie.");
        }
        curr_burned = ans;
    }


    public void display_equivalent(View w)
    {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout,
                (ViewGroup) findViewById(R.id.toast_layout_root));

//        TextView text = (TextView) layout.findViewById(R.id.text);
//        text.setText("This is a custom toast");
//
//        Toast toast = new Toast(getApplicationContext());
//        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
//        toast.setDuration(Toast.LENGTH_LONG);
//        toast.setView(layout);
//        toast.show();

        Dialog d = new Dialog(this, R.style.AppTheme);
        d.setContentView(R.layout.toast_layout);

        Window window = d.getWindow();
        window.setLayout(1000, 1200);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        //d.getWindow().setBackgroundDrawable(@drawable/mybutton);
        //d.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams lp = d.getWindow().getAttributes();
        lp.dimAmount = 0.5f;
        d.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        //d.show();
        equivalent(exercise_type, curr_burned, layout, d);

    }

    private void equivalent(String curr_exercise, int cals, View w, Dialog d) // Shift to a better data structure that allows a flexible number of labels.
    {
        TextView alt1 = (TextView) w.findViewById(R.id.Alt1);
        TextView alt2 = (TextView) w.findViewById(R.id.Alt2);
        TextView alt3 = (TextView) w.findViewById(R.id.Alt3);
       // System.out.println("alt1= "+alt1.getText());

        switch (curr_exercise)
        {
            case "Pushup":alt1.setText(((cals*ex_to_cal.get("Situp"))/100)+" Reps of Situps");
                alt2.setText(((cals*ex_to_cal.get("Jumping Jack"))/100)+" Minutesof Jumping Jacks");
                alt3.setText(((cals*ex_to_cal.get("Jogging"))/100)+" Minutes of Jogging");
                System.out.println("You are in, baby!");
                break;
            case "Situp":alt1.setText(((cals*ex_to_cal.get("Pushup"))/100)+" Reps of Pushups");
                alt2.setText(((cals*ex_to_cal.get("Jumping Jack"))/100)+" Minutes of Jumping Jacks");
                alt3.setText(((cals*ex_to_cal.get("Jogging"))/100)+" Minutes of Jogging");
                break;
            case "Jumping Jack":alt1.setText(((cals*ex_to_cal.get("Pushup"))/100)+" Reps of Pushups");
                alt2.setText(((cals*ex_to_cal.get("Situp"))/100)+" Reps of Situps");
                alt3.setText(((cals*ex_to_cal.get("Jogging"))/100)+" Minutes of Jogging");
                break;
            case "Jogging":alt1.setText(((cals*ex_to_cal.get("Pushup"))/100)+" Reps of Pushups");
                alt2.setText(((cals*ex_to_cal.get("Situp"))/100)+" Reps of Situps");
                alt3.setText(((cals*ex_to_cal.get("Jumping Jack"))/100)+" Minutes of Jumping Jacks");
                break;
        }
        w.invalidate();
        d.show();
    }


    private void set_cal_values()
    {
        ex_to_cal.put("Pushup", 350);
        ex_to_cal.put("Situp",200);
        ex_to_cal.put("Jumping Jack",10);
        ex_to_cal.put("Jogging",12);
    }
}

