package com.example.a2048;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;



public class Board {
    private int[][] Board = new int[4][4];
    private int Score = 0;

    // Varibles for generating random numbers
    Random obj = new Random();

    int num1, num2, num3, num4, num5, num6;

    int[] arr = { 2, 4 };
    List<int[]> emptyPos = new ArrayList<>();

    public Board() {
        this.generateRandomAtFirst();
    }

    public int[][] getBoard() {
        return this.Board;
    }

    private void generateRandomAtFirst() {
        this.num1 = this.obj.nextInt(4);
        this.num2 = this.obj.nextInt(4);
        this.num3 = this.obj.nextInt(4);
        this.num4 = this.obj.nextInt(4);
        while (this.num1 == this.num4) {
            this.num4 = this.obj.nextInt(4);
        }
        this.num5 = this.obj.nextInt(2);
        this.num6 = this.obj.nextInt(2);
        this.Board[this.num1][this.num2] = this.arr[this.num5];
        this.Board[this.num4][this.num3] = this.arr[this.num6];
    }

    private boolean generateRandom() {

        // Getting empty positions
        if (!this.emptyPos.isEmpty()) {
            this.emptyPos.clear();
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (this.Board[i][j] == 0) {
                    this.emptyPos.add(new int[] { i, j });
                }
            }
        }

        try
        {
            this.num1 = this.obj.nextInt(this.emptyPos.size());
            this.Board[this.emptyPos.get(this.num1)[0]][this.emptyPos.get(this.num1)[1]] = this.arr[this.obj.nextInt(2)];

        }
        catch (Exception e){
            return false;
        }
        return true;
    }

    private boolean rightCompress() {
        /**
         * The method compresses the board to right and can be reused in other way by
         * transposing array and making left right
         */
        boolean val1 = false;
        for (int k = 0; k < 3; k++) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 3; j++) {
                    if (this.Board[i][j + 1] == 0 && this.Board[i][j] != 0) {
                        this.Board[i][j + 1] = this.Board[i][j];
                        this.Board[i][j] = 0;
                        val1 = true;

                    }
                }
            }
        }return val1;
    }

    private boolean rightMerge() {
        boolean merge = false;
        /** the method merges elements in right direction if they are same */
        for (int i = 0; i < Board.length; i++) {
            for (int j = 2; j >= 0; j--) {
                if (Board[i][j] != 0 && Board[i][j] == Board[i][j + 1]) {
                    Board[i][j + 1] *= 2;
                    Board[i][j] = 0;
                    merge = true;
                    this.Score += Board[i][j + 1];
                }
            }
        }
        return merge;
    }

    private void flip() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                int temp = this.Board[i][j];
                this.Board[i][j] = this.Board[i][3 - j];
                this.Board[i][3 - j] = temp;
            }
        }
    }

    private void transpose() {
        int[][] transposed = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                transposed[j][i] = this.Board[i][j];
            }
        }
        this.Board = transposed;
    }
    // Some other public methods----------------

    public boolean rightSwipe() {
        boolean val1  = this.rightCompress();
        boolean val = this.rightMerge();
        boolean val2 = this.rightCompress();
        if (val || val1 || val2){
            this.generateRandom();
        }
        return val;


    }

    public boolean leftSwipe() {
        this.flip();
        boolean val = this.rightSwipe();
        this.flip();

        return val;
    }

    public boolean upSwipe() {
        this.transpose();
        this.flip();
        boolean val = this.rightSwipe();
        this.flip();
        this.transpose();

        return val;
    }

    public boolean downSwipe() {
        this.transpose();
        boolean val = this.rightSwipe();
        this.transpose();

        return val;
    }

    public boolean checkWin() {
        for (int[] row : this.Board) {
            for (int item : row) {
                if (item == 2048) {
                    return true;
                }

            }

        }
        return false;
    }

    public boolean checkOver() {
        for (int[] row : this.Board) {
            for (int item : row) {
                if (item == 0) {
                    return false;
                }

            }
        }
        // Checking if anywher it cannot move
        int[][] newBoard = this.Board;
        if (!rightSwipe() && !leftSwipe() && !upSwipe() && !downSwipe()) {
            this.Board = newBoard;
            return true;
        }
        this.Board = newBoard;
        return false;
    }

    public int getScore() {
        return this.Score;
    }

}
