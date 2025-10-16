# 🧪 TestTube

![Built with Grind](https://img.shields.io/badge/Built%20with-Grind-orange?style=for-the-badge&logo=rust)
![Java](https://img.shields.io/badge/Langauge-Java-blue?style=for-the-badge&logo=openjdk)

The new [Grind](https://github.com/AnharHussainMiah/grind/) Java build tool that I've been developing requires a test runner. In a twist of poetic serendipity, what better way to build this test runner than using the very tool itself!

`TestTube` is a small Java CLI to be used by the `grind` Java build tool as the main test runner. `TestTube` leverages the existing JUnit framework API.

## How to compile and Run

first make sure all the dependencies are installed:

```shell
$ grind install
```

And then you can compiled run your project with the follwoing command:

```shell
$ grind run
```

## Building final Jar

Simply run:

```shell
grind build
```
