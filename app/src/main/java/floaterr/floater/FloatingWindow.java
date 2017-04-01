package floaterr.floater;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class FloatingWindow extends Service {
    WindowManager windowManager;
    LinearLayout linearLayout;
    ImageView bubbleIcon;
    ImageButton btnMin;
    ImageButton btnStop;
    LinearLayout llBotones;
    Spinner zonas;
    ListView lv;
    ArrayAdapter<String> adaptadorZonas;
    GymsAdapter adapterListView;

    private final Gyms[] gymsAeropuerto = new Gyms[]{
            new Gyms("Capilla Nuestra Señora De Agua Fría", "25.811112,-100.148533"),
            new Gyms("Capilla Agua Fría", "25.803136,-100.1554"),
            new Gyms("Estructuras Rojas", "25.789891,-100.141425"),
            new Gyms("Papalote (aeropuerto)", "25.786729,-100.136404"),
            new Gyms("Coliseo la Ciudad(cento apodaca)", "25.782865,-100.181855"),
            new Gyms("Imagen De Barro", "25.778443,-100.179567"),
            new Gyms("General Juan Mendez Arcs", "25.78146,-100.189245"),
            new Gyms("Modelo Shield", "25.768715,-100.190591"),
            new Gyms("Guardián De Oro Mural", "25.763805,-100.173697"),
            new Gyms("Biblioteca Pueblo Nuevo", "25.759215,-100.161716"),
            new Gyms("Molino de viento", "25.749065,-100.163579")
    };
    private final Gyms[] gymsNoria = new Gyms[]{
            new Gyms("Capilla Cristo Rey Del Amor", "25.734065,-100.187373"),
            new Gyms("Mural San Judas Tadeo", "25.736455,-100.194629"),
            new Gyms("Industrial Park Water Tank", "25.733664,-100.209502")
    };
    private final Gyms[] gymsCitadel = new Gyms[]{
            new Gyms("SUTERM", "25.73726,-100.213621"),
            new Gyms("Scissors and combs", "25.734385,-100.214722"),
            new Gyms("Muralla Fresnos Del Lago", "25.734148,-100.219504"),
            new Gyms("Oxxo Watertank", "25.748656,-100.227164"),
            new Gyms("Monumento A La Enseñanza", "25.758881,-100.239959"),
            new Gyms("Bandido", "25.731995,-100.240297"),
            new Gyms("El Bob", "25.731272,-100.243901"),
            new Gyms("Barras Conalep", "25.72821,-100.256136"),
            new Gyms("Mirada Triste", "25.725068,-100.241172"),
            new Gyms("Iglesia Incógnita De La Desconocidez", "25.72428,-100.235655"),
            new Gyms("Herradura", "25.719554,-100.234161"),
            new Gyms("Jaguar Oculto", "25.720368,-100.229141"),
            new Gyms("Torre Vivenza", "25.724503,-100.227713"),
            new Gyms("Fuente De Los Cubos", "25.723693,-100.222652"),
            new Gyms("Meme Mural", "25.72989,-100.225406")
    };
    private final Gyms[] gymsGuadalupe = new Gyms[]{
            new Gyms("Campanario Parroquia Del Espiritu Santo", "25.720635,-100.18252"),
            new Gyms("The Lovebug", "25.720061,-100.198479"),
            new Gyms("Iglesia De Jesucristo De Los Últimos Días", "Iglesia De Jesucristo De Los Últimos Días"),
            new Gyms("La dama de los sueños", "25.724813,-100.212416"),
            new Gyms("Bob Esponja y Donkey Kong", "25.707205,-100.192824"),
            new Gyms("El Juego De La Vida El Sube Y Baja", "25.710697,-100.202575"),
            new Gyms("Gente Que Trabaja", "25.712533,-100.203684"),
            new Gyms("Ancla De Barco", "25.71251,-100.204995"),
            new Gyms("Parroquia Cruz del Apostolado", "25.711139,-100.213006"),
            new Gyms("Iglesia Nuestra Señora de las Mercedes", "25.706167,-100.219095"),
            new Gyms("La Virgen Te Habla", "25.702924,-100.214012"),
            new Gyms("Iglesia Sagrado corazón de Jesús ", "25.695144, -100.209725"),
            new Gyms("Aun dormida?", "25.697553,-100.207039")
    };
    private Gyms[] gymsAnahuac = new Gyms[]{
            new Gyms("Fray bartolome de las casas", "25.741136,-100.318899"),
            new Gyms("Fuente Barragan", "25.735816,-100.314304"),
            new Gyms("The mural kings barber shop", "25.736899,-100.320650")
    };
    private final Gyms[] gymsApodaca = new Gyms[]{
            //Apodaca
            new Gyms("Iglesia Cristiana", "25.799198,-100.226293"),
            new Gyms("Parroquia De Santa Teresita Del Niño Jesús", "25.781405,-100.239089"),
            new Gyms("Molinos De Viento", "25.775004,-100.236177"),
            new Gyms("Dragon Ball Mural", "25.78485,-100.248985"),
            new Gyms("Metal Slug Mural", "25.787564,-100.249985"),
            new Gyms("Iglesia de San Marcos", "25.790998,-100.253492"),
            new Gyms("Purple Girl Mural", "25.792214,-100.25945"),
            new Gyms("Palapa", "25.790739,-100.262405"),
            new Gyms("Blue Clues", "25.786606,-100.258962"),
            new Gyms("Escudo Azteca", "25.784201,-100.26259"),
            new Gyms("Jodi Mural", "25.782782,-100.261255"),
            new Gyms("La Virgen en Afganistán Mural", "25.778477,-100.25541"),
            new Gyms("Escudo Chente Suarez", "25.777574,-100.254057"),
            new Gyms("Jaro Mural", "25.773531,-100.261047"),
            new Gyms("Iglesia Cristo Buen Pastor", "25.769952,-100.252015"),
            new Gyms("Escudos Fútbol Club Monterrey", "25.769734,-100.255665")
    };
    private final Gyms[] gymsContry = new Gyms[]{
            //Contry
            new Gyms("Corpus christi church", "25.636991,-100.276911"),
            new Gyms("La fuente de brazos", "25.634189,-100.281097"),
            new Gyms("Puerta al parque", "25.630896,-100.273872"),
            new Gyms("Escuadrón 201", "25.626113,-100.276879"),
            new Gyms("Parque del Gusanito", "25.62263,-100.289298"),
            new Gyms("Plaza alamillo Brisas", "25.619647,-100.279949"),
            new Gyms("Cuida a tus Perritos", "25.623248,-100.28391"),
            new Gyms("Parque Mirador Residencial", "25.62594,-100.293576"),
            new Gyms("Cruz del Sagrado Corazon", "25.617735,-100.297351"),
            new Gyms("Vitral Crucifixion", "25.617684,-100.297884"),
            new Gyms("Galaxia Color", "25.614164,-100.278943"),
            new Gyms("Celestina Rios", "25.613109,-100.279508"),
            new Gyms("Biblioteca Alfredo Garcia Vice", "25.613572,-100.289918")
    };
    private final Gyms[] gymsLasPuentes = new Gyms[]{
            //San nico las puentes
            new Gyms("Parroquia de los Afligidos", "25.745109, -100.2530"),
            new Gyms("Monumento de Arco", "25.745109, -100.253020"),
            new Gyms("El mural frutal", "25.748645, -100.25595"),
            new Gyms("Iglesia de jesuscristo de los ultimos días", "25.752728, -100.252476"),
            new Gyms("Cuervo mural", "25.749389, -100.258463"),
            new Gyms("Villas de Sto.Domingo park", "25.749640, -100.261969"),
            new Gyms("El viejo pozo de agua", "25.747721, -100.261497"),
            new Gyms("Antiguo rostro de México mural", "25.754324,-100.266776"),
            new Gyms("Entrada las Puentes 8vo sector", "25.747438, -100.268784")
    };
    private final Gyms[] gymsSanNico = new Gyms[]{
            //San nico
            new Gyms("Fuerza Trabajadora", "25.738481,-100.254615"),
            new Gyms("The Beatles Pintura", "25.740436,-100.262978"),
            new Gyms("Changüito Con Su Banana", "25.738256,-100.268065"),
            new Gyms("Tacos Lobito", "25.734607,-100.270247"),
            new Gyms("Kamikaze Dávila", "25.735319,-100.272049")
    };
    private final Gyms[] gymsLaredo = new Gyms[]{
            //Laredo
            new Gyms("Cañon De Artillería","25.857733,-100.24606"),
            new Gyms("Biblioteca Santa Rosa","25.824953,-100.2208"),
            new Gyms("Ice Cream Mural","25.822059,-100.257659"),
            new Gyms("Arte Callejera","25.819103,-100.255939"),
            new Gyms("Pasando El Rato Mural","25.811903,-100.255418"),
            new Gyms("Geisha Mural","25.810344,-100.253679"),
            new Gyms("Mega Man Mural","25.811758,-100.2646"),
            new Gyms("Templo Manantial De Vida","25.808,-100.267512"),
            new Gyms("Alien Eyes Mural","25.80609,-100.270284"),
            new Gyms("Eyes Mural","25.805847,-100.27342"),
            new Gyms("Templo Eben-Ezer","25.808508,-100.285156")
    };
    private final String[] zonasString =
            new String[]{"Anahuac","Aeropuerto","Apodaca","Citadel","Contry","Guadalupe", "Noria", "San Nicolas Las Puentes", "San Nicolas", "Laredo"};

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        DisplayMetrics metrics = new DisplayMetrics();
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        int height = metrics.heightPixels;
        int width = metrics.widthPixels;

        final WindowManager.LayoutParams paramsBubble = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        paramsBubble.gravity = Gravity.TOP | Gravity.LEFT;
        paramsBubble.x = 0;
        paramsBubble.y = 100;

        bubbleIcon = new ImageView(this);
        bubbleIcon.setImageResource(R.mipmap.ic_crown_gym);

        final WindowManager.LayoutParams paramsLinearLayout = new WindowManager.LayoutParams(
                width/2, height/2,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        paramsLinearLayout.gravity = Gravity.TOP | Gravity.LEFT;
        paramsLinearLayout.x = 0;
        paramsLinearLayout.y = 100;

        bubbleIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                windowManager.removeView(bubbleIcon);
                paramsLinearLayout.x = 0;
                paramsLinearLayout.y = 200;
                windowManager.addView(linearLayout, paramsLinearLayout);
            }
        });

        windowManager.addView(bubbleIcon, paramsBubble);

        linearLayout = new LinearLayout(this);
        linearLayout.setBackgroundColor(Color.argb(100, 0, 0, 0));
        final LinearLayout.LayoutParams layoutParameteres = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(layoutParameteres);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        btnMin = new ImageButton(this);
        btnMin.setImageResource(R.mipmap.ic_minimize);
        LinearLayout.LayoutParams btnParameters = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        btnParameters.weight = 1f;
        btnMin.setLayoutParams(btnParameters);
        //linearLayout.addView(btnMin);

        btnStop = new ImageButton(this);
        btnStop.setImageResource(R.mipmap.ic_close);
        LinearLayout.LayoutParams btnParameters2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        btnParameters2.weight = 1f;
        btnStop.setLayoutParams(btnParameters2);
        //linearLayout.addView(btnStop);

        llBotones = new LinearLayout(this);
        ViewGroup.LayoutParams btnParameters3 = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        llBotones.setLayoutParams(btnParameters3);
        llBotones.setOrientation(LinearLayout.HORIZONTAL);
        llBotones.setWeightSum(2);
        llBotones.addView(btnMin);
        llBotones.addView(btnStop);
        linearLayout.addView(llBotones);

        zonas = new Spinner(this);
       // zonas.setBackgroundColor(Color.argb(50, 241, 196, 15));

        adaptadorZonas =
                new ArrayAdapter<String>(this, R.layout.spinner_zonas, zonasString);
        zonas.setAdapter(adaptadorZonas);
        linearLayout.addView(zonas);

        lv = new ListView(this);
        adapterListView = new GymsAdapter(this, gymsAnahuac);
        lv.setAdapter(adapterListView);
        linearLayout.addView(lv);

        lv.setOnTouchListener(new View.OnTouchListener() {
            WindowManager.LayoutParams updatedParameters = paramsLinearLayout;
            double x;
            double y;
            double pressedX;
            double pressedY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x = updatedParameters.x;
                        y = updatedParameters.y;
                        pressedX = event.getRawX();
                        pressedY = event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        updatedParameters.x = (int) (x + (event.getRawX() - pressedX));
                        updatedParameters.y = (int) (y + (event.getRawY() - pressedY));
                        windowManager.updateViewLayout(linearLayout, updatedParameters);
                    default:
                        break;
                }
                return false;
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String gymName = ((Gyms) adapterView.getItemAtPosition(i)).getName();
                String gymLocation = ((Gyms) adapterView.getItemAtPosition(i)).getLocation();
                if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
                    android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    clipboard.setText(gymLocation);
                } else {
                    android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    android.content.ClipData clip = android.content.ClipData.newPlainText("Ubicaciòn copiada", gymLocation);
                    clipboard.setPrimaryClip(clip);
                }
                Toast.makeText(getApplicationContext(), "Ubicacion copiada de: " + gymName, Toast.LENGTH_SHORT).show();
            }
        });

        btnMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                windowManager.removeView(linearLayout);
                windowManager.addView(bubbleIcon, paramsBubble);
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                windowManager.removeView(linearLayout);
                stopSelf();
                System.exit(0);
            }
        });

        zonas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String zona = adapterView.getItemAtPosition(i).toString();
                switch (zona){
                    case "Aeropuerto":
                        adapterListView = new GymsAdapter(getApplicationContext(), gymsAeropuerto);
                        lv.setAdapter(adapterListView);
                        break;
                    case "Apodaca":
                        adapterListView = new GymsAdapter(getApplicationContext(), gymsApodaca);
                        lv.setAdapter(adapterListView);
                        break;
                    case "Anahuac":
                        adapterListView = new GymsAdapter(getApplicationContext(), gymsAnahuac);
                        lv.setAdapter(adapterListView);
                        break;
                    case "Citadel":
                        adapterListView = new GymsAdapter(getApplicationContext(), gymsCitadel);
                        lv.setAdapter(adapterListView);
                        break;
                    case "Contry":
                        adapterListView = new GymsAdapter(getApplicationContext(), gymsContry);
                        lv.setAdapter(adapterListView);
                        break;
                    case "Guadalupe":
                        adapterListView = new GymsAdapter(getApplicationContext(), gymsGuadalupe);
                        lv.setAdapter(adapterListView);
                        break;
                    case "San Nicolas Las Puentes":
                        adapterListView = new GymsAdapter(getApplicationContext(), gymsLasPuentes);
                        lv.setAdapter(adapterListView);
                        break;
                    case "San Nicolas":
                        adapterListView = new GymsAdapter(getApplicationContext(), gymsSanNico);
                        lv.setAdapter(adapterListView);
                        break;
                    case "Laredo":
                        adapterListView = new GymsAdapter(getApplicationContext(), gymsLaredo);
                        lv.setAdapter(adapterListView);
                        break;
                    default:
                        break;
                }
                Toast.makeText(getApplicationContext(), zona, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getApplicationContext(), "NADA", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSelf();
    }
}