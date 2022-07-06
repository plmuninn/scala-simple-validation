---
layout: docs
title: "Option validators"
permalink: docs/validators/option/
---
# Option Validators

Validators for **Option** are :

| **Name**              | **Usage in composition syntax** | **Usage in implicit syntax**                              | **Description**                                                                    | **Error codes**  |
|-----------------------|---------------------------------|-----------------------------------------------------------|------------------------------------------------------------------------------------|------------------|
| When value is defined | `ifDefined(<validators>)`       | `.ifDefined(<validators>)`                                | Runs passed to it validators when value is defined                                 | `N\A`            |
| Is defined and        | `N\A`                           | `.definedAnd(<validators>) or .notEmptyAnd(<validators>)` | Fails if value is not defined and runs passed to it validators if value is defined | `empty_field`    |
| If is defined         | `defined`                       | `.notEmpty or .isDefined`                                 | Fails if value is not defined                                                      | `empty_field`    |
| If is not defined     | `notDefined`                    | `.empty or .notDefined`                                   | Fails if value is defined                                                          | `empty_expected` |
