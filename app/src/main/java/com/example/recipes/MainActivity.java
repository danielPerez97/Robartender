package com.example.recipes;

import android.os.Bundle;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.recipes.activities.BartenderActivity;
import com.example.recipes.activities.PatronActivity;
import com.example.recipes.utils.Utils;
import com.jakewharton.rxbinding3.view.RxView;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import kotlin.Unit;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity
{
    @BindView(R.id.bartender_btn) Button bartenderButton;
    @BindView(R.id.patron_btn) Button patronButton;
    CompositeDisposable disposables = new CompositeDisposable();
    Observable<Unit> bartenderClicks;
    Observable<Unit> patronClicks;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Utils.log("Activity Main Loaded");

        bartenderClicks = RxView.clicks(bartenderButton).debounce(400, TimeUnit.MILLISECONDS);
        patronClicks = RxView.clicks(patronButton).debounce(400, TimeUnit.MILLISECONDS);

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
