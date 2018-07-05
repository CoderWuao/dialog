package site.wuao.dialog.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import site.wuao.dialog.R;
import site.wuao.dialog.ui.widget.BottomSheet;
import site.wuao.dialog.ui.widget.CustomActionButton;
import site.wuao.dialog.ui.widget.MenuPopupWindow;
import site.wuao.dialog.ui.widget.TopSheet;

public class MainActivity extends AppCompatActivity {

    private TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTv = findViewById(R.id.tv);
        mTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                menuPopupWindow();
                sheet();
            }
        });


//        actionButton();
//        bottomSheet();


    }

    private void sheet() {
        View view = LayoutInflater.from(this).inflate(R.layout.item_top, null, false);
        TextView tvSecond = view.findViewById(R.id.tv_second);
        tvSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheet bottomSheet = new BottomSheet(MainActivity.this, R.layout.item_bottom, true);
//                bottomSheet.setCanceledOnTouchOutside(true);
                bottomSheet.show();
            }
        });
        TopSheet topSheet = new TopSheet(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        topSheet.showAsDropDown(mTv);
    }

    private void bottomSheet() {
        BottomSheet bottomSheet = new BottomSheet(this, R.layout.item_bottom, true);
        bottomSheet.setCanceledOnTouchOutside(true);
        bottomSheet.show();
    }

    private void actionButton() {
        new CustomActionButton(this)
                .setTitle("配送完成")
                .setMessage("请确认您是否已完成订单配送")
                .setMessage("订单配送订单配送订单配送订单配送订单配送订单配送订单配送订单配送")
                .setLeftButton("取消", null)
                .setRightButton("确定", null)
                .show();
    }

    private void menuPopupWindow() {
        new MenuPopupWindow(this)
                .setActivityAlpha(0.5f)
                .setIconSize(44, 44)
                .setItemPadding(40, 20, 150, 20)
                .setMarginBetteenIconAndText(20)
                .appendItem(R.mipmap.ic_launcher, "hahah", null)
                .showTopRightCornerToBottomCenter(mTv, 0, 0);
    }
}
