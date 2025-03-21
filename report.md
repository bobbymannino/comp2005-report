# 2005 Report

Name: Bobby Mannino

GitHub Repo: [https://github.com/bobbymannino/comp2005-report](https://github.com/bobbymannino/comp2005-report)

YouTube Video: [youtube-video-here](https://youtu.be)

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

### Integration Tests

### Functional Tests

### Usability Tests

## Evaluation

---

_DELETE NOTES BEFORE SUBMITTING_

## Notes

- Me and sir spoke about weather to return objects or patient ids from each
  endpoint, we decided that being consistent matters more, so as long as im
  consistent, it doesn't matter
- use [JISC](https://onlinesurverys.ac.uk) for questionnaires
- use AAA, assert, act, arrange
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

1) is the user on the right track
2) is step visible (next action)
3) is it labeled well
4) feedback

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
