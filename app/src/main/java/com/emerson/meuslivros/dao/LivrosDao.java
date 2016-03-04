package com.emerson.meuslivros.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.emerson.meuslivros.model.Livros;
import java.util.ArrayList;

/**
 * Created by emerson on 04/03/16.
 */
public class LivrosDao extends SQLiteOpenHelper
{
    private static final String DATABASE = "dblivros";
    private static final int VERSION=1;
    private static final String LIVROS = "livros";

    public LivrosDao(Context context) {
        super(context,DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String livroCreate ="CREATE TABLE " + LIVROS + " " +
                            "(" +
                                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                                "titulo TEXT NOT NULL, " +
                                "autor TEXT NOT NULL, " +
                                "paginas INTEGER" +
                            ");";
        db.execSQL(livroCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void salvaLivro(Livros livro) {
        ContentValues values = new ContentValues();

        values.put("titulo", livro.getTitulo());
        values.put("autor", livro.getAutor());
        values.put("paginas", livro.getPaginas());

        getWritableDatabase().insert(LIVROS,null,values);
    }

    public void alteraLivro(Livros livro) {
        ContentValues values = new ContentValues();

        values.put("titulo", livro.getTitulo());
        values.put("autor", livro.getAutor());
        values.put("paginas", livro.getPaginas());

        String[] args = {livro.getId().toString()};
        getWritableDatabase().update(LIVROS, values, "id=?", args);
    }

    public void removeLivro(Livros livro) {
        String[] args = {livro.getId().toString()};
        getWritableDatabase().delete(LIVROS, "id=?", args);
    }

    public ArrayList<Livros> findAllLivros() {
        String[] columns = {"id", "titulo", "autor", "paginas"};
        Cursor cursor = getWritableDatabase().query(LIVROS, columns, null, null, null, null, null, null);
        ArrayList<Livros> listaLivros = new ArrayList<Livros>();

        while (cursor.moveToNext()) {
            Livros livro = new Livros();
            livro.setId(cursor.getLong(0));
            livro.setTitulo(cursor.getString(1));
            livro.setAutor(cursor.getString(2));
            livro.setPaginas(cursor.getInt(3));
            listaLivros.add(livro);
        }

        return listaLivros;
    }
}
