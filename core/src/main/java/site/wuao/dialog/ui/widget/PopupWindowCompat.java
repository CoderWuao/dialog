package site.wuao.dialog.ui.widget;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.widget.PopupWindow;

/**
 * 兼容PopupWindow
 *
 * @author wuao
 * @date 2018.07.05
 * @note -
 * ---------------------------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class PopupWindowCompat extends PopupWindow {
    public PopupWindowCompat(Context context) {
        super(context);
    }

    public PopupWindowCompat(View contentView) {
        super(contentView);
    }

    public PopupWindowCompat(int width, int height) {
        super(width, height);
    }

    public PopupWindowCompat(View contentView, int width, int height) {
        super(contentView, width, height);
    }

    public PopupWindowCompat(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);
    }

    @Override
    public void showAsDropDown(View anchor) {
        // 解决充满屏幕而不是在控件下方bug
        if (Build.VERSION.SDK_INT == 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        // 解决充满屏幕而不是在控件下方bug
        if (Build.VERSION.SDK_INT == 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor, xoff, yoff);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        super.showAsDropDown(anchor, xoff, yoff, gravity);
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
    }
}
