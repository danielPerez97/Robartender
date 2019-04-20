package com.example.recipes;

import android.os.Bundle;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import com.example.recipes.activities.bartenderview.BartenderActivity;
import com.example.recipes.activities.patronview.PatronActivity;
import com.example.recipes.dagger.MyApp;
import com.example.recipes.networking.RobartenderService;
import com.example.recipes.utils.Utils;
import com.jakewharton.rxbinding3.view.RxView;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import kotlin.Unit;

import javax.inject.Inject;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity
{
    Button bartenderButton;
    Button patronButton;
    CompositeDisposable disposables = new CompositeDisposable();
    Observable<Unit> bartenderClicks;
    Observable<Unit> patronClicks;
    @Inject RobartenderService service;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        ((MyApp) getApplication()).getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        bartenderButton = findViewById(R.id.bartender_btn);
        patronButton = findViewById(R.id.patron_btn);

        Utils.log("Activity Main Loaded");

        bartenderClicks = RxView.clicks(bartenderButton)
                .debounce(400, TimeUnit.MILLISECONDS);

        patronClicks = RxView.clicks(patronButton)
                .debounce(400, TimeUnit.MILLISECONDS);

        disposables.add(
                bartenderClicks.subscribe(unit -> Utils.startActivity(this, BartenderActivity.class))
        );
        disposables.add(
                patronClicks.subscribe(unit -> Utils.startActivity(this, PatronActivity.class))
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.dispose();
    }
}
