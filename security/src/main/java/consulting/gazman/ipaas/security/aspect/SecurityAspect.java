package consulting.gazman.ipaas.security.aspect;

import java.util.Optional;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import consulting.gazman.ipaas.security.annotations.RequiresPermission;
import consulting.gazman.ipaas.security.annotations.RequiresTenant;
import consulting.gazman.ipaas.security.service.SecurityService;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class SecurityAspect {
    private final SecurityService securityService;

    public SecurityAspect(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Around("@annotation(requiresPermission)")
    public Object checkPermission(ProceedingJoinPoint joinPoint, RequiresPermission requiresPermission) throws Throwable {
        String permission = buildPermissionString(requiresPermission, joinPoint);
        
        if (!securityService.hasPermission(permission)) {
            throw new AccessDeniedException("Missing required permission: " + permission);
        }
        
        return joinPoint.proceed();
    }

    @Around("@annotation(requiresTenant)")
    public Object checkTenant(ProceedingJoinPoint joinPoint, RequiresTenant requiresTenant) throws Throwable {
        String tenantId = extractTenantId(joinPoint);
        
        if (!securityService.isInTenant(tenantId)) {
            throw new AccessDeniedException("User not authorized for tenant: " + tenantId);
        }
        
        return joinPoint.proceed();
    }



    private String buildPermissionString(RequiresPermission annotation, JoinPoint joinPoint) {
        StringBuilder permission = new StringBuilder(annotation.value());
        
        if (!annotation.action().isEmpty()) {
            permission.append(":").append(annotation.action());
        }
        if (!annotation.resource().isEmpty()) {
            permission.append(":").append(annotation.resource());
        }
        
        return permission.toString();
    }

    private String extractTenantId(JoinPoint joinPoint) {
        // Extract tenantId from method parameters or request context
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
            .map(attrs -> attrs.getAttribute("tenantId", RequestAttributes.SCOPE_REQUEST))
            .map(Object::toString)
            .orElse(null);
    }
}