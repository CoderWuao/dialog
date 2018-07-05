package site.wuao.dialog.ui.utils;

import android.animation.ArgbEvaluator;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;


/**
 * Drawable工具类
 *
 * @author wuao
 * @date 2017.06.10
 * @note -
 * ---------------------------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class DrawableUtil {

    /**
     * 生成圆角图片
     *
     * @param color 颜色
     * @param radius 半径
     * @return Drawable对象
     * @note 对应xml中的shape标签
     */
    public static Drawable generateDrawableCorner(@ColorInt int color, float radius) {
        GradientDrawable drawable = new GradientDrawable();

        // 设置为矩形，默认就是矩形
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setColor(color);
        drawable.setCornerRadius(radius);

        return drawable;
    }

    /**
     * 生成颜色状态选择器
     *
     * @param colorNormal 正常颜色
     * @return Drawable对象
     * @note 按下的颜色比正常颜色深5%
     */
    public static Drawable generateSelectorColor(@ColorInt int colorNormal) {
        ArgbEvaluator evaluator = new ArgbEvaluator();
        int black = Color.parseColor("#000000");
        int colorPressed = (int) evaluator.evaluate(5f / 100, colorNormal, black);

        return generateSelectorColor(colorNormal, colorPressed);
    }

    /**
     * 生成颜色状态选择器
     *
     * @param colorNormal 正常颜色
     * @param colorPressed 按下颜色
     * @return Drawable对象
     */
    public static Drawable generateSelectorColor(@ColorInt int colorNormal, @ColorInt int colorPressed) {
        Drawable drawableNormal = generateDrawableRectangle(colorNormal);
        Drawable drawablePressed = generateDrawableRectangle(colorPressed);

        Drawable drawable = generateSelector(drawableNormal, drawablePressed);

        return drawable;
    }

    /**
     * 生成矩形图片
     *
     * @param color 颜色
     * @return Drawable对象
     */
    public static Drawable generateDrawableRectangle(@ColorInt int color) {
        GradientDrawable gradientDrawable = new GradientDrawable();

        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setColor(color);

        return gradientDrawable;
    }

    /**
     * 生成状态选择器
     *
     * @param drawableNormal 正常图片
     * @param drawablePressed 按下图片
     * @return Drawable对象
     */
    public static Drawable generateSelector(Drawable drawableNormal, Drawable drawablePressed) {
        StateListDrawable sldrawable = new StateListDrawable();

        /*
         * 1. 设置按下的图片
         * 2. 设置默认的图片
         *
         * @note 顺序不能交换
         */
        sldrawable.addState(new int[]{android.R.attr.state_pressed}, drawablePressed);
        sldrawable.addState(new int[0], drawableNormal);

        return sldrawable;
    }

    /**
     * 设置过渡动画
     *
     * @param slDrawable 图片
     */
    private static void setTransitionAnimation(StateListDrawable slDrawable) {
        if (Build.VERSION.SDK_INT > 10) {
            slDrawable.setEnterFadeDuration(500);
            slDrawable.setExitFadeDuration(500);
        }
    }
}
