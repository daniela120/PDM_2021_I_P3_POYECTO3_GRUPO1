package hn.edu.ujcv.pdm_2021_i_p3_poyecto3_grupo1

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.content_main.*

class Menu : AppCompatActivity() {
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        setSupportActionBar(findViewById(R.id.toolbar))

            collapsing_toolbar.title = " "
            collapsing_toolbar.setContentScrimColor(Color.parseColor("#1E213D"));

            layoutManager = LinearLayoutManager(this)
            recyclerView.layoutManager = layoutManager

            adapter = RecyclerAdapter()
            recyclerView.adapter = adapter
        }

}