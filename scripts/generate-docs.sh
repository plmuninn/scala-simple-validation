#!/bin/bash -e

scala-cli fmt documentation/.
scala-cli run documentation/. --main-class documentation_sc