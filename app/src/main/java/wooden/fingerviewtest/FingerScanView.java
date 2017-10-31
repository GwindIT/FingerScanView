package wooden.fingerviewtest;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;


public class FingerScanView extends android.support.v7.widget.AppCompatImageView {
    private Bitmap mScanningLineBitmap;
    private Drawable mFrameDrawable;
    private Drawable mTopMaskDrawable;

    private Rect mScanLineRect;
    private Rect mContentRect;
    private Rect mDestRect;

    // the width and the height of current fingerScanView, of which using
    // to define the width and the height of our view
    private int mWholeWidth;
    private int mWholeHeight;

    // the height value of which using to indicate the scanned percent
    private int mScannedPercentHeight = 0;


    public FingerScanView(Context context) {
        this(context, null);
    }

    public FingerScanView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FingerScanView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initBitmap();
    }

    private void initBitmap() {
        mFrameDrawable = ContextCompat.getDrawable(getContext(), R.drawable.finger_frame);
        mTopMaskDrawable = ContextCompat.getDrawable(getContext(), R.drawable.finger_mask);

        mScanningLineBitmap = ((BitmapDrawable) ContextCompat.getDrawable(getContext(), R.drawable.finger_flash_line)).getBitmap();

        mScanLineRect = new Rect(0, 0, mScanningLineBitmap.getWidth(), mScanningLineBitmap.getHeight());

        mDestRect = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // draw the foreground view
        mFrameDrawable.setBounds(mContentRect);
        mFrameDrawable.draw(canvas);

        // draw the mask drawable
        mTopMaskDrawable.setBounds(mContentRect);
        canvas.save();
        int top = mWholeHeight - mScannedPercentHeight;
        canvas.clipRect(0, 0, mWholeWidth, top);
        mTopMaskDrawable.draw(canvas);
        canvas.restore();

        // draw the line
        mDestRect.set(0, top, mWholeWidth, top + mScanningLineBitmap.getHeight());
        canvas.drawBitmap(mScanningLineBitmap, mScanLineRect, mDestRect, null);

        if (mScannedPercentHeight >= mWholeHeight) {
            mScannedPercentHeight = 0;
        }
        // update 10 each times
        mScannedPercentHeight += 3;

        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWholeWidth = getMeasuredWidth();
        mWholeHeight = getMeasuredHeight();

        mContentRect = new Rect(0, 0, mWholeWidth, mWholeHeight);
    }
}





















































































