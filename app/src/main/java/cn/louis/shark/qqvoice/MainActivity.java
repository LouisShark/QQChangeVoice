package cn.louis.shark.qqvoice;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.fmod.FMOD;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separatorChar + "louis.wav";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FMOD.init(this);
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

            default:
                break;
        }
    }

    private void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

}
