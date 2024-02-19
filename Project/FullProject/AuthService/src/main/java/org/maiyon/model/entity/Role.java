package org.maiyon.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.maiyon.model.enums.RoleName;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;
    @Enumerated(EnumType.STRING)
    private RoleName roleName;
}
