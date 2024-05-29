package cn.louis.shark.qqvoice;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.fmod.FMOD;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private static final String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separatorChar + "louis.wav";

    private InputStream inputStream = null;
    private FileOutputStream outputStream = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FMOD.init(this);
        File oldFile = new File(path);
        if (!oldFile.exists()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        int bytesum = 0;
                        int byteread = 0;
                        File file = new File(path);
                        inputStream = getResources().getAssets().open("singing.wav");
                        outputStream = new FileOutputStream(file);
                        byte[] buffer = new byte[1444];
                        while ((byteread = inputStream.read(buffer)) != -1) {
                            bytesum += byteread;
                            System.out.println(bytesum);
                            outputStream.write(buffer, 0, byteread);
                        }
                        inputStream.close();
                    } catch (Exception e) {
                        System.out.println("复制单个文件操作出错");
                        e.printStackTrace();
                    }
                }
            }).start();
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FMOD.close();
    }


    /**
     * 处理
     *
     * @param btn
     */
    public void mFix(View btn) {
        Log.d("MainActivity", path);
        switch (btn.getId()) {
            case R.id.btn_normal:
                VoiceFixer.fix(path, VoiceFixer.MODE_NORMAL);
                break;
            case R.id.btn_luoli:
                toast("萝莉");
                VoiceFixer.fix(path, VoiceFixer.MODE_LUOLI);
                break;
            case R.id.btn_dashu:
                toast("大叔");
                VoiceFixer.fix(path, VoiceFixer.MODE_DASHU);
                break;
            case R.id.btn_jingsong:
                toast("惊悚");
                VoiceFixer.fix(path, VoiceFixer.MODE_JINGSONG);
                break;
            case R.id.btn_gaoguai:
                toast("搞怪");
                VoiceFixer.fix(path, VoiceFixer.MODE_GAOGUAI);
                break;
            case R.id.btn_kongling:
                toast("空灵");
                VoiceFixer.fix(path, VoiceFixer.MODE_KONGLING);
                break;
            // Add a new case for the save button
            case R.id.btn_save:
                // Assuming the mode to save with effect is MODE_NORMAL for demonstration
                VoiceFixer.saveSoundWithEffect(path, Environment.getExternalStorageDirectory().getAbsolutePath() + File.separatorChar + "louis_saved.wav", VoiceFixer.MODE_NORMAL);
                toast("Sound saved with effect");
                break;
            default:
                break;
        }
    }

    private void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

}
