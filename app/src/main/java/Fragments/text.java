//package Fragments;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import java.util.List;
//import java.util.Map;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//
//public class text extends ArrayAdapter<Map<String, String>> {
//
//    List<Map<String, String>> rs;
//
//    public text(@NonNull Context context, int resource, @NonNull List<Map<String, String>> objects) {
//        super(context, resource, objects);
//
//        this.rs = objects;// assigns your map object to rs
//    }
//
//
//    @Override
//    public int getCount() {
//        return rs.size(); //basically number of Items to be in the listview
//    }
//
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return super.getView(position, convertView, parent); // this is where you use your data
//
//        // first inflate a custom view from xml
//
//        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        convertView =inflater.inflate("custom view here", null); // the custom view
//
//        LinearLayout l=convertView.findViewById("some layoutid inside the custom view");
//
//
//        // Now below you can use your List
//        Map<String, String> r = rs.get(position);
//        for (String valueStr : r.values()) {
//
//            TextView t=new TextView(getContext());
//            t.setText(valueStr);
//
//            l.addView(t);
//        }
//
//
//        return convertView;
//    }
//}
