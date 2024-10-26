package consulting.gazman.ipaas.security.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PreAuthorize;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("@securityService.hasPermission(#root.target.class.name + '_' + #root.method.name)")
public @interface RequiresPermission {
    String value();
    String action() default "";
    String resource() default "";
}