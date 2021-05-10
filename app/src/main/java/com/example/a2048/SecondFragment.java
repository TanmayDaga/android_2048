package com.example.a2048;


import android.annotation.SuppressLint;

import android.app.Dialog;

import android.os.Bundle;

import android.util.DisplayMetrics;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

import android.view.GestureDetector;



import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;



import java.util.HashMap;
import java.util.Map;


public class SecondFragment extends Fragment {

    // Set of colors

    Dialog dialog;
    Board objectBoard = new Board();
    TableRow tr0;
    TableRow tr1;
    TableRow tr2;
    TableRow tr3;



    TextView tw, score; // General variable for setting all values of text
    Map<Integer, String> bgColor = new HashMap<Integer, String>() {
        {
            put(0,"#A3B1A5A5");
            put(2,"#eee4da");
            put(4,"#ede0c8");
            put(8,"#edc850");
            put(16,"#edc53f");
            put(32,"#f67c5f");
            put(64,"#f65e3b");
            put(128,"#edcf72");
            put(256,"#edcc61");
            put(512,"#f2b179");
            put(1024,"#f59563");
            put(2048,"#edc22e");
        }
    };

    Map<Integer, String> Color = new HashMap<Integer, String>() {{
        put(0,"#776e65");
        put(2,"#776e65");
        put(4,"#776e65");
        put(8,"#f9f6f2");
        put(16,"#f9f6f2");
        put(32,"#f9f6f2");
        put(64,"#f9f6f2");
        put(128,"#f9f6f2");
        put(256,"#f9f6f2");
        put(512,"#776e65");
        put(1024,"#f9f6f2");
        put(2048,"#f9f6f2");
    }};


    class myMovement extends Thread{

        int movement;
        SecondFragment object;
        public myMovement(int movement1, SecondFragment object1){
            this.movement = movement1;
            this.object = object1;
        }
        @Override
        public void run() {
            if (movement == 0){
                this.object.objectBoard.leftSwipe();
            }

            else if (movement == 1){
                this.object.objectBoard.upSwipe();
;            }

            else if (movement == 2){
                this.object.objectBoard.rightSwipe();
            }
            else if (movement == 3){
                this.object.objectBoard.downSwipe();
            }

            super.run();
        }
    }
    class checkWinOver extends Thread{


        SecondFragment object;
        public checkWinOver(SecondFragment object1){

            this.object = object1;}

        public boolean check(){
            if (this.object.objectBoard.checkWin() || this.object.objectBoard.checkOver()){
                return true;
            }
            return false;
        }
    }



    SecondFragment myObject = this;


    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment

        dialog = new Dialog(this.getContext());
        dialog.setContentView(R.layout.dialog_box);
        dialog.getWindow().setBackgroundDrawable(requireActivity().getResources().getDrawable(R.drawable.background));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        Button Okey = dialog.findViewById(R.id.button_okey);
        Okey.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        } );




        return inflater.inflate(R.layout.fragment_second, container, false);

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        // Setting objects
        this.tr0 = view.findViewById(R.id.tableRow0);
        this.tr1 = view.findViewById(R.id.tableRow1);
        this.tr2 = view.findViewById(R.id.tableRow2);
        this.tr3 = view.findViewById(R.id.tableRow3);
        this.score = view.findViewById(R.id.scoreView);

        // Setting sizes
        DisplayMetrics displayMetrics = new DisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = width;
        view.findViewById(R.id.tableLayout).setMinimumWidth(width);
        view.findViewById(R.id.tableLayout).setMinimumHeight(height);

        for (int i = 0; i < 4; i++) {
            this.tw = (TextView) this.tr0.getChildAt(i);
            this.tw.setWidth((int) (width-50)/4);
            this.tw.setHeight((int) (height-50)/4);
        }
        for (int i = 0; i < 4; i++) {
            this.tw = (TextView) this.tr1.getChildAt(i);
            this.tw.setWidth((int) (width-50)/4);
            this.tw.setHeight((int) (height-50)/4);

        }
        for (int i = 0; i < 4; i++) {
            this.tw = (TextView) this.tr2.getChildAt(i);
            this.tw.setWidth((int) (width-50)/4);
            this.tw.setHeight((int) (height-50)/4);
        }
        for (int i = 0; i < 4; i++) {
            this.tw = (TextView) this.tr3.getChildAt(i);
            this.tw.setWidth((int) (width-50)/4);
            this.tw.setHeight((int) (height-50)/4);
        }





        setValues(objectBoard.getBoard());

        final GestureDetector gesture = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener(){

            checkWinOver chobj = new checkWinOver(myObject);

            @Override
            public boolean onDown(MotionEvent e){
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                   float velocityY) {
                final int SWIPE_THRESHOLD = 100;
                final int SWIPE_VELOCITY_THRESHOLD = 100;
                boolean result = false;
                try {
                    float diffY = e2.getY() - e1.getY();
                    float diffX = e2.getX() - e1.getX();
                    if (Math.abs(diffX) > Math.abs(diffY)) {
                        if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffX > 0) {
                                onSwipeRight();
                            } else {
                                onSwipeLeft();
                            }
                            result = true;
                        }
                    }
                    else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            onSwipeBottom();
                        } else {
                            onSwipeTop();
                        }
                        result = true;
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return result;
            }
        @SuppressLint("SetTextI18n")
        public void onSwipeRight() throws InterruptedException {
            myMovement obj = new myMovement(2, myObject);
            obj.start();
            obj.join();
            setValues(objectBoard.getBoard());
            score.setText("Score\n"+Integer.toString(objectBoard.getScore()));
            if (chobj.check()){
                dialog.show();
            }

        }

        @SuppressLint("SetTextI18n")
        public void onSwipeLeft() throws InterruptedException {
                myMovement obj = new myMovement(0, myObject);
                obj.start();
                obj.join();

                setValues(objectBoard.getBoard());

            score.setText("Score\n"+Integer.toString(objectBoard.getScore()));
            if (chobj.check()){
                dialog.show();
            }

        }
        @SuppressLint("SetTextI18n")
        public void onSwipeBottom() throws InterruptedException {
            myMovement obj = new myMovement(3, myObject);
            obj.start();
            obj.join();
            setValues(objectBoard.getBoard());
            score.setText("Score\n"+Integer.toString(objectBoard.getScore()));
            if (chobj.check()){
                dialog.show();
            }

        }
        @SuppressLint("SetTextI18n")
        public void onSwipeTop() throws InterruptedException {
            myMovement obj = new myMovement(1, myObject);
            obj.start();
            obj.join();
            setValues(objectBoard.getBoard());
            score.setText("Score\n"+Integer.toString(objectBoard.getScore()));
            if (chobj.check()){
                dialog.show();
            }

        }
        });

        view.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent e){
                return gesture.onTouchEvent(e);

            }
        });

        setValues(objectBoard.getBoard());
        view.findViewById(R.id.resetBut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);

            }

        });


        }









    @SuppressLint("SetTextI18n")
    public void setValues(int[][] arr){

        arr = arr.clone();


        for (int i = 0; i < 4; i++) {
            this.tw = (TextView) this.tr0.getChildAt(i);
            if (Integer.toString(arr[0][i]).equals("0")){
                this.tw.setText("");

            }
            else{
                this.tw.setText(Integer.toString(arr[0][i]));
            }
            this.tw.setBackgroundColor(android.graphics.Color.parseColor(bgColor.get(arr[0][i])));
            this.tw.setTextColor(android.graphics.Color.parseColor(Color.get(arr[0][i])));
        }
        for (int i = 0; i < 4; i++) {
            this.tw = (TextView) this.tr1.getChildAt(i);
            if (Integer.toString(arr[1][i]).equals("0")){
                this.tw.setText("");
            }
            else{
                this.tw.setText(Integer.toString(arr[1][i]));
            }
            this.tw.setBackgroundColor(android.graphics.Color.parseColor(bgColor.get(arr[1][i])));
            this.tw.setTextColor(android.graphics.Color.parseColor(Color.get(arr[1][i])));
        }
        for (int i = 0; i < 4; i++) {
            this.tw = (TextView) this.tr2.getChildAt(i);
            if (Integer.toString(arr[2][i]).equals("0")){
                this.tw.setText("");
            }
            else{
                this.tw.setText(Integer.toString(arr[2][i]));
            }
            this.tw.setBackgroundColor(android.graphics.Color.parseColor(bgColor.get(arr[2][i])));
            this.tw.setTextColor(android.graphics.Color.parseColor(Color.get(arr[2][i])));
        }
        for (int i = 0; i < 4; i++) {
            this.tw = (TextView) this.tr3.getChildAt(i);
            if (Integer.toString(arr[3][i]).equals("0")){
                this.tw.setText("");
            }
            else{
                this.tw.setText(Integer.toString(arr[3][i]));
            }
            this.tw.setBackgroundColor(android.graphics.Color.parseColor(bgColor.get(arr[3][i])));
            this.tw.setTextColor(android.graphics.Color.parseColor(Color.get(arr[3][i])));
        }
    }

}
