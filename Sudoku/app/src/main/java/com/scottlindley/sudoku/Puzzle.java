package com.scottlindley.sudoku;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Scott Lindley on 10/22/2016.
 */

public class Puzzle {
    private int[][] mRowCells;
    private int[][] mColumnCells;
    private int[][] mBoxCells;
    private ArrayList<Integer> mSolution;
    private int[] mKey;
    private static final String TAG = "Puzzle";
    public static final int FORWARDS = 1;
    public static final int BACKWARDS = -1;

    public Puzzle(int[] key) {
        mKey = key;
        initializeArrays();
        Log.d(TAG, "*******\nPuzzle: Solution:\n" + mSolution);
        Log.d(TAG, "*******\nPuzzle: Row Cells:\n" + mRowCells);
        Log.d(TAG, "*******\nPuzzle: Column Cells:\n" + mColumnCells);
        Log.d(TAG, "*******\nPuzzle: Box Cells:\n" + mBoxCells);
        solve();
    }

    private void initializeArrays() {
        //Use the key to start off the solution array list
        mSolution = new ArrayList<>();
        for (int i = 0; i < mKey.length; i++) {
            mSolution.add(mKey[i]);
        }

        //create arrays that contain which cells share a row/column/box
        mRowCells = new int[9][9];
        mColumnCells = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                mRowCells[i][j] = j + (i * 9);
                mColumnCells[i][j] = (i % 9) + (j * 9);
            }
        }
        int[][] boxCells = {
                {0, 1, 2, 9, 10, 11, 18, 19, 20},
                {3, 4, 5, 12, 13, 14, 21, 22, 23},
                {6, 7, 8, 15, 16, 17, 24, 25, 26},
                {27, 28, 29, 36, 37, 38, 45, 46, 47},
                {30, 31, 32, 39, 40, 41, 48, 49, 50},
                {33, 34, 35, 42, 43, 44, 51, 52, 53},
                {54, 55, 56, 63, 64, 65, 72, 73, 74},
                {57, 58, 59, 66, 67, 68, 75, 76, 77},
                {60, 61, 62, 69, 70, 71, 78, 79, 80},
        };
        mBoxCells = boxCells;

        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                Log.d(TAG, "r"+mRowCells[i][j]);
                Log.d(TAG, "c"+mColumnCells[i][j]);
                Log.d(TAG, "b"+mBoxCells[i][j]);
            }
        }



    }

    public void solve() {
        int indexNumber = 0;
        int cellValue;
        int direction = FORWARDS;
        while (true) {
            cellValue = mSolution.get(indexNumber);
            if(!mSolution.contains(0)){break;}
//            Log.d(TAG, "cell value = " + cellValue);
//            Log.d(TAG, "index = " + indexNumber);
            //if this cell was not given in the key, attempt to solve
            if (mKey[indexNumber] == 0) {
                /*Attempt to place n=1 in this cell. If a conflict is found, n++ and try again.
                If no conflict, place the number in the solutions array and move forwards.
                If n=9 and a conflict is found, move backwards.
                */
                if (cellValue == 0) {
                    cellValue = 1;
                }
                for (int n = cellValue; n < 10; n++) {
                    if (indexNumber!=0){Log.d(TAG, ""+n);}
                    boolean isInRow = false, isInColumn = false, isInBox = false;
                    int rowNumber = -1, columnNumber = -1, boxNumber = -1;
                    //Determine the row/cell/box of the current cell
                    for (int i = 0; i < 9; i++) {
                        for (int j = 0; j < 9; j++) {
                            if (mRowCells[i][j] == indexNumber) {
                                rowNumber = i;
                            }
                            if (mColumnCells[i][j] == indexNumber) {
                                columnNumber = i;
                            }
                            if (mBoxCells[i][j] == indexNumber) {
                                boxNumber = i;
                            }
                        }
                    }
                    //Look through its row/cell/box to see if n already exist within
                    for (int i = 0; i < 9; i++) {
                        if (mSolution.get(mRowCells[rowNumber][i]) == n) {
                            isInRow = true;
                        }
                        if (mSolution.get(mColumnCells[columnNumber][i]) == n) {
                            isInColumn = true;
                        }
                        if (mSolution.get(mBoxCells[boxNumber][i]) == n) {
                            isInBox = true;
                        }
                    }
                    boolean conflict = isInRow || isInColumn || isInBox;
                    //if no conflicts are found, add to the solution RETURN TRUE
                    if (!conflict) {
                        mSolution.set(indexNumber, n);
                        Log.d(TAG, "solve: added " + n + " to cell " + indexNumber);
                        if(indexNumber<80) {
                            indexNumber++;
                            direction = FORWARDS;
                            Log.d(TAG, "solve: MOVING FORWARD");
                        }
                        break;
                    }
                    //if a conflict is found and n==9, move backwards RETURN FALSE
                    if (conflict && n == 9) {
                        if (indexNumber > 0) {
                            mSolution.set(indexNumber, 0);
                            indexNumber--;
                            Log.d(TAG, "solve: MOVING BACKWARDS");
                            direction = BACKWARDS;
                            break;
                        }
                    }
                }
            }else{
                if(direction == FORWARDS) {
                    Log.d(TAG, "solve: MOVING FORWARD");
                    indexNumber++;
                }else{
                    indexNumber--;
                    Log.d(TAG, "solve: MOVING BACKWARDS");
                }
            }
        }
    }

    public ArrayList<Integer> getSolution(){
        return mSolution;
    }
}


