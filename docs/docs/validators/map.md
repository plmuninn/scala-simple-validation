---
layout: docs
title: "Map validators"
permalink: docs/validators/map/
---
# Map Validators

Validators for **Map** are :

| **Name**      | **Usage in composition syntax**      | **Usage in implicit syntax**          | **Description**                                                         | **Error codes**              |
|---------------|--------------------------------------|---------------------------------------|-------------------------------------------------------------------------|------------------------------|
| Contains key  | `containsKey("key")`                 | `.containsKey("key")`                 | Fails if map doesn't contains key                                       | `key_missing`                |
| Contains Keys | `containsKeys(List("key1", "key2"))` | `.containsKeys(List("key1", "key2"))` | Fails if map doesn't contains all keys                                  | `key_missing` `keys_missing` |
| Is not empty  | `notEmpty`                           | `.notEmpty`                           | Fails if collection is empty                                            | `empty_field`                |
| Is empty      | `empty`                              | `.empty`                              | Fails if collection is not empty                                        | `empty_expected`             |
| Minimal value | `minimalLength(8)`                   | `.minimalLength(8)`                   | Fails if length of collection is greater or equal minimal value         | `minimal_length`             |
| Maximal value | `maximumLength(8)`                   | `.maximumLength(8)`                   | Fails if length of collection is lower or equal maximal value           | `maximal_length`             |
| Exact length  | `expectedLength(8)`                  | `.expectedLength(8)`                  | Fail if length of collection is not exactly same as defined in function | `expected_length`            |
