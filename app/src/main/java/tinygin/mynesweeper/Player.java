package tinygin.mynesweeper;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Player {
    private String name;
    private int score;
    private int tilesLeft;

    public Player(String name) {
        this.name = name;
        this.score = 0;
        tilesLeft = 0;
    }

    public int getScore() {
        return score;
    }

    public void delScore() {
        score = 0;
    }

    public String getName() {
        return name;
    }

    public void addScore (int n) {
        this.score += n;
    }

    public void onDraw(Canvas canvas, Paint paint) {
        float delta = canvas.getHeight()-canvas.getWidth();

        paint.setTextSize(delta/10);
        paint.setColor(Color.BLUE);

        float bLine = canvas.getHeight() - delta/2 - delta/20;

        float nameWidth = paint.measureText(name);
        canvas.drawText(score+"", 70, bLine, paint);
        canvas.drawText(name, canvas.getWidth() - nameWidth - 20, bLine, paint);
    }

    public void setTileLeft(int tiles) {
        this.tilesLeft = tiles;
    }

    public int getTilesLeft() {
        return this.tilesLeft;
    }

}
