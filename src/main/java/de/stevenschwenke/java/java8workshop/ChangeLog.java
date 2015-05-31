package de.stevenschwenke.java.java8workshop;

/**
 * Container-Annotation to make {@link Change} repeatable.
 */
@interface ChangeLog {
    Change[] value();
}
