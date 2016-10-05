package de.dthomalla.foodrestrictor;

/**
 * Created by dthom on 04.10.2016.
 */
public enum DailyActivityState {
    EMPTY("E"),
    HALF("H"),
    FULL("F");

    private String mStringValue;

    DailyActivityState(String value){
        mStringValue = value;
    }

    @Override
    public String toString() {
        return mStringValue;
    }

    public int getValue() {
        switch (this){
            case EMPTY: return 0;
            case HALF: return 1;
            case FULL: return 2;
            default: return 0;
        }
    }

    public static DailyActivityState parseString(String value) {
        for(DailyActivityState v : values())
            if(v.toString().equalsIgnoreCase(value)) return v;
        throw new IllegalArgumentException();
    }

    public static DailyActivityState parseInt(int value) {
        for(DailyActivityState v : values())
            if(v.getValue() == value) return v;
        throw new IllegalArgumentException();
    }
}
