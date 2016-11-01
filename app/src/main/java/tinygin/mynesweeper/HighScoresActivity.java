package tinygin.mynesweeper;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HighScoresActivity extends Activity {

    private SharedPreferences sPrefs;
    private int[] hsScore;
    private int[] hsName;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);

        sPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Log.d("TAG", sPrefs.getAll().keySet().toString());

        initHsTableData();
        updateScoreTable();

    }

    public static Intent makeHSActivityIntent(Context context) {
        Intent intent = new Intent(context, HighScoresActivity.class);
        return intent;
    }

    protected void onStop() {
        super.onStop();
        finish();
    }

    //yep, this is rly creepy :) want to find another way
    private void updateScoreTable() {

        int i = 0;

        List<Integer> result = sortScores();
        for (Integer score : result) {
            for (String name : sPrefs.getAll().keySet()) {
                if (i < 10) {
                    if (Integer.parseInt(sPrefs.getAll().get(name) + "") == score) {
                        TextView temp_score = (TextView) findViewById(hsScore[i]);
                        TextView temp_name = (TextView) findViewById(hsName[i]);
                        temp_name.setText(name);
                        temp_score.setText(score + "");
                        i += 1;
                    }
                } else {
                    return;
                }
            }
        }
    }

    //yep, this is rly creepy :) want to find another way
    private List<Integer> sortScores() {
        List<Integer> hs = new ArrayList<>();
        for (String name : sPrefs.getAll().keySet()) {
            if (!hs.contains(Integer.parseInt(sPrefs.getAll().get(name) + "")))
                hs.add(Integer.parseInt(sPrefs.getAll().get(name) + ""));
        }
        Collections.sort(hs, Collections.reverseOrder());
        return hs;
    }

    //yep, this is rly creepy :) want to find another way
    private void initHsTableData() {
        hsScore = new int[10];
        hsName = new int[10];

        hsScore[0] = R.id.hsResPl0;
        hsScore[1] = R.id.hsResPl1;
        hsScore[2] = R.id.hsResPl2;
        hsScore[3] = R.id.hsResPl3;
        hsScore[4] = R.id.hsResPl4;
        hsScore[5] = R.id.hsResPl5;
        hsScore[6] = R.id.hsResPl6;
        hsScore[7] = R.id.hsResPl7;
        hsScore[8] = R.id.hsResPl8;
        hsScore[9] = R.id.hsResPl9;

        hsName[0] = R.id.hsNamePl0;
        hsName[1] = R.id.hsNamePl1;
        hsName[2] = R.id.hsNamePl2;
        hsName[3] = R.id.hsNamePl3;
        hsName[4] = R.id.hsNamePl4;
        hsName[5] = R.id.hsNamePl5;
        hsName[6] = R.id.hsNamePl6;
        hsName[7] = R.id.hsNamePl7;
        hsName[8] = R.id.hsNamePl8;
        hsName[9] = R.id.hsNamePl9;
    }
}
