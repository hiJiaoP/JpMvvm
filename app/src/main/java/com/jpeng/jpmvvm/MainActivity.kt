package com.jpeng.jpmvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jpeng.jpmvvm.databinding.ActivityMainBinding
import com.jpeng.jpmvvm.http.RequestStatus
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: TestViewModel

    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewModel = ViewModelProvider(this).get(TestViewModel::class.java)

        btn_post.setOnClickListener {
            viewModel.executeBusiness()
        }

        viewModel.mLiveData.observe(this, Observer {
            when (it.requestStatus) {
                RequestStatus.SUCCESS -> {
                    it.data?.let {
//                        error("Result == " + it.result.size)
                        Log.e(
                            "Result", "code == " + it.code + "message == " + it.message
                                    + "image == " + it.result[0].image
                        )
                    }
                }
            }
        })
    }
}
