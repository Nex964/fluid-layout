# FluidLayout for Android
[![](https://jitpack.io/v/Nex964/fluid-layout.svg)](https://jitpack.io/#Nex964/fluid-layout)

Keeping it simple for now.

It a ViewGroup which can have draggable child <b>Views</b> with fluid effect.

### How to install

Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
}
```
Step 2. Add the dependency
```
dependencies {
	        implementation 'com.github.Nex964:fluid-layout:master-SNAPSHOT'
}
```
## Screenshots
<p float="left">
	<img src="screenshots/ss1.jpg?raw=true" width=250/>
	<img src="screenshots/ss2.jpg?raw=true" width=250/>
	<img src="screenshots/ss3.jpg?raw=true" width=250/>
</p>

## Attributes

| Atrribute Name | Description | Default |
| --- | --- | --- | 
| `color` | Background color for the child views | #000 |
| `lucidness` | Stickyness of the children view | 60 |

### How it works

It reads child bounding box and creates a new background for the child view with the defined color. Then a new path is created with all the bounding boxes available for the child views.
To create a sticky effect a new rectangle path is added between the bounding box's which appears closer to each other which gives a effect of sticky block which don't want to detach from each other.
