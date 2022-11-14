# Enhanced Progress library [![Release](https://jitpack.io/v/jojoyel/enhanced-progress.svg)](https://jitpack.io/#jojoyel/enhanced-progress)

## Description

This android library allows you to implement different types of animated progress indicators and
customize them. There is currently 3 differents progress indicators available in this library :

- ArcProgressBar
- CircleProgressBar (derived from ArcProgressBar)
- LinearProgressBar
- . . . *More will be added in the future*

Each indicator can display multiple animated and color customizable progress on the same bar

## Overview

| Name                 | Example                                                                  |
|----------------------|--------------------------------------------------------------------------|
| Linear progress bar  | <img height="100" src=".\manifest_res\overview_linear.gif" width="100"/> |
| Arc progress bar     | <img height="100" src=".\manifest_res\overview_arc.gif" width="100"/>    |

## Usage example

*Every usable composable has his Javadoc to help you quickly using it in your projects*

ArcProgressBar :

```kotlin
ArcProgressBar(
    progress = listOf(
        ProgressData(.3f, Color.Blue),
        ProgressData(.65f, Color(0xFF00FFFF))
    ),
    radius = 70.dp,
    stroke = 5.dp,
    baseAngle = 270f,
    endAngle = 180f
)
```

LinearProgressBar

```kotlin
LinearProgressBar(
    modifier = Modifier.fillMaxWidth(.5f),
    progress = listOf(
        ProgressData(.15f, Color.Green)
    )
)
```

## Documentation

### Class

#### ProgressData

| Property name | Description                                                                                                |
|---------------|------------------------------------------------------------------------------------------------------------|
| progress      | A float, representing the progress, value must be >= 0f and <=1f (if you want to represent 75%, fill .75f) |
| color         | A color used to display the given progress                                                                 |

#### AnimationSpecs

*Every delay has to be given in millis*

| Property name | Description                                                            |
|---------------|------------------------------------------------------------------------|
| startDelay    | Time before the animations start                                       |
| betweenDelay  | Time between each progress, 0 will animate everything at the same time |
| duration      | Duration for each animation                                            |

### Composables

#### LinearProgressBar

| Property name   | Description                                                               | Image                                                                                 |
|-----------------|---------------------------------------------------------------------------|---------------------------------------------------------------------------------------|
| progress        | A list of ProgressData class to be shown as a bar, see ProgressData class | <img height="100" src=".\manifest_res\doc\linearprogressbar\image1.gif" width="100"/> |
| stroke          | The thickness of all the bars                                             |                                                                                       |
| backgroundColor | The color of the bar at the back                                          |                                                                                       |
| insideCap       | The aspect of the caps not at the edges of the arc                        |                                                                                       |
| animationsSpecs | See AnimationSpecs class                                                  | -                                                                                     |
| animationEnded  | Called when the last animation has finished                               | -                                                                                     |

#### ArcProgressBar

| Property name | Description                                                                                          | Image |
|---------------|------------------------------------------------------------------------------------------------------|-------|
| progress      | A list of ProgressData class to be shown as a bar, see ProgressData class                            |       |
| radius        | Radius from the center, in dp                                                                        |       |
| stroke        | The thickness of all the arcs                                                                        |       |
| baseAngle     | The angle where the arc starts to draw, 0f is EAST and increasing the value will go counterclockwise |       |
| endAngle      | The angle where the arc stops, calculated from the base angle                                        |       |
| insideCap     | The aspect of the caps not at the edges of the arc                                                   |       |

## Install

The library is deployed with [jitpack](https://jitpack.io/#jojoyel/enhanced-progress) and ready to
use in your project, just follow these few steps :

settings.gradle

```gradle
dependencyResolutionManagement {
    ...
    repositories {
        ...
        maven { url 'https://jitpack.io' }       
    }
}
```

build.gradle (app)

```gradle
dependencies {
    implementation 'com.github.jojoyel:enhanced-progress:1.0.0'
}
```
