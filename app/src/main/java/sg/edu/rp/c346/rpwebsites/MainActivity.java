package sg.edu.rp.c346.rpwebsites;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    Spinner spnCat;
    Spinner spnSub;
    Button btnGo;
    ArrayList<String> alSub;
    ArrayAdapter<String> aaSub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spnCat = findViewById(R.id.spinner1);
        spnSub = findViewById(R.id.spinner2);
        btnGo = findViewById(R.id.buttonGo);

        alSub = new ArrayList<>();
        aaSub = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, alSub);

        String[] strSub = getResources().getStringArray(R.array.sub_category_rp);

        alSub.addAll(Arrays.asList(strSub));
        spnSub.setAdapter(aaSub);

        spnCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                alSub.clear();
                switch (i) {

                    case 0:
                        String[] strRP = getResources().getStringArray(R.array.sub_category_rp);
                        alSub.addAll(Arrays.asList(strRP));
                        aaSub.notifyDataSetChanged();
                        break;

                    case 1:
                        String[] strSOI = getResources().getStringArray(R.array.sub_category_soi);
                        alSub.addAll(Arrays.asList(strSOI));
                        aaSub.notifyDataSetChanged();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int pos = spnCat.getSelectedItemPosition();
                int subpos = spnSub.getSelectedItemPosition();
                String[][] sites = {
                        {
                                "https://www.rp.edu.sg/",
                                "https://www.rp.edu.sg/student-life"
                        },
                        {
                                "https://www.rp.edu.sg/soi/full-time-diplomas/details/r47",
                                "https://www.rp.edu.sg/soi/full-time-diplomas/details/r12"
                        }
                };
                Intent intent = new Intent(getBaseContext(), WebViewActivity.class);

                String url = sites[pos][subpos];

                intent.putExtra("url", url);
                startActivity(intent);



            }
        });
    }
    @Override
    protected void onPause(){
        super.onPause();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEdit = prefs.edit();
        int pos = spnCat.getSelectedItemPosition();
        int subpos = spnSub.getSelectedItemPosition();

        prefEdit.putInt("cat", pos);
        prefEdit.putInt("subcat", subpos);

        prefEdit.commit();
    }
    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int cat = prefs.getInt("cat", 0);
        int subcat = prefs.getInt("subcat", 0);

        spnCat.setSelection(cat);
        spnSub.setSelection(subcat);
    }
}
