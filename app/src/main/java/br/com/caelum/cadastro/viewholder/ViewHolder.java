package br.com.caelum.cadastro.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.caelum.cadastro.R;

public class ViewHolder {

   public final TextView nome;
    public final ImageView foto;

    public ViewHolder(View view) {
        nome = (TextView) view.findViewById(R.id.item_nome);
        foto = (ImageView) view.findViewById(R.id.item_foto);

    }


}
