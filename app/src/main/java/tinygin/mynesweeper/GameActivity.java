package tinygin.mynesweeper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import java.util.Random;

public class GameActivity extends Activity {

    public static final String PLAYER_NAME = "PlayerName";

    private final String TAG = "GameActivity";
    private Player player;
    private Buttons hsBtn;
    private FieldDrawer fDraw;
    private int fDim;
    private int fDimH;
    private FieldTile[][] gameField;

    String pName = "John Doe";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        fDim = Resources.getSystem().getDisplayMetrics().widthPixels;
        fDimH = Resources.getSystem().getDisplayMetrics().heightPixels;
        pName = getIntent().getStringExtra(PLAYER_NAME);
        player = new Player(pName);
        hsBtn = new Buttons("HighScores");
        setButtonDimensions(hsBtn, fDim, fDimH);

        try {
            gameField = GameOverActivity.getGameField();
            int bombsNumber = 0;
            for (int i = 0; i < gameField.length; i++) {
                for (int j = 0; j < gameField.length; j++) {
                    gameField[i][j].setUnClicked();
                    if (gameField[i][j].isBomb())
                            bombsNumber += 1;
                }
            }
            player.setTileLeft(gameField.length * gameField.length - bombsNumber);
        }
        catch (Exception e) {
            Log.d(TAG, e.toString());
        }

        if (gameField == null) {
            gameField = generateField();

            setBombs(gameField);
            setNumbers(gameField);
            setFieldDimensions(gameField, fDim);
        }

        fDraw = new FieldDrawer(this, player, gameField, hsBtn);

        setContentView(fDraw);

        Log.d(TAG, "GameActivity was started");
    }

    public static Intent makeGameActivityIntent(Context context, String playerName) {
        Intent gameIntent = new Intent(context, GameActivity.class);
        gameIntent.putExtra(PLAYER_NAME, playerName);
        return gameIntent;
    }

    private FieldTile[][] generateField() {

        Random rand = new Random();
        int fSize = rand.nextInt(11) + 10;

        FieldTile[][] gameField = new FieldTile[fSize][fSize];

        for (int i=0; i < fSize; i++) {
            for (int j = 0; j < fSize; j++) {
                gameField[i][j] = new FieldTile();
            }
        }

        return gameField;
    }

    private void setBombs(FieldTile[][] gameField) {

        int bombsNumber = (gameField.length*4/10)*gameField.length; //40% - бомбы ) вынести куда-нибудь за пределы метода?
        player.setTileLeft(gameField.length * gameField.length - bombsNumber);
        Random rand = new Random();

        while (bombsNumber >0) {
            int i = rand.nextInt(gameField.length);
            int j = rand.nextInt(gameField.length);
            if (!gameField[i][j].isBomb()) {
                gameField[i][j].setAsBomb();
                bombsNumber -= 1;
            }
        }
    }

    private void setNumbers(FieldTile[][] gameField) {

        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField.length; j++) {
                int counter = 0;
                if (!gameField[i][j].isBomb()) {
                    for (int k = i - 1; k < i + 2; k++) {
                        for (int l = j - 1; l < j + 2; l++) {
                            if (k >= 0 && k < gameField.length && l >= 0 && l < gameField.length) {
                                if (gameField[k][l].isBomb())
                                    counter += 1;
                            }
                        }
                    }
                    gameField[i][j].setMyNumber(counter);
                }
            }
        }
    }

    private void setFieldDimensions(FieldTile[][] gameField, int fDim) {

        float width = (2 * fDim) / (3 * gameField.length + 1);
        float delta = width / 2;
        float height = width;

        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField.length; j++) {

                gameField[i][j].setX(delta + i * (delta + width));
                gameField[i][j].setY(2*delta + j * (delta + height));
                gameField[i][j].setHeight(height);
                gameField[i][j].setWidth(width);
            }
        }
    }

    private void setButtonDimensions(Buttons btn, int fDim, int fDimH) {

        float delta = fDimH - fDim;
        btn.setX(20);
        btn.setY(fDimH - delta/3);
        btn.setXEnd(fDim/3);
        btn.setYEnd(fDimH + 10 - delta/3 + delta/10);

    }

    public void finish() {
        super.finish();
        Log.d(TAG, "Was finished");
    }

}
