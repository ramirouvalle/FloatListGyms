package floaterr.floater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by ramir on 29/03/2017.
 */
public class GymsAdapter extends ArrayAdapter<Gyms> {
    private Gyms[] gyms;
    public GymsAdapter(Context context, Gyms[] gyms){
        super(context, android.R.layout.simple_list_item_2, gyms);
        this.gyms = gyms;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(android.R.layout.simple_list_item_2, null);

        TextView tvName = (TextView)item.findViewById(android.R.id.text1);
        tvName.setText(gyms[position].getName());

        TextView tvLocation = (TextView)item.findViewById(android.R.id.text2);
        tvLocation.setText(gyms[position].getLocation());

        return(item);
    }

    public Gyms[] getGyms() {
        return gyms;
    }

    public void setGyms(Gyms[] gyms) {
        this.gyms = gyms;
    }
}
