package com.bodymass.howismassbody;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.example.howismassbody.R;
import com.example.howismassbody.databinding.FragmentResultMassBinding;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResultMassFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResultMassFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentResultMassBinding bindin;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ResultMassFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResultMassFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResultMassFragment newInstance(String param1, String param2) {
        ResultMassFragment fragment = new ResultMassFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        bindin=FragmentResultMassBinding.inflate(inflater, container, false);
        return bindin.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindin.textButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_resultMassFragment_to_SecondFragment);
            }
        });

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        double peso=Float.parseFloat(     sharedPref.getString("Peso", "0"));
        String pesounidad=sharedPref.getString("MedidaPeso", "Kilogramos  (KG)");
        double altura=Float.parseFloat(     sharedPref.getString("Altura", "0"));
        String alturaunidad=sharedPref.getString("MedidaAltura", "Centrimetros (CM)");
        double imc=calcularMasaCorporal(peso, pesounidad, altura, alturaunidad);
        String compicision=evaluarComposicionCorporal(imc);
        bindin.textView8.setText(imc+"");
        bindin.textView9.setText(compicision);
        setAnimationComposicionCorporal(compicision, bindin.lottieAnimationView4);
        bindin.lottieAnimationView4.playAnimation();
        //bindin.lottieAnimationView4.loop(true);
    }

    public double calcularMasaCorporal(double Peso, String unidadpesos, double altura, String unidadaltura){
        double pesoKg=0;
        double alturaM=0;
        double resultadoMasa=0;
        Resources res = getResources();
        String[] UnidadesPesosArray = res.getStringArray(R.array.Unidade_medida_peso);
        String[] UnidadesDistanciaArray = res.getStringArray(R.array.Unidade_medida_distancia);
        //Libras
        if(unidadpesos.equals(UnidadesPesosArray[0])){
            pesoKg=Peso/2.205;
        }
        //Kilogramos
        if(unidadpesos.equals(UnidadesPesosArray[1])){
            pesoKg=Peso;
        }
        //Centimetros
        if(unidadaltura.equals(UnidadesDistanciaArray[0])){
            alturaM=altura/100;
        }
        //Metros
        if(unidadaltura.equals(UnidadesDistanciaArray[1])){
            alturaM=altura;
        }
        resultadoMasa=pesoKg/(alturaM*alturaM);
        return  Math.round(resultadoMasa*100.0)/100.0;
    }
    public String evaluarComposicionCorporal(double imc){
        String composicionCorporal="";
        if(imc<18.5){
            composicionCorporal="Peso inferior al normal";
        }
        if(imc>=18.5 && imc<25.0){
            composicionCorporal="Normal";
        }
        if(imc>=25.0 && imc<30){
            composicionCorporal="Peso superior al normal";
        }
        if(imc>=30.0){
            composicionCorporal="Obesidad";
        }
        return composicionCorporal;
    }
    public void setAnimationComposicionCorporal(String CC, LottieAnimationView lottieAnimationView){
        if(CC.equals("Normal")){
            lottieAnimationView.setAnimation(R.raw.success);
        }else{
            lottieAnimationView.setAnimation(R.raw.warning);
            lottieAnimationView.setRepeatCount(3);
        }
    }
}