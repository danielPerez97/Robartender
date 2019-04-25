package com.example.recipes

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import butterknife.BindView
import butterknife.ButterKnife
import com.example.recipes.activities.bartenderview.BartenderActivity
import com.example.recipes.activities.patronview.PatronActivity
import com.example.recipes.utils.beginActivity
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    @BindView(R.id.bartender_btn)
    lateinit var bartenderButton: Button
    @BindView(R.id.patron_btn) lateinit var patronButton: Button
    lateinit var bartenderClicks: Observable<Unit>
    lateinit var patronClicks: Observable<Unit>
    var disposables = CompositeDisposable()


    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        bartenderClicks = bartenderButton.clicks()
            .debounce(400, TimeUnit.MILLISECONDS)

        patronClicks = patronButton.clicks()
            .debounce(400, TimeUnit.MILLISECONDS)

        disposables += bartenderClicks.subscribe { this.beginActivity(BartenderActivity::class.java) }
        disposables += patronClicks.subscribe { this.beginActivity(PatronActivity::class.java) }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }
}
