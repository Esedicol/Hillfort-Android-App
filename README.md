# Hillfort Mobile App
<br />

Youtube Link: https://www.youtube.com/watch?v=uAz4mHdSBok&t=4s
Video 2: https://drive.google.com/file/d/1WMrRIA3ti2cAbXLq9pKl7gOCKUZl8Q-R/view?usp=sharing

## Introduction

Develop an application supporting an exploration of Hillforts. The app should contain a list of hillforts they have
been assigned to visit and the app should allow users to manipulate every Hillforts. Users visit site, take photos + notes which
augment the information on the hillfort provided by the app. Location of each Hillfort will be manually inserted using Google Map on the
app and stamp the date visited.

## Instruction on how to use the app
- Once you open the app a splash screen will appear for 3 seconds before it redirects you to an authentication page
- You will get a choice of either signing in or register a new account
- Once logged in the app brings you the a page where hillforts are listed using Recycle View
- On the tool bar you have the option og adding a new hillfort, logout, go to account settings or go to the stats activity


## Main Features

### Adding Hillforts:

- add a description of the Hillfort
- add a log/lat pointers in google map
- add date of visit
- add multiple notes and images
- a check box to visually show if you have visited that Hillfort or not

### Account Settings:
- change email
- change password (only works by validating old password)

### Statistics to display:
- total number ot Hillforts
- total number of images uploaded
- total number of notes
- total of Hillforts Visited

All Data is currently stored locally in a json file but will implement a better method os persistance in the future.
