# Backdrop

[ ![Download](https://api.bintray.com/packages/semper-viventem/maven/backdrop/images/download.svg) ](https://bintray.com/semper-viventem/maven/backdrop/_latestVersion)[![](https://jitpack.io/v/Semper-Viventem/BackdropView.svg)](https://jitpack.io/#Semper-Viventem/BackdropView)


## What is it?
This library makes it easy to implement a [Backdrop](https://material.io/design/components/backdrop.html) pattern with a CoordinatorLayout


<img src="/docs/images/Screenshot_20180722-000750.png" width="300">  <img src="/docs/images/Screenshot_20180722-000754.png" width="300">


<img src="/docs/images/Screenshot_20180722-000802.png" width="300">  <img src="/docs/images/Screenshot_20180722-000806.png" width="300">


## Download
**JCenter (Recommended):**
```groovy
dependencies {
    implementation 'ru.semper-viventem.backdrop:backdrop:0.1.6'
}
```

**JitPack:**
```groovy
repositories {
	...
	maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.Semper-Viventem:BackdropView:0.1.6'
}
```

## How to use it?
You need to add front layout and back layout (with toolbar) to CoordinatorLayout.

Add BackdropBehavior to your front layout:

**XML**
```xml
<android.support.design.widget.CoordinatorLayout
    android:layout_width="match_parent"
    android:layout_height="match_parent">
    
    <!-- BackLayout for BackDrop -->
    <com.google.android.material.appbar.AppBarLayout
        android:id="@+id/backLayout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical">

        <!-- Must contain a Toolbar -->
        <androidx.appcompat.widget.Toolbar
            android:id="@+id/toolbar"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"/>

        <!-- For example, NavigationView. Or you can use anything -->
        <com.google.android.material.navigation.NavigationView
            android:id="@+id/navigationView"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:menu="@menu/main_menu"/>
    </com.google.android.material.appbar.AppBarLayout>

    <!-- Add BackdropBehavior to this view -->
    <android.support.design.card.MaterialCardView
        android:id="@+id/foregroundContainer"
        app:layout_behavior="ru.semper_viventem.backdrop.BackdropBehavior"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
        
        <!-- Anything -->
    </android.support.design.card.MaterialCardView>

</android.support.design.widget.CoordinatorLayout>
```


**Kotlin**

*I used this extension to search for behavior:*
```kotlin
fun <T : CoordinatorLayout.Behavior<*>> View.findBehavior(): T = layoutParams.run {
    if (this !is CoordinatorLayout.LayoutParams) throw IllegalArgumentException("View's layout params should be CoordinatorLayout.LayoutParams")

    (layoutParams as CoordinatorLayout.LayoutParams).behavior as? T
            ?: throw IllegalArgumentException("Layout's behavior is not current behavior")
}
```

```kotlin
...

val backdropBehavior: BackdropBehavior = foregroundContainer.findBehavior() // find behavior

with(backdropBehavior) {

        // Attach your back layout to behavior.
        // BackDropBehavior will find the toolbar itself.
        attachBackLayout(R.id.backLayout)
        
        // Set navigation icons for toolbar
        setClosedIcon(R.drawable.ic_menu)
        setOpenedIcon(R.drawable.ic_close)
        
        // Add listener
        addOnDropListener(object : BackdropBehavior.OnDropListener {
            override fun onDrop(dropState: BackdropBehavior.DropState, fromUser: Boolean) {
                // TODO: handle listener            
            }
        })
}

...
```


## License

```

MIT License

Copyright (c) 2018 Konstantin Kulikov (kostyaxxx8@gmail.com)

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