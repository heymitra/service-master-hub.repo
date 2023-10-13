package entity.enumeration;

public enum OrderStatusEnum {
    WAITING_FOR_OFFERS("Waiting for Offers"),
    WAITING_FOR_EXPERT_SELECTION("Waiting for Expert Selection"),
    WAITING_FOR_EXPERT_TO_COME("Waiting for the Expert to Come"),
    STARTED("Started"),
    DONE("Done"),
    PAID("Paid");

    private final String displayName;

    OrderStatusEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
