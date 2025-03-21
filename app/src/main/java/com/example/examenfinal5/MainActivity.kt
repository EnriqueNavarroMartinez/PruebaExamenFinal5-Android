package com.example.examenfinal5

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.examenfinal5.activities.ApiActivity
import com.example.examenfinal5.activities.RoomActivity
import com.example.examenfinal5.databinding.ActivityMainBinding
import com.example.examenfinal5.fragments.ApiFragment
import com.example.examenfinal5.fragments.InicioFragment
import com.example.examenfinal5.fragments.RoomFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Inicializamos con el InicioFragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, InicioFragment())
            .commit()

        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            it.isChecked = true
            when (it.itemId) {
                R.id.navigation_api -> {
                    // Acción al seleccionar ApiFragment
                    replaceFragment(ApiFragment())
                }
                R.id.navigation_api2 -> {
                    val intent = Intent(this, ApiActivity::class.java)  // Lanza una nueva Activity
                    startActivity(intent)
                }
                R.id.navigation_room -> {
                    // Acción al seleccionar RoomFragment
                    replaceFragment(RoomFragment())
                }
                R.id.navigation_room2 -> {
                    val intent = Intent(this, RoomActivity::class.java)  // Lanza una nueva Activity
                    startActivity(intent)
                }
            }
            false
        }
    }

    //Funcion para ir cambiando de fragments
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
}