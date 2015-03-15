import java.lang.annotation.Repeatable;

/**
 * Annotation that tells when and why a change was made.
 */
@Repeatable(value = ChangeLog.class)
@interface Change {
    String date();
    String reason();
}
