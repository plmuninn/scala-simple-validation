---
layout: docs
title: "Any type validators"
permalink: docs/validators/any/
---
# Any type Validators

Validators for **Any type** are :

| **Name**         | **Usage in composition syntax**                                     | **Usage in implicit syntax**                        | **Description**                                                           | **Error codes**    |
|------------------|---------------------------------------------------------------------|-----------------------------------------------------|---------------------------------------------------------------------------|--------------------|
| Values equal     | `equalValue(<value>)`                                               | `.equalValue(<value>)`                              | Runs validators on all collection elements and fails if any of them fails | `equal_field`      |
| Custom validator | `customValid(<error_code>, <error_reason>, <metadata>)(<function>)` | `.custom(<code>, <reason>, <metadata>)(<function>)` | Fails if function returns false                                           | `N\A`              |
| Fields equal     | `fieldsEqual`                                                       | `.fieldsEqual`                                      | Fails if tuple values is not equal                                        | `fields_not_equal` |
