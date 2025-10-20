# 🧪 TestTube

![Built with Grind](https://img.shields.io/badge/Built%20with-Grind-orange?style=for-the-badge&logo=rust)
![Java](https://img.shields.io/badge/Langauge-Java-blue?style=for-the-badge&logo=openjdk)
![License](https://img.shields.io/badge/License-MIT-blue.svg?style=for-the-badge)

The new [Grind](https://github.com/AnharHussainMiah/grind/) Java build tool that I've been developing requires a test runner. In a twist of poetic serendipity, what better way to build this test runner than using the very tool itself!

`TestTube` is a small Java CLI _"plugin"_ to be used by the `grind` Java build tool as the main test runner. `TestTube` leverages the existing JUnit framework API to do the heavy lifting as well as work with the standard testing infrastructure as used in the Java ecosystem.

## How to compile and Run

After cloning this repo down _(and assuming you have grind installed on your machine)_, first make sure that all the dependencies are installed:

```shell
$ grind install
```

And then you can compile run your project with the follwoing command:

```shell
$ grind run
```

## Building final Jar

Simply run:

```shell
grind build
```

## Example Output

When running this CLI assuming you have the classpaths setup correctly, it will look for all the test classes (compiled) inside of the `target/test/` folder, or you can pass down the classnames separated by a space:

```shell
Adding Dynamic Class -> com.example.HelloWorldTest
========== Test Summary ==========
Total tests:      1
✅ Passed:        1
❌ Failed:        0
⏩ Skipped:       0
🚫 Aborted:       0
==================================

✅ All tests passed.

📄 XML reports written to: /home/anhar/Documents/Projects/grind/HelloWorld/reports
```
