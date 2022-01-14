package com.bodymass.howismassbody;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.navigation.fragment.NavHostFragment;


import com.example.howismassbody.R;
import com.example.howismassbody.databinding.FragmentFormCalMassBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

public class FormCalMassFragment extends Fragment {

    private FragmentFormCalMassBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFormCalMassBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Resources res = getResources();
        String[] shoppingItems = res.getStringArray(R.array.Unidade_medida_peso);
        ArrayAdapter arrayAdapter= new ArrayAdapter(view.getContext(), R.layout.dropdown,shoppingItems);
        binding.autoCompleteTextView1.setAdapter(arrayAdapter);

        String[] shoppingItems2 = res.getStringArray(R.array.Unidade_medida_distancia);
        ArrayAdapter arrayAdapter2= new ArrayAdapter(view.getContext(), R.layout.dropdown,shoppingItems2);
        binding.autoCompleteTextView3.setAdapter(arrayAdapter2);
        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(validarCampos(binding.autoCompleteTextView1.getText().toString(), binding.autoCompleteTextView2.getText().toString(), binding.autoCompleteTextView3.getText().toString(), binding.autoCompleteTextView4.getText().toString())){
                   SharedPreferences sharedPreferences= getActivity().getPreferences(Context.MODE_PRIVATE);
                   SharedPreferences.Editor  editor= sharedPreferences.edit();
                   editor.putString("MedidaPeso", binding.autoCompleteTextView1.getText().toString());
                   editor.putString("Peso", binding.autoCompleteTextView2.getText().toString());
                   editor.putString("MedidaAltura", binding.autoCompleteTextView3.getText().toString());
                   editor.putString("Altura", binding.autoCompleteTextView4.getText().toString());
                   editor.commit();
                   NavHostFragment.findNavController(FormCalMassFragment.this)
                           .navigate(R.id.action_SecondFragment_to_loadFragment);
               }else{
                   new MaterialAlertDialogBuilder(view.getContext())
                           .setTitle("            Completa el formulario")
                           .setMessage("Por favor completa el formulario para poder calcular tu índice de masa corporal (IMC)")
                           .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialogInterface, int i) {

                               }
                           })
                           .show();
               }

            }
        });

    }
    public boolean validarCampos(String campo1, String campo2, String campo3, String campo4){
        boolean bandera=true;
        if(campo1.equals("") && bandera){
            bandera=false;
        }
        if(campo2.equals("") && bandera){
            bandera=false;
        }else{
            try {
                double valor=Double.parseDouble(campo2);
                if(!(valor>0 && bandera)){
                    bandera=false;
                }
            }catch (Exception e){
                bandera=false;
            }

        }
        if(campo3.equals("") && bandera){
            bandera=false;
        }

        if(campo4.equals("") && bandera){
            bandera=false;
        }else{
            try {
                double valor2=Double.parseDouble(campo4);
                if(!(valor2>0 && bandera)){
                    bandera=false;
                }
            }catch (Exception e){
                bandera=false;
            }

        }
        return bandera;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}