package com.amrendra.codefiesta.progressbar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Amrendra Kumar on 08/05/16.
 */
public class CustomProgressBar extends View {

    private static final String TAG = CustomProgressBar.class.getSimpleName();

    public enum Style {
        REGULAR, PIE, PIE_OUTER, PIE_INNER, HOLLOW
    }

    // Paints
    private Paint centerBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint progressRingBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint progressRingForegroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint erasePaint = new Paint();

    // Colors
    private int centerBackgroundColor = Color.parseColor("#213051");
    private int progressRingBackgroundColor = Color.parseColor("#DFDFDF");
    private int progressRingForegroundColor = Color.parseColor("#2C98C1");
    private int textColor = Color.parseColor("#FFFFFF");

    // Text progress
    private boolean displayProgressPercentage = true;
    private float textSize = 24;

    private RectF outerRing = new RectF();// ProgressBar rectangle
    private RectF innerRing = new RectF();// Center Background rectangle

    private float ringRadiusRatio = 0.85f;

    private Style style = Style.REGULAR;// Default regular style

    private float padding = 4;
    private float actualPadding = 0;
    private float density = 1;

    private float progress = 0.0f;
    private float startAngle = 0;

    private Bitmap bitmap = null;
    private Canvas canvas = null;

    private String textMiddle = "0";

    public CustomProgressBar(Context context) {
        this(context, null);

    }

    public CustomProgressBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        // Debug.check();
        density = context.getResources().getDisplayMetrics().density;
        actualPadding = density * padding;
        centerBackgroundPaint.setColor(centerBackgroundColor);
        progressRingBackgroundPaint.setColor(progressRingBackgroundColor);
        progressRingForegroundPaint.setColor(progressRingForegroundColor);
        erasePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        // Debug.i("density : " + density + " padding :  " + actualPadding);
    }

    @Override
    protected void onDraw(Canvas viewCanvas) {
        super.onDraw(viewCanvas);
        // debugCanvas(canvas,Color.RED,new RectF(canvas.getClipBounds()));
        // debugCanvas(canvas,Color.BLACK,outerRing);
        // debugCanvas(canvas,Color.BLUE,innerRing);
        bitmap.eraseColor(Color.TRANSPARENT);
        float angle = 3.60f * progress;
        if (style == Style.REGULAR) {
            canvas.drawArc(outerRing, -90, 360, true, progressRingBackgroundPaint);
            canvas.drawArc(outerRing, -90 + startAngle, angle, true, progressRingForegroundPaint);
            canvas.drawArc(innerRing, -90, 360, true, centerBackgroundPaint);
        } else if (style == Style.PIE) {
            canvas.drawArc(outerRing, -90, 360, true, progressRingBackgroundPaint);
            canvas.drawArc(outerRing, -90 + startAngle, angle, true, progressRingForegroundPaint);
        } else if (style == Style.PIE_OUTER) {
            canvas.drawArc(innerRing, -90, 360, true, centerBackgroundPaint);
            canvas.drawArc(outerRing, -90 + startAngle, angle, true, progressRingForegroundPaint);
        } else if (style == Style.PIE_INNER) {
            canvas.drawArc(outerRing, -90, 360, true, centerBackgroundPaint);
            canvas.drawArc(innerRing, -90 + startAngle, angle, true, progressRingForegroundPaint);
        } else if (style == Style.HOLLOW) {
            canvas.drawArc(outerRing, -90, 360, true, progressRingBackgroundPaint);
            canvas.drawArc(outerRing, -90 + startAngle, angle, true, progressRingForegroundPaint);
            canvas.drawArc(innerRing, -90, 360, true, erasePaint);
        } else {
            canvas.drawArc(outerRing, -90, 360, true, progressRingBackgroundPaint);
            canvas.drawArc(outerRing, -90 + startAngle, angle, true, progressRingForegroundPaint);
            // More effects to be added soon
        }

        if (displayProgressPercentage) {
            float textHeight = textPaint.descent() - textPaint.ascent();
            float verticalTextOffset = (textHeight / 2) - textPaint.descent();
            String s = textMiddle;
            float horizontalTextOffset = textPaint.measureText(s) / 2;
            canvas.drawText(s, this.getWidth() / 2 - horizontalTextOffset, this.getHeight() / 2
                    + verticalTextOffset, textPaint);
        }
        viewCanvas.drawBitmap(bitmap, 0, 0, null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int wh = Math.min(getMeasuredWidth(), getMeasuredHeight());
        setMeasuredDimension(wh, wh);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int wh = Math.min(w, h);
        outerRing.set(actualPadding, actualPadding, wh - actualPadding, wh - actualPadding);
        innerRing.set(actualPadding + outerRing.width() / 2 * (1.0f - ringRadiusRatio),
                actualPadding + outerRing.height() / 2 * (1.0f - ringRadiusRatio), wh
                        - actualPadding - outerRing.width() / 2 * (1.0f - ringRadiusRatio), wh
                        - actualPadding - outerRing.height() / 2 * (1.0f - ringRadiusRatio));
        bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        updateView();
    }

    @SuppressWarnings("unused")
    private void debugCanvas(Canvas canvas, int color, RectF rect) {
        Paint debugCanvas = new Paint();
        debugCanvas.setColor(color);
        debugCanvas.setStyle(Paint.Style.STROKE);
        // Debug.i("canvas : "+canvas+" color : "+color+" rect : "+rect);
        canvas.drawRect(rect.left, rect.top, rect.right, rect.bottom, debugCanvas);
    }

    private void updateView() {
        if (outerRing != null && outerRing.width() > 0) {
            postInvalidate();
        }
    }

    public void setMiddleText(String text) {
        textMiddle = text;
        invalidate();
    }

    public synchronized void setProgress(float progress) {
        if (progress < 0) {
            progress = 0;
        }
        if (progress > 100) {
            progress = 100;
        }
        this.progress = progress;
        updateView();
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
        updateView();
    }

    public int getProgressRingForegroundColor() {
        return progressRingForegroundColor;
    }

    public void setProgressRingForegroundColor(int progressRingForegroundColor) {
        this.progressRingForegroundColor = progressRingForegroundColor;
        progressRingForegroundPaint.setColor(progressRingForegroundColor);
        updateView();
    }

    public void setProgressRingForegroundColor(String progressRingForegroundColor) {
        try {
            this.progressRingForegroundColor = Color.parseColor(progressRingForegroundColor);
        } catch (IllegalArgumentException ex) {
            Log.e(TAG,
                    "Cannot convert " + progressRingForegroundColor + "  to proper color. "
                            + ex.getLocalizedMessage());
        }
        progressRingForegroundPaint.setColor(this.progressRingForegroundColor);
        updateView();
    }

    public int getProgressRingBackgroundColor() {
        return progressRingBackgroundColor;
    }

    public void setProgressRingBackgroundColor(int progressRingBackgroundColor) {
        this.progressRingBackgroundColor = progressRingBackgroundColor;
        progressRingBackgroundPaint.setColor(progressRingBackgroundColor);
        updateView();
    }

    public void setProgressRingBackgroundColor(String progressRingBackgroundColor) {
        try {
            this.progressRingBackgroundColor = Color.parseColor(progressRingBackgroundColor);
        } catch (IllegalArgumentException ex) {
            Log.e(TAG,
                    "Cannot convert " + progressRingBackgroundColor + "  to proper color. "
                            + ex.getLocalizedMessage());
        }
        progressRingBackgroundPaint.setColor(this.progressRingBackgroundColor);
        updateView();
    }

    public int getCenterBackgroundColor() {
        return centerBackgroundColor;
    }

    public void setCenterBackgroundColor(int centerBackgroundColor) {
        this.centerBackgroundColor = centerBackgroundColor;
        centerBackgroundPaint.setColor(centerBackgroundColor);
        updateView();
    }

    public void setCenterBackgroundColor(String centerBackgroundColor) {
        try {
            this.centerBackgroundColor = Color.parseColor(centerBackgroundColor);
        } catch (IllegalArgumentException ex) {
            Log.e(TAG,
                    "Cannot convert " + centerBackgroundColor + "  to proper color. "
                            + ex.getLocalizedMessage());
        }
        centerBackgroundPaint.setColor(this.centerBackgroundColor);
        updateView();
    }

    public float getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(float startAngle) {
        this.startAngle = startAngle;
        updateView();
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        this.textPaint.setColor(this.textColor);
        updateView();
    }

    public void setTextColor(String color) {
        this.textColor = Color.parseColor(color);
        this.textPaint.setColor(this.textColor);
        updateView();
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
        this.textPaint.setTextSize(textSize);
        updateView();
    }

    public boolean isDisplayProgressPercentage() {
        return displayProgressPercentage;
    }

    public void setDisplayProgressPercentage(boolean displayProgressPercentage) {
        this.displayProgressPercentage = displayProgressPercentage;
        updateView();
    }

    /*
     * public float getPadding() { return padding; }
     *
     * public void setPadding(float padding) { this.padding = padding;
     * this.actualPadding = density * padding; updateView(); }
     */

    public float getRingRadiusRatio() {
        return ringRadiusRatio;
    }

    public void setRingRadiusRatio(float ringRadiusRatio) {
        this.ringRadiusRatio = ringRadiusRatio;
        updateView();
    }

}
