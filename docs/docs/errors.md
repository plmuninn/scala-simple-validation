---
layout: docs
title: "Errors"
permalink: docs/errors/
---
# Errors

Error structure is reperesented by simple trait:
```scala
trait InvalidField {
  def field: String
  def reason: String
  def code: String
  def metadata: Map[String, String] = Map.empty
}
```
Where each filed represents:
* `field`  - name of validated field
* `reason`  - descriptive reason of failure
* `code`  - code of error
* `metadata`  - list of meta information of error ex. when number was expected to be lower then 10 you will find there what was expected value

# List of error codes

Here is build in list of errors codes with descriptions:

| **Code**                   | **Description**                                                  | **Data types**               |
|----------------------------|------------------------------------------------------------------|------------------------------|
| **equal_field**            | Value was not equal expected value                               | `any`                        |
| **empty_field**            | Value was expected to be not empty                               | `string``option``collection` |
| **empty_expected**         | Value was expected to be empty                                   | `string``option``collection` |
| **email_field**            | Value was expected to be email                                   | `string`                     |
| **min_count_symbols**      | Value was expected contains count of symbols                     | `string`                     |
| **min_count_digits**       | Value was expected contains count of digits                      | `string`                     |
| **min_count_lower_case**   | Value was contains count of lower case characters                | `string`                     |
| **min_count_upper_case**   | Value was expected count of upper case characters                | `string`                     |
| **fields_not_equal**       | Two fields were not equal                                        | `tuple`                      |
| **minimal_value**          | Value was expected to greater or equal minimal value             | `number`                     |
| **maximal_value**          | Value was expected to lower or equal maximal value               | `number`                     |
| **minimal_length**         | Value was expected to have length greater or equal minimal value | `string``collection`         |
| **maximal_length**         | Value was expected to have length lower or equal maximal value   | `string``collection`         |
| **expected_length**        | Value was expected to exact length                               | `string``collection`         |
| **key_missing**            | Value was expected to have specific key                          | `map`                        |
| **keys_missing**           | Value was expected to have specific list of keys                 | `map`                        |
| **value_contains**         | Value was expected to contain expected part of expected value    | `string`                     |
| **one_of_values_contains** | Value was expected to contain part of expected one of values     | `string`                     |
| **one_of_values_missing**  | Value was expected to be one of expected values                  | `string`                     |

