package entity.enumeration;

public enum ExpertStatusEnum {
    NEW("New"),
    AWAITING_APPROVAL("Awaiting Approval"),
    APPROVED("Approved");

    private final String displayName;

    ExpertStatusEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
