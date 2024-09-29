package paperJ.cutservice.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderRequestDto {
    private Long estimateId;
    private String contact;
    private String name;
}
