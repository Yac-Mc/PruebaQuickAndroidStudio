package quick.movie.pruebaquickandroid;

import android.content.Context;
import android.widget.Toast;

public class MessagesToast {

    public static void myToastNameMovieSerie3(Context context){
        Toast.makeText(context, "Por favor ingresar más de 3 letras", Toast.LENGTH_SHORT).show();
    }

    public static void myToastRadioButtonEmpty(Context context){
        Toast.makeText(context, "Por favor elija si es Serie o Movie", Toast.LENGTH_SHORT).show();
    }

    public static void myToastRadioButtonMovie(Context context){
        Toast.makeText(context, "Por favor ingresar el nombre de la película", Toast.LENGTH_SHORT).show();
    }

    public static void myToastRadioButtonSerie(Context context){
        Toast.makeText(context, "Por favor ingresar el nombre de la serie", Toast.LENGTH_SHORT).show();
    }
}
