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

    Button card_ary[] = new Button[16]; // all the card 所有牌
    String[] color_ary; // their front color 所有翻出來的顏色
    int onpick_index = 0; // show 2 card at once at most每次只顯示兩張牌 0 或 1
    int[] onpick = new int[2]; // the 2 card that are showing right now 正在顯示的兩張牌
    int match = 0; // counting on clear times 已配對好的數量

    // in the beginning, 開頁
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // default
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // assign button array
        card_ary[0] = (Button) findViewById(R.id.one);
        card_ary[1] = (Button) findViewById(R.id.two);
        card_ary[2] = (Button) findViewById(R.id.three);
        card_ary[3] = (Button) findViewById(R.id.four);
        card_ary[4] = (Button) findViewById(R.id.five);
        card_ary[5] = (Button) findViewById(R.id.six);
        card_ary[6] = (Button) findViewById(R.id.seven);
        card_ary[7] = (Button) findViewById(R.id.eight);
        card_ary[8] = (Button) findViewById(R.id.nine);
        card_ary[9] = (Button) findViewById(R.id.ten);
        card_ary[10] = (Button) findViewById(R.id.eleven);
        card_ary[11] = (Button) findViewById(R.id.twelve);
        card_ary[12] = (Button) findViewById(R.id.thirteen);
        card_ary[13] = (Button) findViewById(R.id.fourteen);
        card_ary[14] = (Button) findViewById(R.id.fifteen);
        card_ary[15] = (Button) findViewById(R.id.sixteen);

        // setting in the beginning
        
        color_ary = new String[]{"R", "R", "R", "R", "G", "G", "G", "G", "B", "B", "B", "B", "O", "O", "O", "O"};
        // R = red, G = green, B = blue, O = orange
        shuffle_color_ary();

    }

    public void shuffle_color_ary()
    {
        List<String> strList = Arrays.asList(color_ary);
        Collections.shuffle(strList);
        color_ary = strList.toArray(new String[strList.size()]);
    }

    // shuffle and reset all the card, 洗及蓋牌
    public void shuffle(android.view.View v) {

        shuffle_color_ary();

        //cover all the cards, 重新把所有牌蓋上
        for (Button a : card_ary) {
            hideCard(a);
        }

        onpick_index = 0;
        match = 0;

    }

    // select a card, 翻牌
    public void onClick(android.view.View v) {

        // turn button to card index
        switch (v.getId())
        {
            case R.id.one:
                select(0);
                break;
            case R.id.two:
                select(1);
                break;
            case R.id.three:
                select(2);
                break;
            case R.id.four:
                select(3);
                break;
            case R.id.five:
                select(4);
                break;
            case R.id.six:
                select(5);
                break;
            case R.id.seven:
                select(6);
                break;
            case R.id.eight:
                select(7);
                break;
            case R.id.nine:
                select(8);
                break;
            case R.id.ten:
                select(9);
                break;
            case R.id.eleven:
                select(10);
                break;
            case R.id.twelve:
                select(11);
                break;
            case R.id.thirteen:
                select(12);
                break;
            case R.id.fourteen:
                select(13);
                break;
            case R.id.fifteen:
                select(14);
                break;
            case R.id.sixteen:
                select(15);
                break;
        }
    }

    // put the card in "onpick" array, 把牌放入已選範圍
    public void select(int btn_index) {

        // the last pair, 剩下最後一個組合，沒有「第三張牌」，選到第二張就大功告成
        if( match == 7 && onpick_index == 1 ){
            onpick[onpick_index] = btn_index;
            show(btn_index);
            check(btn_index);
        }
        // starting to pick, 同時顥示的只有兩張牌 0 或 1
        else if(onpick_index < 2){
            onpick[onpick_index] = btn_index;
            show(btn_index);
            onpick_index++;
        }
        // pick on the third, 選到第三張牌的時候
        else if(onpick_index == 2)
            check(btn_index);

    }

    // show color, 顯示顏色
    public void show(int btn_index) {

        // show color by shuffled string array. 按洗好的顏色字串，來顯示顏色。
        switch (color_ary[btn_index]) {
            case "R":
                card_ary[btn_index].setBackgroundColor(Color.rgb(252, 45, 55));
                break;
            case "G":
                card_ary[btn_index].setBackgroundColor(Color.rgb(45, 252, 63));
                break;
            case "B":
                card_ary[btn_index].setBackgroundColor(Color.rgb(45, 224, 252));
                break;
            case "O":
                card_ary[btn_index].setBackgroundColor(Color.rgb(252, 204, 45));
                break;
        }//end of switch

        // ban the click after show. 翻出來後，不許再翻一次。
        card_ary[btn_index].setClickable(false);

    }

    // check if color march and take action, 對比顏色並進行相關動作
    public void check(int new_btn_index) {

        // colors matched, 顏色對
        if (color_ary[onpick[0]].equals(color_ary[onpick[1]])) {

            // whiten both card, 白化兩張牌。
            card_ary[onpick[0]].setBackgroundColor(Color.rgb(249, 249, 249));
            card_ary[onpick[1]].setBackgroundColor(Color.rgb(249, 249, 249));
            match++;

            // all clear, 全中
            if (match == 8)
                Toast.makeText(this,"All Clear, click <SHUFFLE> to restart", Toast.LENGTH_LONG).show();

            // not yet clear, 未全中
            else
            {
                // the new card is the first on pick. 新選的牌會是第一張牌
                onpick_index = 0;
                select(new_btn_index);
            }
        }
        // color don't match, 顏色不對
        else {

            // cover the first card. 蓋上第一張牌
            hideTheFirst();

            // the second card become the first. 第二張牌是下一個組合中的第一張
            onpick[0] = onpick[1];

            // the new card become the second . 新選的牌會是第二張牌
            onpick_index = 1;
            select(new_btn_index);
        }

    }

    private void hideCard(Button card) {
        card.setBackgroundColor(Color.GRAY);
        card.setClickable(true);
    }

    // hide the first card. 蓋好第一張牌
    public void hideTheFirst() {
        hideCard(card_ary[onpick[0]]);
    }


}