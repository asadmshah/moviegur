package com.asadmshah.moviegur.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.AttributeSet;
import android.view.View;

public class BlurView extends View {

    private RenderScript renderScript;
    private ScriptIntrinsicBlur scriptIntrinsicBlur;
    private Bitmap bitmapDst;
    private int scaleFactor;

    public BlurView(Context context) {
        super(context);
    }

    public BlurView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BlurView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BlurView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        renderScript = RenderScript.create(getContext());
        scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (bitmapDst != null) {
            canvas.save();
            canvas.scale(scaleFactor, scaleFactor);
            canvas.translate(Math.abs(bitmapDst.getWidth()*scaleFactor - canvas.getWidth()), Math.abs(bitmapDst.getHeight()*scaleFactor - canvas.getHeight()));
            canvas.drawBitmap(bitmapDst, 0, 0, null);
            canvas.restore();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        scriptIntrinsicBlur.destroy();
        renderScript.destroy();
    }

    public void setBlurSource(View view, int downSampleFactor, float radius) {

        if (view != null && view.getWidth() > 0 && view.getHeight() > 0) {
            scaleFactor = downSampleFactor;

            int w = view.getWidth();
            w = w / downSampleFactor;
            int h = view.getHeight();
            h = h / downSampleFactor;

            Bitmap bitmapSrc = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            bitmapDst = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);

            Canvas canvas = new Canvas(bitmapSrc);
            canvas.scale(1f / downSampleFactor, 1f / downSampleFactor);
            view.draw(canvas);

            Allocation allocSrc = Allocation.createFromBitmap(renderScript, bitmapSrc);
            Allocation allocDst = Allocation.createFromBitmap(renderScript, bitmapDst);

            scriptIntrinsicBlur.setRadius(radius);
            scriptIntrinsicBlur.setInput(allocSrc);
            scriptIntrinsicBlur.forEach(allocDst);

            allocDst.copyTo(bitmapDst);
        } else {
            bitmapDst = null;
        }

        invalidate();
    }

}
