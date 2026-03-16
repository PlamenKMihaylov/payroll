# How to Run

## Requirements

- Java 8 or newer

## Compile the app

```sh
mkdir -p out
javac -d out $(find src -name '*.java' | sort)
```

## Run the app

```sh
java -cp out Main
```

## Run the tests

```sh
mkdir -p out-test
javac -d out-test $(find src test -name '*.java' | sort)
java -cp out-test PayrollProcessorTest
```

## What the app does

The sample `Main` program runs payroll for:

- `salaried` employees taxed at the system payroll tax rate
- `contractor` employees with overtime after 160 hours and no tax
- `hourly` employees with overtime after 160 hours and per-employee tax rates

The payroll engine returns a structured `PayrollReport`, and `Main` prints it using `PayrollPrinter`.
