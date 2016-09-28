package zodiaccompatibility.naorembinary.com.zodiaccompatibility;

import android.app.DatePickerDialog;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class FindZodiac extends FragmentActivity implements DatePickerFragment.Communicator {
    Button btnFind;
    TextView tvZodiacResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_zodiac);
        btnFind=(Button) findViewById(R.id.btnFind);
        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getDob();

            }
        });

    }

    private void getDob(){
        DatePickerFragment dp=new DatePickerFragment();
        Calendar today= Calendar.getInstance();

        Bundle args= new Bundle();
        args.putInt("year",today.get(Calendar.YEAR));
        args.putInt("month",today.get(Calendar.MONTH));
        args.putInt("day", today.get(Calendar.DAY_OF_MONTH));

        dp.setArguments(args);
        dp.setCallback(DateCallback);
        dp.show(getSupportFragmentManager(), "DOB!!");
    }
    int m,d;
    String sign="";
    String[] months={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
     DatePickerDialog.OnDateSetListener DateCallback = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            m=monthOfYear+1;
            d=dayOfMonth;


            switch(m) {
                case 1:
                    if (d <= 19) {
                        sign = "Capricorn";
                    } else {
                        sign = "Aquarius";
                    }
                    break;
                case 2:
                    if (d <= 18) {
                        sign = "Aquarius";
                    } else {
                        sign = "Pisces";
                    }
                    break;
                case 3:
                    if (d <= 20) {
                        sign = "Pisces";
                    } else {
                        sign = "Aries";
                    }
                    break;
                case 4:
                    if (d <= 19) {
                        sign = "Aries";
                    } else {
                        sign = "Taurus";
                    }
                    break;
                case 5:
                    if (d <= 20) {
                        sign = "Taurus";
                    } else {
                        sign = "Gemini";
                    }
                    break;
                case 6:
                    if (d <= 20) {
                        sign = "Gemini";
                    } else {
                        sign = "Cancer";
                    }
                    break;
                case 7:
                    if (d <= 22) {
                        sign = "Cancer";
                    } else {
                        sign = "Leo";
                    }
                    break;
                case 8:
                    if (d <= 22) {
                        sign = "Leo";
                    } else {
                        sign = "Virgo";
                    }
                    break;
                case 9:
                    if (d <= 22) {
                        sign = "Virgo";
                    } else {
                        sign = "Libra";
                    }
                case 10:
                    if (d <= 22) {
                        sign = "Libra";
                    } else {
                        sign = "Scorpio";
                    }
                    break;
                case 11:
                    if (d <= 21) {
                        sign = "Scorpio";
                    } else {
                        sign = "Sagittarius";
                    }
                    break;
                case 12:
                    if (d <= 21) {
                        sign = "Sagittarius";
                    } else {
                        sign = "Capricorn";
                    }
                    break;
                default:
                    break;
            }
           // Toast.makeText(FindZodiac.this,"Date:"+year+"/"+monthOfYear+"/"+dayOfMonth,Toast.LENGTH_SHORT).show();
            onDateDone(sign);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_find_zodiac, menu);
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

    @Override
    public void onDateDone(String sign) {
        tvZodiacResult = (TextView) findViewById(R.id.tvZodiacResult);
        tvZodiacResult.setText(months[m-1] + " " + d + ": " + sign.toString());
    }
}
