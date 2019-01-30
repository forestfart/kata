### Kata Repository

[![Build Status](https://travis-ci.org/forestfart/oopKata.svg?branch=master)](https://travis-ci.org/forestfart/oopKata)
[![codecov](https://codecov.io/gh/forestfart/oopKata/branch/master/graph/badge.svg)](https://codecov.io/gh/forestfart/oopKata)


Repository set up to practise TDD and Object Calisthenics (rules listed below)
- Only One Level Of Indentation Per Method
- Don’t Use The ELSE Keyword
- Wrap All Primitives And Strings
- First Class Collections
- One Dot Per Line
- Don’t Abbreviate
- Keep All Entities Small
- No Classes With More Than Two Instance Variables
- No Getters/Setters/Properties


### 1. The Lift Kata

Since lifts are everywhere and they contain software, how easy would it be to write a basic one? Let’s TDD a elevator, starting with simple behaviors and working toward complex ones. Assume good input from calling code and concentrate on the main flow.

##### Here are some suggested elevator features:

- a elevator responds to calls containing a source floor and direction
- a elevator has an attribute floor, which describes it’s current location
- a elevator delivers passengers to requested floors
- you may implement current floor monitor
- you may implement direction arrows
- you may implement doors (opening and closing)
- you may implement DING!
- there can be more than one elevator

Advanced Requirements
- a elevator does not respond immediately. consider options to simulate time, possibly a tick method.
- elevator calls are queued, and executed only as the elevator passes a floor

##### Objects Only 
Can you write a elevator that does not need to be queried? Try writing a elevator that only sends messages to other objects.

