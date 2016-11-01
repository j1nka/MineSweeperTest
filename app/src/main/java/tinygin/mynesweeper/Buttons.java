package tinygin.mynesweeper;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


public class Buttons {
    private float x;
    private float y;
    private float x_end;
    private float y_end;
    private String bText;

    public Buttons(String bText) {
        this.bText = bText;
    }

    public boolean isHit(float hitX, float hitY) {
        if (hitX >= this.x && hitX <= this.x_end
                && hitY >= this.y && hitY <= this.y_end) {
            return true;
        }
        else
            return false;
    }

    public void onDraw(Canvas canvas, Paint paint) {
        float delta = canvas.getHeight()-canvas.getWidth();

        paint.setTextSize(delta/10);
        paint.setColor(Color.GREEN);

        canvas.drawRect(x, y, x_end, y_end, paint);
        paint.setColor(Color.YELLOW);

        canvas.drawText(bText, x+5,y_end-15, paint);
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setXEnd(float x_end) {
        this.x_end = x_end;
    }

    public void setYEnd(float y_end) {
        this.y_end = y_end;
    }

}
