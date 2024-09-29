package paperJ.cutservice.domain;

public enum OrderStatus {
    ORDER_RECEIVED("접수완료"),
    ORDER_CHECKING("주문 확인중"),
    ORDER_COMPLETED("주문완료"),
    CUTTING_PREPARING("재단 준비중"),
    DELIVERY_PREPARING("배송 준비중"),
    DELIVERY("배송중"),
    DELIVERY_COMPLETED("배송완료");

    private final String koreanName;

    OrderStatus(String koreanName) {
        this.koreanName = koreanName;
    }

    public String getKoreanName() {
        return koreanName;
    }
}
