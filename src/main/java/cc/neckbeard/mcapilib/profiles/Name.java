package cc.neckbeard.mcapilib.profiles;

public class Name {

    public final String name;
    public final String changedToAt;

    public Name(String name, String changedToAt) {
        this.name = name;
        this.changedToAt = changedToAt;
    }

    public boolean isCurrent() {
        return changedToAt == null;
    }

}
