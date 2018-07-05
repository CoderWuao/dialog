package site.wuao.dialog.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import site.wuao.dialog.ui.utils.DrawableUtil;
import site.wuao.dialogcore.R;


/**
 * 菜单弹窗控件
 *
 * @author wuao
 * @date 2017.06.10
 * @note -
 * ---------------------------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class MenuPopupWindow extends PopupWindow {
    /** 默认参数 */
    private static MenuPopupWindow.DefaultOptions sOptions;
    /** 上下文 */
    private Context mContext;
    /** 依附的页面 */
    private Activity mActivity;
    /** 菜单条目点击监听 */
    private OnItemClickListener mOnItemClickListener;
    /** 菜单弹窗的根布局 */
    private LinearLayout mRootView;
    /** 内容的根布局 */
    private LinearLayout mContentRootView;
    /** 头布局 */
    private View mHeadView;
    /** 脚布局 */
    private View mFootView;
    /** 固定控件宽度 */
    private int mAnchorMeasuredWidth;
    /** 固定控件高度 */
    private int mAnchorMeasuredHeight;
    /** 弹窗控件宽度 */
    private int mMenuPopupWindowMeasuredWidth;
    /** 弹窗控件高度 */
    private int mMenuPopupWindowMeasureHeight;

    /**
     * 构造函数
     *
     * @param context 上下文
     */
    public MenuPopupWindow(Context context) {
        super(context);

        mContext = context;

        if (mContext instanceof Activity) {
            mActivity = ((Activity) context);
        }

        initView(context);
    }

    /**
     * 获取主色
     *
     * @param context 上下文
     * @return 主色
     */
    private static int getPrimaryColor(final Context context) {
        int color = context.getResources().getIdentifier("colorPrimary", "attr", context.getPackageName());
        if (color != 0) {
            TypedValue t = new TypedValue();
            context.getTheme().resolveAttribute(color, t, true);
            color = t.data;

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            TypedArray t = context.obtainStyledAttributes(new int[]{android.R.attr.colorPrimary});
            color = t.getColor(0, ContextCompat.getColor(context, R.color.menu_popup_window_def_bg));
            t.recycle();

        } else {
            TypedArray t = context.obtainStyledAttributes(new int[]{R.attr.colorPrimary});
            color = t.getColor(0, ContextCompat.getColor(context, R.color.menu_popup_window_def_bg));
            t.recycle();
        }

        return color;
    }

    /**
     * 获取默认参数
     *
     * @return 默认参数对象
     */
    public static MenuPopupWindow.DefaultOptions getDefaultOptions() {
        return sOptions;
    }

    /**
     * 生成默认参数
     *
     * @param context 上下文
     */
    private static void generateDefaultOptions(Context context) {
        sOptions = new DefaultOptions();

        // 背景颜色(只有通过Activity的context来获取)
        if (context != null) {
            sOptions.backgroundColor = getPrimaryColor(context);
        } else {
            sOptions.backgroundColor = ContextCompat.getColor(context, R.color.menu_popup_window_def_bg);
        }

        Resources res = context.getResources();

        sOptions.iconWidth = res.getDimensionPixelOffset(R.dimen.menu_popup_window_icon_width);
        sOptions.iconHeight = res.getDimensionPixelOffset(R.dimen.menu_popup_window_icon_height);
        sOptions.textSize = res.getDimensionPixelOffset(R.dimen.menu_popup_window_text_size);
        sOptions.textColor = ContextCompat.getColor(context, R.color.menu_popup_window_text);
        sOptions.paddingLeft = res.getDimensionPixelOffset(R.dimen.menu_popup_window_item_padding_left);
        sOptions.paddingTop = res.getDimensionPixelOffset(R.dimen.menu_popup_window_item_padding_top);
        sOptions.paddingRight = res.getDimensionPixelOffset(R.dimen.menu_popup_window_item_padding_right);
        sOptions.paddingBottom = res.getDimensionPixelOffset(R.dimen.menu_popup_window_item_padding_bottom);
        sOptions.marginBetweenIconAndText = res.getDimensionPixelOffset(R.dimen.menu_popup_window_margin_icon_text);
        sOptions.drawableDivider = ContextCompat.getDrawable(context, R.drawable.shape_menu_popup_window_divider);
        sOptions.activityAlpha = 1.0f;
    }

    /**
     * 初始化控件
     *
     * @param context 上下文
     */
    private void initView(Context context) {
        if (sOptions == null) {
            generateDefaultOptions(context);
        }

        generateRootView();
        setAnimationStyle(R.style.MenuPopupWindowAnimationDefault);
        setBackgroundColor(sOptions.backgroundColor);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // 设置点击其他区域弹窗消失
        setOutsideTouchable(true);
        // 处理第二次点击触发弹窗按钮时,先弹窗消失后弹出弹窗的问题
        setFocusable(true);
        // 在消失的时候恢复界面透明度
        setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setAlpha(1.0f);
            }
        });
    }

    /**
     * 设置弹出时候背景页面透明度
     *
     * @param alpha 透明度
     */
    private void setAlpha(float alpha) {
        if (mActivity != null) {
            WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
            lp.alpha = alpha;
            mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            mActivity.getWindow().setAttributes(lp);
        }
    }

    /**
     * 获取根布局
     *
     * @return 根布局
     */
    public LinearLayout getRootView() {
        return mRootView;
    }

    /**
     * 获取头布局
     *
     * @return 头布局
     */
    public View getHeadView() {
        return mHeadView;
    }

    /**
     * 获取脚布局
     *
     * @return 脚布局
     */
    public View getFootView() {
        return mFootView;
    }

    /**
     * 设置背景颜色
     *
     * @param color 背景颜色
     * @return MenuPopupWindow对象
     * @note 默认颜色是SimpleTitleBar的背景色
     */
    public MenuPopupWindow setBackgroundColor(@ColorInt int color) {
        sOptions.backgroundColor = color;
        return this;
    }

    /**
     * 生成根布局
     */
    private void generateRootView() {
        mRootView = (LinearLayout) View.inflate(mContext, R.layout.menu_popup_window, null);
        mContentRootView = (LinearLayout) View.inflate(mContext, R.layout.menu_popup_window, null);
        mContentRootView.setBackgroundResource(0);
        mRootView.addView(mContentRootView);
        setContentView(mRootView);
    }

    /**
     * 显示弹窗
     *
     * @param anchor 固定弹窗位置的控件
     * @note 弹窗的左上角点压在传入控件的底部中间点
     */
    public void showTopLeftCornerToBottomCenter(View anchor) {
        showTopLeftCornerToBottomCenter(anchor, 0, 0);
    }

    /**
     * 显示弹窗
     *
     * @param anchor 固定弹窗位置的控件
     * @note 弹窗的顶部中间点压在传入控件的底部中间点
     */
    public void showTopCenterToBottomCenter(View anchor) {
        showTopCenterToBottomCenter(anchor, 0, 0);
    }

    /**
     * 显示弹窗
     *
     * @param anchor 固定弹窗位置的控件
     * @note 弹窗的右上角点压在传入控件的底部中间点
     */
    public void showTopRightCornerToBottomCenter(View anchor) {
        showTopRightCornerToBottomCenter(anchor, 0, 0);
    }

    /**
     * 显示弹窗
     *
     * @param anchor 固定弹窗位置的控件
     * @note 弹窗的左下角点压在传入控件的顶部中间点
     */
    public void showBottomLeftCornerToTopCenter(View anchor) {
        showBottomLeftCornerToTopCenter(anchor, 0, 0);
    }

    /**
     * 显示弹窗
     *
     * @param anchor 固定弹窗位置的控件
     * @note 弹窗的底部中间点压在传入控件的顶部中间点
     */
    public void showBottomCenterToTopCenter(View anchor) {
        showBottomCenterToTopCenter(anchor, 0, 0);
    }

    /**
     * 显示弹窗
     *
     * @param anchor 固定弹窗位置的控件
     * @note 弹窗的右下角点压在传入控件的顶部中间点
     */
    public void showBottomRightCornerToTopCenter(View anchor) {
        showBottomRightCornerToTopCenter(anchor, 0, 0);
    }

    /**
     * 显示弹窗
     *
     * @param anchor 固定弹窗位置的控件
     * @param xOff x方向的偏移量 px
     * @param yOff y方向的偏移量 px
     * @note 弹窗的左上角点压在传入控件的底部中间点
     */
    public void showTopLeftCornerToBottomCenter(View anchor, int xOff, int yOff) {
        doSomethingBeforeShow(anchor);
        // 右移下移
        showAsDropDown(anchor, mAnchorMeasuredWidth / 2 + xOff, yOff);
    }

    /**
     * 显示弹窗
     *
     * @param anchor 固定弹窗位置的控件
     * @param xOff x方向的偏移量 px
     * @param yOff y方向的偏移量 px
     * @note 弹窗的顶部中间点压在传入控件的底部中间点
     */
    public void showTopCenterToBottomCenter(View anchor, int xOff, int yOff) {
        doSomethingBeforeShow(anchor);
        // 右移下移
        showAsDropDown(anchor, mAnchorMeasuredWidth / 2 - mMenuPopupWindowMeasuredWidth / 2 + xOff, yOff);
    }

    /**
     * 显示弹窗
     *
     * @param anchor 固定弹窗位置的控件
     * @param xOff x方向的偏移量 px
     * @param yOff y方向的偏移量 px
     * @note 弹窗的右上角点压在传入控件的底部中间点
     */
    public void showTopRightCornerToBottomCenter(View anchor, int xOff, int yOff) {
        doSomethingBeforeShow(anchor);
        // 右移下移
        showAsDropDown(anchor, mAnchorMeasuredWidth / 2 - mMenuPopupWindowMeasuredWidth + xOff, yOff);
    }

    /**
     * 显示弹窗
     *
     * @param anchor 固定弹窗位置的控件
     * @param xOff x方向的偏移量 px
     * @param yOff y方向的偏移量 px
     * @note 弹窗的左下角点压在传入控件的顶部中间点
     */
    public void showBottomLeftCornerToTopCenter(View anchor, int xOff, int yOff) {
        doSomethingBeforeShow(anchor);
        // 右移下移
        showAsDropDown(anchor, mAnchorMeasuredWidth / 2 + xOff, -mAnchorMeasuredHeight - mMenuPopupWindowMeasureHeight + yOff);
    }

    /**
     * 显示弹窗
     *
     * @param anchor 固定弹窗位置的控件
     * @param xOff x方向的偏移量 px
     * @param yOff y方向的偏移量 px
     * @note 弹窗的底部中间角点压在传入控件的顶部中间点
     */
    public void showBottomCenterToTopCenter(View anchor, int xOff, int yOff) {
        doSomethingBeforeShow(anchor);
        // 右移下移
        showAsDropDown(anchor, mAnchorMeasuredWidth / 2 - mMenuPopupWindowMeasuredWidth / 2 + xOff,
                -mAnchorMeasuredHeight - mMenuPopupWindowMeasureHeight + yOff);
    }

    /**
     * 显示弹窗
     *
     * @param anchor 固定弹窗位置的控件
     * @param xOff x方向的偏移量 px
     * @param yOff y方向的偏移量 px
     * @note 弹窗的右下角点压在传入控件的顶部中间点
     */
    public void showBottomRightCornerToTopCenter(View anchor, int xOff, int yOff) {
        doSomethingBeforeShow(anchor);
        // 右移下移
        showAsDropDown(anchor, mAnchorMeasuredWidth / 2 - mMenuPopupWindowMeasuredWidth + xOff,
                -mAnchorMeasuredHeight - mMenuPopupWindowMeasureHeight + yOff);
    }

    /**
     * 显示弹窗之前的操作
     *
     * @param anchor 固定控件
     */
    private void doSomethingBeforeShow(View anchor) {
        // 添加分割线
        mContentRootView.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        mContentRootView.setDividerDrawable(sOptions.drawableDivider);

        // 固定控件的宽度和高度
        mAnchorMeasuredWidth = anchor.getMeasuredWidth();
        mAnchorMeasuredHeight = anchor.getMeasuredHeight();

        /*
         * 获取弹窗的宽度和高度（没有头脚布局）
         *
         * 1. 已经设置为wrap_content的时候
         * 2. 必须先测量一次才能拿到宽度
         */
        getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        mMenuPopupWindowMeasuredWidth = getContentView().getMeasuredWidth();
        mMenuPopupWindowMeasureHeight = getContentView().getMeasuredHeight();


        // 添加头布局
        if (mHeadView != null) {
            mHeadView.setLayoutParams(
                    new LinearLayoutCompat.LayoutParams(
                            mMenuPopupWindowMeasuredWidth,
                            mContext.getResources().getDimensionPixelOffset(R.dimen.menu_popup_window_headview_height)
                    )
            );
            mHeadView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.menu_popup_window_background_transparent));
            mRootView.addView(mHeadView, 0);
        }

        // 添加脚布局
        if (mFootView != null) {
            mFootView.setLayoutParams(
                    new LinearLayoutCompat.LayoutParams(
                            mMenuPopupWindowMeasuredWidth,
                            mContext.getResources().getDimensionPixelOffset(R.dimen.menu_popup_window_headview_height)
                    )
            );
            mFootView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.menu_popup_window_background_transparent));
            mRootView.addView(mFootView, mRootView.getChildCount());
        }

        /*
         * 获取弹窗的宽度和高度（含有头脚布局）
         *
         * 1. 已经设置为wrap_content的时候
         * 2. 必须先测量一次才能拿到宽度
         */
        getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        mMenuPopupWindowMeasuredWidth = getContentView().getMeasuredWidth();
        mMenuPopupWindowMeasureHeight = getContentView().getMeasuredHeight();

        // 设置背景透明度
        setAlpha(sOptions.activityAlpha);
    }

    /**
     * 添加菜单条目
     *
     * @param iconId 图标id
     * @return MenuPopupWindow对象
     */
    public MenuPopupWindow appendItem(@DrawableRes int iconId) {
        return appendItem(iconId, null, null);
    }

    /**
     * 添加菜单条目
     *
     * @param itemName 条目名字
     * @return MenuPopupWindow对象
     */
    public MenuPopupWindow appendItem(String itemName) {
        return appendItem(-1, itemName, null);
    }

    /**
     * 添加菜单条目
     *
     * @param iconId 图标id
     * @param itemName 条目名字
     * @return MenuPopupWindow对象
     */
    public MenuPopupWindow appendItem(@DrawableRes int iconId, String itemName) {
        return appendItem(iconId, itemName, null);
    }

    /**
     * 添加菜单条目
     *
     * @param iconId 图标id
     * @param onItemClickListener 菜单条目点击监听
     * @return MenuPopupWindow对象
     */
    public MenuPopupWindow appendItem(@DrawableRes int iconId, OnItemClickListener onItemClickListener) {
        return appendItem(iconId, null, onItemClickListener);
    }

    /**
     * 添加菜单条目
     *
     * @param itemName 条目名字
     * @param onItemClickListener 菜单条目点击监听
     * @return MenuPopupWindow对象
     */
    public MenuPopupWindow appendItem(String itemName, OnItemClickListener onItemClickListener) {
        return appendItem(-1, itemName, onItemClickListener);
    }

    /**
     * 添加菜单条目
     *
     * @param iconId 图标id
     * @param itemName 条目名字
     * @param onItemClickListener 菜单条目点击监听
     * @return MenuPopupWindow对象
     */
    public MenuPopupWindow appendItem(@DrawableRes int iconId, String itemName, final OnItemClickListener onItemClickListener) {
        // 设置条目
        LinearLayout linearLayout = generateLinearLayout();
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick();
                }

                dismiss();
            }
        });

        // 设置条目图标
        ImageView imageView = generateImageView(iconId);
        if (imageView != null) {
            linearLayout.addView(imageView);
        }

        // 设置条目文字
        TextView textView = generateTextView(itemName);
        if (textView != null) {
            linearLayout.addView(textView);
        }

        mContentRootView.addView(linearLayout);

        return this;
    }

    /**
     * 生成条目
     *
     * @return LinearLayout对象
     */
    @NonNull
    private LinearLayout generateLinearLayout() {
        Drawable selector = DrawableUtil.generateSelectorColor(sOptions.backgroundColor);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout linearLayout = new LinearLayout(mContext);
        linearLayout.setLayoutParams(params);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER_VERTICAL);
        linearLayout.setPadding(sOptions.paddingLeft, sOptions.paddingTop, sOptions.paddingRight, sOptions.paddingBottom);
        linearLayout.setBackgroundDrawable(selector);

        return linearLayout;
    }

    /**
     * 生成条目文字
     *
     * @param itemName 条目名字
     * @return TextView 对象
     */
    @Nullable
    private TextView generateTextView(String itemName) {
        TextView textView = null;
        if (!TextUtils.isEmpty(itemName)) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            textView = new TextView(mContext);
            textView.setLayoutParams(params);
            textView.setText(itemName);
            textView.setTextColor(sOptions.textColor);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, sOptions.textSize);
        }

        return textView;
    }

    /**
     * 生成条目图标
     *
     * @param iconId 图标id
     * @return ImageView对象
     */
    @Nullable
    private ImageView generateImageView(@DrawableRes int iconId) {
        ImageView imageView = null;
        if (iconId != -1) {

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(sOptions.iconWidth, sOptions.iconHeight);
            params.setMargins(0, 0, sOptions.marginBetweenIconAndText, 0);

            imageView = new ImageView(mContext);
            imageView.setLayoutParams(params);
            imageView.setBackgroundResource(iconId);
        }

        return imageView;
    }

    /**
     * 设置文字颜色
     *
     * @param textColor 文字颜色
     * @return MenuPopupWindow对象
     * @note 默认颜色是白色
     */
    public MenuPopupWindow setTextColor(@ColorInt int textColor) {
        sOptions.textColor = textColor;
        return this;
    }

    /**
     * 设置弹出时候依附页面的透明度
     *
     * @param alpha 透明度
     * @return this
     */
    public MenuPopupWindow setActivityAlpha(float alpha) {
        sOptions.activityAlpha = alpha;

        return this;
    }

    /**
     * 设置图标尺寸
     *
     * @param iconWidth 图标宽度 px
     * @param iconHeight 图标高度 px
     * @return MenuPopupWindow对象
     */
    public MenuPopupWindow setIconSize(int iconWidth, int iconHeight) {
        sOptions.iconWidth = iconWidth;
        sOptions.iconHeight = iconHeight;
        return this;
    }

    /**
     * 设置图标和文字的间距
     *
     * @param margin 间距 px
     * @return MenuPopupWindow对象
     */
    public MenuPopupWindow setMarginBetteenIconAndText(int margin) {
        sOptions.marginBetweenIconAndText = margin;
        return this;
    }

    /**
     * 设置文字尺寸
     *
     * @param textSize 文字尺寸 px
     * @return MenuPopupWindow对象
     */
    public MenuPopupWindow setTextSize(int textSize) {
        sOptions.textSize = textSize;
        return this;
    }

    /**
     * 添加头布局
     *
     * @param headView 头布局
     * @return this
     */
    public MenuPopupWindow addHeadView(View headView) {
        mHeadView = headView;
        return this;
    }

    /**
     * 添加脚布局
     *
     * @param footView 脚布局
     * @return this
     */
    public MenuPopupWindow addFootView(View footView) {
        mFootView = footView;
        return this;
    }

    /**
     * 设置条目的padding值
     *
     * @param left 左面padding px
     * @param top 上面padding px
     * @param right 右面padding px
     * @param bottom 下面padding px
     * @return MenuPopupWindow对象
     * @note -1表示默认值
     */
    public MenuPopupWindow setItemPadding(int left, int top, int right, int bottom) {
        if (left != -1) {
            sOptions.paddingLeft = left;
        }

        if (top != -1) {
            sOptions.paddingTop = top;
        }

        if (right != -1) {
            sOptions.paddingRight = right;
        }

        if (bottom != -1) {
            sOptions.paddingBottom = bottom;
        }
        return this;
    }

    /**
     * 设置分割线
     *
     * @param dividerId 分割线id
     * @return this
     */
    public MenuPopupWindow setDividerDrawable(@DrawableRes int dividerId) {
        Drawable drawableDivider = ContextCompat.getDrawable(mContext, dividerId);
        sOptions.drawableDivider = drawableDivider;
        return this;
    }

    /**
     * 菜单条目点击的监听接口
     */
    public interface OnItemClickListener {
        /**
         * 菜单条目被点击
         */
        void onItemClick();
    }

    /**
     * 默认参数
     */
    public static class DefaultOptions {
        /** 背景颜色 */
        public int backgroundColor;
        /** 图标宽度 px */
        public int iconWidth;
        /** 图标高度 px */
        public int iconHeight;
        /** 文字大小 px */
        public int textSize;
        /** 文字颜色 */
        public int textColor;
        /** 条目左面padding px */
        public int paddingLeft;
        /** 条目上面padding px */
        public int paddingTop;
        /** 条目右面padding px */
        public int paddingRight;
        /** 条目下面padding px */
        public int paddingBottom;
        /** 图标和文字之间的间距 */
        public int marginBetweenIconAndText;
        /** 分割线 */
        public Drawable drawableDivider;
        /** 依附页面的透明度 */
        public float activityAlpha;
    }
}
