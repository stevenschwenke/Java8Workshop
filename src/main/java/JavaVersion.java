import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Annotation that tells the Java version.
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.CONSTRUCTOR, ElementType
        .LOCAL_VARIABLE, ElementType.ANNOTATION_TYPE, ElementType.PACKAGE, ElementType.TYPE_PARAMETER, ElementType
        .TYPE_USE}) // The last two are since 1.8!
@interface JavaVersion {

    String value();

}
