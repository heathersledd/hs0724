import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * This class represents a rental agreement for a checked out {@link Tool}.
 * An instance of this class is generated upon {@link Tool#checkout}.
 * It contains information about the rented tool, checkout date, rental duration, and discount percentage.
 */
public class RentalAgreement {

    private Tool tool;

    private LocalDate checkoutDate;

    private int rentalDays;

    private int discountPercentage;

    public RentalAgreement() {
    }

    public RentalAgreement(Tool tool, LocalDate checkoutDate, int rentalDays, int discountPercentage) {
        this.tool = tool;
        this.checkoutDate = checkoutDate;
        this.rentalDays = rentalDays;
        this.discountPercentage = discountPercentage;
    }

    public Tool getTool() {
        return tool;
    }

    public void setTool(Tool tool) {
        this.tool = tool;
    }

    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(LocalDate checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public int getRentalDays() {
        return rentalDays;
    }

    public void setRentalDays(int rentalDays) {
        this.rentalDays = rentalDays;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    // --- Calculation methods ---

    private LocalDate calcDueDate() {
        return getCheckoutDate().plusDays(getRentalDays());
    }

    private long calcChargeDays() {
        // Count of chargeable days, from day after checkout through and including due date
        // Excluding “no charge” days as specified by the tool type

        ToolType toolType = getTool().getToolType();
        boolean isWeekendCharge = toolType.isWeekendCharge();
        boolean isHolidayCharge = toolType.isHolidayCharge();

        LocalDate checkoutDate = getCheckoutDate();
        LocalDate startChargeDate = checkoutDate.plusDays(1);
        LocalDate endChargeDate = calcDueDate();
        long chargeDays = DAYS.between(checkoutDate, endChargeDate);

        // Take into account lack of weekend charge and/or holiday charge
        if (!isWeekendCharge) {
            chargeDays -= getWeekendDaysWithinDateRange(startChargeDate, endChargeDate);
        }

        if (!isHolidayCharge) {
            chargeDays -= getHolidaysWithinDateRange(startChargeDate, endChargeDate);
        }

        return chargeDays;
    }

    private BigDecimal calcPreDiscountCharge() {
        // Calculated from charge days * daily charge, rounded half-up to cents
        return Optional.ofNullable(getTool())
                .map(t -> t.getToolType().getDailyCharge().multiply(BigDecimal.valueOf(calcChargeDays())))
                .map(c -> c.setScale(2, RoundingMode.HALF_UP))
                .orElse(new BigDecimal("0.0"));
    }

    private BigDecimal calcDiscountAmount() {
        // Calculated from discount % * pre-discount charge, rounded half-up to cents
        return BigDecimal.valueOf(getDiscountPercentage() * 0.01).multiply(calcPreDiscountCharge()).setScale(2, RoundingMode.HALF_DOWN);
    }

    // Make public so we can test
    public BigDecimal calcFinalCharge() {
        // Calculated from pre discount charge - discount amount
        return calcPreDiscountCharge().subtract(calcDiscountAmount());
    }

    // --- Utility methods ---

    private int getHolidaysWithinDateRange(LocalDate startDate, LocalDate endDate) {
        int holidayCount = 0;
        int year = startDate.getYear();

        // Note: this only works if the checkout date is the same year as the holiday you're checking for
        // E.g. an extended checkout period like Dec 1, 2020 - July 8, 2021 breaks this
        LocalDate fourthOfJulyObserved = LocalDate.of(year, 7, 4);

        // Adjust July 4 to be observed on nearest weekday if it lands on a weekend
        if (fourthOfJulyObserved.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
            fourthOfJulyObserved = fourthOfJulyObserved.minusDays(1);
        }
        if (fourthOfJulyObserved.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            fourthOfJulyObserved = fourthOfJulyObserved.plusDays(1);
        }

        // Check if July 4 (observed) is in the date range
        if (!(fourthOfJulyObserved.isBefore(startDate) || fourthOfJulyObserved.isAfter(endDate))) {
            holidayCount++;
        }

        // Check if Labor Day (first Monday in September) is in the date range
        LocalDate laborDay = LocalDate.of(year, 9, 1).with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));
        if (!(laborDay.isBefore(startDate) || laborDay.isAfter(endDate))) {
            holidayCount++;
        }

        return holidayCount;
    }

    private long getWeekendDaysWithinDateRange(LocalDate startDate, LocalDate endDate) {
        return startDate.datesUntil(endDate)
                .map(LocalDate::getDayOfWeek)
                .filter(day -> Arrays.asList(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY).contains(day))
                .count();
    }

    @Override
    public String toString() {
        // Construct string with full rental agreement details
        StringBuilder builder = new StringBuilder();

        Tool tool = getTool();
        ToolType toolType = tool.getToolType();

        // Currency and date formatters
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyy");

        builder.append("Tool code: ").append(tool.getToolCode()).append("\n");
        builder.append("Tool type: ").append(toolType).append("\n");
        builder.append("Tool brand: ").append(tool.getBrand()).append("\n");
        builder.append("Rental days: ").append(rentalDays).append("\n");
        builder.append("Checkout date: ").append(dateFormatter.format(checkoutDate)).append("\n");
        builder.append("Due date: ").append(dateFormatter.format(calcDueDate())).append("\n");
        builder.append("Daily rental charge: ").append(currencyFormatter.format(toolType.getDailyCharge())).append("\n");
        builder.append("Charge days: ").append(calcChargeDays()).append("\n");
        builder.append("Pre-discount charge: ").append(currencyFormatter.format(calcPreDiscountCharge())).append("\n");
        builder.append("Discount percent: ").append(discountPercentage).append("%").append("\n");
        builder.append("Discount amount: ").append(currencyFormatter.format(calcDiscountAmount())).append("\n");
        builder.append("Final charge: ").append(currencyFormatter.format(calcFinalCharge())).append("\n");

        return builder.toString();
    }
}
