package br.com.caelum.cadastro.fragment;

import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import br.com.caelum.cadastro.dao.AlunoDAO;
import br.com.caelum.cadastro.model.Aluno;
import br.com.caelum.cadastro.util.Localizador;

public class MapaFragment extends SupportMapFragment {
    private List<Aluno> alunos;

    @Override
    public void onResume() {
        super.onResume();
        getMapAsync(new OnMapReadyCallback() {

                        @Override
                        public void onMapReady(GoogleMap googleMap) {

                            Localizador localizador = new Localizador(getActivity());
                            LatLng local = localizador.getCoordenada("Rua Vergueiro 3185 Vila Mariana");

                            centralizaNo(local,googleMap);

                            googleMap.addMarker(new MarkerOptions().position(local));
                        }
//        Log.i("MAPA", "Coordenadas da Caelum: " + local);

        });
    }

    private void centralizaNo(LatLng local, GoogleMap mapa){
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(local,11));

    }
}