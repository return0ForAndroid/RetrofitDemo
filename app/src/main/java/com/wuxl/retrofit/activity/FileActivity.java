package com.wuxl.retrofit.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.wuxl.retrofit.R;
import com.wuxl.retrofit.retrofit.http.JsonrpcCallBack;
import com.wuxl.retrofit.retrofit.http.result.FileResultUrl;
import com.wuxl.retrofit.service.impl.FileService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WUXL on 2016/9/19.
 */
public class FileActivity extends AppCompatActivity {

    EditText picPath;
    Button uploadPic;
    ListView picResult;

    EditText filePath;
    Button uploadFile;
    ListView fileResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.wuxl.retrofit.retrofit.R.layout.activity_file);

        picPath = (EditText) findViewById(R.id.edt_picPath);
        uploadPic = (Button) findViewById(R.id.btn_uploadPic);
        picResult = (ListView) findViewById(R.id.lsv_picResult);

        filePath = (EditText) findViewById(R.id.edt_filePath);
        uploadFile = (Button) findViewById(R.id.btn_uploadFile);
        fileResult = (ListView) findViewById(R.id.lsv_fileResult);

        uploadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPic(picPath.getText().toString().trim());
            }
        });

        uploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile(filePath.getText().toString().trim());
            }
        });

        requestStoragePermission();
    }

    private void uploadPic(String picPath) {
        if (TextUtils.isEmpty(picPath)) {
            Toast.makeText(this, "图片路径不能为空", Toast.LENGTH_SHORT).show();
        } else if (!isAuth) {
            Toast.makeText(this, "权限不够", Toast.LENGTH_SHORT).show();
        } else {
            new FileService().uploadPic(picPath, new JsonrpcCallBack<List<FileResultUrl>>() {
                @Override
                public void onSuccess(List<FileResultUrl> fileResultUrl) {
                    refreshResult(picResult, fileResultUrl);
                }
            });
        }
    }

    private void uploadFile(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            Toast.makeText(this, "文件路径不能为空", Toast.LENGTH_SHORT).show();
        } else if (!isAuth) {
            Toast.makeText(this, "权限不够", Toast.LENGTH_SHORT).show();
        } else {
            new FileService().uploadFile(filePath, new JsonrpcCallBack<List<FileResultUrl>>() {
                @Override
                public void onSuccess(List<FileResultUrl> fileResultUrl) {
                    refreshResult(fileResult, fileResultUrl);
                }
            });
        }
    }

    private void refreshResult(ListView listView, List<FileResultUrl> fileResultUrl) {
        ArrayAdapter<FileResultUrl> arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, fileResultUrl);
        listView.setAdapter(arrayAdapter);
    }

    private static final int SDK_PERMISSION_REQUEST = 123;
    private boolean isAuth = false;

    private void requestStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<String>();
            // 读取存储
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (permissions.size() > 0) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
            } else {
                isAuth = true;
            }
        } else {
            isAuth = true;
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // TODO Auto-generated method stub
        switch (requestCode) {
            case SDK_PERMISSION_REQUEST:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    isAuth = true;
                } else {
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
