package flp.model.report;

public enum StatType {

    REST,
    Legacy,
    RESTOnly;

    @Override
    public String toString() {
        switch (this) {
            case REST:
                return "REST API";
            case Legacy:
                return "SERVICE";
            case RESTOnly:
                return "REST API ONLY";
            default:
                throw new IllegalArgumentException();
        }
    }

}
