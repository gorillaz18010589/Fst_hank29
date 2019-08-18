package tw.brad.apps.brad29;
//跟執行緒類似,非同步任務,但用起來會比執行緒好用
//timer time task式週期
//執行緒是做一件事情,對安卓來說太陽春了
//AsyncTask仍然會在背景去做,但優點是不用透過handele直接操控
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView[] names = new TextView[4];
    private int[] rnames ={R.id.name1,R.id.name2,R.id.name3,R.id.name4};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       for(int i=0; i<names.length; i++)names[i] = findViewById(rnames[i]);
    }

    public void test1(View view) {
        MYasyncTask mYasyncTask = new MYasyncTask();
//        mYasyncTask.execute();//傳遞參數先不傳,傳給doin bacground接收
        mYasyncTask.execute("Brad","Hank","hellow","kidd");
    }

    //AsyncTask仍然會在背景去做,但優點是不用透過handele直接操控,實作方法要做
    //把on系列的事件都叫進來
    private class MYasyncTask extends AsyncTask<String,String,Void>{ //void可以無船參數,再來因為犯行一定要物件所以大寫物件型態

        //(執行時順序1)執行前
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.v("brad","onPreExecute");//看一下用法
        }

        //(執行時順序3)執行後
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.v("brad","onPostExecute");//看一下用法
        }


        //取消
        @Override
        protected void onCancelled() {
            super.onCancelled();
            Log.v("brad","onCancelled");//看一下用法
        }

        //取消有參數
        @Override
        protected void onCancelled(Void aVoid) {
            super.onCancelled(aVoid);
            Log.v("brad","onCancelled(args)");//看一下用法
        }

           name1.setText(values[1] + ":" + values[2] + ":" +values[3] + ":" +values[4]);

        //執行跟新進度回報,到哪裡回報我一下,一邊doinbackground一邊回報這邊
        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            Log.v("brad","onProgressUpdate");//看一下用法
           names[Integer]
        }

        //(執行時順序2)在背景執行,執行緒任務都在裡面
        @Override
        protected Void doInBackground(String... names) { //第一個犯行的資料看你範行寫什麼而改
//            Log.v("brad","doInBackground");//看一下用法
            int i=0;
            for(String name : names){
                Log.v("brad" ,"name =" + name);
//                 publishProgress();//回報
                publishProgress("" + i++ ,
                        name,);
            }
            return null;
        }


    }
}
