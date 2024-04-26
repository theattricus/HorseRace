# Horse Race
## Simple horse race program written in Java, GUI made in Swing.

Part 1 issues can be found in Part1/Part1.txt, these have all been fixed in the code.
You can run Part1, which has no graphics, by `cd Part1 && javac *.java && java Part1`
For part 2, it is easiest to install Gradle. Find instructions at https://gradle.org/install#manually. Once setup in path, you can use `gradle run` from within the Part2 folder (`cd Part2`) to run it.
This program uses static resources for the horse (breed) and saddle images. These can be found under Part2/app/main/java/resources, folders `horses` and `saddles` respectively. As these are static, once you add more/ edit these files, recompile (can be done with `gradle run` too) to see it in effect - the program is designed to dynamically find these.
Also the saddles are dynamically placed on the horse - the RGBA(255,0,255,255) pixel (ideally only one found in the image, but will find first in image starting from top-left corner) of the saddle is mapped to the matching pixel in the horse.

Current features:
- Statistics
- Create horses
- Save races
- Save horses
- Terminal output
- Graphical output

Betting functionality still in WIP.