package ab.appdev.drivemaster

import ab.appdev.drivemaster.databinding.ActivityLoginBinding
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.BoringLayout
import android.widget.Toast
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val users = conexion()
        binding.button.setOnClickListener {
            conexion().start()
        }
    }


    private fun conexion(): Thread {

        return Thread {
            val id = binding.idUsuario.text.toString().toInt()
            val url = URL("https://gorest.co.in/public/v2/users")
            val connection = url.openConnection() as HttpsURLConnection
            if (connection.responseCode == 200) {
                val inputStream = connection.inputStream
                val inputStreamReader = InputStreamReader(inputStream, "UTF-8")
                val users = Gson().fromJson(inputStreamReader, Array<User>::class.java)
                controlSesion(users, id)
                inputStreamReader.close()
                inputStream.close()
            } else {
                Snackbar.make(
                    binding.imageView10,
                    "Error al conectar con el API",
                    Snackbar.LENGTH_LONG
                )
                    .show()
            }
        }
    }
    var bandera: Boolean = false
    var bandera2: Boolean = false
    private fun controlSesion(users: Array<User>, id: Int) {
        for (user in users) {
            if (user.id == id && user.status == "active") {
                bandera = true;
                println("entraaaaaaaa")
                break
            }
            if(user.id == id && user.status=="inactive"){
                bandera2 = true
                break
            }
            }
            if(bandera){
            val intent = Intent(this, StartupActivity::class.java)
            startActivity(intent)
        } else if(bandera2) {
                println("inactivooooooooooooooooooooo")
                Snackbar.make(
                    binding.imageView10,
                    "ID de usuario inactivo no puede iniciar",
                    Snackbar.LENGTH_LONG
                )
                    .show()
                bandera2=false
        }else{
                Snackbar.make(
                    binding.imageView10,
                    "ID de usuario incorrecto",
                    Snackbar.LENGTH_LONG
                )
                    .show()
            }
    }
    }



    data class User(
        val id: Int,
        val name: String,
        val email: String,
        val gender: String,
        val status: String
    )

