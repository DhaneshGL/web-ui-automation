# Illustrative Defect Record

This is a fictional example for practicing Jira defect reporting. It is not a
finding from a real storefront.

- **Key:** DEMO-UI-001
- **Title:** Cart total does not reset after successful demo checkout
- **Environment:** Northstar Supply local fixture, Chrome, desktop viewport
- **Severity / priority:** Major / High (example)
- **Status:** Closed (example lifecycle)
- **Precondition:** Add one item to the bag.
- **Steps:** Enter a valid name and email, then select **Place demo order**.
- **Expected:** Confirmation is shown; bag quantity and total reset to zero.
- **Illustrative actual:** Confirmation is shown, but the prior total remains.
- **Reproducibility:** 5/5 (illustrative)
- **Evidence:** Attach a screenshot and the browser console/test output when
  using this example in a practice Jira project.
- **Lifecycle:** Open → Triaged → In Progress → Resolved → Retest Passed →
  Closed.

The current fixture clears the cart after a valid demo checkout; this record is
historical practice data, not an open defect in the supplied test target.
