package floaterr.floater;

import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FloatingWindow extends Service {
    WindowManager windowManager;
    LinearLayout linearLayout;
    ImageView bubbleIcon;
    private Gyms[] gyms = new Gyms[]{
            new Gyms("Capilla Nuestra Señora De Agua Fría", "25.811112,-100.148533"),
            new Gyms("Capilla Agua Fría", "25.803136,-100.1554"),
            new Gyms("Estructuras Rojas", "25.789891,-100.141425"),
            new Gyms("Papalote (aeropuerto)","25.786729,-100.136404"),
            new Gyms("Coliseo la Ciudad(cento apodaca)","25.782865,-100.181855"),
            new Gyms("Imagen De Barro","25.778443,-100.179567"),
            new Gyms("General Juan Mendez Arcs","25.78146,-100.189245"),
            new Gyms("Modelo Shield","25.768715,-100.190591"),
            new Gyms("Guardián De Oro Mural","25.763805,-100.173697"),
            new Gyms("Biblioteca Pueblo Nuevo","25.759215,-100.161716"),
            new Gyms("Molino de viento","25.749065,-100.163579")
    };

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

        Button btnMin = new Button(this);
        btnMin.setText("Minimizar lista");
        ViewGroup.LayoutParams btnParameters = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        btnMin.setLayoutParams(btnParameters);
        linearLayout.addView(btnMin);

        Button btnStop = new Button(this);
        btnStop.setText("Cerrar aplicaciòn");
        ViewGroup.LayoutParams btnParameters2 = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        btnStop.setLayoutParams(btnParameters2);
        linearLayout.addView(btnStop);

        ListView lv = new ListView(this);
        GymsAdapter adapter = new GymsAdapter(this, gyms);
        lv.setAdapter(adapter);
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
                if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
                    android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    clipboard.setText(gymLocation);
                } else {
                    android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    android.content.ClipData clip = android.content.ClipData.newPlainText("Ubicaciòn copiada", gymLocation);
                    clipboard.setPrimaryClip(clip);
                }
                Toast.makeText(getApplicationContext(), "Ubicacion copiada de: "+ gymName, Toast.LENGTH_SHORT).show();
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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSelf();
    }

}