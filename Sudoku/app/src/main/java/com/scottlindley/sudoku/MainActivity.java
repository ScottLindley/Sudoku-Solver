package com.scottlindley.sudoku;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private GridLayout mGridLayout;
    private ArrayList<TextView> mCellViews;
    private int mSelectedNumber;
    TextView[] mSelectedNumbers = new TextView[9];


    private Puzzle mPuzzle;
    private Button mSolveButton;
    private int[] mInputKey = new int[81];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSelectedNumber = 0;

        initializeGridCells();
        initializeSelectionNumbers();

    }

    public void initializeGridCells() {
        mGridLayout = (GridLayout) findViewById(R.id.bord_layout);
        mCellViews = new ArrayList<>();


        for(int i=0; i<mGridLayout.getRowCount(); i++){
            for(int j=0; j<mGridLayout.getColumnCount(); j++) {
                TextView cellView = new TextView(MainActivity.this);
                cellView.setGravity(Gravity.CENTER);
                cellView.setBackgroundResource(R.drawable.border_small);
                cellView.setWidth((55 * (getResources().getDisplayMetrics().densityDpi / 160)));
                cellView.setHeight((55 * (getResources().getDisplayMetrics().densityDpi / 160)));
                cellView.setTextColor(Color.rgb(0,0,0));
                cellView.setTextSize(24f);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams(
                        GridLayout.spec(i,1), GridLayout.spec(j,1));
                cellView.setLayoutParams(params);

                cellView.setOnClickListener(cellClickListener);
                cellView.setOnLongClickListener(cellLongClickListener);

                mGridLayout.addView(cellView);

                mCellViews.add(cellView);
            }
        }
    }

    public void initializeSelectionNumbers(){
        mSelectedNumbers[0] = (TextView)findViewById(R.id.select_1);
        mSelectedNumbers[1] = (TextView)findViewById(R.id.select_2);
        mSelectedNumbers[2] = (TextView)findViewById(R.id.select_3);
        mSelectedNumbers[3] = (TextView)findViewById(R.id.select_4);
        mSelectedNumbers[4] = (TextView)findViewById(R.id.select_5);
        mSelectedNumbers[5] = (TextView)findViewById(R.id.select_6);
        mSelectedNumbers[6] = (TextView)findViewById(R.id.select_7);
        mSelectedNumbers[7] = (TextView)findViewById(R.id.select_8);
        mSelectedNumbers[8] = (TextView)findViewById(R.id.select_9);

        for(int i = 0; i< mSelectedNumbers.length; i++){
            mSelectedNumbers[i].setOnClickListener(selectClickListener);
        }


    }

    View.OnClickListener cellClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(mSelectedNumber!=0){
                TextView tv = (TextView)view;
                tv.setText(Html.fromHtml("<b>"+mSelectedNumber+"</b>"));
            }
        }
    };

    View.OnLongClickListener cellLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            TextView tv = (TextView)view;
            tv.setText("");
            return true;
        }
    };

    View.OnClickListener selectClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            TextView tv = (TextView)view;
            mSelectedNumber = Integer.parseInt(tv.getText().toString());

            for(int i=0; i<mSelectedNumbers.length; i++){
                mSelectedNumbers[i].setBackgroundColor(Color.rgb(231,97,1));
                mSelectedNumbers[i].setTextColor(Color.rgb(0,0,0));
            }
            tv.setBackgroundColor(Color.rgb(63,81,180));
            tv.setTextColor(Color.rgb(255,255,255));
        }
    };


}
