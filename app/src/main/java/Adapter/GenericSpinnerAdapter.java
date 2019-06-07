package Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import Adapter.IAppnterface.CModel;
import Models.Branch;
import Models.Investment;
import Models.Member;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class GenericSpinnerAdapter<T extends CModel> extends ArrayAdapter<T> {
    private List<T> bls;


    public GenericSpinnerAdapter(@NonNull Context context, int resource, @NonNull List<T> objects) {
        super(context, resource, objects);

        bls = objects;
    }

    //List<Branch>
//    public SpinnerAdapterBranch(@NonNull Context context, int resource, @NonNull List<Branch> objects) {
//        super(context, resource, objects);
//    }
//
    @Override
    public int getCount() {
        return bls.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView tv = (TextView) super.getView(position, convertView, parent);


        if (bls.get(position) instanceof Branch) {
            Branch b = (Branch) bls.get(position);
            tv.setText(b.getName());
        }
        if (bls.get(position) instanceof Investment) {
            Investment in = (Investment) bls.get(position);
            tv.setText(in.getName());
        }
        if (bls.get(position) instanceof Member) {
            Member m = (Member) bls.get(position);
            tv.setText(m.getName());
        }

        return tv;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView tv = (TextView) super.getView(position, convertView, parent);


        if (bls.get(position) instanceof Branch) {
            Branch b = (Branch) bls.get(position);
            tv.setText(b.getName());
        }
        if (bls.get(position) instanceof Investment) {
            Investment in = (Investment) bls.get(position);
            tv.setText(in.getName());
        }
        if (bls.get(position) instanceof Member) {
            Member m = (Member) bls.get(position);
            tv.setText(m.getName());
        }

        return tv;
    }
}
