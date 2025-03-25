# 2005 Report

GitHub Repo: [https://github.com/bobbymannino/comp2005-report](https://github.com/bobbymannino/comp2005-report)

<!-- TODO add youtube video -->
YouTube Video: [http://youtu.be](https://youtu.be)

## Introduction

I have been tasked with creating an API that interacts with a predefined APi,
and an application that interacts with my API. Along with this I need to test
(and document the tests) that everything works using multiple testing methods.

### API Routes

- [Patients with multiple staff](http://localhost:8080/patients/multi-staff)
- [Readmitted patients within 7 days of release](http://localhost:8080/admissions/re)
- [Patients who have never been admitted](http://localhost:8080/admissions/never)
- [Month with the most admissions](http://localhost:8080/admissions/most)

## Test Plan

### Units Tests

---

### API

- [x] Endpoints
  - [x] Never admitted patients
  - [x] Readmitted patients within 7 days
  - [x] Most admitted patients month
  - [x] Patients who have had more then 1 staff
- [ ] Tests
  - [ ] Unit tests
  - [ ] Integration testing
  - [ ] System tests
  - [ ] Automate tests

### App

- [x] Windows
  - [x] Main menu
  - [x] Never admitted patients
  - [x] Patient details
- [ ] Alert (popup) when error happens (api service down, parse error, etc)
- [ ] Tests
  - [ ] Unit tests
  - [ ] Integration testing
  - [ ] System tests
  - [ ] Automate tests
  - [ ] Usability tests
    - [ ] Make changes based on response

### Demo

- [ ] API
- [ ] App
- [ ] Tests

### Docs

- [ ] Write report
  - [ ] Intro
  - [ ] Test plan
  - [ ] Table of test types and examples
  - [ ] Instructions on how to run tests/programs
  - [ ] Evaluation
- [ ] YouTube video

---

## Notes

- use [JISC](https://onlinesurverys.ac.uk) for questionnaires
- mention static testing (linters) and dynamic (unit, etc...)
- box testing (black, gray, white)
- code coverage
- edge/corner cases
- unit -> integration -> system -> usability
  - system testing: make sure its as close to real use case as possible
    (environment)
- mention about the port, what if it is in use?
- handle error if service is down, what to show user?

### Cognitive walkthrough

1. is the user on the right track
2. is step visible (next action)
3. is it labeled well
4. feedback

### Test Plan

- objective of test
- scope of test, what is to be included/not
- how long should the test last (in seconds/minutes)
- what type of test is it
- manual/automatic (github actions)
- environment? is it mac, windows, browser, chrome, webkit?
- assumptions
- notes
- measure speed?
  - if stress testing, how much slower does it get?
