<h1 align="center">AdBlockerDetector</h1>
<h4 align="center">Android Library</h4>

Many ad blockers exist on Android, this is a real problem for developers that rely on ad incomes.

This project proposes an open source library that can detect most of ad blockers.

Then developers can display a dialog to inform user that an ad blocker has been detected and to propose, for example, to buy an ad-free version of the application or to quit.

# Details
Currently, the following methods are used to detect ad blockers:
  * Search for known ad blockers application package names
  * Resolve known ad server domains and check if it redirects to a local address (work for both DNS & hosts file modification)
  * Check in hosts file for known patterns (work for hosts file modification)

Detection methods will not give false positive but may not detect some ad blockers.

This library will be improved regularly to ensure a maximum ad blocker detection rate.

# Usage
Add dependency and plugin to project level build.gradle.
```
dependencies {
    compile 'com.cuneytayyildiz:adblockerdetector:1.0.0'
}
```

# Simple example
```
public void checkAdBlocker()
{
    // Asynchronous detection in a background thread
    new AdBlockerDetector(this).detectAdBlockers(new Constants.AdBlockerCallback()
    {
        @Override
        public void onResult(boolean adBlockerFound, Info info)
        {
            if(adBlockerFound)
            {
              // Show your information dialog here to user
            }
        }   
    });
}
```
The method checkAdBlocker() should be called in an Handler, by a delayed message sent in onCreate().

# Licence
```
The MIT License (MIT)

Copyright (c) 2016 Cüneyt Ayyıldız

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
`
