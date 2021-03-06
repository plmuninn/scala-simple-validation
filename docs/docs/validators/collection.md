---
layout: docs
title: "Collection validators"
permalink: docs/validators/collection/
---
# Collection Validators

Validators for **Collection** are :

| **Name**             | **Usage in composition syntax** | **Usage in implicit syntax** | **Description**                                                           | **Error codes**   |
|----------------------|---------------------------------|------------------------------|---------------------------------------------------------------------------|-------------------|
| Runs on all elements | `all(<validators>)`             | `.all(<validators>)`         | Runs validators on all collection elements and fails if any of them fails | `N\A`             |
| Is not empty         | `notEmptyCollection`            | `.notEmpty`                  | Fails if collection is empty                                              | `empty_field`     |
| Is empty             | `emptyCollection`               | `.empty`                     | Fails if collection is not empty                                          | `empty_expected`  |
| Minimal value        | `minimalLengthCollection(8)`    | `.minimalLength(8)`          | Fails if length of collection is greater or equal minimal value           | `minimal_length`  |
| Maximal value        | `maximalLengthCollection(8)`    | `.maximumLength(8)`          | Fails if length of collection is lower or equal maximal value             | `maximal_length`  |
| Exact length         | `exactLengthCollection(8)`      | `.expectedLength(8)`         | Fail if length of collection is not exactly same as defined in function   | `expected_length` |

# Collection types

Methods for collection comes in two versions - generic and specific. List above define generic validators. Specific one are defined using pattern of `validation + [?In] + type`. For example: `emptyList` or `allInVector`


Definitions are provided for:
* List
* Seq
* Set
* Vector
