package site.wuao.dialog.ui.widget;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import site.wuao.core.ui.widget.ActionButton;
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
        setTextColor(R.id.btn_left_dialog_action_button, ContextCompat.getColor(context, R.color.font_747778));
        setTextColor(R.id.btn_right_dialog_action_button, ContextCompat.getColor(context, R.color.font_0bd9b2));
        setTextColor(R.id.tv_title_dialog_action_button, ContextCompat.getColor(context, R.color.font_1b1b1c));
        setTextColor(R.id.tv_message_dialog_action_button, ContextCompat.getColor(context, R.color.font_747778));
    }
}
