# Todo #

## Bugs ##
+ Leaving a multiplayer game may leave the main menu in a broken state.

## Code quality ##
+ Add generic 2D matrix class (`board`).
+ Avoid initializing variables to Java's defaults?
+ Canonicalize code style.
+ Limit error-checking to public methods, and use assert otherwise.
+ Reduce amount of class fields.
+ Sort methods and fields.

## Correctness ##
+ Use `clone` where appropriate.
+ Use sets instead of arrays in `MatchThreeModel`?

## Documentation ##
+ Clarify documentation in some areas.
+ Fill in missing documentation.

## Features ##
+ Add "Load" main menu screen.
+ Add "Settings" main menu screen.
+ Score counter (number of matches and of what types they were).

## Performance ##
+ Improve application launch and quit times.
+ Improve board update efficiency in multiplayer mode.
+ Improve board update performance.

## Portability ##
+ Improve native platform support (primarily macOS/OS X).

## Structure ##
+ Avoid excessive nesting of view controllers. Try to have views create other
  views instead and use message passing or like for cross-layer communication.
+ Consider packaging after application feature when suitable.
