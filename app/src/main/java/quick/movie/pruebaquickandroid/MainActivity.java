package quick.movie.pruebaquickandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class MainActivity extends AppCompatActivity {

    //Elementos UI y para compartir con otras clases
    private EditText editTextName;
    private Button btnSearch;
    private RadioButton radioButtonMovie;
    private RadioButton radioButtonSerie;
    public static final int MOVIE_OPTION = 1;
    public static final int SERIE_OPTION = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = findViewById(R.id.editTextName);
        btnSearch = findViewById(R.id.buttonSearch);
        radioButtonMovie = findViewById(R.id.radioButtonMovie);
        radioButtonSerie = findViewById(R.id.radioButtonSerie);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameMovieSerie = editTextName.getText().toString();
                int nameMovieSerieCount = nameMovieSerie.length();
                int option = (radioButtonSerie.isChecked()) ? SERIE_OPTION : MOVIE_OPTION;

                if(nameMovieSerie != null && !nameMovieSerie.isEmpty()){
                    if(nameMovieSerieCount >= 3){
                        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                        intent.putExtra("nameMovieSerie", nameMovieSerie);
                        intent.putExtra("option", option);
                        startActivity(intent);
                    }else{
                        MessagesToast.myToastNameMovieSerie3(MainActivity.this);
                    }
                }else{
                    if(option == 1 ){
                        MessagesToast.myToastRadioButtonMovie(MainActivity.this);
                    }else{
                        MessagesToast.myToastRadioButtonSerie(MainActivity.this);
                    }
                }
            }
        });
    }
}
