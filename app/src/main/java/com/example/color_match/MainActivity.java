package com.example.color_match;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button[] card_ary = new Button[16];
    String[] color_ary;

    int onpick_index = 0;
    int[] onpick = new int[2];
    int match = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        card_ary[0] = findViewById(R.id.one);
        card_ary[1] = findViewById(R.id.two);
        card_ary[2] = findViewById(R.id.three);
        card_ary[3] = findViewById(R.id.four);
        card_ary[4] = findViewById(R.id.five);
        card_ary[5] = findViewById(R.id.six);
        card_ary[6] = findViewById(R.id.seven);
        card_ary[7] = findViewById(R.id.eight);
        card_ary[8] = findViewById(R.id.nine);
        card_ary[9] = findViewById(R.id.ten);
        card_ary[10] = findViewById(R.id.eleven);
        card_ary[11] = findViewById(R.id.twelve);
        card_ary[12] = findViewById(R.id.thirteen);
        card_ary[13] = findViewById(R.id.fourteen);
        card_ary[14] = findViewById(R.id.fifteen);
        card_ary[15] = findViewById(R.id.sixteen);

        color_ary = new String[]{
                "R", "R", "R", "R",
                "G", "G", "G", "G",
                "B", "B", "B", "B",
                "O", "O", "O", "O"
        };

        shuffle_color_ary();

        // Give every hidden card a fixed appearance.
        for (Button card : card_ary) {
            hideCard(card);
        }
    }

    public void shuffle_color_ary() {

        List<String> strList = Arrays.asList(color_ary);
        Collections.shuffle(strList);
        color_ary = strList.toArray(new String[0]);
    }

    private void hideCard(Button card) {

        card.setBackgroundColor(Color.GRAY);
        card.setClickable(true);
    }

    public void shuffle(View v) {

        shuffle_color_ary();

        for (Button card : card_ary) {
            hideCard(card);
        }

        onpick_index = 0;
        match = 0;
    }

    public void onClick(View v) {

        int id = v.getId();

        if (id == R.id.one) {
            select(0);
        } else if (id == R.id.two) {
            select(1);
        } else if (id == R.id.three) {
            select(2);
        } else if (id == R.id.four) {
            select(3);
        } else if (id == R.id.five) {
            select(4);
        } else if (id == R.id.six) {
            select(5);
        } else if (id == R.id.seven) {
            select(6);
        } else if (id == R.id.eight) {
            select(7);
        } else if (id == R.id.nine) {
            select(8);
        } else if (id == R.id.ten) {
            select(9);
        } else if (id == R.id.eleven) {
            select(10);
        } else if (id == R.id.twelve) {
            select(11);
        } else if (id == R.id.thirteen) {
            select(12);
        } else if (id == R.id.fourteen) {
            select(13);
        } else if (id == R.id.fifteen) {
            select(14);
        } else if (id == R.id.sixteen) {
            select(15);
        }
    }

    public void select(int btn_index) {

        // Last remaining pair
        if (match == 7 && onpick_index == 1) {

            onpick[onpick_index] = btn_index;
            show(btn_index);
            check(btn_index);
        }

        // First or second selected card
        else if (onpick_index < 2) {

            onpick[onpick_index] = btn_index;
            show(btn_index);
            onpick_index++;
        }

        // Third click checks the previous pair
        else if (onpick_index == 2) {

            check(btn_index);
        }
    }

    public void show(int btn_index) {

        switch (color_ary[btn_index]) {

            case "R":
                card_ary[btn_index].setBackgroundColor(
                        Color.rgb(252, 45, 55)
                );
                break;

            case "G":
                card_ary[btn_index].setBackgroundColor(
                        Color.rgb(45, 252, 63)
                );
                break;

            case "B":
                card_ary[btn_index].setBackgroundColor(
                        Color.rgb(45, 224, 252)
                );
                break;

            case "O":
                card_ary[btn_index].setBackgroundColor(
                        Color.rgb(252, 204, 45)
                );
                break;
        }

        card_ary[btn_index].setClickable(false);
    }

    public void check(int new_btn_index) {

        if (color_ary[onpick[0]].equals(color_ary[onpick[1]])) {

            card_ary[onpick[0]].setBackgroundColor(
                    Color.rgb(249, 249, 249)
            );

            card_ary[onpick[1]].setBackgroundColor(
                    Color.rgb(249, 249, 249)
            );

            match++;

            if (match == 8) {

                Toast.makeText(
                        this,
                        "All Clear, click <SHUFFLE> to restart",
                        Toast.LENGTH_LONG
                ).show();
            } else {

                onpick_index = 0;
                select(new_btn_index);
            }
        } else {

            hideTheFirst();

            onpick[0] = onpick[1];

            onpick_index = 1;
            select(new_btn_index);
        }
    }

    public void hideTheFirst() {

        hideCard(card_ary[onpick[0]]);
    }
}