package today.exusiai.event;

import java.lang.annotation.*;

@Documented
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ActiveEvent {
    byte value() default 2;
}
