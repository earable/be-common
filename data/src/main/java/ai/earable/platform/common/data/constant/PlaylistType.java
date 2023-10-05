package ai.earable.platform.common.data.constant;

public enum PlaylistType {
    RECOVERY("RECOVERY"),
    FOCUS("FOCUS"),
    RELAX("RELAX"),
    VOICE_COACH_SLEEP("VOICE COACH (SLEEP)"),
    VOICE_COACH_FOCUS("VOICE COACH (FOCUS)"),
    VOICE_COACH_RELAX("VOICE COACH (RELAX)");

    private final String label;

    public String getLabel() {
        return label;
    }

    PlaylistType(String label) {
        this.label = label;
    }

    public static PlaylistType valueOfLabel(String label) {
        for (PlaylistType e : values()) {
            if (e.label.equals(label)) {
                return e;
            }
        }
        return null;
    }
}
