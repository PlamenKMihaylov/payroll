# How to Run

## Requirements

- Java 17 or newer
- Maven 3.9 or newer for running tests

## Compile the app

```sh
mkdir -p out
javac -d out $(find src -name '*.java' | sort)
```

## Run the app

```sh
java -cp out app.Main
```

## Run the tests

```sh
mvn test
```

## What the app does

The sample `Main` program runs payroll for:

- `salaried` employees taxed at the system payroll tax rate
- `contractor` employees with overtime after 160 hours and no tax
- `hourly` employees with overtime after 160 hours and per-employee tax rates

The payroll engine returns a structured `PayrollReport`, and `app.Main` prints it using `app.PayrollPrinter`.
