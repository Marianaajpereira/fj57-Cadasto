package br.com.caelum.cadastro.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import br.com.caelum.cadastro.convext.ContextActionBar;
import br.com.caelum.cadastro.permission.Permissao;
import br.com.caelum.cadastro.R;
import br.com.caelum.cadastro.adapter.ListaAlunosAdapter;
import br.com.caelum.cadastro.dao.AlunoDAO;
import br.com.caelum.cadastro.model.Aluno;
import br.com.caelum.cadastro.task.EnviaAlunosTask;

public class ListaAlunosActivity extends AppCompatActivity {
    private ListView listaAlunos;
    private List<Aluno> alunos;

    public static final String ALUNO_SELECIONADO = "alunoSelecionado";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);
        Permissao.fazPermissao(this);

//        String[] alunos={"Anderson", "Felipe", "Guilherme"};
//        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,alunos);


        this.listaAlunos=(ListView) findViewById(R.id.lista_alunos);

       listaAlunos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view, int posicao, long id) {
                Aluno alunoSelecionado = (Aluno) adapter.getItemAtPosition(posicao);

/*                String aluno = (String) adapter.getItemAtPosition(posicao);
                Toast.makeText(ListaAlunosActivity.this, "Clique longo: " + aluno, Toast.LENGTH_LONG).show();*/

             ContextActionBar actionBar = new ContextActionBar(ListaAlunosActivity.this,alunoSelecionado);
                ListaAlunosActivity.this.startSupportActionMode(actionBar);
                return true;
            }
        });

//        registerForContextMenu(listaAlunos);

        Button botaoAdiciona = (Button)
                findViewById(R.id.lista_alunos_floating_button);

        botaoAdiciona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                startActivity(intent);
            }
        });

        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View view, int posicao, long id) {
                Intent edicao = new Intent(ListaAlunosActivity.this, FormularioActivity.class);

                edicao.putExtra("alunoSelecionado",(Aluno) listaAlunos.getItemAtPosition(posicao));
                startActivity(edicao);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }

    public void carregaLista() {
        AlunoDAO dao = new AlunoDAO(this);
        alunos = dao.getLista();
        dao.close();
//        ArrayAdapter<Aluno> adapter=new ArrayAdapter<Aluno>(this,android.R.layout.simple_list_item_1,alunos);
        ListaAlunosAdapter adapter = new ListaAlunosAdapter(this, alunos);
        listaAlunos.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_alunos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_enviar_notas:
/*                AlunoDAO dao = new AlunoDAO(this);
                List<Aluno> alunos = dao.getLista();
                dao.close();
                String json = new AlunoConverter().toJSON(alunos);
                WebClient client = new WebClient();
                String response = null;
                try {
                    response = client.post(json);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(this, response, Toast.LENGTH_LONG).show();*/

                new EnviaAlunosTask(this).execute();
                return true;

            case R.id.menu_receber_provas:
                Intent provas = new Intent(this, ProvasActivity.class);
                startActivity(provas);
                return true;

            case R.id.menu_mapa:
                Intent mapa = new Intent(this, MostraAlunosAcitivity.class);
                startActivity(mapa);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}