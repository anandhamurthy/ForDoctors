package com.fordoctors.fordoctors.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.fordoctors.fordoctors.DetailsActivity;
import com.fordoctors.fordoctors.R;
import com.fordoctors.fordoctors.model.Prescription;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class PrescriptionsAdapter extends RecyclerView.Adapter<PrescriptionsAdapter.ImageViewHolder> implements Filterable {


    private Context mContext;
    private List<Prescription> mPrescriptionList;
    private FirebaseAuth mAuth;
    private List<Prescription> mDefaultPrescriptionList;
    private DatabaseReference mPatientsDatabase, mUsersDatabase;
    private SearchAdapterListener listener;
    private FirebaseUser mFirebaseUser;
    private String mCurrentUserId;

    public PrescriptionsAdapter(Context context, List<Prescription> mPrescriptionList, PrescriptionsAdapter.SearchAdapterListener listener) {
        mContext = context;
        this.mPrescriptionList = mPrescriptionList;
        this.mDefaultPrescriptionList = mPrescriptionList;
        this.listener=listener;
    }

    @Override
    public PrescriptionsAdapter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.single_layout_patients_details, parent, false);
        return new PrescriptionsAdapter.ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PrescriptionsAdapter.ImageViewHolder holder, final int position) {

        mAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mAuth.getCurrentUser();
        mCurrentUserId = mFirebaseUser.getUid();
        mPatientsDatabase = FirebaseDatabase.getInstance().getReference().child("Patients").child(mCurrentUserId);
        mPatientsDatabase.keepSynced(true);
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mUsersDatabase.keepSynced(true);

        final Prescription prescription = mPrescriptionList.get(position);
        holder.Name.setText("Name : "+prescription.getName());
        holder.Details.setText("Age : "+prescription.getAge()+' '+"Gender : "+prescription.getGender());
        holder.Location.setText("City : "+prescription.getCity()+' '+"State : "+prescription.getState()+' '+"Country : "+prescription.getCountry());
        holder.Email.setText("Email : "+prescription.getEmail());
        holder.Timestamp.setText("Date - Time : "+prescription.getTimestamp());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailsActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("prescription", prescription);
                intent.putExtras(b);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mPrescriptionList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString().toLowerCase();
                if (charString.isEmpty()) {
                    mPrescriptionList = mDefaultPrescriptionList;
                } else {
                    List<Prescription> filteredList = new ArrayList<>();
                    for (Prescription row : mDefaultPrescriptionList) {

                        if (row.getName().toLowerCase().contains(charString) ||
                                row.getDetails().toLowerCase().contains(charSequence) ||
                                row.getEmail().toLowerCase().contains(charSequence) ||
                                row.getState().toLowerCase().contains(charSequence) ||
                                row.getCity().toLowerCase().contains(charSequence)
                        ) {
                            filteredList.add(row);
                        }
                    }

                    mPrescriptionList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mPrescriptionList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mPrescriptionList = (ArrayList<Prescription>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder {

        private TextView Name, Email, Details, Location, Timestamp;

        public ImageViewHolder(View itemView) {
            super(itemView);

            Name=itemView.findViewById(R.id.name);
            Email=itemView.findViewById(R.id.email);
            Location=itemView.findViewById(R.id.location);
            Details=itemView.findViewById(R.id.details);
            Timestamp=itemView.findViewById(R.id.timestamp);


        }
    }
    public interface SearchAdapterListener {
        void onSearchSelected(Prescription prescription);
    }
}