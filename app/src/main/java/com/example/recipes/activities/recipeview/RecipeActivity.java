package com.example.recipes.activities.recipeview;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import butterknife.BindView;
import butterknife.ButterKnife;
//import com.example.recipes.Database;
import com.example.recipes.R;
import com.squareup.sqldelight.Query;
import com.squareup.sqldelight.android.AndroidSqliteDriver;
import com.squareup.sqldelight.db.SqlDriver;
import com.squareup.sqldelight.runtime.rx.RxQuery;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
//import model.Recipe;

import java.util.List;

public class RecipeActivity extends AppCompatActivity
{
    @BindView(R.id.recipe_list_view) ListView recipes;
    ArrayAdapter<String> listAdapter;
    SqlDriver driver;
//    Database db;
    CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
//        ButterKnife.bind(this);
//        driver = new AndroidSqliteDriver(Database.Companion.getSchema(), getApplicationContext(), "db.db");
//        db = Database.Companion.invoke(driver);
//        compositeDisposable = new CompositeDisposable();
//
//
//        listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
//        recipes.setAdapter(listAdapter);
//        Query<Recipe> recipeQuery = db.getRecipeQueries().selectAll();
//        Observable<List<Recipe>> listObservable = RxQuery.toObservable(recipeQuery, Schedulers.io())
//                .to(RxQuery::mapToList);
//
//        Disposable disposable = listObservable
//                .observeOn(Schedulers.computation())
//                .flatMap( (List<Recipe> recipes) -> Observable.just(recipes)
//                        .flatMapIterable(list -> list)
//                        .map(Recipe::getName)
//                        .toList()
//                        .toObservable()
//                )
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe( (List<String> recipeNames) -> {
//                    listAdapter.clear();
//                    listAdapter.addAll(recipeNames);
//                });
//        compositeDisposable.add(disposable);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}
