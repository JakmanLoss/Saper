package com.example.cletochki;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Random;


public class MainActivity extends AppCompatActivity {
    Random rnd = new Random(System.currentTimeMillis());
    TextView mines;
    Button[][] cells;
    final int W = 10;
    final int H = 10;

    int win = 0;

    final int mins = 5;
    final int MINECONST = mins;
    int MinesCurrent = mins;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mines = findViewById(R.id.TV);
        mines.setText(""+MINECONST+" /"+MinesCurrent);
        generate();
    }

    public void generate(){
        GridLayout layout = findViewById(R.id.GRID);
        layout.removeAllViews();
        layout.setColumnCount(W);
        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        cells = new Button[H][W];
        boolean[][] array = new boolean[H][W];
        boolean[][] blue = new boolean[H][W];
        for(int i = 0; i < H; i++){
            for(int j = 0; j < W; j++){
                cells[i][j] = (Button) inflater.inflate(R.layout.sell, layout, false);
            }
        }
        int one = 0, two = 0;
        int[][] kolvo = new int[H][W];
        int[][] ich = new int[H][W];
        for(int i = 0; i < mins; i++){
            one = 0 + rnd.nextInt(9 - 0 + 1);
            two = 0 + rnd.nextInt(9 - 0 + 1);;
            cells[one][two].setBackgroundColor(Color.RED);
            array[one][two] = true;
            kolvo[one][two] = 1;
        }
        int k = 0;
        for(int i = 0; i < H; i++){
            for(int j = 0; j < W; j++){
                if(i - 1 >= 0) {
                    if (kolvo[i - 1][j] == 1) {
                        k++;
                    }
                }
                if(i - 1 >= 0 && j - 1 >= 0){
                    if(kolvo[i - 1][j - 1] == 1){
                        k++;
                    }
                }
                if(j - 1 >= 0){
                    if(kolvo[i][j - 1] == 1){
                        k++;
                    }
                }
                if(j - 1 >= 0 && i + 1 <= 9){
                    if(kolvo[i + 1][j - 1] == 1){
                        k++;
                    }
                }
                if(i + 1 <= 9){
                    if(kolvo[i + 1][j] == 1){
                        k++;
                    }
                }
                if(i + 1 <= 9 && j + 1 <= 9){
                    if(kolvo[i + 1][j + 1] == 1){
                        k++;
                    }
                }
                if(j + 1 <= 9){
                    if(kolvo[i][j + 1] == 1){
                        k++;
                    }
                }
                if(j + 1 <= 9 && i - 1 >= 0){
                    if(kolvo[i - 1][j + 1] == 1){
                        k++;
                    }
                }

                ich[i][j] = k;
                k = 0;
            }
        }
        for(int i = 0; i < H; i++){
            for(int j = 0; j < W; j++){
                int finalI = i;
                int finalJ = j;
                int kol = 0;
                kol = ich[i][j];
                String kl;
                kl = Integer.toString(kol);
                cells[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(array[finalI][finalJ]){
                            Toast.makeText(getApplicationContext(), "ПУПУПУ", Toast.LENGTH_LONG).show();
                        }
                        else {
                            TextView tv = (TextView) view;
                            tv.setText(kl);
                        }
                    }
                });
                cells[i][j].setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        if(!blue[finalI][finalJ] && MinesCurrent > 0) {
                            view.setBackgroundColor(Color.BLUE);
                            MinesCurrent--;
                            mines.setText("" + MINECONST +" / " + MinesCurrent);
                            blue[finalI][finalJ] = true;
                            if(kolvo[finalI][finalJ] == 1){
                                win++;
                            }
                        }
                        else if((MinesCurrent < mins + 1 && MinesCurrent > 0) || (blue[finalI][finalJ])){
                            view.setBackgroundColor(Color.WHITE);
                            MinesCurrent++;
                            mines.setText("" + MINECONST + " / " + MinesCurrent);
                            blue[finalI][finalJ] = false;
                            if(kolvo[finalI][finalJ] == 1){
                                win--;
                                view.setBackgroundColor(Color.RED);
                            }
                        }
                        if(MinesCurrent == 0 && win == mins){
                            Toast.makeText(getApplicationContext(), "WIN!", Toast.LENGTH_LONG).show();
                        }
                        return true;
                    }
                });
                cells[i][j].setTag("" + j + H * i);
                layout.addView(cells[i][j]);
            }
        }
    }
}