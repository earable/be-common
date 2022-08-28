package ai.earable.platform.common.data.user.enums;

/**
 * @author Hungnv
 * @date 02/06/2022
 */
public enum Gender {
    MALE("male"), FEMALE("female"), OTHER("other");

    private String value;

    Gender(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
