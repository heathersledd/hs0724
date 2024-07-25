import java.time.LocalDate;

/**
 * This class represents a Tool instance that can be checked out.
 * Each tool has a corresponding tool code, tool type, and brand.
 */
public class Tool {

    private String toolCode;

    private ToolType toolType;

    Brand brand;

    public String getToolCode() {
        return toolCode;
    }

    public void setToolCode(String toolCode) {
        this.toolCode = toolCode;
    }

    public ToolType getToolType() {
        return toolType;
    }

    public void setToolType(ToolType toolType) {
        this.toolType = toolType;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Tool() {
        // Default constructor
    }

    public Tool(String toolCode, ToolType toolType, Brand brand) {
        this.toolCode = toolCode;
        this.toolType = toolType;
        this.brand = brand;
    }

    /**
     * Generates a {@link RentalAgreement} with details of the tool checkout.
     * Will throw an exception if the rental day count is less than 1 or
     * if the discount percent is not a number 0-100.
     *
     * @param checkoutDate Date the tool is to be checked out
     * @param rentalDayCount Amount of days from the day after checkout that the tool is to be rented
     * @param discountPercent Discount percent represented as a number 0-100
     * @return A rental agreement from the attributes provided; non-null
     * @throws IllegalArgumentException
     */
    public RentalAgreement checkout(LocalDate checkoutDate, int rentalDayCount, int discountPercent) throws IllegalArgumentException {
        if (rentalDayCount < 1) {
            throw new IllegalArgumentException("Rental day count must be greater than 0");
        }

        if (discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException("Discount percent must be between 0 and 100");
        }

        // NOTE: instead of passing tool code, I just pass the whole tool object
        // so other tool fields can be accessed more seamlessly from the rental agreement.
        return new RentalAgreement(
                this,
                checkoutDate,
                rentalDayCount,
                discountPercent);
    }
}
