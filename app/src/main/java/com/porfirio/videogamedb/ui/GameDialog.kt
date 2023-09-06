package com.porfirio.videogamedb.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.Message
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.porfirio.videogamedb.R
import com.porfirio.videogamedb.application.VideogamesDBApp
import com.porfirio.videogamedb.data.GameRepository
import com.porfirio.videogamedb.data.db.model.GameEntity
import com.porfirio.videogamedb.databinding.GameDialogBinding
import kotlinx.coroutines.launch
import java.io.IOException

class GameDialog(
    private var newGame: Boolean = true,
    private var game: GameEntity = GameEntity(
        title = "",
        genre = "",
        developer = ""
    ),
    private val updateUI: () -> Unit,
    private val message: (String) -> Unit
): DialogFragment() {

    private var _binding: GameDialogBinding? = null
    private val binding get() = _binding!!

    private lateinit var builder: AlertDialog.Builder
    private lateinit var dialog: Dialog

    private var saveButton: Button? = null

    private lateinit var repository: GameRepository

    //Se configura el diálogo inicial
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = GameDialogBinding.inflate(requireActivity().layoutInflater)

        repository = (requireContext().applicationContext as VideogamesDBApp).repository

        builder = AlertDialog.Builder(requireContext())

        /*binding.tietTitle.setText(game.title)
        * binding.tietGenre.setText(game.genre)
        * binding.tietDeveloper.setText(game.developer)*/

        binding.apply {
            tietTitle.setText(game.title)
            tietGenre.setText(game.genre)
            tietDeveloper.setText(game.developer)
        }

        dialog = if(newGame){
            buildDialog("Guardar", "Cancelar",{
                game.title = binding.tietTitle.text.toString()
                game.genre = binding.tietGenre.text.toString()
                game.developer= binding.tietDeveloper.text.toString()

                try {
                    lifecycleScope.launch {
                        repository.insertGame(game)
                    }
                    message("Juego guardado exitosamente")

                    //Actualizar la UI
                    updateUI()
                }catch (e: IOException){
                    e.printStackTrace()
                    message("Error al guardar el juego")
                }
            }, {
                //Cancelar
            })
        }else{
            buildDialog("Actualizar", "Borrar",{
                //update
                game.title = binding.tietTitle.text.toString()
                game.genre = binding.tietGenre.text.toString()
                game.developer= binding.tietDeveloper.text.toString()

                try {
                    lifecycleScope.launch {
                        repository.updateGame(game)
                    }
                    message("Juego actualizado exitosamente")

                    //Actualizar la UI
                    updateUI()
                }catch (e: IOException){
                    e.printStackTrace()
                    message("Error al actualizar el juego")
                }
            }, {
                //Delete
                AlertDialog.Builder(requireContext())
                    .setTitle("Confirmación")
                    .setMessage("¿Realmente deseas eliminar el juego ${game.title}?")
                    .setPositiveButton("Aceptar"){_ , _ ->
                        try{
                            lifecycleScope.launch {
                                repository.deleteGame(game)
                            }
                            message("Juego eliminado exitosamente")

                            //Actualizar la UI
                            updateUI()
                        }catch (e: IOException){
                            e.printStackTrace()
                            message("Error al eliminar el juego")
                        }
                    }
                    .setNegativeButton("Cancelar"){ dialog, _ ->
                        dialog.dismiss()
                    }
                    .create()
                    .show()
            })
        }

        /*dialog = builder.setView(binding.root)
            .setTitle("Juego")
            //PositiveButton es un botón cuando el usuario acepta algo
            .setPositiveButton("Guardar", DialogInterface.OnClickListener { dialog, which ->
                //Guardar
                game.title = binding.tietTitle.text.toString()
                game.genre = binding.tietGenre.text.toString()
                game.developer = binding.tietDeveloper.text.toString()

                try {
                    lifecycleScope.launch {
                        repository.insertGame(game)
                    }

                    Toast.makeText(requireContext(), "juego guardado exitosamente", Toast.LENGTH_SHORT).show()

                }catch(e: IOException){
                    e.printStackTrace()
                    Toast.makeText(requireContext(), "Error al guardar el juego", Toast.LENGTH_SHORT).show()
                }
            })
            //NegativeButton es un botón que el usuario usa cuando rechaza algo
            .setNegativeButton("Cancelar", DialogInterface.OnClickListener { dialog, which ->

            })
            .create()*/

        return dialog
    }

    //Cuando se destruye el fragment
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    //Se llama después de que se muestra el diálogo en pantalla
    override fun onStart() {
        super.onStart()

        val alertDialog = dialog as AlertDialog //Lo usamos para poder emplear el método getButton (no lo tiene el dialog)
        saveButton = alertDialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE)
        saveButton?.isEnabled = false   //Al inicio el botón estará deshabilitado

        //Cuando el usario modifique el contenido en el text input edit text es cuando se hara algo
        //Esto lo tenemos que hacer para cada text box (titulo, genero y desarrollador)
        binding.tietTitle.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                saveButton?.isEnabled = validateFields()
            }
        })

        binding.tietGenre.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                saveButton?.isEnabled = validateFields()
            }

        })

        binding.tietDeveloper.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                saveButton?.isEnabled = validateFields()
            }

        })

    }

    private fun validateFields() =
        (binding.tietTitle.text.toString().isNotEmpty() && binding.tietGenre.text.toString()
            .isNotEmpty() && binding.tietDeveloper.text.toString().isNotEmpty())
    //Si todos los campos no estan vacios, es decir, el usuario escribio algo, se regresara un true
    //y si alguno de los campos esta vacio, la función regresará un false

    private fun buildDialog(
        btn1Text: String,
        btn2Text: String,
        positiveButton:() -> Unit,
        negativeButton: () -> Unit
    ): Dialog =
        builder.setView(binding.root)
            .setTitle("Juego")
            .setPositiveButton(btn1Text, DialogInterface.OnClickListener{ dialog, wich ->
                //Acción para el botón positivo
                positiveButton()
            })
            .setNegativeButton(btn2Text) { _, _ ->
                //Acción para el botón negativo
                negativeButton()
            }
            .create()

}