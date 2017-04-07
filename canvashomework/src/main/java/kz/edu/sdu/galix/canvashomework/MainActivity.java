package kz.edu.sdu.galix.canvashomework;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new DrawView(this));
    }

    class DrawView extends View {

        Paint p;
        Path path;
        Path pathDst;
        Matrix matrix;
        RectF rectf;

        public DrawView(Context context) {
            super(context);
            p = new Paint();
            p.setStrokeWidth(3);
            p.setStyle(Paint.Style.STROKE);

            path = new Path();
            pathDst = new Path();

            rectf = new RectF(200,10,400,190);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawARGB(80, 102, 204, 255);
            path.reset();

            path.addArc(rectf,0f,360f);
            path.close();
            path.moveTo(70,75);

            path.arcTo(rectf,0f,190);

            path.addCircle(250, 110, 15, Path.Direction.CW);

            canvas.drawPath(path,p);
            pathDst.reset();
            pathDst.moveTo(250,300);
            pathDst.addCircle(290, 400, 200, Path.Direction.CW);
            canvas.drawPath(pathDst,p);


        }
    }


}
