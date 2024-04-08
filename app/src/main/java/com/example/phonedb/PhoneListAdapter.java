package com.example.phonedb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PhoneListAdapter extends RecyclerView.Adapter<PhoneListAdapter.PhoneViewHolder> {

    interface OnItemClickListener {
        void onItemClickListener(Phone phone);
    }

    private OnItemClickListener mOnItemClickListener;
    private LayoutInflater mLayoutInflater;
    private List<Phone> mPhoneList;

    public PhoneListAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mPhoneList = null;

        try {
            mOnItemClickListener = (OnItemClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnItemClickListener");
        }
    }

    @NonNull
    @Override
    public PhoneListAdapter.PhoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.phone_item, parent, false);
        return new PhoneViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneListAdapter.PhoneViewHolder holder, int position) {
        if (mPhoneList != null) {
            Phone currentPhone = mPhoneList.get(position);
            holder.bindData(currentPhone);
        }
    }

    @Override
    public int getItemCount() {
        return mPhoneList != null ? mPhoneList.size() : 0;
    }

    public Phone getPhoneAtPosition(int position) {
        return mPhoneList.get(position);
    }

    public void setPhoneList(List<Phone> phoneList) {
        mPhoneList = phoneList;
        notifyDataSetChanged();
    }

    public class PhoneViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tv_brand;
        public TextView tv_model;

        public PhoneViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_brand = itemView.findViewById(R.id.tv_phone_brand);
            tv_model = itemView.findViewById(R.id.tv_phone_model);

            itemView.setOnClickListener(this);
        }

        public void bindData(Phone phone) {
            tv_brand.setText(phone.getBrand());
            tv_model.setText(phone.getModel());
        }

        @Override
        public void onClick(View view) {
            if (mOnItemClickListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Phone clickedPhone = mPhoneList.get(position);
                    mOnItemClickListener.onItemClickListener(clickedPhone);
                }
            }
        }
    }
}


