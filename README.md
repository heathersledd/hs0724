# hs0724

**Author**: Heather Sledd

**Completed**: Jul 24, 2024

**Notes**:

 1. ToolType for the time being is an enum since there were predefined instances laid out. In a more robust system, perhaps one where the types and tools themselves are managed, this would probably have been an interface with concrete types implementing it.
 2. I didn't add all of the defined values for RentalAgreement to the class as fields since most of them were derivative. For this same reason, I only tested certain values - charge days, discount amount, and final charge.
 3. I made most of the calculation methods private because in this particular use case, there were no usage outside of the class, but in a different environment where potentially those values may need to be publicly available, I might have made those calculated values fields or public methods instead.
