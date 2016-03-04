package com.emerson.meuslivros;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.emerson.meuslivros.dao.LivrosDao;
import com.emerson.meuslivros.model.Livros;

import java.text.Normalizer;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    ListView listaLivros;
    LivrosDao dao;
    ArrayList<Livros> listLivros;
    ArrayAdapter adapter;
    Livros livro;

    @Override
    protected void onResume() {
        super.onResume();
        carregaLivros();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listaLivros = (ListView) findViewById(R.id.listaDeLivros);
        registerForContextMenu(listaLivros);
        listaLivros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                Livros livroSelecionado = (Livros) adapter.getItemAtPosition(position);
                Intent i = new Intent(MainActivity.this, FormLivros.class);
                i.putExtra("livroListaSelecionado", livroSelecionado);
                startActivity(i);
            }
        });

        listaLivros.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long id) {
                livro = (Livros) adapter.getItemAtPosition(position);
                return false;
            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem menuDelete = menu.add("Remover");
        menuDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                dao = new LivrosDao(MainActivity.this);
                dao.removeLivro(livro);
                dao.close();

                carregaLivros();

                return true;
            }
        });
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            Intent i = new Intent(MainActivity.this, FormLivros.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    public void carregaLivros() {
        dao = new LivrosDao(MainActivity.this);
        listLivros = dao.findAllLivros();
        dao.close();
        if (listLivros!=null) {
            adapter = new ArrayAdapter<Livros>(MainActivity.this, android.R.layout.simple_list_item_1, listLivros);
            listaLivros.setAdapter(adapter);
        }
    }
}
