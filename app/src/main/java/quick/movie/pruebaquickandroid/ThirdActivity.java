package quick.movie.pruebaquickandroid;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ThirdActivity extends AppCompatActivity {

    //Variable para extraer data del json
    private String nameTitle = "";
    private String image = "";
    private int calification;

    //Elementos UI
    private ImageView imagen;
    private TextView nameTitleDetail;
    private TextView yearDetail;
//    private TextView calificationValue;
    private TextView genreDetail;
    private TextView actorDetail;
    private TextView descriptionDetail;
    private ImageView star1;
    private ImageView star2;
    private ImageView star3;
    private ImageView star4;
    private ImageView star5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        //Flecha para devolverse
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imagen = findViewById(R.id.imageViewDetail);
        nameTitleDetail = findViewById(R.id.textViewTitleDetail);
        yearDetail = findViewById(R.id.textViewYear);
        genreDetail = findViewById(R.id.textViewGenre);
        actorDetail = findViewById(R.id.textViewActor);
        descriptionDetail = findViewById(R.id.textViewDescription);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            nameTitle = bundle.getString("nameTitle");
        }

        //Llamado de la data
        getDataDetail(nameTitle);

        Picasso.get()
                .load(image)
                .error(R.mipmap.ic_launcher)
                .fit()
                .centerInside()
                .into(imagen);

        star1 = findViewById(R.id.star1);
        star2 = findViewById(R.id.star2);
        star3 = findViewById(R.id.star3);
        star4 = findViewById(R.id.star4);
        star5 = findViewById(R.id.star5);

        if(calification <= 2){
            star2.setVisibility(View.INVISIBLE);
            star3.setVisibility(View.INVISIBLE);
            star4.setVisibility(View.INVISIBLE);
            star5.setVisibility(View.INVISIBLE);
        }else if(calification > 2 && calification <= 4){
            star3.setVisibility(View.INVISIBLE);
            star4.setVisibility(View.INVISIBLE);
            star5.setVisibility(View.INVISIBLE);
        }else if (calification > 4 && calification <= 6){
            star4.setVisibility(View.INVISIBLE);
            star5.setVisibility(View.INVISIBLE);
        }else if(calification > 6 && calification <= 8){
            star5.setVisibility(View.INVISIBLE);
        }else{
            star1.setVisibility(View.VISIBLE);
            star2.setVisibility(View.VISIBLE);
            star3.setVisibility(View.VISIBLE);
            star4.setVisibility(View.VISIBLE);
            star5.setVisibility(View.VISIBLE);
        }

    }

    public void getDataDetail(String nameTitle){
        String apiTitle = "http://www.omdbapi.com/?t="+ nameTitle +"&apikey=6c43c325";
        String ratingsValue = "";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        URL url = null;
        HttpURLConnection conn;

        try {
            url = new URL(apiTitle);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String inputLine;
            StringBuffer response = new StringBuffer();
            String json;

            while((inputLine = in.readLine()) != null){
                response.append(inputLine);
            }

            json = response.toString();
            JSONObject myObject = new JSONObject(json);

            nameTitleDetail.setText(myObject.optString("Title"));
            image = myObject.optString("Poster");
            yearDetail.setText(myObject.optString("Year"));
            genreDetail.setText(myObject.optString("Genre"));
            actorDetail.setText(myObject.optString("Actors"));
            descriptionDetail.setText(myObject.optString("Plot"));

            ratingsValue = myObject.optString("imdbRating");
            if(ratingsValue != null && !ratingsValue.isEmpty()){
                String[] valueRatings = ratingsValue.split("");
                try{
                    calification = (valueRatings[0].isEmpty()) ? Integer.valueOf(valueRatings[1]) : Integer.valueOf(valueRatings[0]);
                }catch (Exception e){
                    System.out.println("ERROR: el valor "+ratingsValue+" de la calificación, no es un valor númerico o empieza con un caracter especial");
                }

            }else{
                calification = 0;
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
