package com.bitorax.priziq.domain.activity.slide;

import com.bitorax.priziq.domain.BaseEntity;
import com.bitorax.priziq.domain.activity.Activity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "slides")
public class Slide extends BaseEntity {
    @Id
    String slideId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "activity_id", nullable = false)
    @JsonIgnore
    Activity activity;

    @OneToMany(mappedBy = "slide", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    List<SlideElement> slideElements;

    String transitionEffect;

    @Builder.Default
    BigDecimal transitionDuration = BigDecimal.ONE;

    @Column(nullable = false)
    @Builder.Default
    Integer autoAdvanceSeconds = 0;
}
