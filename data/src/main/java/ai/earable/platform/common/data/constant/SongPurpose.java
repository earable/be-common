package ai.earable.platform.common.data.constant;

public enum SongPurpose {
    BACKGROUND_SLEEP("Background (Sleep)"),
    VOICE_COACH_SLEEP("Voice coach (Sleep)"),
    BACKGROUND_FOCUS("Background (Focus)"),
    VOICE_COACH_FOCUS("Voice coach (Focus)"),
    BACKGROUND_RELAX("Background (Relax)"),
    VOICE_COACH_RELAX("Voice coach (Relax)");

    private final String label;

    public String getLabel() {
        return label;
    }

    SongPurpose(String label) {
        this.label = label;
    }

    public static SongPurpose valueOfLabel(String label) {
        for (SongPurpose e : values()) {
            if (e.label.equals(label)) {
                return e;
            }
        }
        return null;
    }
}
