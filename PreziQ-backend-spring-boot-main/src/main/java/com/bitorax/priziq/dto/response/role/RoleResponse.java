package com.bitorax.priziq.dto.response.role;

import com.bitorax.priziq.dto.response.common.AuditResponse;
import com.bitorax.priziq.dto.response.permission.PermissionResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleResponse extends AuditResponse {
    String roleId;
    String name;
    String description;
    Boolean active;
    List<PermissionResponse> permissions;
}
