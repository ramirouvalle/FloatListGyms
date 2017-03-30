package floaterr.floater;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FloatingWindow extends Service {
    WindowManager wm;
    LinearLayout ll;
    LinearLayout ll2;
    private Gyms[] gyms =
            new Gyms[]{
                    new Gyms("Título 1", "Subtítulo largo 1"),
                    new Gyms("Título 2", "Subtítulo largo 2"),
                    new Gyms("Título 3", "Subtítulo largo 3"),
                    new Gyms("Título 4", "Subtítulo largo 4"),
                    new Gyms("Título 1", "Subtítulo largo 1"),
                    new Gyms("Título 2", "Subtítulo largo 2"),
                    new Gyms("Título 3", "Subtítulo largo 3"),
                    new Gyms("Título 4", "Subtítulo largo 4"),
                    new Gyms("Título 1", "Subtítulo largo 1"),
                    new Gyms("Título 2", "Subtítulo largo 2"),
                    new Gyms("Título 3", "Subtítulo largo 3"),
                    new Gyms("Título 4", "Subtítulo largo 4"),
                    new Gyms("Título 15", "Subtítulo largo 15")};
    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();

        wm = (WindowManager) getSystemService(WINDOW_SERVICE);

        ll2 = new LinearLayout(this);
        ll2.setBackgroundColor(Color.RED);
        LinearLayout.LayoutParams layoutParameteres2 = new LinearLayout.LayoutParams(
                100,100);
        Button btnN = new Button(this);
        btnN.setText("Nuevo boton");
        ll2.addView(btnN);

        ll = new LinearLayout(this);
        ll.setBackgroundColor(Color.argb(100, 255, 255, 102));

        LinearLayout.LayoutParams layoutParameteres = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 400);

        ll.setLayoutParams(layoutParameteres);
        ll.setOrientation(LinearLayout.VERTICAL);

        final WindowManager.LayoutParams parameters = new WindowManager.LayoutParams(
                1000, 1000, WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        parameters.gravity = Gravity.TOP | Gravity.LEFT;
        parameters.x = 0;
        parameters.y = 50;

        TextView tvTitle = new TextView(this);
        tvTitle.setText("Gimnasios");
        ViewGroup.LayoutParams tvTitleParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        tvTitle.setLayoutParams(tvTitleParams);
        tvTitle.setBackgroundColor(Color.RED);
        tvTitle.setTextSize(25);
        ll.addView(tvTitle);

        ListView lv = new ListView(this);
        GymsAdapter adapter = new GymsAdapter(this, gyms);
        lv.setAdapter(adapter);
        ll.addView(lv);

        Button stop = new Button(this);
        stop.setText("Stop");
        ViewGroup.LayoutParams btnParameters = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        stop.setLayoutParams(btnParameters);
        ll.addView(stop);

        wm.addView(ll, parameters);

        tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WindowManager.LayoutParams updatedParameters = parameters;
                parameters.width = 250;
                parameters.height = 250;
                wm.removeView(ll);
                wm.addView(ll2,updatedParameters);
                //wm.updateViewLayout(ll2, updatedParameters);
            }
        });
        lv.setOnTouchListener(new View.OnTouchListener() {
            WindowManager.LayoutParams updatedParameters = parameters;
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

                        wm.updateViewLayout(ll, updatedParameters);

                    default:
                        break;
                }

                return false;
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String gym = ((Gyms)adapterView.getItemAtPosition(i)).getName();
                Toast.makeText(getApplicationContext(), "Click en " + gym, Toast.LENGTH_LONG).show();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wm.removeView(ll);
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