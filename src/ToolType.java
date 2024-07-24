import java.math.BigDecimal;

/**
 * An enum representing different tool types.
 * Each tool type has an associated daily rental charge,
 * and information regarding whether the charge applies on the weekends or during defined holidays.
 */
public enum ToolType {

    LADDER (new BigDecimal("1.99"), true, false),
    CHAINSAW (new BigDecimal("1.49"), false, true),
    JACKHAMMER (new BigDecimal("2.99"), false, false);

    private final BigDecimal dailyCharge;

    private final boolean isWeekendCharge;

    private final boolean isHolidayCharge;

    ToolType(BigDecimal dailyCharge, boolean isWeekendCharge, boolean isHolidayCharge) {
        this.dailyCharge = dailyCharge;
        this.isWeekendCharge = isWeekendCharge;
        this.isHolidayCharge = isHolidayCharge;
    }

    public BigDecimal getDailyCharge() {
        return dailyCharge;
    }

    public boolean isWeekendCharge() {
        return isWeekendCharge;
    }

    public boolean isHolidayCharge() {
        return isHolidayCharge;
    }

    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}
