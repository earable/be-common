package ai.earable.platform.common.data.session;

public enum DeviceUpdateStatus {
    UPDATED_FROM_FILE("Cập nhật từ file"),
    UPDATED_FROM_APP("Cập nhật S/N FW"),
    VERIFIED("Đã kiểm tra"),;

    private final String label;

    public String getLabel() {
        return label;
    }

    DeviceUpdateStatus(String label) {
        this.label = label;
    }

    public static DeviceUpdateStatus valueOfLabel(String label) {
        for (DeviceUpdateStatus e : values()) {
            if (e.label.equals(label)) {
                return e;
            }
        }
        return null;
    }
}
