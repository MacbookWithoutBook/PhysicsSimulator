package com.bignerdranch.android.physicsSim.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.physicsSim.R;
import com.bignerdranch.android.physicsSim.activities.SceneActivity;
import com.bignerdranch.android.physicsSim.entities.Simulation;
import com.bignerdranch.android.physicsSim.viewmodels.SimulationViewModel;
import com.bignerdranch.android.physicsSim.viewmodels.UserAccountViewModel;

import java.util.List;

import javax.xml.XMLConstants;

public class LoadSimFragment extends Fragment {
    private final String TAG = getClass().getSimpleName();

    private RecyclerView mSimulationRecyclerView;
    private SimulationAdapter mSimulationAdapter;
    private List<Simulation> mUserSimulationList;
    private SimulationViewModel mSimulationViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Activity activity = requireActivity();
        mSimulationViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(SimulationViewModel.class);
        mSimulationViewModel.getUserSimulations().observe((LifecycleOwner) activity, simList -> {
            SimulationAdapter simulationAdapter = new SimulationAdapter(simList);
            mSimulationRecyclerView.swapAdapter(simulationAdapter, true);
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_load_sim, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Activity activity = requireActivity();
        mSimulationRecyclerView = view.findViewById(R.id.simulation_recycler_view);
        mSimulationRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
    }

    private static class SimulationHolder extends RecyclerView.ViewHolder {
        private final String TAG = getClass().getSimpleName();
        private final TextView mSimulationTextView;
        private final Button mLoadButton;
        private final ImageButton mDeleteButton;

        SimulationHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_simulation, parent, false));
            mSimulationTextView = itemView.findViewById(R.id.simulation_info);
            mLoadButton = itemView.findViewById(R.id.button_load);
            mDeleteButton = itemView.findViewById(R.id.button_delete);
        }

        void bind(Simulation simulation, SimulationViewModel simViewModel) {
            String name = simulation.mSimName;
            mSimulationTextView.setText(name);

            mLoadButton.setOnClickListener(v -> {
                Log.d(TAG, "Load simulation: " + simulation);
                // Set the current simid.
                simViewModel.setSimid(simulation.mSimid);
                Toast.makeText(v.getContext(), "Simulation loaded.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), SceneActivity.class);
                v.getContext().startActivity(intent);
            });

            mDeleteButton.setOnClickListener(v -> {
                Log.d(TAG, "Delete simulation: " + simulation);
                new AlertDialog.Builder(v.getContext())
                        .setTitle("Delete simulation")
                        .setMessage("Are you SURE you want to delete simulation " + simulation.mSimName + "?")
                        .setPositiveButton(android.R.string.yes, (dialogueInterface, i) -> {
                            simViewModel.deleteSimulation(simulation);
                            Toast.makeText(v.getContext(), "Simulation deleted.", Toast.LENGTH_SHORT).show();
                            ((Activity) v.getContext()).finish();
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            });
        }

        void unbind() {
            mLoadButton.setOnClickListener(null);
        }
    }

    private class SimulationAdapter extends RecyclerView.Adapter<SimulationHolder> {
        private final List<Simulation> mUserSimulationList;

        SimulationAdapter(List<Simulation> userSimulationList) { mUserSimulationList = userSimulationList; }

        @NonNull
        @Override
        public SimulationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = requireActivity().getLayoutInflater();
            return new SimulationHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull SimulationHolder holder, int position) {
            Simulation simulation = mUserSimulationList.get(position);
            holder.bind(simulation, mSimulationViewModel);
        }

        @Override
        public void onViewRecycled(@NonNull SimulationHolder holder) {
            holder.unbind();
        }

        @Override
        public int getItemCount() { return mUserSimulationList.size(); }
    }
}