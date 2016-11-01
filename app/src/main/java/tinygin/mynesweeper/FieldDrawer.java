package tinygin.mynesweeper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class FieldDrawer extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = "FIELD_DRAWER";
    private GameThread mThread;
    private Paint paint;
    private Player player;
    private static FieldTile[][] gameField;
    public static final String YOU_LOSE = "Sorry, you lose :(";
    public static final String YOU_WIN = "Great job! You win!";
    Buttons hsBtn;
    Activity myActivity;

    public class GameThread extends Thread {

        private SurfaceHolder sHolder;
        private boolean running = false;

        public GameThread(SurfaceHolder sHolder) {
            this.sHolder = sHolder;
        }

        public void setRunning(boolean running) {
            this.running = running;
        }
        public void run()
        {
            Canvas canvas;
            while(this.running) {
                canvas = null;
                try {
                    canvas = sHolder.lockCanvas();
                    if (canvas == null)
                        continue;
                    drawTheGame(canvas);
                }
                catch (Exception e) {
                    //Log.d(TAG, e.getMessage());
                }
                finally {
                    if (canvas != null)
                        sHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    public FieldDrawer(Context context, Player player, FieldTile[][] gameField, Buttons hsBtn) {
        super(context);
        this.player = player;
        this.gameField = gameField;
        getHolder().addCallback(this);
        paint = new Paint();
        myActivity = (Activity)context;
        this.hsBtn = hsBtn;

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mThread = new GameThread(getHolder());
        mThread.setRunning(true);
        mThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        mThread.setRunning(false);
        while (retry) {
            try {
                mThread.join();
                retry = false;
            } catch (InterruptedException e) {
                //Log.d(TAG, e.getMessage());
            }
        }
    }

    private void drawTheGame(Canvas canvas) {

        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

        canvas.drawColor(Color.WHITE);

        hsBtn.onDraw(canvas, paint);

        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField.length; j++) {
                gameField[i][j].onDraw(canvas, paint);
            }
        }

        player.onDraw(canvas, paint);
    }

    public boolean onTouchEvent(MotionEvent e) {

        float hitX = e.getX();
        float hitY = e.getY();

        if (e.getAction() == MotionEvent.ACTION_DOWN) {

            if (hsBtn.isHit(hitX, hitY)) {
                myActivity.startActivity(HighScoresActivity.makeHSActivityIntent(myActivity));
            }

            for (int i = 0; i < gameField.length; i++) {
                for (int j = 0; j < gameField.length; j++) {

                    if (gameField[i][j].hitTheTile(hitX, hitY)) {
                        onHitCheck(i, j);
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private void onHitCheck(int i, int j) {
        if (gameField[i][j].isClicked()) {

        }
        else if (gameField[i][j].isBomb()) {
            myActivity.startActivity(GameOverActivity.makeGameOverActivityIntent(myActivity, player.getName(), YOU_LOSE, player.getScore()));
            myActivity.finish();
        }
        else {
            int counter = 1;
            gameField[i][j].setClicked();
            for (int k = i - 1; k < i + 2; k++) {
                for (int l = j - 1; l < j + 2; l++) {
                    if (k >= 0 && k < gameField.length && l >= 0 && l < gameField.length) {
                        if (!gameField[k][l].isBomb() && !gameField[k][l].isClicked()) {
                            gameField[k][l].setClicked();
                            counter += 1;
                        }
                    }
                }
            }
            player.addScore(counter);
            if (player.getTilesLeft() == player.getScore()) {
                myActivity.startActivity(GameOverActivity.makeGameOverActivityIntent(myActivity, player.getName(), YOU_WIN, player.getScore()));
                myActivity.finish();
            }
        }
    }

    public static FieldTile[][] getGameField() {
        return gameField;
    }

}
