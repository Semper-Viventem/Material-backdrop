# BackdropView



## What is it?
This library makes it easy to implement a [Backdrop](https://material.io/design/components/backdrop.html) pattern with a CoordinatorLayout


<img src="/docs/images/Screenshot_20180720-191017.png">
<img src="/docs/images/Screenshot_20180720-191029.png">


## How to use it?
You need to add a layout Toolbar, back container and foreground container

**Gradle**
```groovy
implementation 'ru.semper-viventem.backdrop:backdrop:0.1.0'
```

**XML**
```xml
<android.support.design.widget.CoordinatorLayout
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <android.support.v7.widget.Toolbar
        android:id="@+id/toolbar"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>

    <LinearLayout
        android:id="@+id/backContainer"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical">
        
        <!-- anything -->
    </LinearLayout>

    <android.support.design.card.MaterialCardView
        android:id="@+id/foregroundContainer"
        app:layout_behavior="ru.semper_viventem.backdrop.BackdropBehavior"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
        
        <!-- anything -->
    </android.support.design.card.MaterialCardView>

</android.support.design.widget.CoordinatorLayout>
```

**Kotlin**

```kotlin
private lateinit var backdropBehavior: BackdropBehavior

...

if (foregroundContainer.layoutParams is CoordinatorLayout.LayoutParams) {
    val behavior = (foregroundContainer.layoutParams as CoordinatorLayout.LayoutParams).behavior
    if (behavior is BackdropBehavior) {
                backdropBehavior = behavior
    }
}

with(backdropBehavior) {
        attacheBackContainer(R.id.backContainer) // set back container
        attacheToolbar(R.id.toolbar) // set toolbar
        
        // add listener
        addOnDropListener(object : BackdropBehavior.OnDropListener {
            // do anyging
        }
    })
}
```


## License

```

MIT License

Copyright (c) 2017 Dmitriy Gorbunov (dmitriy.goto@gmail.com)
                   and Vasili Chyrvon (vasili.chyrvon@gmail.com)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```