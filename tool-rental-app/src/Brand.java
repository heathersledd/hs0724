/**
 * An enum representing different options for tool brands.
 */
public enum Brand {

    STIHL("Stihl"),
    WERNER("Werner"),
    DEWALT("DeWalt"),
    RIDGID("Ridgid");

    private final String brandName;

    // Constructor
    Brand(String brandName) {
        this.brandName = brandName;
    }

    // Getter method
    public String getBrandName() {
        return brandName;
    }

    @Override
    public String toString() {
        return brandName;
    }
}
