import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * A test suite to prove the correctness of the tool rental application.
 */
public class TestToolRental {

    private final List<Tool> tools = new ArrayList<>();

    /**
     * Initializes list of tools before conducting tests.
     */
    @Before
    public void initializeTools() {
        tools.add(new Tool("CHNS", ToolType.CHAINSAW, Brand.STIHL));
        tools.add(new Tool("LADW", ToolType.LADDER, Brand.WERNER));
        tools.add(new Tool("JAKD", ToolType.JACKHAMMER, Brand.DEWALT));
        tools.add(new Tool("JAKR", ToolType.JACKHAMMER, Brand.RIDGID));
    }

    /**
     * Asserts while attempting to call Tool#checkout with discount percentage of 101,
     * an error is thrown as expected.
     * Asserts that the error message thrown is equivalent to the expected string regarding invalid discount percentage.
     */
    @Test
    public void testInvalidDiscountPercent() {
        Tool tool = tools.get(3);
        IllegalArgumentException thrown = Assert.assertThrows(
                IllegalArgumentException.class,
                () -> tool.checkout(LocalDate.of(2015, 9, 3), 5, 101));

        Assert.assertEquals("Discount percent must be between 0 and 100", thrown.getMessage());
    }

    /**
     * Asserts when checking out a Ladder from 7/2/20 for 3 days at a 10% discount
     * the final charge is the expected value of $3.58
     */
    @Test
    public void testLadderWithHolidayAndDiscount() {
        Tool tool = tools.get(1);
        RentalAgreement rentalAgreement = tool.checkout(LocalDate.of(2020, 7, 2), 3, 10);
        System.out.println(rentalAgreement);

        Assert.assertEquals(rentalAgreement.getChargeDays(), 2);
        Assert.assertEquals(rentalAgreement.calcDiscountAmount(), new BigDecimal("0.40"));
        Assert.assertEquals(rentalAgreement.getFinalCharge(), new BigDecimal("3.58"));
    }

    /**
     * Asserts when checking out a Chainsaw from 7/2/15 for 5 days at a 25% discount
     * the final charge is the expected value of $3.35
     */
    @Test
    public void testChainsawWithHolidayAndDiscount() {
        Tool tool = tools.getFirst();
        RentalAgreement rentalAgreement = tool.checkout(LocalDate.of(2015, 7, 2), 5, 25);
        System.out.println(rentalAgreement);

        Assert.assertEquals(rentalAgreement.getChargeDays(), 3);
        Assert.assertEquals(rentalAgreement.calcDiscountAmount(), new BigDecimal("1.12"));
        Assert.assertEquals(rentalAgreement.getFinalCharge(), new BigDecimal("3.35"));
    }

    /**
     * Asserts when checking out a Jackhammer (DeWalt) from 9/3/15 for 6 days at no discount
     * the final charge is the expected value of $8.97
     */
    @Test
    public void testJackhammerDWithHolidayNoDiscount() {
        Tool tool = tools.get(2);
        RentalAgreement rentalAgreement = tool.checkout(LocalDate.of(2015, 9, 3), 6, 0);
        System.out.println(rentalAgreement);

        Assert.assertEquals(rentalAgreement.getChargeDays(), 3);
        Assert.assertEquals(rentalAgreement.calcDiscountAmount(), new BigDecimal("0.00"));
        Assert.assertEquals(rentalAgreement.getFinalCharge(), new BigDecimal("8.97"));
    }

    /**
     * Asserts when checking out a Jackhammer (Ridgid) from 7/2/15 for 9 days at no discount
     * the final charge is the expected value of $17.94
     */
    @Test
    public void testJackhammerRWithHolidayNoDiscount() {
        Tool tool = tools.get(3);
        RentalAgreement rentalAgreement = tool.checkout(LocalDate.of(2015, 7, 2), 9, 0);
        System.out.println(rentalAgreement);

        Assert.assertEquals(rentalAgreement.getChargeDays(), 6);
        Assert.assertEquals(rentalAgreement.calcDiscountAmount(), new BigDecimal("0.00"));
        Assert.assertEquals(rentalAgreement.getFinalCharge(), new BigDecimal("17.94"));
    }

    /**
     * Asserts when checking out a Jackhammer (Ridgid) from 7/2/20 for 4 days at a 50% discount
     * the final charge is the expected value of $1.50
     */
    @Test
    public void testJackhammerRWithHolidayAndDiscount() {
        Tool tool = tools.get(3);
        RentalAgreement rentalAgreement = tool.checkout(LocalDate.of(2020, 7, 2), 4, 50);
        System.out.println(rentalAgreement);

        Assert.assertEquals(rentalAgreement.getChargeDays(), 1);
        Assert.assertEquals(rentalAgreement.calcDiscountAmount(), new BigDecimal("1.49"));
        Assert.assertEquals(rentalAgreement.getFinalCharge(), new BigDecimal("1.50"));
    }
}
