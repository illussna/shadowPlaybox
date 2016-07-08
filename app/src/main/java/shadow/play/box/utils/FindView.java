package shadow.play.box.utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

@Retention(RetentionPolicy.RUNTIME) @Target(FIELD)
public @interface FindView {
  /** View ID to which the field will be bound. */
  int id();
  String onClick() default "";
  String onLongClick() default "";
  String onTouchEvent() default "";
  String onKeyListener() default "";
}
