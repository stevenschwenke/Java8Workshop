Java 8 Workshop
==============

[![Build Status](https://api.shippable.com/projects/5593645bedd7f2c0524cb0ba/badge/master)](https://app.shippable.com/projects/5593645bedd7f2c0524cb0ba/builds/latest)

Meta: About the workshop
--------------------------
The workshop is an internal event for my coworkers. Target audience are experienced Java developers who are 
interested in the new features of Java 8 (all updates included). Goals of the workshop are

- having a fair overview over the new Java 8 features
- having a set of code examples to use as templates
- having a better knowledge about what Java version brings which features - however focus of this course is Java 8!

How to use this
-----------------
This repository is supposed to be a code base for own experiments and a reference to go to while writing own code.
Hence, it is not necessary to vocally explain every line of code to the participants. 

Content
---------
0. concepts of Java 1.5: Generics, for-each-loop and varargs
1. functional interfaces and lambdas + exercises
2. default methods
3. method references + exercises
4. streams + exercises
5. Classifying streams
6. Date and Time API + exercises
7. Concurrency
8. Annotations
9. JavaFX
10. Other stuff
11. MissionControl
12. Outlook

What this course does NOT cover:

- Deep insight into concurrency. In fact, this topic is only scratched on the surface.
- Foundation of JavaFX. Go to https://github.com/stevenschwenke/JavaFXWorkshop for that one.
 

The workshop is based on unit tests that have a lot of comments to explain what's going on. This way, 
everyone can go through the workshop alone and without help. Each unit test class covers one aspect of the new 
features of Java 8. Feel free to pick one and read through the file. I recommend to read the tests from top to 
bottom because they depend on each other. Yeah I know, that's not what unit tests are for. But I don't care. ;)
 
 **Attention: There are hideous unit tests that do weird things such as not terminating. Please don't run all unit 
 tests at once and expect them to be green.** 

Feel free to give feedback to steven@stevenschwenke.de

Meta: Copyright
----------------
All files in this repository are under Creative Commons 4.0 (see http://creativecommons.org/licenses/by/4.0/). 

You are free to:

- Share — copy and redistribute the material in any medium or format
- Adapt — remix, transform, and build upon the material for any purpose, even commercially.

The licensor cannot revoke these freedoms as long as you follow the license terms.

Under the following terms:

- Attribution — You must give appropriate credit, provide a link to the license, and indicate if changes were made. You may do so in any reasonable manner, but not in any way that suggests the licensor endorses you or your use.
- No additional restrictions — You may not apply legal terms or technological measures that legally restrict others from doing anything the license permits.


    
