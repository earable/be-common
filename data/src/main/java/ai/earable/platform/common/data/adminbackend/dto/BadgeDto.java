package ai.earable.platform.common.data.adminbackend.dto;

import ai.earable.platform.common.data.adminbackend.BenefitType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BadgeDto {
    private  String badgeId;
    private String title;
    private String description;
    private String icon;
    private Double benefitValue;
    private BenefitType benefitType;
    private LocalDate createAt;
}
