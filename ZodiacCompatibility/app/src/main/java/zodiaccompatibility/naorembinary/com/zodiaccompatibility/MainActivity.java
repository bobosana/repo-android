package zodiaccompatibility.naorembinary.com.zodiaccompatibility;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static  Spinner mSpinner,fSpinner;
    private static  Button bFind;
    private static  String mZodiac,fZodiac;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bFind = (Button) findViewById(R.id.btnFind);
        bFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            mSpinner = (Spinner) findViewById(R.id.spinner_male);
            fSpinner = (Spinner) findViewById(R.id.spinner_female);
            mZodiac=mSpinner.getSelectedItem().toString();
            fZodiac=fSpinner.getSelectedItem().toString();
            calculate(v.getContext(),mZodiac,fZodiac);
            }
        });
    }

    private void calculate(Context c,String mZodiac,String fZodiac) {
        ZCalculate z=new ZCalculate();
        String result;
        result=z.zCalculate(c,mZodiac,fZodiac);
        TextView tvResult= (TextView) findViewById(R.id.tvResult);
        tvResult.setText("\""+result.toString()+"\"");
        //Toast.makeText(c,"male:"+mZodiac+"\nfemale:"+fZodiac+"Compatibility:"+result,Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main,  menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
