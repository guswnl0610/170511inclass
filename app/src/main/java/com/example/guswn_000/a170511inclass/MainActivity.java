package com.example.guswn_000.a170511inclass;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkpermission();
        et = (EditText)findViewById(R.id.et);
    }


    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn2:
                try
                {
                    BufferedWriter bw = new BufferedWriter(new FileWriter(getFilesDir() + "test.txt", true));
                    bw.write("안녕하세요 Hello");
                    bw.close();
                    Toast.makeText(this, "저장완료" , Toast.LENGTH_SHORT).show();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                    Toast.makeText(this,e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btn1:
                try {
                    BufferedReader br = new BufferedReader(
                            new FileReader(getFilesDir() +"test.txt"));
                    String readStr = "";
                    String str = null;
                    while((str = br.readLine()) != null)
                        readStr += str +"\n";
                    br.close();
                    Toast.makeText(this,
                            readStr.substring(0, readStr.length()-1),
                            Toast.LENGTH_SHORT).show();
                }
                catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                    Toast.makeText(this, "File not found",
                            Toast.LENGTH_SHORT).show();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

                break;

            case R.id.btn3:
                try {
                    InputStream is =
                            getResources().openRawResource(R.raw.about);
                    byte[] readStr =
                            new byte[is.available()];
                    is.read(readStr);
                    is.close();
                    Toast.makeText(this, new String(readStr),
                            Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn4: // 외부 읽기
                try
                {
                    String path = getExternalPath();


                    BufferedReader br = new BufferedReader(new FileReader(path + "test.txt"));
                    String readStr = "";
                    String str = null;
                    while((str = br.readLine()) != null)
                    {
                        readStr += str + "\n";
                        br.close();
                        Toast.makeText(this,readStr.substring(0, readStr.length() - 1), Toast.LENGTH_SHORT).show();
                    }
                }
                catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                    Toast.makeText(this, "File not found",
                            Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;

            case R.id.btn5: // 외부 쓰기
                try
                {
                    String path = getExternalPath();
                    //디렉토리 생성

                    BufferedWriter bw = new BufferedWriter(new FileWriter(path + "mydiary/" + "test.txt",true));
                    bw.write("안녕하세요 SDCard Hello");
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                    Toast.makeText(this,e.getMessage() + ":" + getFilesDir(),Toast.LENGTH_SHORT).show();
                }


                break;

            case R.id.btn6: // 디렉터리 생성

                try
                {
                    String path = getExternalPath();
                    //디렉토리 생성

                    File file = new File(path + "mydiary");
                    file.mkdir();
                    String msg = "디렉토리 생성";
                    if(file.isDirectory() == false)
                    {
                        msg = "디렉토리 생성 오류";
                    }
                    Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();



                    BufferedWriter bw = new BufferedWriter(new FileWriter(path + "mydiary/" + "test.txt",true));
                    bw.write("안녕하세요 SDCard Hello");

                    BufferedWriter bw2 = new BufferedWriter(new FileWriter(path + "mydiary/" + "test2.txt",true));
                    bw.write("뫼에엥");
                    bw.close();
                    bw2.close();
                    Toast.makeText(this,"저장완료",Toast.LENGTH_SHORT).show();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                    Toast.makeText(this,e.getMessage() + ":" + getFilesDir(),Toast.LENGTH_SHORT).show();
                }


                break;


            case R.id.btn7:

                try
                {
                    String path = getExternalPath();

                    //메모리 파일 목록
                    File[] files = new File(path + "mydiary").listFiles();

                    String filesstr = "";
                    for(File f : files)
                    {
                        filesstr += f.getName() + "\n";
                    }
                    et.setText(filesstr);




                    BufferedReader br = new BufferedReader(new FileReader(path + "test.txt"));
                    String readStr = "";
                    String str = null;
                    while((str = br.readLine()) != null)
                    {
                        readStr += str + "\n";
                        br.close();
                        Toast.makeText(this,readStr.substring(0, readStr.length() - 1), Toast.LENGTH_SHORT).show();
                    }
                }
                catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                    Toast.makeText(this, "File not found",
                            Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;




        }
    }

    public String getExternalPath()
    {
        String sdPath = "";
        String ext = Environment.getExternalStorageState();
        if(ext.equals(Environment.MEDIA_MOUNTED))
        {
            sdPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
                    //sdPath = "mnt/sdcard/";
        }
        else
        {
            sdPath = getFilesDir() + "";
        }

        Toast.makeText(getApplicationContext(),sdPath,Toast.LENGTH_SHORT).show();
        return sdPath;
    }




    public void checkpermission()
    {

        int permissioninfo = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permissioninfo == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this,
                    "SDCard 쓰기 권한 있음",Toast.LENGTH_SHORT).show();
        }
        else {

            //사실 이프문 안써도되는데
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                Toast.makeText(this,
                        "어플리케이션 설정에서 저장소 사용 권한을 허용해주세요",Toast.LENGTH_SHORT).show();

                //이밑에꺼 해야 권한허용 대화상자가 다시뜸
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        100);
            }
            else{
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        100);  // 이 100은 리퀘스트코드다
            }
        }
    }


    //만약 OK눌렀을때 뭐 얘기할게 있으면 이 함수를 써라
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        String str = null;
        if (requestCode == 100)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                str = "SD Card 쓰기권한 승인";
            else
                str = "SD Card 쓰기권한 거부";
            Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
        }
    }
}
