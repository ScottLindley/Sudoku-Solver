package com.scottlindley.sudoku;

import java.util.ArrayList;

/**
 * Created by Scott Lindley on 10/22/2016.
 */

public class Puzzle {
    private ArrayList<Cell> mCells;
    private int[] mSolution;
    private int[] mKey;

    public Puzzle(int[] key) {
        mKey = key;
    }


}
