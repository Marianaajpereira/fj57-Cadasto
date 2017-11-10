package br.com.caelum.cadastro.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import br.com.caelum.cadastro.model.Aluno;
import br.com.caelum.cadastro.R;
import br.com.caelum.cadastro.viewholder.ViewHolder;

public class ListaAlunosAdapter extends BaseAdapter {

    private final List<Aluno> alunos;
    private final Activity activity;

    public ListaAlunosAdapter (Activity activity, List<Aluno> alunos) {
        this.activity = activity;
        this.alunos = alunos;
    }

    @Override
    public int getCount() {
        return alunos.size();
    }

    @Override
    public Object getItem(int posicao) {
        return alunos.get(posicao);
    }

    @Override
    public long getItemId(int posicao) {
        return alunos.get(posicao).getId();
    }

    @Override
    public View getView(int posicao, View view, ViewGroup parent) {

        View item;
        ViewHolder holder;
        if(view == null) {
            LayoutInflater inflate = activity.getLayoutInflater();
            item = inflate.inflate(R.layout.item, parent, false);
            holder = new ViewHolder(item);
            item.setTag(holder);
        } else {
            item = view;
            holder = (ViewHolder) item.getTag();
        }
        Aluno aluno = alunos.get(posicao);
        holder.nome.setText(aluno.getNome());

        if (posicao % 2 == 0) {
            item.setBackgroundColor(activity.getResources().getColor(R.color.lista_par, activity.getTheme()));
        } else {
            item.setBackgroundColor(activity.getResources().getColor(R.color.lista_impar, activity.getTheme()));
        }

        Bitmap bm;
        if (aluno.getCaminhoFoto() != null) {
            bm = BitmapFactory.decodeFile(aluno.getCaminhoFoto());
        } else {
            bm = BitmapFactory.decodeResource(activity.getResources(),R.drawable.ic_no_image);
        }

        bm = Bitmap.createScaledBitmap(bm, 100, 100, true);
        holder.foto.setImageBitmap(bm);

        return item;
    }
}
