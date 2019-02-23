package com.example.user.wordbook;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

import static com.example.user.wordbook.R.layout.word_list;

/**
 * Created by User on 12/1/2017.
 */

public class ItemAdapter extends ArrayAdapter<Item>{
    public ItemAdapter(@NonNull Context context,ArrayList<Item> items)
    {
        super(context,0,items);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View l=convertView;
        if(l==null)
        {
            l=LayoutInflater.from(getContext()).inflate(word_list,parent,false);
        }

        Item ob = getItem(position);



        TextView t1=(TextView) l.findViewById(R.id.type);
        t1.setText(ob.getType());

        TextView t2=(TextView) l.findViewById(R.id.meaning);
        String s=ob.getDefination();
        t2.setText(s);
        s=" Ex: ";
        t2=(TextView) l.findViewById(R.id.example);
        s+=ob.getExample();
        t2.setText(s);
        return  l;
    }
}
