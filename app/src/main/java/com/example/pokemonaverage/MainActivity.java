/*
 * Sample Android app for Capstone Research Milestone
 * Weighted average Pokemon Stat calculator
 *      Written by Nicholas Mueller
 *
 *      This simple app takes a series of 6 numbers from the user
 *      these values represent a Pokemon's Individual Values, a
 *      set of numbers ranging from 0-31 which set a baseline for
 *      that Pokemon's respective manifested stat.
 *
 *      As an average, the number is meant to show a generalized value
 *      to represent the overall potential of the Pokemon; however, in nearly
 *      all cases, a Pokemon will only ever use either its Attack or Sp. Attack
 *      stat, whichever is higher. Therefore, by including an irrelevant number
 *      to a running average, the effective average stat is fluffed out to appear
 *      either worse or better than the Pokemon will become in practice.
 *
 *      This app makes a simple comparison to determine if the Attack and Sp. Attack
 *      differ by a factor greater than 15% of the greater stat. If so, the weaker stat
 *      is removed from the calculation, and the Pokemon's new weighted average stat is
 *      displayed.
 *
 */

package com.example.pokemonaverage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pokemonaverage.R;

public class MainActivity extends AppCompatActivity {

    int HP, ATK, DEF, SPD, SATK, SDEF;
    double IRR_FACTOR = 0.85;

    EditText hpInput;
    EditText atkInput;
    EditText defInput;
    EditText spdInput;
    EditText satkInput;
    EditText sdefInput;

    Button showAverage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hpInput = findViewById(R.id.hpInput);
        atkInput = findViewById(R.id.atkInput);
        defInput = findViewById(R.id.defInput);
        spdInput = findViewById(R.id.spdInput);
        satkInput = findViewById(R.id.satkInput);
        sdefInput = findViewById(R.id.sdefInput);

        showAverage = findViewById(R.id.showAverage);
        showAverage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    HP = Integer.parseInt(hpInput.getText().toString());
                    ATK = Integer.parseInt(atkInput.getText().toString());
                    DEF = Integer.parseInt(defInput.getText().toString());
                    SPD = Integer.parseInt(spdInput.getText().toString());
                    SATK = Integer.parseInt(satkInput.getText().toString());
                    SDEF = Integer.parseInt(sdefInput.getText().toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    showToast("Individual Values must exist!");
                }

                if (HP>31 | ATK>31 | DEF>31 | SPD>31 | SATK>31 | SDEF>31) {
                    showToast("Individual Values must be 31 or lower!");
                }
                else if (HP<0 | ATK<0 | DEF<0 | SPD<0 | SATK<0 | SDEF<0) {
                    showToast("Individual Values must exist!");
                }
                else showToast("Your weighted average is: " + weightedAverage(HP, ATK, DEF, SPD, SATK, SDEF));
            }
        });
    }
    private int weightedAverage(int hp, int atk, int def, int spd, int satk, int sdef) {
        int avg = 0;
        int sum = 0;
        int count = 6;
        if (atk > Math.round(satk * IRR_FACTOR)) {
            count = 5;
            sum = hp + atk + def + spd + sdef;
        }
        else if (satk > Math.round(atk * IRR_FACTOR)) {
            count = 5;
            sum = hp + def + spd + satk + sdef;
        }
        else {
            sum = hp + atk + def + spd + satk + sdef;
        }
        avg = sum / count;

        return avg;
    }
    private void showToast(String t) {
        Toast.makeText(MainActivity.this, t, Toast.LENGTH_LONG).show();
    }
}


