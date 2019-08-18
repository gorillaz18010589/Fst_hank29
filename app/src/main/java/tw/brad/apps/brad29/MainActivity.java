package tw.brad.apps.brad29;
//跟執行緒類似,非同步任務,但用起來會比執行緒好用
//timer time task式週期
//執行緒是做一件事情,對安卓來說太陽春了
//AsyncTask仍然會在背景去做,但優點是不用透過handele直接操控
//PENDING => Running => finish
//你叫他取消他還有做完,但取消還是有做
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView[] names = new TextView[4];
    private int[] rnames ={R.id.name1,R.id.name2,R.id.name3,R.id.name4};
    MYasyncTask mYasyncTask = new MYasyncTask();
    private TextView status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        status = findViewById(R.id.status);
       for(int i=0; i<names.length; i++)names[i] = findViewById(rnames[i]);//陣列物件的findid方式
    }

    //按鈕一執行加入字串,開始執行
    public void test1(View view) {
         mYasyncTask = new MYasyncTask();
//        mYasyncTask.execute();//傳遞參數先不傳,傳給doin bacground接收
        mYasyncTask.execute("Brad","Hank","hellow","kidd"); //第一個犯行
    }
    //按鈕二取消，發現do背景會執行完才跑來結束,所以要馬上執行結束重點不在這
    public void test2(View view) {
        if (mYasyncTask != null){ //如果在執行
            Log.v("brad", "status:" + mYasyncTask.getStatus());//印出現在狀態
            if (!mYasyncTask.isCancelled()){//如果被取消了
                mYasyncTask.cancel(true);//那就取消
            }
        }
    }
    //AsyncTask仍然會在背景去做,但優點是不用透過handele直接操控,實作方法要做
    //把on系列的事件都叫進來
    private class MYasyncTask extends AsyncTask<String,String,String>{ //void可以無船參數,再來因為犯行一定要物件所以大寫物件型態
        //

        //(執行時順序1)執行前　
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.v("brad","onPreExecute");//看一下用法
        }

        //(執行時順序3)執行後,動到第三個範行要調整
        @Override
        protected void onPostExecute(String  mesg) {
            Log.v("brad", "onPostExecute:" + mesg);
            status.setText(mesg);
        }


        //取消
        @Override
        protected void onCancelled() {
            super.onCancelled();
            Log.v("brad","onCancelled");//看一下用法
        }

        //取消有參數,動到第三個範行要調整
        @Override
        protected void onCancelled(String mesg) {
            Log.v("brad", "onCancelled(args)" + mesg);
            status.setText(mesg);
        }



        //執行跟新進度回報,到哪裡回報我一下,一邊doinbackground一邊回報這邊,接收到doinback的參數訊息顯示出來
        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            Log.v("brad","onProgressUpdate");//看一下用法
            names[Integer.parseInt(values[0])].setText( //把執行的顯示出來
                    values[1] + ":" + values[2] +":" + values[3]+":"+values[4]);
        }

        //(執行時順序2)在背景執行,執行緒任務都在裡面
        //可以玩三個範行
        //1.第一個犯行直接帶進來doInBackground(String... names)
        //2.第二個範行 回報得中的訊息傳給protected void onProgressUpdate(String... values)
        //3.第三個範行回傳訊息 return retMesg;
        //真正要取消要在doInbackground裡去做
        @Override
        protected String doInBackground(String... names) { //看你第一個犯行寫什麼而改,第三範行會動到回傳值
            String retMesg ="正常化";
            int i=0;
            for (String name : names){
                Log.v("brad", "name = " + name);
                publishProgress("" + i++, //回報亂數參數給 onProgressUpdate=>一邊做一邊回報
                        name,
                        ""+(int)(Math.random()*49+1),
                        ""+(int)(Math.random()*49+1),
                        ""+(int)(Math.random()*49+1));

                //如果按了取消後,直接回傳cancel結束
                if (isCancelled()){
                    retMesg = "cancel";
                    break;
                }

                try {
                    Thread.sleep(1000); //延遲讓同步執行回報感覺出來
                }catch (Exception e){

                }
            }
            return retMesg;//如果都沒問題回報正常,第三個範行回報資訊
        }
    }

}