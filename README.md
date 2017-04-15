# Canvas

A small app that let's you try out the palette api on different images and apply the colours to the status bar.

## Motivation

This idea came to me when there was a need to come up with a colour for the status bar when the background has a predominant image.

At the time of creation I was quite new to kotlin and decided would be quite nice to also give it a try.

This might not be the most koltin-ish project you'll fine out there, but I'm quite pleased with the end result.

## How does it work?

You put images under ``app/src/main/assets/backgrounds`` and build the app. The app will load the images into a viewpager.
 
You can scroll through the several images. On phones at the bottom of the screen there'll be an expandable list with all the colours created with the Palette api for the current displayed image.

When you click on a colour it'll be set as the status bar colour.
