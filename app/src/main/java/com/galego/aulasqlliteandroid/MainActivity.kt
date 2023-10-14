package com.galego.aulasqlliteandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.galego.aulasqlliteandroid.database.DataBaseHelper
import com.galego.aulasqlliteandroid.database.ProdutoDAO
import com.galego.aulasqlliteandroid.databinding.ActivityMainBinding
import com.galego.aulasqlliteandroid.model.Produto

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }


    private val bancoDados by lazy {
        DataBaseHelper(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding) {
            btnSalvar.setOnClickListener {
                salvar()
            }
            btnListar.setOnClickListener {
                listar()
            }

            btnAtualizar.setOnClickListener {
                atualizar()
            }

            btnRemover.setOnClickListener {
                remover()
            }
        }

    }

    private fun remover() {

        val produtoDAO = ProdutoDAO(this)
        produtoDAO.remover(2)

    }

    private fun atualizar() {
        val titulo = binding.editProduto.text.toString()
               val produtoDAO = ProdutoDAO(this)
        val produto = Produto(
            2,
            titulo,
            "descricao....",
        )
        produtoDAO.atualizar(produto)
    }

    private fun listar() {
        val produtoDAO = ProdutoDAO(this)
        val listaProdutos = produtoDAO.listar()

        var texto = ""
        if(listaProdutos.isNotEmpty()){
            listaProdutos.forEach{  produto ->
                texto += "${produto.idProduto} - ${produto.titulo} - ${produto.descricao} \n"
                Log.i("info_db", "${produto.idProduto} - ${produto.titulo} - ${produto.descricao}")
            }
            
            binding.textResultado.text = texto
        }else{
           binding.textResultado.text = "Nenhum item cadastrado"
        }
    }

    private fun salvar() {

        val titulo = binding.editProduto.text.toString()


        val produtoDAO = ProdutoDAO(this)
        val produto = Produto(
            -1,
            titulo,
            "descricao....",
        )
        if(produtoDAO.salvar(produto)){
            binding.editProduto.text.clear()
            Toast.makeText(this, "Produto salvo com sucesso!", Toast.LENGTH_SHORT).show()
            binding.textResultado.text = ""
        }else{
            Toast.makeText(this, "Erro ao salvar.", Toast.LENGTH_SHORT).show()
        }

    }



}