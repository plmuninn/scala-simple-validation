---
layout: docs
title: "String validators"
permalink: docs/validators/string/
---
# String Validators

Validators for **String** are :

| **Name**          | **Usage in composition syntax** | **Usage in implicit syntax** | **Description**                                                                                                        | **Error codes**       |
|-------------------|---------------------------------|------------------------------|------------------------------------------------------------------------------------------------------------------------|-----------------------|
| Empty string      | `emptyString`                   | `.emptyString`               | Fails if string is not empty                                                                                           | `empty_expected`      |
| None empty string | `noneEmptyString`               | `.noneEmptyString`           | Fails if string is empty                                                                                               | `empty_field`         |
| None empty string | `emailString`                   | `.emailString`               | Fails if string is not email                                                                                           | `email_field`         |
| Complex password  | `complexPassword`               | `.complexPassword`           | Fails if string is not at least 8 characters long and contains 1 number and 1 special symbol and big and small letters | `password_complexity` |
| Minimal length    | `stringMinimalLength(8)`        | `.minimalLength(8)`          | Fails if length is greater or equal minimal value                                                                      | `minimal_length`      |
| Maximal length    | `stringMaximalLength(8)`        | `.maximalLength(8)`          | Fails if length is lower or equal maximal value                                                                        | `maximal_length`      |
| Expected length   | `stringLength(8)`               | `.expectedLength(8)`         | Fail if length is not exactly same value defined in function                                                           | `expected_length`     |
