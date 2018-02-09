# MatchThree #

Programming assignment for course DAT055, Chalmers University of Technology.

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

| Directory         | Purpose                |
| :---------------- | :--------------------- |
| `resource/`       | Run-time assets        |
| `src/`            | Source code            |
| `src/Controller/` | Controllers            |
| `src/GameModes/`  | Game modes             |
| `src/Model/`      | Models                 |
| `src/View/`       | Views                  |
| `target/`         | Built artifacts        |
| `target/doc`      | Compiled documentation |
| `target/main`     | Compiled program       |

## Authors ##

See [`AUTHORS`](AUTHORS).

## License ##

See [`LICENSE`](LICENSE).
