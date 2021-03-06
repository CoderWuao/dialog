package site.wuao.dialog.ui.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager.LayoutParams;

import site.wuao.dialog.R;


/**
 * 底部弹出对话框
 *
 * @author mos
 * @date 2017.02.24
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class BottomSheet extends PopupDialog {

    /**
     * 构造函数
     *
     * @param context 上下文(Activity的Context)
     * @param layoutId 布局id
     * @param dim 是否背景变暗
     */
    public BottomSheet(Context context, int layoutId, boolean dim) {
        super(context, layoutId, (dim ? R.style.FullScreenDialog : R.style.FullScreenDialogTrans));
        initView();
    }

    /**
     * 构造函数
     *
     * @param context 上下文(Activity的Context)
     * @param layoutId 布局id
     * @param style 样式
     */
    public BottomSheet(Context context, int layoutId, int style) {
        super(context, layoutId, style);
        initView();
    }

    /**
     * 初始化View
     */
    private void initView() {
        mWindow.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        mWindow.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        // 默认使用底部弹出效果
        setWindowAnimations(R.style.BottomDialogAnimation);
    }
}
