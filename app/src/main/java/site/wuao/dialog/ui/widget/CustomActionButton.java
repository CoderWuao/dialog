package site.wuao.dialog.ui.widget;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import site.wuao.dialog.R;

/**
 * 页面
 *
 * @author wuao
 * @date 2018.07.04
 * @note -
 * ---------------------------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class CustomActionButton extends ActionButton {
    /**
     * 构造函数
     *
     * @param context 上下文(Activity的Context)
     */
    public CustomActionButton(Context context) {
        super(context);

        // title
        FrameLayout flTitle = findViewById(R.id.fl_top_dialog_action_button);
        LinearLayout.LayoutParams originParams = (LinearLayout.LayoutParams) flTitle.getLayoutParams();
        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        titleParams.setMargins(originParams.leftMargin, context.getResources().getDimensionPixelOffset(R.dimen.dis_64), originParams.rightMargin, originParams.bottomMargin);
        flTitle.setLayoutParams(titleParams);

        // message
        FrameLayout flCenter = findViewById(R.id.fl_center_dialog_action_button);
        LinearLayout.LayoutParams centerOriginParams = (LinearLayout.LayoutParams) flCenter.getLayoutParams();
        LinearLayout.LayoutParams centerParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        centerParams.setMargins(context.getResources().getDimensionPixelOffset(R.dimen.dis_80), context.getResources().getDimensionPixelOffset(R.dimen.dis_80),
                context.getResources().getDimensionPixelOffset(R.dimen.dis_80),  context.getResources().getDimensionPixelOffset(R.dimen.dis_64));
        flCenter.setLayoutParams(centerParams);

        setTextColor(R.id.btn_left_dialog_action_button, ContextCompat.getColor(context, R.color.font_747778));
        setTextColor(R.id.btn_right_dialog_action_button, ContextCompat.getColor(context, R.color.font_0bd9b2));
        setTextColor(R.id.tv_title_dialog_action_button, ContextCompat.getColor(context, R.color.font_1b1b1c));
        setTextColor(R.id.tv_message_dialog_action_button, ContextCompat.getColor(context, R.color.font_747778));
    }
}
