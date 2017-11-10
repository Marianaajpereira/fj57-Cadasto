package br.com.caelum.cadastro.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import br.com.caelum.cadastro.model.Aluno;
import br.com.caelum.cadastro.converter.AlunoConverter;
import br.com.caelum.cadastro.dao.AlunoDAO;
import br.com.caelum.cadastro.support.WebClient;

public class EnviaAlunosTask extends AsyncTask<Object, Object, String> {

    private final Context context;
    private ProgressDialog progress;

    public EnviaAlunosTask(Context context) {
        this.context = context;
    }

    protected String doInBackground(Object... params) {

        AlunoDAO dao = new AlunoDAO(context);
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

        return response;
    }

    @Override
    protected void onPostExecute(String result) {
     progress.dismiss();
        Toast.makeText(context, result, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPreExecute() {
        progress = ProgressDialog.show(context, "Aguarde...", "Envio de dados para a web", true, true);

    }
}
