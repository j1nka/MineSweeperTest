package tinygin.mynesweeper;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class FieldTile {

    private float topX;
    private float topY;
    private float width;
    private float height;
    private int myNumber;
    private boolean bomb;
    private boolean clicked;

    public FieldTile() {
        this.topX = 0;
        this.topY = 0;
        this.width = 0;
        this.height = 0;
        this.myNumber = 0;
        this.clicked = false;
        this.bomb = false;
    }

    public void setX(float x) {
        this.topX = x;
    }

    public float X() {
        return this.topX;
    }

    public void setY(float y) {
        this.topY = y;
    }

    public float Y() {
        return this.topY;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getWidth() {
        return this.width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getHeight() {
        return this.height;
    }

    public boolean isClicked() {
        return this.clicked;
    }

    public boolean isBomb() {
        return this.bomb;
    }

    public void setAsBomb() {
        this.bomb = true;
    }

    public void setMyNumber(int number) {
        this.myNumber = number;
    }

    public int getMyNumber() {
        return this.myNumber;
    }

    public void setClicked() {
        this.clicked = true;
    }

    public void setUnClicked() {
        this.clicked = false;
    }

    public boolean hitTheTile(float hitX, float hitY) {
        if (hitX >= this.topX && hitX <= (this.topX + this.width)
                && hitY >= this.topY && hitY <= (this.topY + this.height)) {
            return true;
        }
        else
            return false;
    }

    public void onDraw(Canvas canvas, Paint paint) {

        float x_st = topX;
        float x_end = topX + width;
        float y_st = topY;
        float y_end = topY + height;

        if (!this.isClicked()) {

               paint.setColor(Color.GREEN);
               canvas.drawRect(x_st, y_st, x_end, y_end, paint);

        }

        else if (!this.isBomb()) {

            paint.setColor(Color.GRAY);
            canvas.drawRect(x_st, y_st, x_end, y_end, paint);
            paint.setTextSize(width);
            paint.setColor(Color.WHITE);
            canvas.drawText(myNumber+"", topX + width/4,
                    topY + 3*height/4, paint);

        }

    }

}
