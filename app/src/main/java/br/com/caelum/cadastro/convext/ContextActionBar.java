package br.com.caelum.cadastro.convext;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import br.com.caelum.cadastro.activity.ListaAlunosActivity;
import br.com.caelum.cadastro.dao.AlunoDAO;
import br.com.caelum.cadastro.model.Aluno;

public class ContextActionBar implements ActionMode.Callback {

    private Aluno alunoSelecionado;
    private ListaAlunosActivity activity;
    public ContextActionBar(ListaAlunosActivity activity, Aluno alunoSelecionado) {
        this.activity = activity;
        this.alunoSelecionado = alunoSelecionado;
    }

    @Override
    public boolean onCreateActionMode(final ActionMode mode, Menu menu) {

/*      AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Aluno alunoSelecionado = (Aluno) listaAlunos.getAdapter().getItem(info.position); */

        MenuItem ligar = menu.add("Ligar");
        Intent intentLigar = new Intent(Intent.ACTION_CALL);
        intentLigar.setData(Uri.parse("tel:" + alunoSelecionado.getTelefone()));
        ligar.setIntent(intentLigar);

        MenuItem SMS = menu.add("Enviar SMS");
        Intent intentSMS = new Intent(Intent.ACTION_VIEW);
        intentSMS.setData(Uri.parse("sms: " + alunoSelecionado.getTelefone()));
        SMS.setIntent(intentSMS);

        MenuItem mapa = menu.add("Achar no Mapa");
        Intent intentMapa = new Intent(Intent.ACTION_VIEW);
        intentMapa.setData(Uri.parse("geo:0,0?z=14&q=" + alunoSelecionado.getEndereco()));
        mapa.setIntent(intentMapa);

        MenuItem Site = menu.add("Navegar no site");
        Intent intentSite = new Intent(Intent.ACTION_VIEW);
        String site = alunoSelecionado.getSite();
        if (!site.startsWith("http://")) {
            site = "http://" + site;
        }
        intentSite.setData(Uri.parse(site));

        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                new AlertDialog.Builder(activity).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Deletar").setMessage("Deseja mesmo deletar?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                AlunoDAO dao = new AlunoDAO(activity);
                                dao.deletar(alunoSelecionado);
                                dao.close();
                                activity.carregaLista();
                            }
                        }).setNegativeButton("Não", null).show();

                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {

    }
}
