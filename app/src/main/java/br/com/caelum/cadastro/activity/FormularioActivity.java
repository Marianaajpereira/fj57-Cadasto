package br.com.caelum.cadastro.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

import br.com.caelum.cadastro.helper.FormularioHelper;
import br.com.caelum.cadastro.R;
import br.com.caelum.cadastro.dao.AlunoDAO;
import br.com.caelum.cadastro.model.Aluno;

public class FormularioActivity extends AppCompatActivity {

    private FormularioHelper helper;
    private static final String ALUNO_SELECIONADO = "alunoSelecionado";
    private String localArquivoFoto;
    private static final int TIRA_FOTO = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

/*      Button botao = (Button) findViewById(R.id.formulario_botao);
     botao.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
              Toast.makeText(FormularioActivity.this, "Vocẽ clicou no botão", Toast.LENGTH_LONG).show();

              finish();
           }
       }); */

        ActionBar bar=getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);

        this.helper = new FormularioHelper(this);

        Intent intent = this.getIntent();

        Aluno aluno = (Aluno) getIntent().getSerializableExtra("alunoSelecionado");
        if(aluno != null) {
           helper.colocaNoFormulario(aluno);
        }

        Button foto = helper.getFotoButton();
        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                localArquivoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";
                Intent irParaCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Uri localFoto = Uri.fromFile(new File(localArquivoFoto));
                irParaCamera.putExtra(MediaStore.EXTRA_OUTPUT, localFoto);
                startActivityForResult(irParaCamera, TIRA_FOTO);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_formulario_ok:

                Aluno aluno = helper.pegaAlunoDoFormulario();
                AlunoDAO dao = new AlunoDAO(FormularioActivity.this);

                if (aluno.getId() == null) {
                    dao.insere(aluno);
                } else {
                    dao.altera(aluno);
                }
                dao.close();
                finish();
                return false;

/*                if (helper.temNome()) {

                    Aluno aluno = helper.pegaAlunoDoFormulario();

               Toast.makeText(FormularioActivity.this, "Objeto aluno criado: " + aluno.getNome(),Toast.LENGTH_SHORT).show();

                    AlunoDAO dao = new AlunoDAO(FormularioActivity.this);
                    dao.insere(aluno);
                    dao.close();
                    finish();
                    return true;
                } else {
                    helper.mostraErro();
                }*/

            default:
                return super.onOptionsItemSelected(item);

            case android.R.id.home:
                Toast.makeText(this,"Menu",Toast.LENGTH_LONG).show();
                finish();
                return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == TIRA_FOTO) {
            if (resultCode == Activity.RESULT_OK) {
                helper.carregaImagem(this.localArquivoFoto);
            } else {
                this.localArquivoFoto = null;
            }
        }
    }
}
