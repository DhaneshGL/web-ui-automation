# Storefront UI Regression Test Plan

## Objective and scope

Validate the local Northstar Supply demo storefront's catalog, search, cart,
and demo checkout flows. This is a QA exercise against a static local fixture;
it does not process orders or access customer data.

## Test approach

- **Levels/types:** functional, smoke, sanity, UI, integration-style browser
  flow, regression, negative, and exploratory checks.
- **Process:** review acceptance criteria, design cases and traceability,
  prepare deterministic data, execute in Chrome, record defects with
  reproduction/evidence, retest fixes, and publish a test summary.
- **Automation:** Selenium WebDriver with Java and TestNG; Maven Surefire is the
  runner and report producer. Page interactions are grouped in a small page
  object.
- **Manual/exploratory ideas:** keyboard-only navigation, narrow viewport,
  repeated search edits, browser refresh, long names, invalid email, and
  boundary quantities/prices.

## Entry and exit criteria

- Entry: Java 17+, Maven, Chrome, and the fixture are available.
- Exit: all six automated cases pass; no open critical/high-severity defects;
  known limitations and any skipped checks are recorded.

## Risks and assumptions

- The demo intentionally uses static client-side data and has no real backend,
  authentication, payment, persistence, or cross-browser guarantee.
- Selenium Manager may need network access to acquire ChromeDriver.
- Styling uses local system-font fallbacks; no external font service is needed.

## Execution and defect process

Use the case IDs in `test-cases.csv` and link each run to the relevant
requirement in `traceability.csv`. Record actual build, date, browser, result,
and evidence path in the summary template. For defects, capture environment,
steps, expected/actual result, severity, priority, evidence, status, and
retest. A sample defect lifecycle is documented separately.

## Roles and tools

QA owner: portfolio author. Tools: Chrome DevTools, Selenium, TestNG, Maven,
Git/GitHub, and optional Jira/Zephyr/Confluence/Jenkins. These optional
services are not dependencies.
