package jp.ac.u_tokyo.t.utdroid_ui;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

/**
 * Activityとは、ざっくり言うとアプリの中の1画面のこと。
 * 古い端末との互換性のために、ActivityではなくAppCompatActivityを継承（拡張）して使う
 */
public class MainActivity extends AppCompatActivity {
    /* バイブレータを制御するための変数 */
    private Vibrator vibrator;

    /**
     * 画面が生成された時に呼ばれるメソッド。
     * Androidの場合、onCreateがエントリーポイントとなる。
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /* 継承元の処理を実行させる */
        super.onCreate(savedInstanceState);

        /* [res/layout/activity_main.xml]のレイアウトを読み込む */
        setContentView(R.layout.activity_main);

        /* "button"という名前のViewを取得する */
        Button button = (Button) findViewById(R.id.button);
        /* クリックした時の動作を指定する */
        button.setOnClickListener(new View.OnClickListener() {
            /**
             * クリックすると、onClickが呼ばれる
             */
            @Override
            public void onClick(View v) {
                /* ダイアログを準備する */
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("タイトル");
                builder.setMessage("メッセージ");
                /* OKボタンとクリック時の動作 */
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /* 簡易なポップアップを表示 */
                        Toast.makeText(MainActivity.this, "OKボタンが押下されました。", Toast.LENGTH_SHORT).show();
                    }
                });
                /* Cancelボタン（クリック時の動作なし） */
                builder.setNegativeButton("Cancel", null);
                /* ダイアログを表示する */
                builder.create();
                builder.show();
            }
        });

        /* バイブレータの初期化 */
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        /* "toggleButton"という名前のViewを取得する */
        ToggleButton toggleButton = (ToggleButton) findViewById(R.id.toggleButton);
        /* クリックした時の動作を指定する。ボタンとは少し違う */
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            /**
             * クリックすると、onCheckedChangedが呼ばれる
             * isCheckedには、ボタンの状態(ON/OFF)がtrue/falseで渡される
             */
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    /* 簡易なポップアップを表示 */
                    Toast.makeText(MainActivity.this, "トグルボタンがONになりました。", Toast.LENGTH_SHORT).show();

                    /* バイブレータを1回だけ作動させる場合（長さをミリ秒で指定） */
                    /* vibrator.vibrate(500); */

                    /* 指定のパターンで繰り返し動作させる場合 */
                    long pattern[] = {0, 100, 400, 100}; /* OFFの時間、ONの時間、OFFの時間、ONの時間… */
                    int repeat = 2; /* パターンの(repeat+1)番目から後ろを繰り返す。-1で繰り返しなし。 */
                    vibrator.vibrate(pattern, repeat);
                } else {
                    /* 簡易なポップアップを表示 */
                    Toast.makeText(MainActivity.this, "トグルボタンがOFFになりました。", Toast.LENGTH_SHORT).show();

                    /* 途中でもバイブレータを止める */
                    vibrator.cancel();
                }
            }
        });

        /* "spinner"という名前のViewを取得する */
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        /* プルダウンリストの中身を定義 */
        String[] list = {"Cupcake", "Donut", "Eclair", "Froyo", "Gingerbread",
                "Honeycomb", "IceCream Sandwich", "Jellybean", "Kitkat", "Lollipop", "Marshmallow", "Nougat"};
        /* リストとUIを繋ぐアダプタ。2番目の引数はプルダウンの表示レイアウト（Android標準で用意されているものを使用） */
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list);
        spinner.setAdapter(adapter);
        /* プルダウンを選択した時の動作を指定する */
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /**
             * 何かを選択した時に、onItemSelectedが呼ばれる
             * タップされたView自体や、それが何番目であったか、といった情報が渡される
             */
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /* 選択した内容を取得する（どちらも結果は同じ） */
                String item = (String) parent.getSelectedItem();
                /* String item = (String) parent.getAdapter().getItem(position); */

                /* 簡易なポップアップを表示 */
                Toast.makeText(MainActivity.this, item, Toast.LENGTH_SHORT).show();
            }

            /**
             * 何も選ばなかった時に、onNothingSelectedが呼ばれる
             * （実際には呼ばれることはまずない）
             */
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(MainActivity.this, "何も選ばれなかったよ。", Toast.LENGTH_SHORT).show();

            }
        });

        /* "seekBar"という名前のViewを取得する */
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
        /* スライドバーを動かした時の動作を指定する */
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            /**
             * スライドバーの目盛りが変化した時に、onProgressChangedが呼ばれる
             * 目盛りがいくつになったか、ユーザの操作によるものか（プログラムでの制御ではないか）、といった情報が渡される
             */
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            /**
             * タッチ開始時に、onStartTrackingTouchが呼ばれる
             */
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            /**
             * タッチ終了時に、onStopTrackingTouchが呼ばれる
             */
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                /* 簡易なポップアップを表示 */
                Toast.makeText(MainActivity.this, "シークバーの値が" + seekBar.getProgress() + "になりました。", Toast.LENGTH_SHORT).show();
            }
        });

        /* "checkBox1"という名前のViewを取得する */
        CheckBox checkBox1 = (CheckBox) findViewById(R.id.checkBox1);
        /* クリックした時の動作を指定する。チェックボックスはON/OFF状態を持つので、トグルボタンの仲間 */
        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            /**
             * クリックすると、onCheckedChangedが呼ばれる
             * isCheckedには、ボタンの状態(ON/OFF)がtrue/falseで渡される
             */
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                /* 簡易なポップアップを表示 */
                Toast.makeText(MainActivity.this, "チェックボックス1が" + isChecked + "になりました。", Toast.LENGTH_SHORT).show();
            }
        });

        /* "radioGroup"という名前のViewを取得する */
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        /* クリックした時の動作を指定する。トグルボタンやチェックボックスとは微妙に違う */
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            /**
             * クリックすると、onCheckedChangedが呼ばれる
             * checkedIdには、選択されたラジオボタンのIDが渡される
             */
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                /* 選択されたラジオボタンを取得する。findViewByIdの引数は整数（int）で、viewに割り当てたidと対応している */
                RadioButton radioButton = (RadioButton) findViewById(checkedId);
            }
        });

        // dpをpxに変換する倍率（物理解像度1080px幅の端末なら3.0）
        final float dp2pxScale = getResources().getDisplayMetrics().density;

        // ソースコード内から動的にViewを追加してみる
        LinearLayout scrollRootLayout = (LinearLayout) findViewById(R.id.scrollRootLayout);
        TextView piTextView = new TextView(this);
        piTextView.setText("3.14159265358979...");
        piTextView.setTextColor(Color.rgb(34, 177, 76));
        piTextView.setPadding(0, 0, 0, (int)(30*dp2pxScale)); // ソースコード内でのサイズ指定はpx単位（dp単位ではない）
        scrollRootLayout.addView(piTextView, 0);
    }

    /**
     * メニューの項目を準備する時に呼ばれるメソッド
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* [res/menu/menu_main.xml]のメニューを読み込む */
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * メニューの項目がタップされた時に呼ばれるメソッド
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /* メニュー項目にもidを振ってあるので、idで条件分岐 */
        switch (item.getItemId()) {
            case R.id.action_notify:
                /* 通知を表示するメソッドを呼び出す */
                showNotification("タイトル","メッセージ");
                break;
            case R.id.action_settings:
                /* 簡易なポップアップを表示 */
                Toast.makeText(MainActivity.this, "設定メニューが押下されました。", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_close:
                /* アプリを終了する */
                finish();
                break;
        }

        /* 継承元の処理を実行させる */
        return super.onOptionsItemSelected(item);
    }

    /* 通知バーに通知を表示する */
    private void showNotification(String title, String message) {
        /* タップした時の動作を指定（詳細はIntentの回を参照） */
        Intent bootIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, bootIntent, 0);

        /* 通知の内容を指定する */
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            /* Android 4.4以下では、LargeIconのみを指定する。
               通知エリア：アイコンがそのまま表示される
               通知バー：サムネイルが表示される */
            builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
            /* ティッカー表示はAndroid 5.0で廃止された */
            builder.setTicker(title);
        }else{
            /* Android 5.0以上では、SmallIconと背景色を指定する。
               通知エリア：背景色で塗り潰された円の中に、アイコンの不透明部分のみが白塗りで表示される
               通知バー：アイコンの不透明部分のみが白塗りで表示される */
            builder.setColor(Color.rgb(34, 177, 76));
        }
        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setContentTitle(title);
        builder.setContentText(message);
        builder.setWhen(System.currentTimeMillis());
        builder.setAutoCancel(true);
        builder.setDefaults(Notification.DEFAULT_SOUND);
        builder.setContentIntent(contentIntent);

        /* 実際に通知を表示する */
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}
