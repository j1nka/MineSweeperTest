package tinygin.mynesweeper;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameOverActivity extends Activity implements View.OnClickListener {
    public static final String PLAYER_INFO = "PlayerName";
    public static final String PLAYER_SCORE = "PlayerScore";
    public static final String END_CONDITION = "WinnerOrLooser";
    public static final String TAG = "GameOverActivity";
    private String pName;
    private int pScore;
    private static FieldTile[][] gameField;
    private SharedPreferences sPrefs;
    private int[] hsScore;
    private int[] hsName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameover);
        initHsTableData();

        Intent intent = getIntent();
        gameField = null;

        TextView tv = (TextView)findViewById(R.id.goMessage);
        tv.setTextSize(20);
        tv.setText(intent.getStringExtra(END_CONDITION));

        pName = intent.getStringExtra(PLAYER_INFO);
        pScore = intent.getIntExtra(PLAYER_SCORE, 0);
        gameField = FieldDrawer.getGameField();
        sPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        //cleanScore();
        saveScore();
        updateScoreTable();

        Button restartGame = (Button)findViewById(R.id.restartGame);
        Button newGame = (Button)findViewById(R.id.newGame);
        Button quitGame = (Button)findViewById(R.id.quitGame);

        restartGame.setOnClickListener(this);
        newGame.setOnClickListener(this);
        quitGame.setOnClickListener(this);

    }

    public static Intent makeGameOverActivityIntent(Context context, String pName, String condition, int score) {
        Intent gameIntent = new Intent(context, GameOverActivity.class);

        gameIntent.putExtra(PLAYER_INFO, pName);
        gameIntent.putExtra(END_CONDITION, condition);
        gameIntent.putExtra(PLAYER_SCORE, score);

        return gameIntent;
    }

    public void onClick(View view) {

        Intent intent = new Intent();

        switch(view.getId()){
            case R.id.restartGame:
                intent = GameActivity.makeGameActivityIntent(this, pName);
                startActivity(intent);
                break;
            case R.id.newGame:
                gameField = null;
                intent = GameActivity.makeGameActivityIntent(this, pName);
                startActivity(intent);
                break;
            case R.id.quitGame:
                this.finishAffinity();
                break;
            default:
        }
    }

    public static FieldTile[][] getGameField() {
        return gameField;
    }

    protected void onStop() {
        super.onStop();
        finish();
        Log.d(TAG, "GameOverActivity Was finished");
    }

    private void saveScore() {

        SharedPreferences.Editor ed = sPrefs.edit();

        if (sPrefs.getAll().keySet().contains(pName)) {

            if (Integer.parseInt(sPrefs.getAll().get(pName)+"") > pScore) {
                return;
            }
            else {
                ed.putInt(pName, pScore);
                ed.apply();
                Toast.makeText(this, pName + "'s score saved", Toast.LENGTH_SHORT).show();
            }
        }

        else {
            ed.putInt(pName, pScore);
            ed.apply();
            Toast.makeText(this, pName + "'s score saved", Toast.LENGTH_SHORT).show();
        }
    }

    private void cleanScore() {
        sPrefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPrefs.edit();
        ed.clear();
        ed.apply();
    }

    //yep, this is rly creepy :) want to find another way
    private void updateScoreTable() {

        int i = 0;

        List<Integer> result = sortScores();
        for (Integer score : result) {
            for (String name : sPrefs.getAll().keySet()) {
                if (i < 10) {
                    if (Integer.parseInt(sPrefs.getAll().get(name)+"") == score) {
                        TextView temp_score = (TextView)findViewById(hsScore[i]);
                        TextView temp_name = (TextView)findViewById(hsName[i]);
                        temp_name.setText(name);
                        temp_score.setText(score + "");
                        i += 1;
                    }
                }
                else {
                    return;
                }
            }
        }
    }

    //yep, this is rly creepy :) want to find another way
    private List<Integer> sortScores() {
        List<Integer> hs = new ArrayList<>();
        for(String name : sPrefs.getAll().keySet()) {
            if (!hs.contains(Integer.parseInt(sPrefs.getAll().get(name)+"")))
                hs.add(Integer.parseInt(sPrefs.getAll().get(name)+""));
        }
        Collections.sort(hs, Collections.reverseOrder());
        return hs;
    }

    //yep, this is rly creepy :) want to find another way
    private void initHsTableData() {
        hsScore = new int[10];
        hsName = new int[10];

        hsScore[0] = R.id.resPl0;
        hsScore[1] = R.id.resPl1;
        hsScore[2] = R.id.resPl2;
        hsScore[3] = R.id.resPl3;
        hsScore[4] = R.id.resPl4;
        hsScore[5] = R.id.resPl5;
        hsScore[6] = R.id.resPl6;
        hsScore[7] = R.id.resPl7;
        hsScore[8] = R.id.resPl8;
        hsScore[9] = R.id.resPl9;

        hsName[0] = R.id.namePl0;
        hsName[1] = R.id.namePl1;
        hsName[2] = R.id.namePl2;
        hsName[3] = R.id.namePl3;
        hsName[4] = R.id.namePl4;
        hsName[5] = R.id.namePl5;
        hsName[6] = R.id.namePl6;
        hsName[7] = R.id.namePl7;
        hsName[8] = R.id.namePl8;
        hsName[9] = R.id.namePl9;
    }

}

