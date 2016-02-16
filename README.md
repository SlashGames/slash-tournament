slash-tournament
================

Website for scheduling tournaments based on the [Play! Framework 2.2.1](https://www.playframework.com/).  Includes player authentication, participation management, game-specific rule sets, and different tournament formats and modes.

## Setup

1. Download and install [Play! Framework 2.2.1](https://www.playframework.com/download).
1. Verify Play! is correctly installed by typing `play help` on the command-line.
1. Copy the contents of this repository to a directory of your choice.
1. Add your database configuration data to conf/application.conf. You should put the password in double-quotes to avoid errors.
1. Add your mailer configuration to conf/application.conf.
1. Open TCP port 9000 in your firefall.

## Future Work

* super administration (adding administrators)
* second game (for verifying abstraction level)
* localization
