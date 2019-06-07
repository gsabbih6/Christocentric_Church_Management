package Adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dighisoft.christocentric.MemberActivity;
import com.dighisoft.christocentric.R;
import com.dighisoft.christocentric.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

import Models.Member;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MyViewHolder> {

    List<Member> memberList;

    RecyclerView recyclerView;

    public MemberAdapter(List<Member> memberList, RecyclerView v) {
        this.memberList = memberList;
        this.recyclerView = v;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_member, parent, false);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int itemPosition = recyclerView.getChildLayoutPosition(v);
                Member item = memberList.get(itemPosition);

                Intent i = new Intent(parent.getContext(), MemberActivity.class);
                i.putExtra(MemberActivity.MEMBER_ID,item.getId());
                parent.getContext().startActivity(i);

                //

            }
        });
        return new MyViewHolder(v);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        myViewHolder.memberName.setText(memberList.get(i).getName());
        myViewHolder
                .memberStatus.
                setText(memberList.
                        get(i).getStatus() == null ? "Status: Visitor" : "Status: Member ");
        myViewHolder
                .memberTel.
                setText("|| Tel: " + memberList.get(i).getTelephone());

//        if (memberList.get(i).getPhoto().getUrl().isEmpty())

        try {
            Picasso.get()
                    .load(Utils.BASE_URL + memberList.get(i).getPhoto().getUrl())
                    .placeholder(R.drawable.member_gradient)
                    .into(myViewHolder.photo);
        } catch (NullPointerException e) {

        }

//        Log.d("Photo URL", memberList.get(i).getPhoto().getUrl());

    }

    @Override
    public int getItemCount() {
        return memberList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView memberName;
        public TextView memberStatus;
        public TextView memberTel;
        public ImageView photo;

        public MyViewHolder(View v) {
            super(v);
            memberName = v.findViewById(R.id.member__fullname);
            this.memberStatus = v.findViewById(R.id.member_status);
            this.memberTel = v.findViewById(R.id.member_tel);
            this.photo = v.findViewById(R.id.member_photo);
        }
    }


}
