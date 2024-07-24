import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * A test suite to prove the correctness of the tool rental application.
 */
public class TestToolRental {

    private final List<Tool> tools = new ArrayList<>();

    /**
     * Initialize list of tools before conducting tests.
     */
    @Before
    public void initializeTools() {
        tools.add(new Tool("CHNS", ToolType.CHAINSAW, Brand.STIHL));
        tools.add(new Tool("LADW", ToolType.LADDER, Brand.WERNER));
        tools.add(new Tool("JAKD", ToolType.JACKHAMMER, Brand.DEWALT));
        tools.add(new Tool("JAKR", ToolType.JACKHAMMER, Brand.RIDGID));
    }

    /**
     * Assert that while attempting to call Tool#checkout with discount percentage of 101,
     * an error is thrown as expected.
     * Assert that the error message thrown is equivalent to the expected string regarding invalid discount percentage.
     */
    @Test
    public void testInvalidDiscountPercent() {
        Tool tool = tools.get(3);
        IllegalArgumentException thrown = Assert.assertThrows(
                IllegalArgumentException.class,
                () -> tool.checkout(LocalDate.of(2015, 9, 3), 5, 101));

        Assert.assertEquals("Discount percent must be between 0 and 100", thrown.getMessage());
    }

    @Test
    public void test2() {
        Tool tool = tools.get(1);
        RentalAgreement rentalAgreement = tool.checkout(LocalDate.of(2020, 7, 2), 3, 10);
        System.out.println(rentalAgreement);
    }

    @Test
    public void test3() {
        Tool tool = tools.getFirst();
        RentalAgreement rentalAgreement = tool.checkout(LocalDate.of(2015, 7, 2), 5, 25);
        System.out.println(rentalAgreement);
    }

    @Test
    public void test4() {
        Tool tool = tools.get(2);
        RentalAgreement rentalAgreement = tool.checkout(LocalDate.of(2015, 9, 3), 6, 0);
        System.out.println(rentalAgreement);
    }

    @Test
    public void testJackhammerRWithHoliday() {
        Tool tool = tools.get(3);
        RentalAgreement rentalAgreement = tool.checkout(LocalDate.of(2015, 7, 2), 9, 0);
        System.out.println(rentalAgreement);
    }

    @Test
    public void testJackhammerRWithHolidayAndDiscount() {
        Tool tool = tools.get(3);
        RentalAgreement rentalAgreement = tool.checkout(LocalDate.of(2020, 7, 2), 4, 50);
        System.out.println(rentalAgreement);
    }
}
