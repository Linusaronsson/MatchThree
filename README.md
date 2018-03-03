# MatchThree #

Programming assignment for course DAT055, Chalmers University of Technology.

*MatchThree* is a puzzle game about reordering tiles in a grid in order to
create chains. Score is rewarded upon successful chain creation. The game is
over when the move counter reaches zero, and any score accumulates is recorded
on the high score table.

## Building ##

To build the project, run `$ ./make.sh build`. The compiled program will be put
in the `target/main` directory. Use `$ ./make.sh run` to run it.

Documentation can be build using `$ ./make.sh doc`, and end up in `target/doc`.
Source files can be linted using
*[checkstyle](http://checkstyle.sourceforge.net/)* with `$ ./make.sh lint`. To
remove any built artifacts, use `$ ./make.sh clean` (not yet implemented).

Note that the build command does not have to be run from the project root, and
that arguments may be combined, for example: `$ ../make.sh lint build run`. In
this form, the arguments will run in sequence, and abort on failure.

## Directory structure ##

| Directory          | Purpose                   |
| :----------------- | :------------------------ |
| `doc/`             | Various documentation     |
| `libraries/`       | Run-time dependencies     |
| `resources/`       | Run-time assets           |
| `src/`             | Source code               |
| `src/controller/`  | Controllers               |
| `src/message/`     | Event messages            |
| `src/model/`       | Models                    |
| `src/multiplayer/` | Multiplayer (provisional) |
| `src/view/`        | Views                     |
| `src/util/`        | Utilities (provisional)   |
| `target/`          | Built artifacts           |
| `target/doc/`      | Compiled documentation    |
| `target/main/`     | Compiled program          |

## Authors ##

See [`AUTHORS`](AUTHORS).

## License ##

See [`LICENSE`](LICENSE).
