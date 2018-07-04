package site.wuao.dialog.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import site.wuao.dialog.R;
import site.wuao.dialog.ui.widget.CustomActionButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        new CustomActionButton(this)
                .setTitle("配送完成")
                .setMessage("请确认您是否已完成订单配送")
                .setLeftButton("取消", null)
                .setRightButton("确定", null)
                .show();
    }
}
