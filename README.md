
# GymFinder
<img width="403" height="737" alt="image" src="https://github.com/user-attachments/assets/01c5a4e8-0933-4448-ae74-b110fa5625e4" />


**`GymFinder App`**
### Pages
* EditLocation
* GymView
* GymList(main)
* Setting
* GymMap
### GymList
* in this page you can the following
* Create button -> Goes to create new gyhm
* Map -> shows all gyms on google maps
* click on gym  -> goes to GymView to edit GymView
* 3 Dots -> Goes to settings
### GymView
<img width="411" height="733" alt="image" src="https://github.com/user-attachments/assets/8369ce60-12a4-44a5-92a1-b6dc8c80edbc" />



## Gym has the following the values
* Title
* Description
* Gym Rating
* Counties
* Images
* Location

## EditLocation
  <img width="386" height="713" alt="image" src="https://github.com/user-attachments/assets/10e5866a-a394-4f10-83dd-97e4e7b77fe6" />

  
* Custom getInfoWindow()
* Edit GymLocations

## MapActivity
<img width="358" height="699" alt="image" src="https://github.com/user-attachments/assets/e8c46958-c037-45d1-be45-c39509cdb428" />

* Firebase
**`Installation`**
* Change Key.xml to google maps api key
```
<resources>
    <string name="MAPS_API_KEY"></string>
</resources>
```

<br>
<br>
<br>
<br>

**`Installation`**
* JSON
* Uncomment Json And remove FireStore
 ```
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
//         gyms = GymMemStore()
         gyms = GymJsonStore(applicationContext)
//        gyms = GymFireStore(applicationContext)

        i("GymFinder started")
    }
``` 
