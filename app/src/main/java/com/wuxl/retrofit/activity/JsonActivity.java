package com.wuxl.retrofit.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.wuxl.retrofit.entity.NewsEntity;
import com.wuxl.retrofit.retrofit.R;
import com.wuxl.retrofit.retrofit.http.JsonrpcCallBack;
import com.wuxl.retrofit.service.impl.JsonService;

import java.util.ArrayList;
import java.util.List;

public class JsonActivity extends AppCompatActivity {

    private ListView mTestLsv;
    private JsonrpcCallBack<List<NewsEntity>> cusCallBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        Toolbar toolbar;
        setContentView(R.layout.activity_test);

        Button btn_test = (Button) findViewById(R.id.btn_test);
        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                test();
            }
        });

        mTestLsv = (ListView) findViewById(R.id.lsv_test);
    }

    private void test() {

        cusCallBack = new JsonrpcCallBack<List<NewsEntity>>() {
            @Override
            public void onSuccess(List<NewsEntity> govEntities) {
                List<String> strList = new ArrayList<String>();
                for (int i = 0; i < govEntities.size(); i++) {
                    strList.add(govEntities.get(i).getTitle());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(JsonActivity.this, android.R.layout.simple_list_item_1, strList);
                mTestLsv.setAdapter(adapter);
            }

            @Override
            public void onError(String errorCode, String errorMessage) {
            }
        };

        new JsonService().queryLatestPhotoNews(8, cusCallBack);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
