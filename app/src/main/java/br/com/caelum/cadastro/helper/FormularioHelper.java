package br.com.caelum.cadastro.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import br.com.caelum.cadastro.R;
import br.com.caelum.cadastro.activity.FormularioActivity;
import br.com.caelum.cadastro.model.Aluno;

public class FormularioHelper {

    private EditText nome;
    private Aluno aluno;
    private EditText telefone;
    private EditText site;
    private RatingBar nota;
    private EditText endereco;
    private ImageView foto;
    private Button fotoButton;

    public FormularioHelper(FormularioActivity activity) {
        this.aluno = new Aluno();
        this.nome = (EditText) activity.findViewById(R.id.formulario_nome);
        this.telefone = (EditText) activity.findViewById(R.id.formulario_telefone);
        this.site = (EditText) activity.findViewById(R.id.formulario_site);
        this.nota = (RatingBar) activity.findViewById(R.id.formulario_nota);
        this.endereco = (EditText) activity.findViewById(R.id.formulario_endereco);

        this.foto = (ImageView) activity.findViewById(R.id.formulario_foto);
        this.fotoButton = (Button) activity.findViewById(R.id.formulario_foto_button);
    }

    public Button getFotoButton() {
        return fotoButton;
    }

    public Aluno pegaAlunoDoFormulario() {
        aluno.setNome(nome.getText().toString());
        aluno.setEndereco(endereco.getText().toString());
        aluno.setSite(site.getText().toString());
        aluno.setTelefone(telefone.getText().toString());
        aluno.setNota(Double.valueOf(nota.getProgress()));
        aluno.setCaminhoFoto((String) foto.getTag());

        return aluno;
    }

    public boolean temNome() {
        return !nome.getText().toString().isEmpty();
    }

    public void mostraErro() {
        nome.setError("Campo nome não pode ser vazio");
    }

    public void colocaNoFormulario(Aluno aluno) {
        nome.setText(aluno.getNome());
        nota.setProgress(aluno.getNota().intValue());
        telefone.setText(aluno.getTelefone());
        site.setText(aluno.getSite());
        endereco.setText(aluno.getEndereco());

        if (aluno.getCaminhoFoto()!=null) {
            carregaImagem(aluno.getCaminhoFoto());
        }
        this.aluno = aluno;
    }

    public void carregaImagem(String localArquivoFoto) {
        Bitmap imagemFoto = BitmapFactory.decodeFile(localArquivoFoto);
        Bitmap imagemFotoReduzida = Bitmap.createScaledBitmap(imagemFoto, 300, 300, true);

        foto.setImageBitmap(imagemFotoReduzida);
        foto.setTag(localArquivoFoto);
        foto.setScaleType(ImageView.ScaleType.FIT_XY);
    }
}
