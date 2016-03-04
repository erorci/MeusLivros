package com.emerson.meuslivros;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.emerson.meuslivros.dao.LivrosDao;
import com.emerson.meuslivros.model.Livros;


public class FormLivros extends AppCompatActivity {


    EditText titulo, autor, paginas;
    Button btnAcao;
    Livros livroToEdit, livro;
    LivrosDao dao;
    Boolean salvar = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_livros);

        Intent intent = getIntent();
        livroToEdit = (Livros) intent.getSerializableExtra("livroListaSelecionado");

        livro = new Livros();
        dao = new LivrosDao(FormLivros.this);

        titulo = (EditText) findViewById(R.id.titulo);
        autor = (EditText) findViewById(R.id.autor);
        paginas = (EditText) findViewById(R.id.paginas);
        btnAcao = (Button) findViewById(R.id.btn_salvar);

        if(livroToEdit != null) {
            btnAcao.setText("Alterar");
            titulo.setText(livroToEdit.getTitulo());
            autor.setText(livroToEdit.getAutor());
            paginas.setText(livroToEdit.getPaginas()+"");
            livro.setId(livroToEdit.getId());
            salvar = false;
        }else{
            btnAcao.setText("Salvar");
            salvar = true;
        }

        btnAcao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                livro.setTitulo(titulo.getText().toString());
                livro.setAutor(autor.getText().toString());
                livro.setPaginas(Integer.parseInt(paginas.getText().toString()));

                if(salvar) {
                    dao.salvaLivro(livro);
                    dao.close();
                } else {
                    dao.alteraLivro(livro);
                    dao.close();
                }

                finish();
            }
        });


    }
}
