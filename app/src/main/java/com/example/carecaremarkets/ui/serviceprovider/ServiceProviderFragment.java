package com.example.carecaremarkets.ui.serviceprovider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carecaremarkets.CareCareMarketsDatabase;
import com.example.carecaremarkets.R;
import com.example.carecaremarkets.constants;
import com.example.carecaremarkets.daos.ServiceProvidersDAO;
import com.example.carecaremarkets.daos.UsersDAO;
import com.example.carecaremarkets.databinding.FragmentHomeBinding;
import com.example.carecaremarkets.databinding.FragmentServiceProviderBinding;
import com.example.carecaremarkets.recyclerView.HomeServiceProviderRecyclerViewAdapter;
import com.example.carecaremarkets.recyclerView.ServiceProviderRecyclerViewAdapter;
import com.example.carecaremarkets.tables.ServiceProviders;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;

public class ServiceProviderFragment extends Fragment implements SearchView.OnQueryTextListener, CompoundButton.OnCheckedChangeListener, AdapterView.OnItemSelectedListener {

    SharedPreferences pref;
    ServiceProvidersDAO providersDAO;
    ServiceProviderRecyclerViewAdapter adapter;
    RecyclerView serviceProviderRecyclerView;
    List<ServiceProviders> serviceProviders= new ArrayList<>();
    String governorate;
    SearchView serviceProvidersSearchView;
    Chip topRatedChip;
    Spinner governoratesSpinner;
    ArrayAdapter<CharSequence> adapter2;
private FragmentServiceProviderBinding binding;

public View onCreateView(@NonNull LayoutInflater inflater,
    ViewGroup container, Bundle savedInstanceState) {

    binding = FragmentServiceProviderBinding.inflate(inflater, container, false);
    View root = binding.getRoot();
    //setting ServiceProviderRecyclerView
    serviceProviderRecyclerView = binding.serviceProviderRecyclerView;
    LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext(),LinearLayoutManager.VERTICAL,false);
    serviceProviderRecyclerView.setLayoutManager(layoutManager);
    adapter = new ServiceProviderRecyclerViewAdapter(serviceProviders,root.getContext());
    serviceProviderRecyclerView.setAdapter(adapter);
    getServiceProviders();
    serviceProvidersSearchView = binding.serviceProvidersSearchView;
    serviceProvidersSearchView.setOnQueryTextListener(this);

    topRatedChip = binding.topRatedChip;
    topRatedChip.setOnCheckedChangeListener(this);

    pref = this.getActivity().getSharedPreferences("userData", Context.MODE_PRIVATE);

    governoratesSpinner = binding.governoratesSpinner;
    governorate = pref.getString("userGovernorate","error");
    adapter2 = ArrayAdapter.createFromResource(this.getActivity(), R.array.governorates2, android.R.layout.simple_spinner_item);
    governoratesSpinner.setAdapter(adapter2);
    if (governorate != null) {
        int spinnerPosition = adapter2.getPosition(governorate);
        governoratesSpinner.setSelection(spinnerPosition);
    }

    governoratesSpinner.setOnItemSelectedListener(this);



    return root;
    }

    void getServiceProviders()
    {
        serviceProviders.clear();
        providersDAO = CareCareMarketsDatabase.getInstance(this.getActivity()).serviceProvidersDAO();
        serviceProviders.addAll(providersDAO.getTop10ServiceProviders(constants.getGovernorate(this.getActivity())));
        adapter.notifyDataSetChanged();

    }


@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if(s.isEmpty())
            getServiceProviders();
        else
        {
            providersDAO = CareCareMarketsDatabase.getInstance(this.getActivity()).serviceProvidersDAO();
            serviceProviders.clear();
            serviceProviders.addAll(providersDAO.getServiceProviders(s+"%",constants.getGovernorate(this.getActivity())));
            adapter.notifyDataSetChanged();
        }


        return false;
    }

    void getTopRated() {
        providersDAO = CareCareMarketsDatabase.getInstance(this.getActivity()).serviceProvidersDAO();
        serviceProviders.clear();
        serviceProviders.addAll(providersDAO.getTopServiceProviders(constants.getGovernorate(this.getActivity())));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if(b)
            getTopRated();
        else
            getServiceProviders();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(i == 0)
        {
            providersDAO = CareCareMarketsDatabase.getInstance(this.getActivity()).serviceProvidersDAO();
            serviceProviders.clear();
            serviceProviders.addAll(providersDAO.getTopServiceProvidersWithoutGovernorate());
            adapter.notifyDataSetChanged();
        }
        else
        {
            providersDAO = CareCareMarketsDatabase.getInstance(this.getActivity()).serviceProvidersDAO();
            serviceProviders.clear();
            serviceProviders.addAll(providersDAO.getTopServiceProviders(governoratesSpinner.getSelectedItem().toString()));
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}