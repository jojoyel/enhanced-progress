# Enhanced Progress library

*Current version : **1.0.0***
<hr />

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

## Install

First, download the .aar file in
the [releases page](https://github.com/jojoyel/EnhancedProgress/releases) and pick the version you
want. Then move it in your project by opening the *project structure* [**
Ctrl+Alt+Shift+S** (by default)] and open *
Dependencies tab*, under *All dependencies* select *JAR/AAR Dependency*
![](.\manifest_res\install_1.png "Install info")
Fill the path to the file and click *OK*, all set to go !