package site.wuao.core.ui.widget;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

/**
 * 顶部弹窗
 *
 * @author wuao
 * @date 2018.07.05
 * @note -
 * ---------------------------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class TopSheet extends PopupWindowCompat {
    private View mContentView;

    public TopSheet(Context context) {
        super(context);
        initView();
    }

    public TopSheet(View contentView) {
        super(contentView);
        initView();
    }

    public TopSheet(int width, int height) {
        super(width, height);
        initView();
    }

    public TopSheet(View contentView, int width, int height) {
        super(contentView, width, height);
        mContentView = contentView;
        initView();
    }

    public TopSheet(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);
        mContentView = contentView;
        initView();
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        startAnimation();
    }

    @Override
    public void showAsDropDown(View anchor) {
        super.showAsDropDown(anchor);
        startAnimation();
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        super.showAsDropDown(anchor, xoff, yoff);
        startAnimation();
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        super.showAsDropDown(anchor, xoff, yoff, gravity);
        startAnimation();
    }

    private void initView() {
        setOutsideTouchable(true);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void startAnimation() {
        ScaleAnimation sa = new ScaleAnimation(1.0f, 1.0f, 0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);
        sa.setDuration(200);
        if (mContentView != null) {
            mContentView.startAnimation(sa);
        }
    }
}
