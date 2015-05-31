Java 8 Workshop
==============

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


    
