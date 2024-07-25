# hs0724

**Author**: Heather Sledd

**Completed**: Jul 24, 2024

**Notes**:

 1. ToolType for the time being is an enum since there were predefined instances laid out. In a more robust system, perhaps one where the types and tools themselves are managed, this would probably have been an interface with concrete types implementing it.
 2. I didn't add all of the defined values for RentalAgreement to the class as fields since most of them were derivative. For this same reason, I only tested certain values - charge days, discount amount, and final charge.
 3. I made most of the calculation methods private because in this particular use case, there were no usage outside of the class, but in a different environment where potentially those values may need to be publicly available, I might have made those calculated values fields or public methods instead.
 4. Apologies for the wonky commit pattern. I also did the bulk of the work in the first commit - ideally that would have been separated out into smaller chunks. Changes to the README were made directly from the main branch. But any code changes I did against a develop branch as it just felt sacrilege to commit straight to main. I ended up needing 3 separate PRs since I would close one and decide more changes needed to be made.
