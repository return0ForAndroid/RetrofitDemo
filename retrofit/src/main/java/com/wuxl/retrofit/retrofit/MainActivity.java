package com.wuxl.retrofit.retrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.wuxl.retrofit.retrofit.http.JsonrpcCallBack;
import com.wuxl.retrofit.retrofit.service.impl.GitHubService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mTestLsv;
    private JsonrpcCallBack<List<GovEntity>> cusCallBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        cusCallBack = new JsonrpcCallBack<List<GovEntity>>() {
            @Override
            public void onSuccess(List<GovEntity> govEntities) {
                List<String> strList = new ArrayList<String>();
                for (int i = 0; i < govEntities.size(); i++) {
                    strList.add(govEntities.get(i).getTitle());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, strList);
                mTestLsv.setAdapter(adapter);
            }

            @Override
            public void onError(String errorCode, String errorMessage) {
            }
        };

        new GitHubService().queryLatestPhotoNews(8, cusCallBack);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
