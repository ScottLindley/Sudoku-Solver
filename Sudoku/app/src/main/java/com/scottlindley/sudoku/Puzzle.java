package com.scottlindley.sudoku;

import android.util.Log;

import java.util.ArrayList;
import java.util.ListIterator;

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

    public Puzzle(int[] key) {
        mKey = key;
        initializeArrays();
        Log.d(TAG, "*******\nPuzzle: Solution:\n"+mSolution);
        Log.d(TAG, "*******\nPuzzle: Row Cells:\n"+mRowCells);
        Log.d(TAG, "*******\nPuzzle: Column Cells:\n"+mColumnCells);
        Log.d(TAG, "*******\nPuzzle: Box Cells:\n"+mBoxCells);
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
    }

    public void solve() {
        ListIterator<Integer> iterator = mSolution.listIterator();
        int indexNumber = iterator.nextIndex();
        int cellValue = mSolution.get(indexNumber);
        while(iterator.hasNext()) {
            //if this cell was not given in the key, attempt to solve
            if(mKey[indexNumber]!=0) {
                /*Attempt to place n=1 in this cell. If a conflict is found, n++ and try again.
                If no conflict, place the number in the solutions array and move forwards.
                If n=9 and a conflict is found, move backwards.
                */
                for (int n=cellValue; n<10; n++) {
                    boolean isInRow = false, isInColumn = false, isInBox = false;
                    int rowNumber = -1, columnNumber = -1, boxNumber = -1;
                    //Determine the row/cell/box of the current cell
                    for (int i=0; i< 9; i++) {
                        for(int j=0; j<9; j++){
                            if(mRowCells[i][j]==indexNumber){
                                rowNumber = i;
                            }
                            if(mColumnCells[i][j]==indexNumber){
                                columnNumber = i;
                            }
                            if(mBoxCells[i][j]==indexNumber){
                                boxNumber = i;
                            }
                        }
                    }
                    //Look through its row/cell/box to see if n already exist within
                    for(int i=0; i<9; i++){
                        if(mSolution.get(mRowCells[rowNumber][i])==n){
                            isInRow = true;
                        }
                        if(mSolution.get(mColumnCells[columnNumber][i])==n){
                            isInColumn = true;
                        }
                        if(mSolution.get(mBoxCells[boxNumber][i])==n){
                            isInBox = true;
                        }
                    }
                    boolean conflict = isInRow||isInColumn||isInBox;
                    //if no conflicts are found, add to the solution
                    if(!conflict){
                        mSolution.set(indexNumber, n);
                        Log.d(TAG, "solve: added "+ cellValue+ " to cell "+ indexNumber);
                        indexNumber = iterator.nextIndex();
                        cellValue = iterator.next();
                        break;
                    }
                    //if a conflict is found and n==9, move backwards
                    if(conflict && n==9){
                        if (iterator.hasPrevious()){
                            indexNumber = iterator.previousIndex();
                            cellValue = iterator.previous();
                        }
                    }
                }
            }
        }
    }

    public ArrayList<Integer> getSolution(){
        return mSolution;
    }
}


