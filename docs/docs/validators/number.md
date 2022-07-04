---
layout: docs
title: "Number validators"
permalink: docs/validators/number/
---
# Number Validators

Validators for **Number** are :

| **Name**      | **Usage in composition syntax** | **Usage in implicit syntax** | **Description**                                             | **Error codes** |
|---------------|---------------------------------|------------------------------|-------------------------------------------------------------|-----------------|
| Minimal value | `minimalNumberValue(8)`         | `.min(8)`                    | Fails if value is greater or equal minimal value            | `minimal_value` |
| Maximal value | `maximalNumberValue(8)`         | `.max(8)`                    | Fails if value is lower or equal maximal value              | `maximal_value` |
| Equal value   | `numberEqual(8)`                | `.equal(8)`                  | Fail if value is not exactly same value defined in function | `equal_field`   |
