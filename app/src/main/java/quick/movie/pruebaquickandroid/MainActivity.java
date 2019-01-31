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
    public static final String MOVIE_OPTION = "Movie";
    public static final String SERIE_OPTION = "Series";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Carga del icono en el Action Bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_quick_icon_round);

        editTextName = findViewById(R.id.editTextName);
        btnSearch = findViewById(R.id.buttonSearch);
        radioButtonMovie = findViewById(R.id.radioButtonMovie);
        radioButtonSerie = findViewById(R.id.radioButtonSerie);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameMovieSerie = editTextName.getText().toString();
                int nameMovieSerieCount = nameMovieSerie.length();

                if(radioButtonMovie.isChecked()){
                    if(nameMovieSerie != null && !nameMovieSerie.isEmpty()) {
                        if(nameMovieSerieCount >= 3) {
                            String option = MOVIE_OPTION;
                            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                            intent.putExtra("nameMovieSerie", nameMovieSerie);
                            intent.putExtra("option", option);
                            startActivity(intent);
                        }else{
                            MessagesToast.myToastNameMovieSerie3(MainActivity.this);
                        }
                    }else{
                        MessagesToast.myToastRadioButtonMovie(MainActivity.this);
                    }
                }else if(radioButtonSerie.isChecked()){
                    if(nameMovieSerie != null && !nameMovieSerie.isEmpty()) {
                        if(nameMovieSerieCount >= 3) {
                            String option = SERIE_OPTION;
                            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                            intent.putExtra("nameMovieSerie", nameMovieSerie);
                            intent.putExtra("option", option);
                            startActivity(intent);
                        }else{
                            MessagesToast.myToastNameMovieSerie3(MainActivity.this);
                        }
                    }else{
                        MessagesToast.myToastRadioButtonSerie(MainActivity.this);
                    }
                }else{
                    MessagesToast.myToastRadioButtonEmpty(MainActivity.this);
                }
            }
        });
    }
}
